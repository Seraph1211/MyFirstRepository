package com.example.carboncreditapplication.bottomnavigation.home.rank;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;


import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.RankBean;
import com.example.carboncreditapplication.bottomnavigation.BottomNavigationActivity;
import com.example.carboncreditapplication.utils.HttpUtils;
import com.example.carboncreditapplication.utils.StatusBarUtils;
import com.example.carboncreditapplication.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RankActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    private static final String TAG = "RankActivity";
    private TabLayout mTab;
    private FrameLayout mFrame;
    private RankBean rankBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        StatusBarUtils.setStatusBarColor(RankActivity.this, R.color.colorOrange);  //设置状态栏颜色

        initView();
        initTab();
        mTab.addOnTabSelectedListener(this);

        //queryRankInfo();

        setFragment(new TotalRankFragment());

    }

    private void initView() {
        // 获取控件对象
        mTab = findViewById(R.id.rank_tab);
        mFrame = findViewById(R.id.rank_frame);
    }

    private void initTab(){
        mTab.addTab(mTab.newTab().setText("总碳积分排行"));
        mTab.addTab(mTab.newTab().setText("月度碳积分排行"));
    }

    public void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.rank_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()){
            case 0:{
                setFragment(new TotalRankFragment());
                break;
            }
            case 1:{
                setFragment(new MonthRankFragment());
                break;
            }
            default:break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_rank_back: {
                finish();
                startActivity(new Intent(RankActivity.this, BottomNavigationActivity.class));
            }

        }
    }


    //废弃
    public void queryRankInfo(){
        Log.d(TAG, "queryRankInfo: ");
        HttpUtils.getInfo(HttpUtils.userRankingInfoUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showToast(RankActivity.this, "服务器错误");
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                Log.d(TAG, "Rank code="+code);

                if(code==200){
                    String responseContent = response.body().string();
                        Log.d(TAG, "onResponse: responseContent="+responseContent);

                        rankBean = new Gson().fromJson(responseContent, RankBean.class);
                        String msgCode = rankBean.getMsg_code();
                        if(msgCode.equals("0000")){  //处理成功
                            setFragment(new TotalRankFragment());
                    }else {
                        ToastUtils.showToast(RankActivity.this, rankBean.getMsg_message());
                    }
                }else {
                    ToastUtils.showToast(RankActivity.this, "服务器错误");
                }

            }
        });
    }

    public RankBean getRankBean(){
        return rankBean;
    }
}
