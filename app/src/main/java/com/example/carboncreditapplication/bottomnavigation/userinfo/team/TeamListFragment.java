package com.example.carboncreditapplication.bottomnavigation.userinfo.team;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.TeamBean;
import com.example.carboncreditapplication.beans.UserInfoBean;

import java.util.ArrayList;
import java.util.List;

public class TeamListFragment extends Fragment {
    private static final String TAG = "TeamListFragment";
    private View view;
    private RecyclerView recyclerViewMemberList;
    private List<TeamBean.ResultBean.UserListBean> memberList = new ArrayList<>();
    private TeamActivity activity;
    private TextView textTeamCarbonCredits;
    private TextView textTeamRanking;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_team_list, container, false);

        init();

        return view;
    }

    public void init(){
        activity = (TeamActivity)getActivity();
        textTeamCarbonCredits = view.findViewById(R.id.textTeamCarbonCredits);
        textTeamRanking = view.findViewById(R.id.textTeamRanking);

        activity.setTeamName(activity.getTeamBean().getResult().getTeamName());  //将toolbar上的标题设为队伍名
        textTeamRanking.setText(activity.getTeamBean().getResult().getTeamRank()+"");
        textTeamCarbonCredits.setText(activity.getTeamBean().getResult().getTeamCarbonCredits()+"");

        //初始化recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewMemberList = view.findViewById(R.id.recyclerViewTeamList);
        recyclerViewMemberList.setLayoutManager(layoutManager);
        memberList = activity.getMemberList();
        recyclerViewMemberList.setAdapter(new TeamMemberAdapter(getContext(), memberList));

    }

}
