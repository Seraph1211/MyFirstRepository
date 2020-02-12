package com.example.carboncreditapplication.bottomnavigation.userinfo.merchant;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.MerchantBean;
import com.example.carboncreditapplication.utils.HttpUtils;
import com.example.carboncreditapplication.utils.MySharedPreferencesUtils;
import com.example.carboncreditapplication.utils.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 此活动创建时会从后台获取MerchantInfoBean(存储商家的相关信息),
 * 修改任意一项信息时会将该Bean传给SetMerchantInfoActivity,然后在SetMerchantInfoActivity中向后台提交修改后的bean
 */

public class MerchantInfoActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MerchantInfoActivity";
    static final int MERCHANT_NAME = 12;
    static final int MERCHANT_PHONE = 13;
    static final int MERCHANT_ADDRESS = 14;
    static final int MERCHANT_INTRODUCTION = 15;
    static final int MERCHANT_PASSWORD = 16;
    private ConstraintLayout name;
    private ConstraintLayout phoneNum;
    private ConstraintLayout email;
    private ConstraintLayout address;
    private ConstraintLayout introduction;
    private ConstraintLayout password;
    private TextView textMerchantName;
    private TextView textMerchantPhone;
    private TextView textMerchantAddress;
    private TextView textMerchantIntroduction;
    private TextView textMerchantEamil;

    private int merchantId;
    private String merchantName;
    private String merchantPhone;
    private String merchantEmail;
    private String merchantAddress;
    private String merchantIntroduction;

    private MerchantBean bean = new MerchantBean();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_info);

        initView();
        initData();
    }


    public void initView(){
        name = findViewById(R.id.constraintLayoutMerchantInfoName);
        phoneNum = findViewById(R.id.constraintLayoutMerchantInfoPhoneNum);
        email = findViewById(R.id.constraintLayoutMerchantInfoEmail);
        address = findViewById(R.id.constraintLayoutMerchantInfoAddress);
        introduction = findViewById(R.id.constraintLayoutMerchantInfoIntroduce);
        password = findViewById(R.id.constraintLayoutMerchantInfoPassword);

        textMerchantIntroduction = findViewById(R.id.textMerchantIntroduce);
        textMerchantPhone = findViewById(R.id.textMerchantPhoneNum);
        textMerchantEamil = findViewById(R.id.textMerchantEmail);
        textMerchantAddress = findViewById(R.id.textMerchantAddress);
        textMerchantName = findViewById(R.id.textMerchantName);

        name.setOnClickListener(this);
        phoneNum.setOnClickListener(this);
        email.setOnClickListener(this);
        address.setOnClickListener(this);
        introduction.setOnClickListener(this);
        password.setOnClickListener(this);
    }

    public void initData(){
        merchantId = MySharedPreferencesUtils.getInt(MerchantInfoActivity.this, "merchant_id");
        merchantName = MySharedPreferencesUtils.getString(MerchantInfoActivity.this, "merchant_name");
        merchantPhone = MySharedPreferencesUtils.getString(MerchantInfoActivity.this, "merchant_phone");
        merchantAddress = MySharedPreferencesUtils.getString(MerchantInfoActivity.this, "merchant_address");
        merchantEmail = MySharedPreferencesUtils.getString(MerchantInfoActivity.this, "merchant_email");
        merchantIntroduction = MySharedPreferencesUtils.getString(MerchantInfoActivity.this, "merchant_introduction");

        if(merchantId==-2 || merchantName.equals("empty") || merchantPhone.equals("empty") || merchantEmail.equals("empty") || merchantAddress.equals("empty")
        || merchantIntroduction.equals("empty")){  //任意一项数据没从sp中拿到时，请求网络数据
            queryMerchantInfo();
        }else {
            textMerchantName.setText(merchantName);
            textMerchantPhone.setText(merchantPhone);
            textMerchantEamil.setText(merchantEmail);
            textMerchantAddress.setText(merchantAddress);
            textMerchantIntroduction.setText(merchantIntroduction);

            bean.setMerchantId(merchantId);
            bean.setUserId(UserInfo.userId);
            bean.setMerchantName(merchantName);
            bean.setMerchantPhoneNumber(merchantPhone);
            bean.setMerchantEmail(merchantEmail);
            bean.setMerchantAddress(merchantAddress);
            bean.setMerchantIntroduce(merchantIntroduction);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.constraintLayoutMerchantInfoName:{
                Toast.makeText(MerchantInfoActivity.this, " Set Name!", Toast.LENGTH_SHORT).show();
                startModifyMerchantInfoActivity(MERCHANT_NAME);
                break;
            }
            case R.id.constraintLayoutMerchantInfoPhoneNum:{
                Toast.makeText(MerchantInfoActivity.this, " Set Phone Num!", Toast.LENGTH_SHORT).show();
                startModifyMerchantInfoActivity(MERCHANT_PHONE);
                break;
            }
            case R.id.constraintLayoutMerchantInfoEmail:{
                Toast.makeText(MerchantInfoActivity.this, "电子邮箱不可修改！", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.constraintLayoutMerchantInfoAddress:{
                Toast.makeText(MerchantInfoActivity.this, " Set Address!", Toast.LENGTH_SHORT).show();
                startModifyMerchantInfoActivity(MERCHANT_ADDRESS);
                break;
            }
            case R.id.constraintLayoutMerchantInfoIntroduce: {
                Toast.makeText(MerchantInfoActivity.this, " Set Introduction!", Toast.LENGTH_SHORT).show();
                startModifyMerchantInfoActivity(MERCHANT_INTRODUCTION);
                break;
            }
            case R.id.constraintLayoutMerchantInfoPassword:{
                startModifyMerchantInfoActivity(MERCHANT_PASSWORD);
                break;
            }
        }
    }

    public void queryMerchantInfo(){
        HttpUtils.getInfo(HttpUtils.merchantInfoUrl + "?userId=" + UserInfo.userId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MerchantInfoActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();
                Log.d(TAG, "onResponse: "+responseContent);

                try {
                    JSONObject jsonObject = new JSONObject(responseContent);

                    bean.setUserId(jsonObject.getInt("userId"));
                    bean.setMerchantPhoneNumber(jsonObject.getString("merchantPhoneNumber"));
                    bean.setMerchantEmail(jsonObject.getString("merchantEmail"));
                    bean.setMerchantName(jsonObject.getString("merchant_name"));
                    bean.setMerchantAddress(jsonObject.getString("merchant_address"));
                    bean.setMerchantIntroduce(jsonObject.getString("merchantIntroduce"));
                    bean.setMerchantImage(jsonObject.getString("merchantImage"));

                    MySharedPreferencesUtils.saveMerchantInfo(MerchantInfoActivity.this, bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(bean.getUserId()==-1 || TextUtils.isEmpty(bean.getMerchantName()) || TextUtils.isEmpty(bean.getMerchantPhoneNumber())
                || TextUtils.isEmpty(bean.getMerchantEmail()) || TextUtils.isEmpty(bean.getMerchantAddress())
                || TextUtils.isEmpty(bean.getMerchantIntroduce())){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MerchantInfoActivity.this, "访问网络数据失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textMerchantName.setText(bean.getMerchantName());
                            textMerchantPhone.setText(bean.getMerchantPhoneNumber());
                            textMerchantEamil.setText(bean.getMerchantEmail());
                            textMerchantAddress.setText(bean.getMerchantAddress());
                            textMerchantIntroduction.setText(bean.getMerchantIntroduce());
                        }
                    });
                }

            }
        });
    }

    /**
     * 通过type来告知SetMerchantInfoActivity要修改的是哪一项信息
     */
    public void startModifyMerchantInfoActivity(int type){
        Intent intent = new Intent(MerchantInfoActivity.this, ModifyMerchantInfoActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("MerchantBean", bean);
        startActivity(intent);
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
