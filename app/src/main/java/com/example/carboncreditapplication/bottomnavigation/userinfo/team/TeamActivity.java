package com.example.carboncreditapplication.bottomnavigation.userinfo.team;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.TeamBean;
import com.example.carboncreditapplication.beans.UserInfoBean;
import com.example.carboncreditapplication.bottomnavigation.userinfo.merchant.MerchantActivity;
import com.example.carboncreditapplication.utils.HttpUtils;
import com.example.carboncreditapplication.utils.MySharedPreferencesUtils;
import com.example.carboncreditapplication.utils.ToastUtils;
import com.example.carboncreditapplication.utils.UserInfo;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TeamActivity extends AppCompatActivity {
    private static final String TAG = "TeamActivity";
    private static final int TEAM_ID_READY = 111;
    private List<TeamBean.ResultBean.UserListBean> memberList = new ArrayList<>();
    private ImageButton buttonBackTeam;  //返回
    private ImageButton buttonExitTeam;  //退出队伍
    private TextView textTeamName;
    private int teamId;
    private TeamBean teamBean;

    Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case TEAM_ID_READY:{  //此举是为了保证从服务器拿到team_id之后再执行其他行为(当本地没有存储team_id时，team_id须从服务器获取)
                    if(teamId == -1){
                        setFragment(new NoTeamFragment());
                    }else {
                        queryTeamInfo();
                    }
                    break;
                }
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        initView();
        initData();
    }

    public void initView(){
        buttonBackTeam = findViewById(R.id.buttonTeamBack);
        buttonExitTeam = findViewById(R.id.buttonExitTeam);
        textTeamName = findViewById(R.id.textTeamName);

        buttonExitTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ExitTeam");
                if(teamId!=-2 && teamId!=-1){  //-2为sp存储int型数据时的默认值，-1表示没有队伍
                    AlertDialog dialog = new AlertDialog.Builder(TeamActivity.this)
                            .setMessage("确定要退出此队伍吗？")
                            .setCancelable(false)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    exitTeam();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create();

                    dialog.show();
                }else if(teamId == -1){
                    Toast.makeText(TeamActivity.this, "你还没有加入任何队伍！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonBackTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initData(){
        teamId = MySharedPreferencesUtils.getInt(TeamActivity.this, "team_id");
        if(teamId==-2){  //本地没有存储team_id, 则从服务器获取
            setTeamIdFromServer();
        }else if(teamId == -1){  //没有加入任何队伍
            setFragment(new NoTeamFragment());
        }else{  //有队伍
            queryTeamInfo();
        }


    }

    public void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutTeam, fragment);
        fragmentTransaction.commit();
    }

    public void setTeamIdFromServer(){

        HttpUtils.getInfo(HttpUtils.userInfoUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();
                Log.d(TAG, "onResponse: responseContent="+responseContent);

                UserInfoBean userInfoBean = new Gson().fromJson(responseContent, UserInfoBean.class);
                teamId = userInfoBean.getResultBean().getTeamId();
                mHandler.sendEmptyMessage(TEAM_ID_READY);

            }
        });

    }

    /**
     * 当用户有队伍时，执行此方法加载队伍的相关数据
     * 网络数据请求成功时，在此方法内加载TeamList碎片
     */
    public void queryTeamInfo(){
        HttpUtils.getInfo(HttpUtils.teamInfoUrl+"&team_id="+1, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
                ToastUtils.showToast(TeamActivity.this, "网络数据请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();
                Log.d(TAG, "onResponse: responseContent="+responseContent);

                teamBean = new Gson().fromJson(responseContent, TeamBean.class);
                memberList = teamBean.getResult().getUserList();
                setFragment(new TeamListFragment());  //加载TeamList碎片
            }
        });
    }

    public void exitTeam(){
        HttpUtils.getInfo(HttpUtils.deleteTeamMemberUrl+"?user_id="+UserInfo.userId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
                ToastUtils.showToast(TeamActivity.this, "退出队伍失败，请重试");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: "+response.body().string());
                ToastUtils.showToast(TeamActivity.this, "成功退出队伍");
                MySharedPreferencesUtils.putInt(TeamActivity.this, "team_id", -1);  //将本地存储的队伍id设为-1，表示没有加入队伍
                setFragment(new NoTeamFragment());  //重新加载碎片
            }
        });
    }

    public TeamBean getTeamBean(){
        return teamBean;
    }

    public List<TeamBean.ResultBean.UserListBean> getMemberList() {
        return memberList;
    }

    public void setTeamName(String teamName){
        textTeamName.setText(teamName);
    }
}
