package com.example.carboncreditapplication.bottomnavigation.home.store;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.CommodityBean;

public class CommodityInfoActivity extends AppCompatActivity {
    private static final String TAG = "CommodityInfoActivity";

    private CommodityBean commodityBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_info);

        commodityBean = (CommodityBean)getIntent().getSerializableExtra("commodityBean");

        Log.d(TAG, "onCreate: "+commodityBean.getCommodityResultBean().getCommodityName());

    }
}
