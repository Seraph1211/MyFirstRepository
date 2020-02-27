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

public class SecondHandCommodityFragment extends Fragment {

    private View view;
    private Store3Activity store3Activity;
    private RecyclerView rvSecondHandCommodity;
    private List<CommodityBean> secondHandCommodityList = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_second_hand_commodity, container, false);

        initView();

        return view;
    }

    public void initView(){
        store3Activity = (Store3Activity)getActivity();

        initList();
        rvSecondHandCommodity = view.findViewById(R.id.rv_second_hand_commodity);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        rvSecondHandCommodity.setLayoutManager(mLinearLayoutManager);
        rvSecondHandCommodity.setAdapter(new StoreRecyclerViewAdapter(getContext(), secondHandCommodityList));

    }


    public void initList(){
        for (int i=0; i<10; i++){
            CommodityBean commodityBean = new CommodityBean();
            commodityBean.getCommodityResultBean().setCommodityName("Second-hand Commodity");
            commodityBean.getCommodityResultBean().setCommodityPrice(200);
            secondHandCommodityList.add(commodityBean);
        }
    }



}
