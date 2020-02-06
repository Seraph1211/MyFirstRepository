package com.example.carboncreditapplication.bottomnavigation.userinfo.merchant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.CouponResultBean;
import com.example.carboncreditapplication.beans.MerchantBean;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Line;

import static android.support.constraint.Constraints.TAG;

public class MerchantHomeFragment extends Fragment implements View.OnClickListener{
    View view;
    private MerchantActivity activity;
    private ImageView imageMerchantHome;
    private ImageView imageMerchantScan;  //扫一扫
    private ImageView imageMerchantAddTicket;  //添加券
    private ImageView imageMerchantInfo;  //商家个人信息中心
    private RecyclerView recyclerAddedGoods;  //在首页展示的已添加的优惠券，目前不清楚要展示什么，暂时搁置
    private GoodsAdapter goodsAdapter;
    private List<CouponResultBean.ResultBean.CouponBean> addedCouponList = new ArrayList<>();
    private MerchantBean bean = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_merchant_home, container, false);

        initView();
        initRecyclerView();

        return view;
    }

    public void initView(){
        activity = (MerchantActivity) getActivity();

        imageMerchantHome = view.findViewById(R.id.imageMerchantHome);
        imageMerchantAddTicket = view.findViewById(R.id.imageMerchantAddCard);
        imageMerchantScan = view.findViewById(R.id.imageMerchantScan);
        imageMerchantInfo = view.findViewById(R.id.imageMerchantInfo);

        imageMerchantScan.setOnClickListener(this);
        imageMerchantAddTicket.setOnClickListener(this);
        imageMerchantInfo.setOnClickListener(this);
    }

    public void initRecyclerView(){
        for(int i=0; i<10; i++){
            CouponResultBean.ResultBean.CouponBean c = new CouponResultBean.ResultBean.CouponBean();
            c.setCoupon_name("三花聚顶红包"+i+"号");
            addedCouponList.add(c);
        }

        recyclerAddedGoods = view.findViewById(R.id.recyclerViewGoodsAdded);
        goodsAdapter = new GoodsAdapter(getContext(), addedCouponList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerAddedGoods.setLayoutManager(layoutManager);
        recyclerAddedGoods.setAdapter(goodsAdapter);
        Log.d(TAG, "initRecyclerView: recyclerView="+recyclerAddedGoods.toString());
        Log.d(TAG, "initRecyclerView: adapter="+goodsAdapter.toString());
        Log.d(TAG, "initRecyclerView: recyclerView's adapter="+recyclerAddedGoods.getAdapter());
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
                startActivity(new Intent(getContext(), AddGoodsActivity.class));
                break;
            }
            case R.id.imageMerchantInfo:{
                Toast.makeText(getContext(), "商家信息！", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), MerchantInfoActivity.class));
                break;
            }
        }
    }

    class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.CouponViewHolder>{
        Context context;
        List<CouponResultBean.ResultBean.CouponBean> couponBeanList;


        public GoodsAdapter(Context context, List<CouponResultBean.ResultBean.CouponBean> couponBeanList){
            this.context = context;
            this.couponBeanList = couponBeanList;
        }

        @NonNull
        @Override
        public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_coupon, viewGroup, false);
            CouponViewHolder viewHolder = new CouponViewHolder(context, view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CouponViewHolder couponViewHolder, int i) {

            CouponResultBean.ResultBean.CouponBean couponBean = couponBeanList.get(i);
            couponViewHolder.textCouponName.setText(couponBean.getCoupon_name());
            /*
             *过期时间、商家尚未设置
             * couponViewHolder.textCouponSill.setText(couponBean.getSill());
             * couponViewHolder.textCouponValue.setText(couponBean.getValue());
             */

            couponViewHolder.buttonDeleteCoupon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Delete Coupon!", Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public int getItemCount() {
            return couponBeanList.size();
        }

        class CouponViewHolder extends RecyclerView.ViewHolder{

            TextView textCouponValue;
            TextView textCouponName;
            TextView textCouponMerchant;
            TextView textCouponExpirationTime;
            TextView textCouponSill;
            Button buttonDeleteCoupon;

            public CouponViewHolder(Context context, @NonNull View itemView) {
                super(itemView);

                textCouponName = itemView.findViewById(R.id.textCouponName);
                textCouponMerchant = itemView.findViewById(R.id.textCouponMerchant);
                textCouponValue = itemView.findViewById(R.id.textCouponValue);
                textCouponSill = itemView.findViewById(R.id.textCouponSill);
                textCouponExpirationTime = itemView.findViewById(R.id.textCouponExpirationTime);
                buttonDeleteCoupon = itemView.findViewById(R.id.buttonDeleteCoupon);
            }
        }
    }


}
