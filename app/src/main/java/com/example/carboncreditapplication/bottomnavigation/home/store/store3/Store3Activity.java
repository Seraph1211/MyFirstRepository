package com.example.carboncreditapplication.bottomnavigation.home.store.store3;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.bottomnavigation.userinfo.team.NoTeamFragment;
import com.example.carboncreditapplication.utils.HttpUtils;
import com.example.carboncreditapplication.utils.MySharedPreferencesUtils;
import com.example.carboncreditapplication.utils.StatusBarUtils;
import com.example.carboncreditapplication.utils.ToastUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Store3Activity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    private static final int COUPON_INFO_READY = 231;
    private static final int COMMODITY_INFO_READY = 232;
    private static final int SECOND_HAND_READY = 234;
    private static final String TAG = "Store3Activity";
    private ImageView ivBanner;
    private TabLayout tabLayout;
    private TextView tvAvailableCredits;
    private Button btnShoppingRecord;

    Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case COUPON_INFO_READY:{  //此举是为了保证从服务器拿到team_id之后再执行其他行为(当本地没有存储team_id时，team_id须从服务器获取)

                    break;
                }
                case COMMODITY_INFO_READY:{
                    break;
                }
                case SECOND_HAND_READY:{
                    break;
                }
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store3);

        StatusBarUtils.setStatusBarColor(Store3Activity.this, R.color.colorWhite);  //设置状态栏颜色
        StatusBarUtils.setLightStatusBar(Store3Activity.this, true, true);  //状态栏字体颜色-黑

        queryGoodsInfo();

        initView();
    }

    public void initView(){
        ivBanner = findViewById(R.id.imageBannerStore3);
        tabLayout = findViewById(R.id.tabLayoutStore3);
        tvAvailableCredits = findViewById(R.id.textCreditsStore3);
        btnShoppingRecord = findViewById(R.id.buttonToShoppingCredits);

        tvAvailableCredits.setText(MySharedPreferencesUtils.getInt(Store3Activity.this, "carbon_credits_available")+"");

        initTab();

        Glide.with(Store3Activity.this)
                .load(R.drawable.banner_store)
                .into(ivBanner);

        btnShoppingRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(Store3Activity.this, "Shopping Record");
            }
        });
    }

    public void initTab(){
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.addTab(tabLayout.newTab().setText("优惠券"));
        tabLayout.addTab(tabLayout.newTab().setText("商品"));
        tabLayout.addTab(tabLayout.newTab().setText("二手交易"));
    }

    public void queryCouponList(){
        setFragment(new CouponFragment());
    }

    public void queryCommodityList(){
        setFragment(new CommodityFragment());
    }

    public void querySecondHandList(){
        setFragment(new SecondHandCommodityFragment());
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()){
            case 0:{
                queryCouponList();
                break;
            }
            case 1:{
                queryCommodityList();
                break;
            }
            case 2:{
                querySecondHandList();
                break;
            }

        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameStore3, fragment);
        fragmentTransaction.commit();
    }

    public void queryGoodsInfo(){
        HttpUtils.getInfo(HttpUtils.commodityInfoUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                Log.d(TAG, "Store code="+code);

                if(code==200){
                    String responseContent = response.body().string();
                    Log.d(TAG, "Store responseContent="+responseContent);
                }else {
                    ToastUtils.showToast(Store3Activity.this, "服务器错误");
                }
            }
        });
    }
}
