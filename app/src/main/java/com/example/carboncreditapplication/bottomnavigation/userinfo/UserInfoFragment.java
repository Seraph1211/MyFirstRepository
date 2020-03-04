package com.example.carboncreditapplication.bottomnavigation.userinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.CarbonCreditsInfoBean;
import com.example.carboncreditapplication.beans.UserInfoBean;
import com.example.carboncreditapplication.bottomnavigation.BottomNavigationActivity;
import com.example.carboncreditapplication.bottomnavigation.userinfo.cardpackage.CardPackageActivity;
import com.example.carboncreditapplication.bottomnavigation.userinfo.merchant.MerchantActivity;
import com.example.carboncreditapplication.bottomnavigation.userinfo.team.TeamActivity;
import com.example.carboncreditapplication.utils.CustomProgressDialogUtils;
import com.example.carboncreditapplication.utils.HttpUtils;
import com.example.carboncreditapplication.utils.MySharedPreferencesUtils;
import com.example.carboncreditapplication.utils.NetworkUtil;
import com.example.carboncreditapplication.utils.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.support.constraint.Constraints.TAG;

public class UserInfoFragment extends Fragment implements View.OnClickListener{

    private TextView textUserName;  //用户昵称
    private CircleImageView imageHeadPortrait;  //用户头像
    private TextView textUserTotalCarbonCredits;  //总碳积分
    private TextView textUserAvailableCredits;    //可用碳积分
    private TextView textUserReadyToGetCredits;   //待领取的碳积分
    private Button buttonGetCredits;  //按钮，领取碳积分
    private View view;
    private ImageView imageJumpToCardPackage;  //卡包入口
    private ImageView imageJumpToMerchantIn;  //商家入口
    private ImageView imageJumpToHelpInfo;  //帮助信息入口
    private ImageView imageJumpToCallUs;  //联系方式入口
    private ImageView imageJumpToTeam;  //队伍入口

    private ConstraintLayout clCardPackage;
    private ConstraintLayout clMerchant;
    private ConstraintLayout clTeam;
    private ConstraintLayout clCallUs;
    private ConstraintLayout clExplain;

    private int availableCarbonCredits=0;
    private int totalCarbonCredits=0;
    private int readyToGetCarbonCredits=0;
    private String userNickname;
    private String userHeadPortrait;

