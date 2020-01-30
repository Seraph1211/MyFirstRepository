package com.example.carboncreditapplication.bottomnavigation.userinfo.team;

import android.content.Context;
import android.icu.lang.UScript;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.UserInfoBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeamMemberAdapter extends RecyclerView.Adapter<TeamMemberAdapter.TeamMemberViewHolder> {
    private Context context;
    private List<UserInfoBean> memberList;

    static class TeamMemberViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imageMemberHeadPortrait;
        TextView textMemberName;
        Button buttonSendCredits;

        public TeamMemberViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            imageMemberHeadPortrait = itemView.findViewById(R.id.imageMemberHeadPortrait);
            textMemberName = itemView.findViewById(R.id.textMemberName);
            buttonSendCredits = itemView.findViewById(R.id.buttonSendCredits);
        }
    }

    public TeamMemberAdapter(Context context, List<UserInfoBean> memberList){
        this.context = context;
        this.memberList = memberList;
    }

    @NonNull
    @Override
    public TeamMemberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_team_meber, viewGroup, false);
        TeamMemberViewHolder memberViewHolder = new TeamMemberViewHolder(view, context);

        return memberViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamMemberViewHolder teamMemberViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }



}
