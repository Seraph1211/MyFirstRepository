package com.example.carboncreditapplication.bottomnavigation.userinfo.merchant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.carboncreditapplication.R;

public class MerchantHomeFragment extends Fragment implements View.OnClickListener{
    View view;
    private ImageView imageMerchantHome;
    private ImageView imageMerchantScan;  //扫一扫
    private ImageView imageMerchantAddTicket;  //添加券
    private ImageView imageMerchantInfo;  //商家个人信息中心
    private RecyclerView recyclerAddedTickets;  //在首页展示的已添加的优惠券，目前不清楚要展示什么，暂时搁置

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_merchant_home, container, false);

        initView();

        return view;
    }

    public void initView(){
        imageMerchantHome = view.findViewById(R.id.imageMerchantHome);
        imageMerchantAddTicket = view.findViewById(R.id.imageMerchantAddCard);
        imageMerchantScan = view.findViewById(R.id.imageMerchantScan);
        imageMerchantInfo = view.findViewById(R.id.imageMerchantInfo);

        imageMerchantScan.setOnClickListener(this);
        imageMerchantAddTicket.setOnClickListener(this);
        imageMerchantInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageMerchantScan:{
                Toast.makeText(getContext(), "扫一扫！", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.imageMerchantAddCard:{
                Toast.makeText(getContext(), "添加优惠券！", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.imageMerchantInfo:{
                Toast.makeText(getContext(), "商家信息！", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}
