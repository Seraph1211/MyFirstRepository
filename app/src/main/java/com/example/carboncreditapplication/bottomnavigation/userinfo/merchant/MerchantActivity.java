package com.example.carboncreditapplication.bottomnavigation.userinfo.merchant;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.carboncreditapplication.R;


/**
 * 一个Activity,多个Fragment。
 * 返回上一个Fragment: activity.setFragment(LastFragment);
 */

public class MerchantActivity extends AppCompatActivity {

    private TextView textMerchant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);

        initView();

        setFragment(new MerchantHomeFragment());
    }

    public void initView(){
        textMerchant = findViewById(R.id.textMerchant);
    }

    /**
     * 根据user_id向服务端查询应该加载哪一个碎片(同时更改toolbar上的文字)
     * 未注册商家：注册界面
     * 已注册但已超出登录时限：登录界面
     * 已注册且未超出登录时限：商家首页
     */
    public void setFragment(){

    }

    public void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameMerchant, fragment);
        fragmentTransaction.commit();
    }

    public TextView getTextMerchant() {
        return textMerchant;
    }

}