package com.example.carboncreditapplication.bottomnavigation.userinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.example.carboncreditapplication.bottomnavigation.userinfo.cardpackage.CardPackageActivity;
import com.example.carboncreditapplication.bottomnavigation.userinfo.merchant.MerchantActivity;
import com.example.carboncreditapplication.bottomnavigation.userinfo.team.TeamActivity;
import com.example.carboncreditapplication.utils.HttpUtils;
import com.example.carboncreditapplication.utils.MySharedPreferencesUtils;
import com.google.gson.Gson;

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

        //initData();

        return view;
    }

    public void queryUserInfo(){
      HttpUtils.getInfo(HttpUtils.userInfoUrl, 1, new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
               Log.d(TAG, "onFailure: 从服务器读取用户信息失败！");
           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
               String responseContent = response.body().string();
               Log.i(TAG, "onResponse: userInfo's responseContent: "+responseContent);

               if(!TextUtils.isEmpty(responseContent)){
                   userInfoBean = new Gson().fromJson(responseContent, UserInfoBean.class);
                   Log.i(TAG, "onResponse: UserInfoBean: "+userInfoBean.toString());

                   Activity activity = (Activity)view.getContext();
                   activity.runOnUiThread(new Runnable() {
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


               }else{
                   Log.d(TAG, "onResponse: 读取到的用户信息为空！");
               }
           }
       });
    }

    public void queryCarbonCreditsInfo(){
        HttpUtils.getInfo(HttpUtils.carbonCreditsInfoUrl, 1, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: 从服务器读取碳积分信息失败！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();
                Log.d(TAG, "onResponse: carbonCreditsInfo's responseContent: "+responseContent);

                if(!TextUtils.isEmpty(responseContent)){
                    carbonCreditsInfoBean = new Gson().fromJson(responseContent, CarbonCreditsInfoBean.class);
                    Log.d(TAG, "onResponse: CarbonCreditsInfoBean: "+carbonCreditsInfoBean.toString());

                    if(carbonCreditsInfoBean != null){
                        availableCarbonCredits = carbonCreditsInfoBean.getResultBean().getCarbonCreditsAvailable();
                        totalCarbonCredits = carbonCreditsInfoBean.getResultBean().getCarbonCreditsTotal();
                        readyToGetCarbonCredits = carbonCreditsInfoBean.getResultBean().getCarbonCreditsToday();

                        Activity activity = (Activity)view.getContext();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textUserTotalCarbonCredits.setText(String.valueOf(carbonCreditsInfoBean.getResultBean().getCarbonCreditsTotal()));  //设置用户总碳积分
                                textUserReadyToGetCredits.setText(String.valueOf(carbonCreditsInfoBean.getResultBean().getCarbonCreditsToday()));  //设置用户待领取的碳积分
                                textUserAvailableCredits.setText(String.valueOf(carbonCreditsInfoBean.getResultBean().getCarbonCreditsAvailable()));  //设置用户可用碳积分
                            }
                        });
                    }else {
                        Log.d(TAG, "onResponse: carbonCreditsBean="+carbonCreditsInfoBean.toString());
                    }

                }else{
                    Log.d(TAG, "onResponse: 读取到的碳积分信息为空！");
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

        //注册监听器
        buttonGetCredits.setOnClickListener(this);
        imageJumpToMerchantIn.setOnClickListener(this);
        imageJumpToHelpInfo.setOnClickListener(this);
        imageJumpToCardPackage.setOnClickListener(this);
        imageJumpToCallUs.setOnClickListener(this);
        imageJumpToTeam.setOnClickListener(this);

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
        userHeadPortrait = MySharedPreferencesUtils.getString(getContext(), "user_image_path");

        if(totalCarbonCredits==-2 || availableCarbonCredits==-2 || readyToGetCarbonCredits==-2
                || userNickname.equals("empty") || userHeadPortrait.equals("empty")){ //以上任意一项没从本地拿到数据，则全部从网络访问数据
            queryCarbonCreditsInfo();
            queryUserInfo();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonGetCredits:{  //一键领取,接口尚未给出！！！
                Toast.makeText(view.getContext(),"Get Credits !", Toast.LENGTH_SHORT).show();
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
                break;
            }
            case R.id.imageJumpToCardPackage:{
                Toast.makeText(view.getContext(), "Card Package !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(view.getContext(), CardPackageActivity.class));
                break;
            }
            case R.id.imageJumpToHelp:{
                Toast.makeText(view.getContext(), "Help Info !", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.imageJumpToCallUs:{
                Toast.makeText(view.getContext(), "Call Us !", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.imageJumpToMerchant:{
                Toast.makeText(view.getContext(), "Merchant In !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), MerchantActivity.class));
                break;
            }
            case R.id.imageJumpToTeam:{
                Toast.makeText(view.getContext(), "Team !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), TeamActivity.class));
            }
            default:break;
        }
    }

}
