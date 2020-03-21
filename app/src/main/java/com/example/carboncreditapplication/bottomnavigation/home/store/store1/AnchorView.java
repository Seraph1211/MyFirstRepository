package com.example.carboncreditapplication.bottomnavigation.home.store.store1;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.CommodityBean;
import com.example.carboncreditapplication.bottomnavigation.home.store.CommodityItemAdapter;

import java.util.List;

public class AnchorView extends LinearLayout {

    private TextView tvAnchor;
    private RecyclerView rvGoods;
    //private Context context;
    //private List<CommodityBean> commodityBeanList;

    public AnchorView(Context context, List<CommodityBean> commodityBeanList) {
        this(context, null, commodityBeanList);
    }

    public AnchorView(Context context, @Nullable AttributeSet attrs, List<CommodityBean> commodityBeanList) {
        this(context, attrs, 0, commodityBeanList);
    }

    public AnchorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, List<CommodityBean> commodityBeanList) {
        super(context, attrs, defStyleAttr);
        init(context, commodityBeanList);
        //this.context = context;
        //this.commodityBeanList = commodityBeanList;
    }

    private void init(Context context, List<CommodityBean> commodityBeanList) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_anchor, this, true);
        tvAnchor = view.findViewById(R.id.tv_anchor);
        rvGoods = view.findViewById(R.id.recyclerViewGoods);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rvGoods.setLayoutManager(layoutManager);
        rvGoods.setAdapter(new CommodityItemAdapter(context, commodityBeanList));

    }

    public void setAnchorTxt(String txt) {
        tvAnchor.setText(txt);
    }

}
