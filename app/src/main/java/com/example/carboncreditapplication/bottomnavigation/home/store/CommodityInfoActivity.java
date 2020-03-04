package com.example.carboncreditapplication.bottomnavigation.home.store;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.CommodityBean;
import com.example.carboncreditapplication.utils.StatusBarUtils;

public class CommodityInfoActivity extends AppCompatActivity {
    private static final String TAG = "CommodityInfoActivity";

    private CommodityBean commodityBean;
    private ImageButton btnBack;
    private Button btnExchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_info);

        StatusBarUtils.setStatusBarColor(CommodityInfoActivity.this, R.color.colorWhite);  //设置状态栏颜色
        StatusBarUtils.setLightStatusBar(CommodityInfoActivity.this, true, true);  //状态栏字体颜色-黑

        commodityBean = (CommodityBean)getIntent().getSerializableExtra("commodityBean");
        Log.d(TAG, "onCreate: "+commodityBean.getCommodityResultBean().getCommodityName());

        btnBack = findViewById(R.id.button_commodity_info_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        btnExchange = findViewById(R.id.buttonExchange);
        btnExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                buyCommodity();
            }
        });

    }

    public void buyCommodity(){

    }
}
