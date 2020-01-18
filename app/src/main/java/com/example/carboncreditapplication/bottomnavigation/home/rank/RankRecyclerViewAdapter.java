package com.example.carboncreditapplication.bottomnavigation.home.rank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carboncreditapplication.R;

import java.util.List;

public class RankRecyclerViewAdapter extends RecyclerView.Adapter<RankRecyclerViewAdapter.RankViewHolder> {

    private Context context;
    private List<RankItem> rankItemList;

    static class RankViewHolder extends RecyclerView.ViewHolder{
         TextView textRanking;  //排名
         ImageView imageHeadPortrait; //头像
         TextView textUserName; //用户名
         TextView textCredits; //积分，总积分或月度积分

        public RankViewHolder(View view, Context context){
            super(view);
            textRanking = view.findViewById(R.id.text_ranking);
            imageHeadPortrait = view.findViewById(R.id.image_ranking);
            textUserName = view.findViewById(R.id.text_ranking_name);
            textCredits = view.findViewById(R.id.text_ranking_credits);
        }
    }

    public RankRecyclerViewAdapter(Context context, List<RankItem> rankItemList){
        this.context = context;
        this.rankItemList = rankItemList;
    }


    @NonNull
    @Override
    public RankViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rank, viewGroup, false);
        RankViewHolder viewHolder = new RankViewHolder(view, context);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RankViewHolder viewHolder, int i) {
        RankItem rankItem = rankItemList.get(i);
        viewHolder.textRanking.setText(String.valueOf(rankItem.getRanking()));
        viewHolder.imageHeadPortrait.setImageResource(rankItem.getImageId());
        viewHolder.textUserName.setText(rankItem.getUserName());
        viewHolder.textCredits.setText(String.valueOf(rankItem.getCredits()));
    }

    @Override
    public int getItemCount() {
        return rankItemList.size();
    }
}
