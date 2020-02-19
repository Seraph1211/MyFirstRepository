package com.example.carboncreditapplication.bottomnavigation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.CarbonCreditsInfoBean;
import com.example.carboncreditapplication.beans.UserInfoBean;
import com.example.carboncreditapplication.bottomnavigation.game.GameFragment;
import com.example.carboncreditapplication.bottomnavigation.home.HomeFragment;
import com.example.carboncreditapplication.bottomnavigation.home.rank.MonthRankFragment;
import com.example.carboncreditapplication.bottomnavigation.home.report.MonthReportActivity;
import com.example.carboncreditapplication.bottomnavigation.home.sign.SignInActivity;
import com.example.carboncreditapplication.bottomnavigation.home.store.Store2Activity;
import com.example.carboncreditapplication.bottomnavigation.userinfo.UserInfoFragment;
import com.example.carboncreditapplication.bottomnavigation.home.rank.RankActivity;
import com.example.carboncreditapplication.bottomnavigation.home.store.StoreActivity;
import com.example.carboncreditapplication.pedometer.PedometerService;
import com.example.carboncreditapplication.utils.CustomProgressDialogUtils;
import com.example.carboncreditapplication.utils.HttpUtils;
import com.example.carboncreditapplication.utils.MySharedPreferencesUtils;
import com.google.gson.Gson;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.support.constraint.Constraints.TAG;

public class BottomNavigationActivity extends AppCompatActivity {

    private static final String TAG = "BottomNavigationActivity";
    private UserInfoBean userInfoBean = null;
    private CarbonCreditsInfoBean carbonCreditsInfoBean = null;
    BottomNavigationActivity activity =  BottomNavigationActivity.this;
    private boolean hasUserInfo;
    private boolean hasCreditsInfo;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressLint("LongLogTag")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(new HomeFragment());
                    Log.d(TAG,"onNavigationItemSelected: you clicked home");
                    return true;
                case R.id.navigation_game:
                    setFragment(new GameFragment());
                    Log.d(TAG, "onNavigationItemSelected: you clicked game");
                    //queryCommodityInfoTestDrive();
                    return true;
                case R.id.navigation_my_credits:
                    setFragment(new UserInfoFragment());
                    Log.d(TAG, "onNavigationItemSelected: you clicked my credits");
                    return true;
            }
            return false;
        }
    };

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);



        ZXingLibrary.initDisplayOpinion(this);  //二维码相关
        //获取相机拍摄读写权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //版本判断
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 1);
            }
        }

        int versionCodes = Build.VERSION.SDK_INT;//取得SDK版本
        Log.d(TAG, "onCreate: sdk版本="+versionCodes);

        //启动服务
        Intent intent = new Intent(BottomNavigationActivity.this, PedometerService.class);
        startService(intent);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //initData();

        //设置默认界面（HomeFragment)、按钮
        navView.getMenu().getItem(1).setChecked(true);
        navView.setItemIconTintList(null);
        setFragment(new HomeFragment());
    }

    public void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.store:{
                Toast.makeText(BottomNavigationActivity.this, "Store!!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BottomNavigationActivity.this, Store2Activity.class));
                break;
            }
            case R.id.rank:{
                Toast.makeText(BottomNavigationActivity.this, "Rank!!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BottomNavigationActivity.this, RankActivity.class));
                break;
            }
            case R.id.sign_in:{
                Toast.makeText(BottomNavigationActivity.this, "Sign in!!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BottomNavigationActivity.this, SignInActivity.class));
                break;
            }
            case R.id.report:{
                Toast.makeText(BottomNavigationActivity.this, "Report!!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BottomNavigationActivity.this, MonthReportActivity.class));
                break;
            }

        }
    }

    public void initData(){
        hasUserInfo = MySharedPreferencesUtils.getBoolean(BottomNavigationActivity.this, "has_user_info");
        hasCreditsInfo = MySharedPreferencesUtils.getBoolean(BottomNavigationActivity.this, "has_credits_info");
        if(!hasUserInfo){  //本地没有保存User模块的信息
            queryUserInfo();
        }
        if (!hasCreditsInfo){ //本地没有保存Credits模块的信息（碳积分、里程）
            queryCarbonCreditsInfo();
        }
    }

    /**
     * 从服务器获取用户信息并保存在本地
     */
    public void queryUserInfo(){
        //CustomProgressDialogUtils.showLoading(BottomNavigationActivity.this);

        HttpUtils.getInfo(HttpUtils.userInfoUrl, 1, new Callback() {
            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BottomNavigationActivity.this, "获取网络数据失败，请重试！", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.d(TAG, "onFailure: 从服务器读取用户信息失败！");
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();
                Log.i(TAG, "onResponse: userInfo's responseContent: "+responseContent);

                if(!TextUtils.isEmpty(responseContent)){
                    userInfoBean = new Gson().fromJson(responseContent, UserInfoBean.class);
                    MySharedPreferencesUtils.saveUserInfo(BottomNavigationActivity.this, userInfoBean.getResultBean());
                    MySharedPreferencesUtils.putBoolean(BottomNavigationActivity.this, "has_user_info", true);
                }else{
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(BottomNavigationActivity.this, "获取网络数据失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    /**
     * 从服务器获取碳积分信息并保存在本地
     */
    public void queryCarbonCreditsInfo(){
       // CustomProgressDialogUtils.showLoading(BottomNavigationActivity.this);

        HttpUtils.getInfo(HttpUtils.carbonCreditsInfoUrl, 1, new Callback() {
            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //CustomProgressDialogUtils.stopLoading();
                        Toast.makeText(BottomNavigationActivity.this, "获取网络数据失败，请重试！", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.d(TAG, "onFailure: 从服务器读取碳积分信息失败！");
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();
                Log.d(TAG, "onResponse: carbonCreditsInfo's responseContent: "+responseContent);

                if(!TextUtils.isEmpty(responseContent)){
                    carbonCreditsInfoBean = new Gson().fromJson(responseContent, CarbonCreditsInfoBean.class);
                    //即使没拿到数据bean也不会为Null，所以不必判空，但是当没拿到东西或者拿到一些奇怪的东西时的处理还没写好

                    MySharedPreferencesUtils.saveUserInfo(BottomNavigationActivity.this, carbonCreditsInfoBean.getResultBean());
                    MySharedPreferencesUtils.putBoolean(BottomNavigationActivity.this, "has_credits_info", true);
                    Log.d(TAG, "onResponse: MyPreferences,user_nickname="+MySharedPreferencesUtils.getString(BottomNavigationActivity.this, "nickname"));

                }else{
                    Log.d(TAG, "onResponse: 读取到的碳积分信息为空！");
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(BottomNavigationActivity.this, "获取网络数据失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

}
