package com.example.carboncreditapplication.bottomnavigation.home;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.CarbonCreditsInfoBean;
import com.example.carboncreditapplication.beans.UserInfoBean;
import com.example.carboncreditapplication.bottomnavigation.BottomNavigationActivity;
import com.example.carboncreditapplication.utils.HttpUtils;
import com.example.carboncreditapplication.utils.MySharedPreferencesUtils;
import com.example.carboncreditapplication.utils.NetworkUtil;
import com.example.carboncreditapplication.utils.ToastUtils;
import com.example.carboncreditapplication.utils.UserInfo;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.support.constraint.Constraints.TAG;


public class HomeFragment extends Fragment {
    private List<Integer> imageUrlList;
    private List<String> titleList;
    private Banner banner;

    View view;
    private BottomNavigationActivity activity;

    private SwipeRefreshLayout srHome;

    private TextView textSubwayMileagesTotal;
    private TextView textSubwayMileagesYesterday;
    private TextView textBusMileagesTotal;
    private TextView textBusMileagesYesterday;
    private TextView textBikeMileagesTotal;
    private TextView textBikeMileagesYesterday;
    private TextView textWalkMileagesTotal;
    private TextView textWalkMileagesYesterday;

    private int subwayMileagesTotal = 0;
    private int subwayMileagesYesterday = 0;
    private int busMileagesTotal = 0;
    private int busMileagesYesterday = 0;
    private int bikeMileagesTotal = 0;
    private int bikeMileagesYesterday = 0;
    private int walkMileagesTotal = 0;  //总步行里程
    private int walkMileagesYesterday = 0;  //昨日步数

    private boolean hasUserInfo = false;
    private boolean hasCreditsInfo = false;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initBanner();
        initView();
        initData();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    public void initView(){
        activity = (BottomNavigationActivity)getActivity();
        textSubwayMileagesTotal = view.findViewById(R.id.textSubwayMileagesTotal);
        textSubwayMileagesYesterday = view.findViewById(R.id.textSubwayMileagesYesterday);
        textBusMileagesTotal = view.findViewById(R.id.textBusMileagesTotal);
        textBusMileagesYesterday = view.findViewById(R.id.textBusMileagesYesterday);
        textBikeMileagesTotal = view.findViewById(R.id.textBikeMileagesTotal);
        textBikeMileagesYesterday = view.findViewById(R.id.textBikeMileagesYesterday);
        textWalkMileagesTotal = view.findViewById(R.id.textWalkMileagesTotal);
        textWalkMileagesYesterday = view.findViewById(R.id.textWalkMileagesYesterday);

        initSwipeRefresh();
    }

