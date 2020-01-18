package com.example.carboncreditapplication.bottomnavigation.home.store;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.bottomnavigation.BottomNavigationActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 此积分商城通过tablayout来实现商品的分区
 */

public class StoreActivity extends AppCompatActivity {
    private static final String TAG = "StoreFragment";
    private TabLayout tabLayout;
    private CustomScrollView customScrollView;
    private LinearLayout linearContainer;
    private String[] tabTxt = {"全部","人气专区", "商品折扣券", "乘车券", "折扣专区"};
    //内容块view的集合
    private List<LinearLayout> linearLayoutList = new ArrayList<>();
    //判读是否是scrollview主动引起的滑动，true-是，false-否，由tablayout引起的
    private boolean isScroll;
    //记录上一次位置，防止在同一内容块里滑动 重复定位到tablayout
    private int lastPos = 0;
    //监听判断最后一个模块的高度，不满一屏时让最后一个模块撑满屏幕
    private ViewTreeObserver.OnGlobalLayoutListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        tabLayout = findViewById(R.id.tablayout);
        customScrollView = findViewById(R.id.scrollView);
        linearContainer = findViewById(R.id.container);

        //模拟数据，填充scrollview
        StoreNavigationView storeNavigationView = new StoreNavigationView(this);
        linearLayoutList.add(storeNavigationView);
        linearContainer.addView(storeNavigationView);
        for (int i = 1; i < tabTxt.length; i++) {
            AnchorView anchorView = new AnchorView(this);
            anchorView.setAnchorTxt(tabTxt[i]);
            anchorView.setContentTxt(tabTxt[i]);
            linearLayoutList.add(anchorView);
            linearContainer.addView(anchorView);
        }

        //tablayout设置标签
        for (int i = 0; i < tabTxt.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabTxt[i]));
        }

        listener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                int screenH = getScreenHeight();
                int statusBarH = getStatusBarHeight(StoreActivity.this);
                int tabH = tabLayout.getHeight();
                //计算内容块所在的高度，全屏高度-状态栏高度-tablayout的高度-内容container的padding 16dp
                int lastH = screenH - statusBarH - tabH - 16 * 3;
                AnchorView lastView = (AnchorView) linearLayoutList.get(linearLayoutList.size() - 1);
                //当最后一个view 高度小于内容块高度时，设置其高度撑满
                if (lastView.getHeight() < lastH) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.height = lastH;
                    lastView.setLayoutParams(params);
                }
                linearContainer.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
            }
        };
        linearContainer.getViewTreeObserver().addOnGlobalLayoutListener(listener);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //点击标签，使scrollview滑动，isScroll置false
                isScroll = false;
                int pos = tab.getPosition();
                Log.d(TAG, "onTabSelected: tabPosition=" + pos);
                int top = linearLayoutList.get(pos).getTop();
                Log.d(TAG, "onTabSelected: top=" + top);
                if (pos == 0) {
                    top = 0;
                }
                customScrollView.smoothScrollTo(0, top);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        customScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //当滑动由scrollview触发时，isScroll 置true
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    isScroll = true;
                }
                return false;
            }
        });

        customScrollView.setCallbacks(new CustomScrollView.Callbacks() {
            @Override
            public void onScrollChanged(int x, int y, int oldx, int oldy) {
                if (isScroll) {
                    for (int i = tabTxt.length - 1; i >= 0; i--) {
                        Log.d(TAG, "onScrollChanged: setCallbacks:i=" + i);
                        Log.d(TAG, "onScrollChanged: setCallbacks:y=" + y);
                        //根据滑动距离，对比各模块距离父布局顶部的高度判断
                        if (y == 0) {
                            setScrollPos(0);
                        }
                        if (y > linearLayoutList.get(i).getTop() - 10) {
                            setScrollPos(i);
                            break;
                        }
                    }
                }
            }
        });
    }

    //tablayout对应标签的切换
    private void setScrollPos(int newPos) {
        if (lastPos != newPos) {
            //该方法不会触发tablayout 的onTabSelected 监听
            tabLayout.setScrollPosition(newPos, 0, true);
        }
        lastPos = newPos;
    }

    private int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_store_back:{
                finish();
                startActivity(new Intent(StoreActivity.this, BottomNavigationActivity.class));
            }
        }
    }

}
