package com.example.carboncreditapplication.bottomnavigation.userinfo.merchant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.carboncreditapplication.R;

/**
 * AddGoodsActivity中添加commodity(碳积分+rmb商品)的Fragment
 */

public class AddCommodityFragment extends Fragment {
    private String picturePath;  //图片路径
    private String commodityName;  //商品名
    private int commodityType;  //商品种类
    private String commodityIntroduction;  //介绍
    private int commodityOriginalPrice;  //原价
    private int commodityPrice;  //现价
    private int commodityCreditsNeed;  //所需碳积分
    private int commodityRemaining;  //剩余数量

    private EditText editCommodityName;
    private TextView editCommodityType;
    private TextView editCommodityIntroduce;
    private TextView editCommodityOriginalPrice;
    private TextView editCommodityPrice;
    private TextView editCommodityCreditsNeed;
    private TextView editCommodityRemaining;

    View view;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_commodity, container, false);

        initView();

        return view;
    }

    private void initView(){
        editCommodityName = view.findViewById(R.id.editSetCommodityName);
        editCommodityRemaining = view.findViewById(R.id.editSetCommodityRemaining);
        editCommodityOriginalPrice = view.findViewById(R.id.editSetCommodityOriginalPrice);
        editCommodityPrice = view.findViewById(R.id.editSetCommodityPrice);
        editCommodityCreditsNeed = view.findViewById(R.id.editSetCommodityCreditsNeed);
        editCommodityIntroduce = view.findViewById(R.id.editSetCommodityIntroduction);
    }
}
