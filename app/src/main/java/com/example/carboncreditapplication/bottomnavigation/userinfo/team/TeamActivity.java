package com.example.carboncreditapplication.bottomnavigation.userinfo.team;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.UserInfoBean;

import java.util.ArrayList;
import java.util.List;

public class TeamActivity extends AppCompatActivity {
    private String teamName;
    private List<UserInfoBean> memberList = new ArrayList<>();
    private ImageButton buttonBackTeam;  //返回
    private ImageButton buttonExitTeam;  //退出队伍

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        setFragment(new NoTeamFragment());

    }

    public void init(){
        //若队伍的成员列表长度为0,则加载NoTeamFragment
        if(memberList.size()==0){
            setFragment(new NoTeamFragment());
        }else {
            setFragment(new TeamListFragment());
        }
    }

    public void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutTeam, fragment);
        fragmentTransaction.commit();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<UserInfoBean> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<UserInfoBean> memberList) {
        this.memberList = memberList;
    }
}
