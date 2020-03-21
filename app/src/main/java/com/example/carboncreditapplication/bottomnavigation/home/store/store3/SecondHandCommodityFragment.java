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
import com.example.carboncreditapplication.bottomnavigation.home.store.CommodityItemAdapter;

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
        rvSecondHandCommodity.setAdapter(new CommodityItemAdapter(getContext(), secondHandCommodityList));

    }


    public void initList(){
        String commodityNames[]={"墨锦 春装新款上衣长袖衬衫女", "Kindle Oasis3 电子书阅读器", "英雄联盟LOL彩虹小马公仔", "联想 游戏商务办公有线鼠标", "人本学生百搭小白鞋松糕板鞋黑色女鞋", "墨锦 春装新款上衣长袖衬衫女", "Kindle Oasis3 电子书阅读器", "英雄联盟LOL彩虹小马公仔玩偶", "联想 游戏商务办公有线鼠标", "人本学生百搭小白鞋松糕板鞋黑色女鞋"};
        int commodityPicRes[] = {R.drawable.secondhand1, R.drawable.secondhand2, R.drawable.secondhand3, R.drawable.secondhand4, R.drawable.secondhand5, R.drawable.secondhand1, R.drawable.secondhand2, R.drawable.secondhand3, R.drawable.secondhand4, R.drawable.secondhand5 };
        int commodityPrices[] = {220, 1200, 100, 80, 90, 220, 1200, 100, 80, 90};
        int commodityCreditsCost[] = {200,  600, 90, 120, 300, 200,  600, 90, 120, 300};
        for (int i=0; i<10; i++){
            CommodityBean commodityBean = new CommodityBean();
            commodityBean.getCommodityResultBean().setCommodityName(commodityNames[i]);
            commodityBean.getCommodityResultBean().setCommodityPrice(commodityPrices[i]);
            commodityBean.getCommodityResultBean().setCarbonCreditsNeeds(commodityCreditsCost[i]);
            commodityBean.getCommodityResultBean().setCommodityPicture(commodityPicRes[i]);
            commodityBean.getCommodityResultBean().setCommodityIntroduce("......");
            secondHandCommodityList.add(commodityBean);
        }
    }



}
