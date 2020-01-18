package com.example.carboncreditapplication.bottomnavigation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.bottomnavigation.game.GameFragment;
import com.example.carboncreditapplication.bottomnavigation.home.HomeFragment;
import com.example.carboncreditapplication.bottomnavigation.home.rank.MonthRankFragment;
import com.example.carboncreditapplication.bottomnavigation.home.report.MonthReportActivity;
import com.example.carboncreditapplication.bottomnavigation.home.sign.SignInActivity;
import com.example.carboncreditapplication.bottomnavigation.home.store.Store2Activity;
import com.example.carboncreditapplication.bottomnavigation.userinfo.UserInfoFragment;
import com.example.carboncreditapplication.bottomnavigation.home.rank.RankActivity;
import com.example.carboncreditapplication.bottomnavigation.home.store.StoreActivity;
import com.example.carboncreditapplication.utils.HttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BottomNavigationActivity extends AppCompatActivity {

    private static final String TAG = "BottomNavigationActivity";

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
                    queryCommodityInfoTestDrive();
                    return true;
                case R.id.navigation_my_credits:
                    setFragment(new UserInfoFragment());
                    Log.d(TAG, "onNavigationItemSelected: you clicked my credits");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //设置默认界面、按钮
        navView.getMenu().getItem(1).setChecked(true);
        navView.setItemIconTintList(null);
        setDefaultFragment();
    }

    public void setDefaultFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, new HomeFragment());
        transaction.commit();
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


    public void queryCommodityInfoTestDrive(){

        HttpUtils.getInfo("http://121.36.4.52:8090/commodity/getCommodityList?page_no=1&page_size=10", new Callback() {
            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: 请求失败！");
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: 请求成功！");
                Log.d(TAG, "onResponse: "+response.body().string());
            }
        });

    }

}