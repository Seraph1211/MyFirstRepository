package com.example.carboncreditapplication.bottomnavigation.home.store;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.CommodityBean;
import com.example.carboncreditapplication.utils.MySharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 此积分商城不对商品分区
 */

public class Store2Activity extends AppCompatActivity {
    private static final String TAG = "Store2Activity";
    private RecyclerView commodityRecyclerView;
    private List<CommodityBean> commodityBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store2);

        commodityRecyclerView = findViewById(R.id.recyclerViewStore2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        commodityRecyclerView.setLayoutManager(layoutManager);

        init();

        commodityRecyclerView.setAdapter(new StoreRecyclerViewAdapter(this, commodityBeanList));


        Log.i(TAG, "onCreate: testKey2="+ MySharedPreferencesUtils.getInt(Store2Activity.this, "testKey"));
        MySharedPreferencesUtils.putInt(Store2Activity.this, "testKey", 2);
        Log.i(TAG, "onCreate: testKey3="+MySharedPreferencesUtils.getInt(Store2Activity.this, "testKey"));
    }

    public void init(){
        for(int i=0; i<10; i++){
            CommodityBean commodityBean = new CommodityBean();
            commodityBean.getCommodityResultBean().setCommodityName("大力丸");
            commodityBean.getCommodityResultBean().setCommodityPrice(200);
            commodityBeanList.add(commodityBean);
        }
    }

}
