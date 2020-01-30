package com.example.carboncreditapplication.bottomnavigation.userinfo.team;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.UserInfoBean;

import java.util.ArrayList;
import java.util.List;

public class TeamListFragment extends Fragment {
    View view;
    RecyclerView recyclerViewMemberList;
    List<UserInfoBean> memberList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_team_list, container, false);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewMemberList = view.findViewById(R.id.recyclerViewTeamList);
        recyclerViewMemberList.setLayoutManager(layoutManager);

        //此时memberList为空
        recyclerViewMemberList.setAdapter(new TeamMemberAdapter(getContext(), memberList));

        return view;
    }

}
