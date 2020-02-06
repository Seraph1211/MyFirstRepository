package com.example.carboncreditapplication.bottomnavigation.userinfo.team;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.UserInfoBean;
import com.example.carboncreditapplication.utils.HttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TeamActivity extends AppCompatActivity {
    private static final String TAG = "TeamActivity";
    private String teamName;
    private List<UserInfoBean> memberList = new ArrayList<>();
    private ImageButton buttonBackTeam;  //返回
    private ImageButton buttonExitTeam;  //退出队伍

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        queryTeamInfo();

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

    public void queryTeamInfo(){
        HttpUtils.getInfo(HttpUtils.teamInfoUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();
                Log.d(TAG, "onResponse: responseContent="+responseContent);
            }
        });
    }
}
