package com.example.carboncreditapplication.bottomnavigation.game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.CommodityBean;
import com.example.carboncreditapplication.utils.HttpUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GameFragment extends Fragment {
    private static final String TAG = "GameFragment";
    private CommodityBean commodityBean = new CommodityBean();
    private ImageView imageView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        imageView = view.findViewById(R.id.imageView2);
        queryCommodityInfoTestDrive(view);

        return view;
    }

    public void queryCommodityInfoTestDrive(final View view){

        HttpUtils.getInfo("http://121.36.4.52:8090/commodity/getCommodityList?page_no=1&page_size=10", new Callback() {
            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: 请求失败！");
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: 请求成功！");


                String responseContent = response.body().string();

                Log.d(TAG, "onResponse: "+responseContent);

                commodityBean = new Gson().fromJson(responseContent, CommodityBean.class);

                Activity activity = (Activity)view.getContext();

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(GameFragment.this)
                                .load(commodityBean.getCommodityResultBean().getCommodityPicture())
                                .error(R.drawable.error)
                                .placeholder(R.drawable.img3)
                                .into(imageView);
                    }
                });

            }
        });

    }
}

