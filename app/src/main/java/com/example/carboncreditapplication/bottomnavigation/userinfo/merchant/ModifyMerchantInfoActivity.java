package com.example.carboncreditapplication.bottomnavigation.userinfo.merchant;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.MerchantBean;
import com.example.carboncreditapplication.utils.StatusBarUtils;

public class ModifyMerchantInfoActivity extends AppCompatActivity {

    private TextView textBar;
    private EditText editSetMerchantInfo;
    private Button buttonSetMerchantInfo;
    private static final String TAG = "SetMerchantInfoActivity";
    private MerchantBean merchantBean;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_merchant_info);

        StatusBarUtils.setStatusBarColor(ModifyMerchantInfoActivity.this, R.color.colorWhite);  //设置状态栏颜色
        StatusBarUtils.setLightStatusBar(ModifyMerchantInfoActivity.this, true, true);  //状态栏字体颜色-黑

        initData();
        initView();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void initData(){
        merchantBean = (MerchantBean) getIntent().getSerializableExtra("MerchantBean");
        Log.d(TAG, "initData: bean="+merchantBean);
        type = getIntent().getIntExtra("type", -1);
    }

    public void initView(){
        editSetMerchantInfo = findViewById(R.id.editSetMerchantInfo);
        buttonSetMerchantInfo = findViewById(R.id.buttonSetMerchantInfo);
        textBar = findViewById(R.id.textModifyMerchantInfo);

        //加载碎片
        if(type == MerchantInfoActivity.MERCHANT_PASSWORD){
            setFragment(new ModifyPasswordFragment());
        }else {
            setFragment(new ModifyNormalInfoFragment());
        }
    }

    public void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameModifyInfo, fragment);
        fragmentTransaction.commit();
    }

    public MerchantBean getMerchantBean(){
        return merchantBean;
    }

    public int getType(){
        return type;
    }


}
