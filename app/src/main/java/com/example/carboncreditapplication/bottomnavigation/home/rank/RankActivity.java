package com.example.carboncreditapplication.bottomnavigation.home.rank;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;


import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.bottomnavigation.BottomNavigationActivity;

public class RankActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    private TabLayout mTab;
    private FrameLayout mFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        initView();
        initTab();
        mTab.setOnTabSelectedListener(this);

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
}
