package com.example.carboncreditapplication.bottomnavigation.home.sign;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.CarbonCreditsInfoBean;
import com.example.carboncreditapplication.beans.UserInfoBean;
import com.example.carboncreditapplication.bottomnavigation.home.store.Store2Activity;
import com.example.carboncreditapplication.utils.HttpUtils;
import com.example.carboncreditapplication.utils.MySharedPreferencesUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "SignInActivity";

    private ImageView[] checkImageList = new ImageView[7]; //存放7张check图片的数组，累计签到n次，点亮n张图片
    private Button buttonSignIn;  //签到按钮
    private Button buttonToStore;  //跳转至积分商城的按钮
    private ImageButton buttonBack; //返回
    private TextView textAvailableCredits;  //可用碳积分
    private TextView textSignInNum;  //连续签到天数

    private int signInNumber = 0;  //累计签到天数
    private int signInToday = 0; //今日是否签到
    private int carbonCreditsAvailable = -1;  //可用碳积分

    //签到奖励，还需考虑后续的更新
    private final int promotionalCrditsForOneDay = 0;
    private final int promotionalCrditsForThreeDay = 0;
    private final int promotionalCrditsForSevenDay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();

    }

    public void init(){
        buttonSignIn = findViewById(R.id.buttonSignIn);
        //buttonToStore = findViewById(R.id.buttonToStore);
        buttonBack = findViewById(R.id.buttonSignInBack);
        //textAvailableCredits = findViewById(R.id.textAvailableCreditsSignIn);
        textSignInNum = findViewById(R.id.textSignInNumber);

        checkImageList[0] = findViewById(R.id.imageCheck1);
        checkImageList[1] = findViewById(R.id.imageCheck2);
        checkImageList[2] = findViewById(R.id.imageCheck3);
        checkImageList[3] = findViewById(R.id.imageCheck4);
        checkImageList[4] = findViewById(R.id.imageCheck5);
        checkImageList[5] = findViewById(R.id.imageCheck6);
        checkImageList[6] = findViewById(R.id.imageCheck7);

        buttonSignIn.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

       initData();

    }

    public void initData(){
        carbonCreditsAvailable = MySharedPreferencesUtils.getInt(SignInActivity.this, "carbon_credits_available");
        signInNumber = MySharedPreferencesUtils.getInt(SignInActivity.this, "sign_in_num");
        signInToday = MySharedPreferencesUtils.getInt(SignInActivity.this, "sign_in_today");


        /* 获取可用碳积分
        if(carbonCreditsAvailable != -2){
            textAvailableCredits.setText(String.valueOf(carbonCreditsAvailable));
        }else {
            queryCarbonCreditsInfo();
        }
         */

        if(signInNumber==-2 || signInToday==-2){
            queryUserInfo();
        }else {
            textSignInNum.setText(signInNumber+"天");
        }
        reloadCheckImages();
    }

    public void reloadCheckImages(){
        if(signInNumber == 0){
            for (int i=0; i<checkImageList.length; i++){
                checkImageList[i].setImageResource(R.drawable.check_gray);
            }
        }else {
            for(int i=0; i<signInNumber; i++){
                checkImageList[i].setImageResource(R.drawable.check_blue);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSignIn:{
                Toast.makeText(SignInActivity.this, "you clicked buttonSignIn", Toast.LENGTH_SHORT).show();

                //签到满7天后重置签到天数的问题未考虑,未告知服务器今日已签到
                if(signInNumber>-1 && signInNumber<7 && signInToday!=1){  //如果
                    signInNumber++;
                    signInToday = 1;
                    reloadCheckImages();
                    textSignInNum.setText(signInNumber+"天");
                    MySharedPreferencesUtils.putInt(SignInActivity.this,"sign_in_num", signInNumber);
                    MySharedPreferencesUtils.putInt(SignInActivity.this, "sign_in_today", signInToday);
                    Toast.makeText(SignInActivity.this, "签到成功！", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SignInActivity.this, "今日已签到！", Toast.LENGTH_SHORT).show();
                }

                break;
            }
            case R.id.buttonSignInBack:{
                finish();
                break;
            }

            default:break;
        }
    }

    //从服务端获取用户已连续签到的天数、今日是否已经签到
    public void queryUserInfo(){
        HttpUtils.getInfo(HttpUtils.userInfoUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();
                Log.d(TAG, "onResponse: responseContent="+responseContent);
                UserInfoBean userInfoBean = new Gson().fromJson(responseContent, UserInfoBean.class);
                if(userInfoBean!=null){
                    MySharedPreferencesUtils.saveUserInfo(SignInActivity.this, userInfoBean.getResultBean());
                    //获取已连续签到天数、今日是否已签到
                    signInNumber =  userInfoBean.getResultBean().getSignInNumber();
                    signInToday = userInfoBean.getResultBean().getSignInToday();
                    textSignInNum.setText(String.valueOf(signInNumber));
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignInActivity.this, "获取签到信息失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    //从服务器获取用户可用碳积分
    public void queryCarbonCreditsInfo(){
        HttpUtils.getInfo(HttpUtils.carbonCreditsInfoUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();
                Log.d(TAG, "onResponse: responseContent="+responseContent);

                CarbonCreditsInfoBean carbonCreditsInfoBean = new Gson().fromJson(responseContent, CarbonCreditsInfoBean.class);

                if(carbonCreditsInfoBean!=null){
                    MySharedPreferencesUtils.saveUserInfo(SignInActivity.this, carbonCreditsInfoBean.getResultBean());  //本地存储4
                    carbonCreditsAvailable = carbonCreditsInfoBean.getResultBean().getCarbonCreditsAvailable();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(carbonCreditsAvailable!=-2){
                                textAvailableCredits.setText(String.valueOf(carbonCreditsAvailable));
                            }

                        }
                    });
                }else {
                    Log.d(TAG, "onResponse: 碳积分信息获取失败");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignInActivity.this, "碳积分信息获取失败！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

}
