package com.example.carboncreditapplication.bottomnavigation.home.store.store1;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carboncreditapplication.R;

public class StoreNavigationView extends LinearLayout {

    private TextView textView;

    public StoreNavigationView(Context context) {
        super(context,null);
    }

    public StoreNavigationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StoreNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.view_store_navigation, this, true);
        textView = view.findViewById(R.id.hunzi);
    }
}
