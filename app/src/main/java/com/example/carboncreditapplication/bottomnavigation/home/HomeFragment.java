package com.example.carboncreditapplication.bottomnavigation.home;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dyhdyh.widget.loadingbar2.LoadingBar;
import com.example.carboncreditapplication.R;

import static android.support.constraint.Constraints.TAG;


public class HomeFragment extends Fragment {
    private ImageViewPager imageViewPager;
    View view;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        imageViewPager = new ImageViewPager(getContext(), view);
        imageViewPager.init();

        //以View的形式显示loading
        //这里的parent需要是可覆盖的布局，如果传的不是可覆盖的布局，会一直随着层级往上找
        //FrameLayout/RelativeLayout/ConstraintLayout/DrawerLayout/CoordinatorLayout/CardView
        //LoadingBar.dialog(getContext()).show();



        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: HomeFragment");
        if(imageViewPager.thread!=null){
            imageViewPager.thread.interrupt();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: HomeFragment");
    }
}