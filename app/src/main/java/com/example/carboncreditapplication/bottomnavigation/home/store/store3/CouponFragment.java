package com.example.carboncreditapplication.bottomnavigation.home.store.store3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.CommodityBean;
import com.example.carboncreditapplication.bottomnavigation.home.store.StoreRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class CouponFragment extends Fragment {
    private View view;
    Store3Activity store3Activity;
    RecyclerView rvCoupon;
    List<CommodityBean> couponList = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_coupon, container, false);

        initView();

        return view;
    }

    public void initView(){
        store3Activity = (Store3Activity)getActivity();

        initList();
        rvCoupon = view.findViewById(R.id.rv_coupon);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        rvCoupon.setLayoutManager(mLinearLayoutManager);
        rvCoupon.setAdapter(new StoreRecyclerViewAdapter(getContext(), couponList));

    }


    public void initList(){
        for (int i=0; i<10; i++){
            CommodityBean commodityBean = new CommodityBean();
            commodityBean.getCommodityResultBean().setCommodityName("Coupon");
            commodityBean.getCommodityResultBean().setCommodityPrice(200);
            couponList.add(commodityBean);
        }
    }
}
