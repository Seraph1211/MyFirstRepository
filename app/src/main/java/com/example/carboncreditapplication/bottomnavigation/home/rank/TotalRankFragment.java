package com.example.carboncreditapplication.bottomnavigation.home.rank;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carboncreditapplication.R;

import java.util.ArrayList;
import java.util.List;

public class TotalRankFragment extends Fragment {

    List<RankItem> totalRankItemList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_total_rank, container, false);

        initList();

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_total);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new RankRecyclerViewAdapter(getContext(), totalRankItemList));

        return view;
    }

    public void initList(){
        for(int i=0; i<50; i++){
            RankItem rankItem = new RankItem();
            rankItem.setRanking(i+1);
            totalRankItemList.add(rankItem);
        }
    }
}
