package com.example.carboncreditapplication.bottomnavigation.userinfo.merchant;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.carboncreditapplication.R;

public class SetMerchantInfoActivity extends AppCompatActivity {

    private EditText editSetMerchantInfo;
    private Button buttonSetMerchantInfo;
    private static final String TAG = "SetMerchantInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_merchant_info);

        initView();

        if(getToken().equals("")){
            editSetMerchantInfo.setText("token为空");
        }else{
            editSetMerchantInfo.setText(getToken());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setToken(editSetMerchantInfo.getText().toString());
    }

    public void initView(){
        editSetMerchantInfo = findViewById(R.id.editSetMerchantInfo);
        buttonSetMerchantInfo = findViewById(R.id.buttonSetMerchantInfo);
    }

    public String getToken(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SetMerchantInfoActivity.this);
        String tokenContent = preferences.getString("token", "empty");

        Log.d(TAG, "getToken: "+tokenContent);

        if(tokenContent.equals("empty")){  //没有存储的token信息
            //则新建一个token（为空）并用sp存起来
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("token", "");
            editor.apply();
            return "";
        }

        return tokenContent;
    }

    public void setToken(String tokenContent){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SetMerchantInfoActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", tokenContent);
        editor.apply();
        Log.d(TAG, "setToken: "+preferences.getString("token", "Empty"));
    }

}
