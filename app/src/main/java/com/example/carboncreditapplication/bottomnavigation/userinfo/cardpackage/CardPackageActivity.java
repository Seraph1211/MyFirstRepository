package com.example.carboncreditapplication.bottomnavigation.userinfo.cardpackage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.CardInfoBean;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class CardPackageActivity extends AppCompatActivity {
    private RecyclerView cardPackageRecyclerView;
    private List<CardInfoBean> cardInfoBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_package);

        cardPackageRecyclerView = findViewById(R.id.recyclerViewCardPackage);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        cardPackageRecyclerView.setLayoutManager(layoutManager);

        init();

        cardPackageRecyclerView.setAdapter(new CardPackageRecyclerViewAdapter(this, cardInfoBeanList));

    }

    public void init(){
        for(int i=0; i<20; i++){
            CardInfoBean cardInfoBean = new CardInfoBean();
            cardInfoBean.getCardInfoResultBean().getCardList().setCardName("吕小布的蛋糕店"+(i+1)+"号");
            cardInfoBeanList.add(cardInfoBean);
        }
    }
}