    private UserInfoBean userInfoBean = new UserInfoBean();  //用户信息
    private CarbonCreditsInfoBean carbonCreditsInfoBean = new CarbonCreditsInfoBean();  //用户碳积分信息


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_info, container, false);

        initView();

        initData();

        return view;
    }

    public void queryUserInfo(){
        CustomProgressDialogUtils.showLoading(getContext());

      HttpUtils.getInfo(HttpUtils.userInfoUrl, new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
               ToastUtils.showToast(getContext(), "服务器错误");
               Log.d(TAG, "onFailure: 从服务器读取用户信息失败！");
           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
               CustomProgressDialogUtils.stopLoading();

               int code = response.code();
               Log.d(TAG, "onResponse: code="+code);

               if(code==200){  //code==200表示服务器成功处理了请求
                   String responseContent = response.body().string();
                   Log.i(TAG, "onResponse: userInfo's responseContent: "+responseContent);

                   userInfoBean = new Gson().fromJson(responseContent, UserInfoBean.class);
                   String msgCode = userInfoBean.getMsg_code();
                   Log.d(TAG, "onResponse: msgCode="+msgCode);

                   if(msgCode.equals("0000")){ //信息获取成功
                       getActivity().runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               textUserName.setText(userInfoBean.getResultBean().getNickname());  //设置用户昵称
                               Glide.with(UserInfoFragment.this)
                                       .load(userInfoBean.getResultBean().getUserImagePath())
                                       .error(R.drawable.error)
                                       .placeholder(R.drawable.girl)
                                       .into(imageHeadPortrait);
                           }
                       });
                   }

               }else {
                   ToastUtils.showToast(getContext(), "服务器故障");
               }

           }
       });
    }

    public void queryCarbonCreditsInfo(){
        HttpUtils.getInfo(HttpUtils.carbonCreditsInfoUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: 从服务器读取碳积分信息失败！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                Log.d(TAG, "onResponse: code="+code);

                if(code==200){  //服务器成功处理请求
                    String responseContent = response.body().string();
                    Log.d(TAG, "onResponse: CarbonCreditsInfo: "+responseContent);

                    carbonCreditsInfoBean = new Gson().fromJson(responseContent, CarbonCreditsInfoBean.class);
                    Log.d(TAG, "onResponse: CarbonCreditsInfoBean: "+carbonCreditsInfoBean.toString());

                    String msgCode = carbonCreditsInfoBean.getMsg_code();
                    Log.d(TAG, "onResponse: CarbonCreditsBean's msgCode="+msgCode);

                    if(msgCode.equals("0000")){  //成功获取信息
                        availableCarbonCredits = carbonCreditsInfoBean.getResultBean().getCarbonCreditsAvailable();
                        totalCarbonCredits = carbonCreditsInfoBean.getResultBean().getCarbonCreditsTotal();
                        readyToGetCarbonCredits = carbonCreditsInfoBean.getResultBean().getCarbonCreditsToday();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textUserTotalCarbonCredits.setText(String.valueOf(carbonCreditsInfoBean.getResultBean().getCarbonCreditsTotal()));  //设置用户总碳积分
                                textUserReadyToGetCredits.setText(String.valueOf(carbonCreditsInfoBean.getResultBean().getCarbonCreditsToday()));  //设置用户待领取的碳积分
                                textUserAvailableCredits.setText(String.valueOf(carbonCreditsInfoBean.getResultBean().getCarbonCreditsAvailable()));  //设置用户可用碳积分
                            }
                        });
                    }

                }else {
                    ToastUtils.showToast(getContext(), "服务器错误");
                }
            }
        });
    }

    //初始化控件，目前后台有问题，拿不到数据
    public void initView(){
        //各控件获取实例
        imageHeadPortrait = view.findViewById(R.id.head_portrait);
        textUserName = view.findViewById(R.id.text_user_name);
        textUserTotalCarbonCredits = view.findViewById(R.id.text_total_credit);
        textUserAvailableCredits = view.findViewById(R.id.text_available_credit);
        textUserReadyToGetCredits = view.findViewById(R.id.text_readyToGet_credit);
        buttonGetCredits = view.findViewById(R.id.buttonGetCredits);
        imageJumpToCallUs = view.findViewById(R.id.imageJumpToCallUs);
        imageJumpToCardPackage = view.findViewById(R.id.imageJumpToCardPackage);
        imageJumpToHelpInfo = view.findViewById(R.id.imageJumpToHelp);
        imageJumpToMerchantIn = view.findViewById(R.id.imageJumpToMerchant);
        imageJumpToTeam = view.findViewById(R.id.imageJumpToTeam);

        clCallUs = view.findViewById(R.id.clCallUs);
        clCardPackage = view.findViewById(R.id.clCardPackage);
        clExplain = view.findViewById(R.id.clExplain);
        clMerchant = view.findViewById(R.id.clMerchant);
        clTeam = view.findViewById(R.id.clTeam);

        //注册监听器
        buttonGetCredits.setOnClickListener(this);
        clTeam.setOnClickListener(this);
        clMerchant.setOnClickListener(this);
        clExplain.setOnClickListener(this);
        clCardPackage.setOnClickListener(this);
        clCallUs.setOnClickListener(this);


        //userInfoBean = new UserInfoBean();
        //carbonCreditsInfoBean = new CarbonCreditsInfoBean();
    }

    //初始化用户数据
    public void initData(){
        //通过sp从本地获取用户信息以及碳积分信息
        totalCarbonCredits = MySharedPreferencesUtils.getInt(getContext(), "carbon_credits_total");
        availableCarbonCredits = MySharedPreferencesUtils.getInt(getContext(), "carbon_credits_available");
        readyToGetCarbonCredits = MySharedPreferencesUtils.getInt(getContext(), "carbon_credits_today");
        userNickname = MySharedPreferencesUtils.getString(getContext(), "nickname");
        //头像 userHeadPortrait = MySharedPreferencesUtils.getString(getContext(), "user_image_path");

        if(totalCarbonCredits==-2 || availableCarbonCredits==-2 || readyToGetCarbonCredits==-2
                || userNickname.equals("empty") /*|| userHeadPortrait.equals("empty")*/){ //以上任意一项没从本地拿到数据，则全部从网络访问数据

            if(!NetworkUtil.isNetworkAvailable(getContext())){
                ToastUtils.showToast(getContext(), "未连接网络");
                return;
            }
            queryUserInfo();
            queryCarbonCreditsInfo();
        }else{
            //更新UI
            textUserTotalCarbonCredits.setText(String.valueOf(totalCarbonCredits));
            textUserAvailableCredits.setText(String.valueOf(availableCarbonCredits));
            textUserReadyToGetCredits.setText(String.valueOf(readyToGetCarbonCredits));
            textUserName.setText(userNickname);
            Glide.with(UserInfoFragment.this)
                    .load(userHeadPortrait)
                    .error(R.drawable.error)
                    .placeholder(R.drawable.girl)
                    .into(imageHeadPortrait);
        }
    }

    /**
     * 一键领取待领取的碳积分
     */
    public void getCredits(){
        HttpUtils.getInfo(HttpUtils.getCarbonCreditsUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showToast(getContext(), "服务器故障");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                Log.d(TAG, "getCredits: code="+code);

                if(code==200){  //服务器成功处理请求
                    String responseContent = response.body().string();
                    Log.d(TAG, "getCredits responseContent="+responseContent);

                    String msgCode = "";
                    String msgMsg = "";
                    try {
                        JSONObject jsonObject = new JSONObject(responseContent);
                        msgCode = jsonObject.getString("msg_code");
                        Log.d(TAG, "getCredits msgCode="+msgCode);
                        msgMsg = jsonObject.getString("msg_msg");
                        Log.d(TAG, "getCredits msgMsg="+msgMsg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(msgCode.equals("0000")){
                        availableCarbonCredits += readyToGetCarbonCredits;
                        totalCarbonCredits += readyToGetCarbonCredits;
                        readyToGetCarbonCredits = 0;
                        //更新UI
                        textUserAvailableCredits.setText(String.valueOf(availableCarbonCredits));
                        textUserReadyToGetCredits.setText(String.valueOf(readyToGetCarbonCredits));
                        textUserTotalCarbonCredits.setText(String.valueOf(totalCarbonCredits));
                /*
                //更新bean
                carbonCreditsInfoBean.getResultBean().setCarbonCreditsAvailable(availableCarbonCredits);
                carbonCreditsInfoBean.getResultBean().setCarbonCreditsToday(0);
                carbonCreditsInfoBean.getResultBean().setCarbonCreditsTotal(totalCarbonCredits);
                */
                        //更新sp中存储的数据
                        MySharedPreferencesUtils.putInt(getContext(), "carbon_credits_total", totalCarbonCredits);
                        MySharedPreferencesUtils.putInt(getContext(), "carbon_credits_available", availableCarbonCredits);
                        MySharedPreferencesUtils.putInt(getContext(), "carbon_credits_today", readyToGetCarbonCredits);

                        ToastUtils.showToast(getContext(), "领取成功！");
                    }else{
                        ToastUtils.showToast(getContext(), msgMsg);
                    }
                }else {
                    ToastUtils.showToast(getContext(), "服务器错误");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonGetCredits:{
                if(readyToGetCarbonCredits!=0){
                    getCredits();
                }

                break;
            }
            case R.id.clCardPackage:{
                Toast.makeText(view.getContext(), "Card Package !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(view.getContext(), CardPackageActivity.class));
                break;
            }
            case R.id.clExplain:{
                Toast.makeText(view.getContext(), "Help Info !", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.clCallUs:{
                Toast.makeText(view.getContext(), "Call Us !", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.clMerchant:{
                Toast.makeText(view.getContext(), "Merchant In !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), MerchantActivity.class));
                break;
            }
            case R.id.clTeam:{
                Toast.makeText(view.getContext(), "Team !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), TeamActivity.class));
            }
            default:break;
        }
    }

}
