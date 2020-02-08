package com.example.carboncreditapplication.bottomnavigation.userinfo.team;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.UserInfoBean;
import com.example.carboncreditapplication.utils.HttpUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddTeamFragment extends Fragment {
    private static final String TAG = "AddTeamFragment";
    View view;
    private EditText editAddTeam;
    private Button buttonAddTeam2;
    private String teamName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_team, container, false);


        return view;
    }

    public void initView(){
        editAddTeam = view.findViewById(R.id.editAddTeam);
        buttonAddTeam2 = view.findViewById(R.id.buttonAddTeam2);

        buttonAddTeam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void addToTeam(String  teamName){

    }
}
