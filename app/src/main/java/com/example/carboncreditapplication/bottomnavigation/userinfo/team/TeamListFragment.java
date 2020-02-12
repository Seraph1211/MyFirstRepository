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
    private TextView textTeamName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_team_list, container, false);
        activity = (TeamActivity)getActivity();

        textTeamName = view.findViewById(R.id.textTeamName);
        textTeamName.setText(activity.getTeamBean().getResult().getTeamName());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerViewMemberList = view.findViewById(R.id.recyclerViewTeamList);
        recyclerViewMemberList.setLayoutManager(layoutManager);

        memberList = activity.getMemberList();

        recyclerViewMemberList.setAdapter(new TeamMemberAdapter(getContext(), memberList));

        return view;
    }

}
