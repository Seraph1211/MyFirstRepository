package com.example.carboncreditapplication.bottomnavigation.userinfo.merchant;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import com.example.carboncreditapplication.R;

/**
 * 此活动创建时会从后台获取MerchantInfoBean(存储商家的相关信息),
 * 修改任意一项信息时会将该Bean传给SetMerchantInfoActivity,然后在SetMerchantInfoActivity中向后台提交修改后的bean
 */

public class MerchantInfoActivity extends AppCompatActivity implements View.OnClickListener{
    ConstraintLayout name;
    ConstraintLayout phoneNum;
    ConstraintLayout email;
    ConstraintLayout address;
    ConstraintLayout introduction;
    ConstraintLayout password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_info);

        initView();
    }


    public void initView(){
        name = findViewById(R.id.constraintLayoutMerchantInfoName);
        phoneNum = findViewById(R.id.constraintLayoutMerchantInfoPhoneNum);
        email = findViewById(R.id.constraintLayoutMerchantInfoEmail);
        address = findViewById(R.id.constraintLayoutMerchantInfoAddress);
        introduction = findViewById(R.id.constraintLayoutMerchantInfoIntroduce);
        password = findViewById(R.id.constraintLayoutMerchantInfoPassword);

        name.setOnClickListener(this);
        phoneNum.setOnClickListener(this);
        email.setOnClickListener(this);
        address.setOnClickListener(this);
        introduction.setOnClickListener(this);
        password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.constraintLayoutMerchantInfoName:{
                Toast.makeText(MerchantInfoActivity.this, " Set Name!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MerchantInfoActivity.this, SetMerchantInfoActivity.class));
                break;
            }
            case R.id.constraintLayoutMerchantInfoPhoneNum:{
                Toast.makeText(MerchantInfoActivity.this, " Set Phone Num!", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.constraintLayoutMerchantInfoEmail:{
                Toast.makeText(MerchantInfoActivity.this, " Set Email!", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.constraintLayoutMerchantInfoAddress:{
                Toast.makeText(MerchantInfoActivity.this, " Set Address!", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.constraintLayoutMerchantInfoIntroduce: {
                Toast.makeText(MerchantInfoActivity.this, " Set Introduction!", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    //半透明状态栏
    protected void setHalfTransparent() {

        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
