package com.example.carboncreditapplication.bottomnavigation.userinfo.cardpackage;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.CardPackageBean;
import com.example.carboncreditapplication.utils.HttpUtils;
import com.example.carboncreditapplication.utils.StatusBarUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 在init()中完成recyclerView数据的加载???
 */

public class CardPackageActivity extends AppCompatActivity {
    private static final String TAG = "CardPackageActivity";
    private RecyclerView cardPackageRecyclerView;
    private List<CardPackageBean.ResultBean.CouponBagBean> couponBagBeans = new ArrayList<>();
    private ImageButton buttonBack;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_package);

        StatusBarUtils.setStatusBarColor(CardPackageActivity.this, R.color.colorWhite);  //设置状态栏颜色
        StatusBarUtils.setLightStatusBar(CardPackageActivity.this, true, true);  //状态栏字体颜色-黑

        init();

        queryCouponInfo();
    }

    public void init(){
        //模拟卡包中的数据
        for(int i=0; i<20; i++){
            CardPackageBean.ResultBean.CouponBagBean couponBagBean = new CardPackageBean.ResultBean.CouponBagBean();
            couponBagBean.setCoupon_name("吕小布的蛋糕店"+(i+1)+"号");
            couponBagBean.setCoupon_id(i);
            couponBagBeans.add(couponBagBean);
        }

        //初始化RecyclerView
        cardPackageRecyclerView = findViewById(R.id.recyclerViewCardPackage);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        cardPackageRecyclerView.setLayoutManager(layoutManager);
        cardPackageRecyclerView.setAdapter(new CardPackageRecyclerViewAdapter(this, couponBagBeans));

        buttonBack = findViewById(R.id.buttonCardPackageBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void queryCouponInfo(){
        HttpUtils.getInfo(HttpUtils.cardPackageUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();
                Log.d(TAG, "onResponse: responseContent="+responseContent);
                CardPackageBean cardPackageBean = new Gson().fromJson(responseContent, CardPackageBean.class);
                List<CardPackageBean.ResultBean.CouponBagBean> couponBag = cardPackageBean.getResult().getCoupon_bag();  //拿到coupon list
            }
        });
    }
}
