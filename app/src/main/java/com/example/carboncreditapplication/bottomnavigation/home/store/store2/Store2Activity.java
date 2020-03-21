package com.example.carboncreditapplication.bottomnavigation.home.store.store2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.CarbonCreditsInfoBean;
import com.example.carboncreditapplication.beans.CommodityBean;
import com.example.carboncreditapplication.bottomnavigation.home.sign.SignInActivity;
import com.example.carboncreditapplication.bottomnavigation.home.store.CommodityItemAdapter;
import com.example.carboncreditapplication.bottomnavigation.userinfo.cardpackage.CardPackageActivity;
import com.example.carboncreditapplication.utils.HttpUtils;
import com.example.carboncreditapplication.utils.MySharedPreferencesUtils;
import com.example.carboncreditapplication.utils.UserInfo;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 此积分商城不对商品分区
 */

public class Store2Activity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "Store2Activity";
    private RecyclerView commodityRecyclerView;
    private List<CommodityBean> commodityBeanList = new ArrayList<>();
    private ImageButton buttonBack;
    private Button buttonJumpToSignIn;
    private Button buttonJumpToCardPackage;
    private TextView textAvailableCredits;
    private int availableCredits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store2);

        initView();


    }

    public void initView(){
        buttonBack = findViewById(R.id.buttonStore2Back);
        buttonJumpToSignIn = findViewById(R.id.buttonToSignInFromStore);
        buttonJumpToCardPackage = findViewById(R.id.buttonToCardPackage);
        textAvailableCredits = findViewById(R.id.textAvailableCreditsInStore);

        buttonBack.setOnClickListener(this);
        buttonJumpToCardPackage.setOnClickListener(this);
        buttonJumpToSignIn.setOnClickListener(this);

        //recyclerView
        commodityRecyclerView = findViewById(R.id.recyclerViewStore2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        commodityRecyclerView.setLayoutManager(layoutManager);
        initData();
        commodityRecyclerView.setAdapter(new CommodityItemAdapter(this, commodityBeanList));


    }

    public void initData(){
        for(int i=0; i<10; i++){
            CommodityBean commodityBean = new CommodityBean();
            commodityBean.getCommodityResultBean().setCommodityName("长生不老药");
            commodityBean.getCommodityResultBean().setCommodityPrice(200);
            commodityBeanList.add(commodityBean);
        }

        availableCredits = MySharedPreferencesUtils.getInt(Store2Activity.this, "available_carbon_credits");
        if(availableCredits!=-2){
            textAvailableCredits.setText(String.valueOf(availableCredits));
        }else{
            //queryAvailableCredits();
        }
    }

    public void queryAvailableCredits(){
        HttpUtils.getInfo(HttpUtils.carbonCreditsInfoUrl+"&mileage_walk_today="+MySharedPreferencesUtils.getInt(Store2Activity.this, "step_count_today")*0.00065,
                new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Store2Activity.this, "获取网络数据失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();
                CarbonCreditsInfoBean bean = new Gson().fromJson(responseContent, CarbonCreditsInfoBean.class);

                if(bean != null){
                    availableCredits = bean.getResultBean().getCarbonCreditsAvailable();
                    MySharedPreferencesUtils.saveUserInfo(Store2Activity.this, bean.getResultBean());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textAvailableCredits.setText(String.valueOf(availableCredits));
                        }
                    });
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Store2Activity.this, "获取网络数据失败!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonToSignInFromStore:{
                startActivity(new Intent(Store2Activity.this, SignInActivity.class));
                break;
            }
            case R.id.buttonToCardPackage:{
                startActivity(new Intent(Store2Activity.this, CardPackageActivity.class));
                break;
            }
            case R.id.buttonStore2Back:{
                finish();
                break;
            }
        }
    }
}
