package com.example.carboncreditapplication.bottomnavigation.home.rank;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.RankBean;
import com.example.carboncreditapplication.utils.HttpUtils;
import com.example.carboncreditapplication.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.support.constraint.Constraints.TAG;

public class MonthRankFragment extends Fragment {
    private RankActivity activity;
    private View view;
    private RecyclerView recyclerView;
    List<RankBean.ResultBean.UserListBean> monthRankItemList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_month_rank, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_month);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (RankActivity)getActivity();

        //queryMonthRankingList();
        initRecyclerView();
    }

    public void initRecyclerView(){
        initList();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new RankRecyclerViewAdapter(getContext(), monthRankItemList));
    }

    public void initList(){
        for(int i=0; i<50; i++){
            RankBean.ResultBean.UserListBean rankItem = new RankBean.ResultBean.UserListBean();
            rankItem.setUserRank(i+1);
            rankItem.setNickname("江南");
            //rankItem.setImageId(R.drawable.img7);
            rankItem.setCarbonCredits(135322);
            monthRankItemList.add(rankItem);
        }
    }

    public void queryMonthRankingList(){
        HttpUtils.getInfo(HttpUtils.userRankingInfoUrl + "&rank_type=" + 0, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showToast(getContext(), "服务器故障");
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                Log.d(TAG, "queryTotalRankingList code="+code);

                if(code==200){
                    String responseContent = response.body().string();
                    Log.d(TAG, "queryTotalRankingList responseContent="+responseContent);

                    RankBean bean = new Gson().fromJson(responseContent, RankBean.class);
                    if(bean.getMsg_code().equals("0000")){
                        monthRankItemList = bean.getResult().getUser_list();
                        initRecyclerView();
                    }else {
                        ToastUtils.showToast(getContext(), bean.getMsg_message());
                    }
                }else {
                    ToastUtils.showToast(getContext(), "服务器错误");
                }
            }
        });
    }

}
