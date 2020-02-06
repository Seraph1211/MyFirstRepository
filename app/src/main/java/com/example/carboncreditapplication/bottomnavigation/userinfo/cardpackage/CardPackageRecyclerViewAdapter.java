package com.example.carboncreditapplication.bottomnavigation.userinfo.cardpackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.CardPackageBean;

import java.util.List;

public class CardPackageRecyclerViewAdapter extends RecyclerView.Adapter<CardPackageRecyclerViewAdapter.CardViewHolder> {
    private Context context;
    private List<CardPackageBean.ResultBean.CouponBagBean> cardInfoBeanList;


    static class CardViewHolder extends RecyclerView.ViewHolder{

        TextView textCardValue;
        TextView textCardName;
        TextView textCardMerchant;
        TextView textCardExpirationTime;
        TextView textCardSill;
        Button buttonUseCard;

        public CardViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            textCardValue = itemView.findViewById(R.id.textCardValue);
            textCardName = itemView.findViewById(R.id.textCardName);
            textCardExpirationTime = itemView.findViewById(R.id.textCardExpirationTime);
            textCardMerchant = itemView.findViewById(R.id.textCardMerchant);
            textCardSill = itemView.findViewById(R.id.textCardSill);
            buttonUseCard = itemView.findViewById(R.id.buttonUseCard);

        }
    }

    public CardPackageRecyclerViewAdapter(Context context, List<CardPackageBean.ResultBean.CouponBagBean> cardInfoBeanList){
        this.context = context;
        this.cardInfoBeanList = cardInfoBeanList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_card, viewGroup, false);
        CardViewHolder viewHolder = new CardViewHolder(view, context);
        return viewHolder;
    }

    //多个属性未设置
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, int i) {
        final CardPackageBean.ResultBean.CouponBagBean couponBagBean= cardInfoBeanList.get(i);
        cardViewHolder.textCardName.setText(couponBagBean.getCoupon_name());

        /*尚未设置的属性  超多的 couponBagBean
        cardViewHolder.textCardValue.setText(String.);
        cardViewHolder.textCardMerchant.setText(cardInfoBean.getCardInfoResultBean().getCardList().getUseStoreId());
        cardViewHolder.textCardExpirationTime.setText(cardInfoBean.getCardInfoResultBean().getCardList().getDateTime().toString());
        cardViewHolder.textCardSill.setText(cardInfoBean.getCardInfoResultBean().getCardList().getSill());
        */

        //为button注册点击事件
        cardViewHolder.buttonUseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Use Card !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardInfoBeanList.size();
    }
}
