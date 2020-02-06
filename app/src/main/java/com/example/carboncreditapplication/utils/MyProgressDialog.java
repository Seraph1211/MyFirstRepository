package com.example.carboncreditapplication.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carboncreditapplication.R;

/**
 * 通用加载提示框MyProgressDialog类
 */
public class MyProgressDialog extends Dialog {

    private ImageView mLoadingImg;
    private TextView mMessageTV;

    public MyProgressDialog(Context context) {
        super(context, R.style.Theme_AppCompat_DayNight_Dialog_Alert);  //?
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.process_dialog);
        mLoadingImg = (ImageView) findViewById(R.id.loadingimg);
        mMessageTV = (TextView) findViewById(R.id.messagetv);
    }

    /**
     * 当没有消息时只展示转圈
     *
     * @param message
     */
    public void showMessage(String message) {
        try {
            super.show();
            if (!TextUtils.isEmpty(message)) {
                mMessageTV.setText(message);
                mMessageTV.setVisibility(View.VISIBLE);
            } else {
                mMessageTV.setText("");
                mMessageTV.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        mLoadingImg.startAnimation(animation);
    }
}