    public void initSwipeRefresh(){
        srHome = view.findViewById(R.id.sr_home);

        /*
        srHome.post(new Runnable() {
            @Override
            public void run() {
                srHome.setRefreshing(true);
            }
        });
         */

        srHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryCarbonCreditsInfo();
                queryUserInfo();
            }
        });
    }

    public void initData(){
        hasUserInfo = MySharedPreferencesUtils.getBoolean(getContext(), "has_user_info");
        hasCreditsInfo = MySharedPreferencesUtils.getBoolean(getContext(), "has_credits_info");
        Log.d(TAG, "initData: hasUserInfo="+hasUserInfo+" hasCreditsInfo="+hasCreditsInfo);
        if(!hasUserInfo){  //本地没有保存User模块的信息
            srHome.post(new Runnable() {
                @Override
                public void run() {
                    srHome.setRefreshing(true);
                }
            });
            queryUserInfo();
        }
        if (!hasCreditsInfo){ //本地没有保存Credits模块的信息（碳积分、里程）
            srHome.post(new Runnable() {
                @Override
                public void run() {
                    srHome.setRefreshing(true);
                }
            });
            queryCarbonCreditsInfo();
        }else {
            getMileageInfoFromSP();
            reloadMileageInfo();
        }
    }

    public void initBanner(){
        imageUrlList = new ArrayList<>();
        titleList = new ArrayList<>();

        imageUrlList.add(R.drawable.banner_1);
        imageUrlList.add(R.drawable.banner_2);
        imageUrlList.add(R.drawable.banner_3);
        imageUrlList.add(R.drawable.banner_4);

        titleList.add("低碳环保，新能源出行");
        titleList.add("健康生活，低碳出行");
        titleList.add("爱护地球，人人有责");
        titleList.add("3.30 地球一小时");

        banner = view.findViewById(R.id.bannerHome);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setImageLoader(new MyLoader());
        banner.setImages(imageUrlList);
        banner.setBannerTitles(titleList);
        banner.setBannerAnimation(Transformer.Default);
        banner.setDelayTime(2000);  //切换频率
        banner.isAutoPlay(true);  //自动启动
        banner.setIndicatorGravity(BannerConfig.CENTER);  //位置设置
        banner.start();

    }

    /**
     * 结束刷新
     */
    public void finishSwipeRefresh(){
        if(srHome.isRefreshing()){
            srHome.post(new Runnable() {
                @Override
                public void run() {
                    srHome.setRefreshing(false);
                }
            });

        }
    }

    /**
     * 从服务器获取用户信息并保存在本地
     */
    public void queryUserInfo(){
        //CustomProgressDialogUtils.showLoading(BottomNavigationActivity.this);
        if(!NetworkUtil.isNetworkAvailable(getContext())){
            ToastUtils.showToast(getContext(), "未连接网络");
            finishSwipeRefresh();
            return;
        }

        HttpUtils.getInfo(HttpUtils.userInfoUrl+"?user_id="+UserInfo.userId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showToast(getContext(), "服务器错误");
                Log.d(TAG, "onFailure: 从服务器读取用户信息失败！");
                finishSwipeRefresh();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                finishSwipeRefresh();

                int code = response.code();
                Log.d(TAG, "UserInfo: code="+code);

                if(code==200){  //服务器成功处理请求
                    String responseContent = response.body().string();
                    Log.d(TAG, "UserInfo responseContent="+responseContent);

                    UserInfoBean userInfoBean = new Gson().fromJson(responseContent, UserInfoBean.class);
                    String msgCode = userInfoBean.getMsg_code();

                    if(msgCode.equals("0000")){
                        MySharedPreferencesUtils.saveUserInfo(getContext(), userInfoBean.getResultBean());
                        MySharedPreferencesUtils.putBoolean(getContext(), "has_user_info", true);
                    }else{
                        ToastUtils.showToast(getContext(), "获取数据失败");
                    }
                }else{
                    ToastUtils.showToast(getContext(), "服务器故障");
                }

            }
        });
    }

    /**
     * 从服务器获取碳积分信息并保存在本地
     */
    public void queryCarbonCreditsInfo(){
        // CustomProgressDialogUtils.showLoading(BottomNavigationActivity.this);
        if(!NetworkUtil.isNetworkAvailable(getContext())){
            ToastUtils.showToast(getContext(), "未连接网络");
            finishSwipeRefresh();
            return;
        }

        HttpUtils.getInfo(HttpUtils.carbonCreditsInfoUrl+"&mileage_walk_today="+MySharedPreferencesUtils.getInt(getContext(), "step_count_today")*0.00065,
                new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //CustomProgressDialogUtils.stopLoading();
                        Toast.makeText(activity, "服务器故障", Toast.LENGTH_SHORT).show();
                    }
                });
                finishSwipeRefresh();
                Log.d(TAG, "onFailure: 从服务器读取碳积分信息失败！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                finishSwipeRefresh();

                int code = response.code();
                Log.d(TAG, "CarbonCredits code="+code);

                if(code==200){  //服务器成功处理请求
                    String responseContent = response.body().string();
                    Log.d(TAG, "CarbonCreditsInfoBean:"+responseContent);

                    CarbonCreditsInfoBean carbonCreditsInfoBean = new Gson().fromJson(responseContent, CarbonCreditsInfoBean.class); //即使没拿到数据bean也不会为Null，所以不必判空，但是当没拿到东西或者拿到一些奇怪的东西时的处理还没写好

                    String msgCode = carbonCreditsInfoBean.getMsg_code();
                    if(msgCode.equals("0000")){  //获取数据成功
                        MySharedPreferencesUtils.saveUserInfo(getContext(), carbonCreditsInfoBean.getResultBean());
                        MySharedPreferencesUtils.putBoolean(getContext(), "has_credits_info", true);
                        reloadMileageInfo();
                    }else {  //失败，则提示用户错误信息
                        ToastUtils.showToast(getContext(), carbonCreditsInfoBean.getMsg_message());
                    }
                }else{
                    ToastUtils.showToast(getContext(), "服务器错误");
                }
            }
        });
    }

    public void reloadMileageInfo(){
        textSubwayMileagesTotal.setText(subwayMileagesTotal+"");
        textSubwayMileagesYesterday.setText(subwayMileagesYesterday+"");
        textBusMileagesTotal.setText(busMileagesTotal+"");
        textBusMileagesYesterday.setText(busMileagesYesterday+"");
        textBikeMileagesTotal.setText(bikeMileagesTotal+"");
        textBikeMileagesYesterday.setText(bikeMileagesYesterday+"");
        textWalkMileagesTotal.setText(walkMileagesTotal+"");
        textWalkMileagesYesterday.setText(walkMileagesYesterday+"");

        finishSwipeRefresh();  //结束SwipeRefresh的刷新
    }

    public void getMileageInfoFromSP(){
        subwayMileagesTotal = MySharedPreferencesUtils.getInt(getContext(), "mileage_subway_total");
        subwayMileagesYesterday = MySharedPreferencesUtils.getInt(getContext(), "mileage_subway_yesterday");
        busMileagesTotal = MySharedPreferencesUtils.getInt(getContext(), "mileage_bus_total");
        busMileagesYesterday = MySharedPreferencesUtils.getInt(getContext(), "mileage_bus_yesterday");
        bikeMileagesTotal = MySharedPreferencesUtils.getInt(getContext(), "mileage_bike_total");
        bikeMileagesYesterday = MySharedPreferencesUtils.getInt(getContext(), "mileage_bike_yesterday");
        walkMileagesTotal = MySharedPreferencesUtils.getInt(getContext(), "mileage_walk_total");
        walkMileagesYesterday = MySharedPreferencesUtils.getInt(getContext(), "mileage_walk_yesterday");
    }

    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(getActivity())
                    .load(path).
                    into(imageView);
        }
    }

}