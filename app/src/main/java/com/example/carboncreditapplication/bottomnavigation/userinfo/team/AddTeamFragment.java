package com.example.carboncreditapplication.bottomnavigation.userinfo.team;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.carboncreditapplication.R;

public class AddTeamFragment extends Fragment {
    View view;
    private EditText editAddTeam;
    private Button buttonAddTeam2;
    private String teamName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_team, container, false);

        editAddTeam = view.findViewById(R.id.editAddTeam);
        buttonAddTeam2 = view.findViewById(R.id.buttonAddTeam2);

        return view;
    }
}
