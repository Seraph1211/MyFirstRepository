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

import com.example.carboncreditapplication.R;

public class CreateTeamFragment extends Fragment {
    View view;
    private Button buttonCreateTeam2;
    private EditText editCreateTeam;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_team, container, false);

        buttonCreateTeam2 = view.findViewById(R.id.buttonCreateTeam2);
        editCreateTeam = view.findViewById(R.id.editCreateTeam);

        return view;
    }
}
