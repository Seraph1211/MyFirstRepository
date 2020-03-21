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
import com.example.carboncreditapplication.beans.CouponResultBean;
import com.example.carboncreditapplication.bottomnavigation.home.store.CommodityItemAdapter;
import com.example.carboncreditapplication.bottomnavigation.home.store.CouponItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class CouponFragment extends Fragment {
    private View view;
    Store3Activity store3Activity;
    RecyclerView rvCoupon;
    List<CouponResultBean.ResultBean.CouponBean> couponList = new ArrayList<>();

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
        rvCoupon.setAdapter(new CouponItemAdapter(getContext(), couponList));

    }


    public void initList(){
        String couponNames[] = {"饿了么无门槛红包", "美团无门槛红包", "肯德基优惠券", "麦当劳满减券","西门烧烤抵扣券","饿了么无门槛红包", "美团无门槛红包", "肯德基优惠券", "麦当劳满减券","西门烧烤抵扣券"};
        int couponValues[] = {10, 6, 20, 15, 8, 10, 6, 20, 15, 8};
        int couponCost[] = {100, 80, 160, 120, 150, 100, 80, 160, 120, 150};
        for (int i=0; i<10; i++){
            CouponResultBean.ResultBean.CouponBean bean = new CouponResultBean.ResultBean.CouponBean();
            bean.setValue(couponValues[i]);
            bean.setCoupon_name(couponNames[i]);
            bean.setCoupon_cost(couponCost[i]);
            couponList.add(bean);
        }
    }
}
