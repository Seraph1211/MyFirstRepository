package com.example.carboncreditapplication.bottomnavigation.userinfo;

import android.app.Activity;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.CarbonCreditsInfoBean;
import com.example.carboncreditapplication.beans.UserInfoBean;
import com.example.carboncreditapplication.utils.HttpUtils;
import com.google.gson.Gson;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.support.constraint.Constraints.TAG;

public class UserInfoFragment extends Fragment {
    private CircleImageView headPortrait;
    private TextView userName;
    private TextView userTotalCarbonCredits;  //总碳积分
    private TextView userAvailableCredits;    //可用碳积分
    private TextView userReadyToGetCredits;   //待领取的碳积分
    private Button buttonGetCredits;  //按钮，领取碳积分
    private UserInfoBean userInfoBean;
    private CarbonCreditsInfoBean carbonCreditsInfoBean;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_info, container, false);

        headPortrait = view.findViewById(R.id.head_portrait);
        userName = view.findViewById(R.id.text_user_name);
        userTotalCarbonCredits = view.findViewById(R.id.text_total_credit);
        userAvailableCredits = view.findViewById(R.id.text_available_credit);
        userReadyToGetCredits = view.findViewById(R.id.text_readyToGet_credit);
        buttonGetCredits = view.findViewById(R.id.buttonGetCredits);

        userInfoBean = new UserInfoBean();
        carbonCreditsInfoBean = new CarbonCreditsInfoBean();

       buttonGetCredits.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               queryCarbonCreditsInfo(view);
               queryUserInfo(view);
               //setUI();

           }
       });

        return view;
    }

    public void queryUserInfo(final View view){
      HttpUtils.getInfo(HttpUtils.userInfoUrl, 1, new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
               Log.d(TAG, "onFailure: 从服务器读取用户信息失败！");
           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
               String responseContent = response.body().string();
               Log.d(TAG, "onResponse: userInfo's responseContent: "+responseContent);

               if(!TextUtils.isEmpty(responseContent)){
                   userInfoBean = new Gson().fromJson(responseContent, UserInfoBean.class);
                   Log.d(TAG, "onResponse: UserInfoBean: "+userInfoBean.toString());

                   Activity activity = (Activity)view.getContext();
                   activity.runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           userName.setText(userInfoBean.getResultBean().getNickname());  //设置用户昵称
                           Glide.with(UserInfoFragment.this)
                                   .load(userInfoBean.getResultBean().getUserImagePath())
                                   .error(R.drawable.error)
                                   .placeholder(R.drawable.girl)
                                   .into(headPortrait);
                       }
                   });


               }else{
                   Log.d(TAG, "onResponse: 读取到的用户信息为空！");
               }
           }
       });
    }

    public void queryCarbonCreditsInfo(final View view){
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
                    Log.d(TAG, "onResponse: CarbonCreditsInfoBean: "+userInfoBean.toString());

                    Activity activity = (Activity)view.getContext();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            userTotalCarbonCredits.setText(String.valueOf(carbonCreditsInfoBean.getResultBean().getCarbonCreditsTotal()));  //设置用户总碳积分
                            userReadyToGetCredits.setText(String.valueOf(carbonCreditsInfoBean.getResultBean().getCarbonCreditsToday()));  //设置用户待领取的碳积分
                            userAvailableCredits.setText(String.valueOf(carbonCreditsInfoBean.getResultBean().getCarbonCreditsAvailable()));  //设置用户可用碳积分
                        }
                    });

                }else{
                    Log.d(TAG, "onResponse: 读取到的碳积分信息为空！");
                }
            }
        });
    }

    public void setUI(){
        userName.setText(userInfoBean.getResultBean().getNickname());  //设置用户昵称
        userTotalCarbonCredits.setText(carbonCreditsInfoBean.getResultBean().getCarbonCreditsTotal());  //设置用户总碳积分
        userReadyToGetCredits.setText(carbonCreditsInfoBean.getResultBean().getCarbonCreditsToday());  //设置用户待领取的碳积分
        userAvailableCredits.setText(carbonCreditsInfoBean.getResultBean().getCarbonCreditsAvailable());  //设置用户可用碳积分
    }

    public void onClickInMyCreditsFragment(View view){
        switch (view.getId()){
            case R.id.buttonGetCredits:{
                //queryUserInfo(view);
                break;
            }
            default:break;
        }
    }
}