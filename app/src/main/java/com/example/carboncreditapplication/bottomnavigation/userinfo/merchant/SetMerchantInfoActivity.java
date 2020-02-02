package com.example.carboncreditapplication.bottomnavigation.userinfo.merchant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.carboncreditapplication.R;

public class SetMerchantInfoActivity extends AppCompatActivity {

    private EditText editSetMerchantInfo;
    private Button buttonSetMerchantInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_merchant_info);

        initView();

    }

    public void initView(){
        editSetMerchantInfo = findViewById(R.id.editSetMerchantInfo);
        buttonSetMerchantInfo = findViewById(R.id.buttonSetMerchantInfo);
    }

}
