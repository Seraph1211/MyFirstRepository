package com.example.carboncreditapplication.bottomnavigation.userinfo.merchant;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 一个Activity,多个Fragment。
 * 返回上一个Fragment: activity.setFragment(LastFragment);
 * 从服务端拿到数据后再setFragment
 */

public class MerchantActivity extends AppCompatActivity {

    private static final String TAG = "MerchantActivity";
    private TextView textMerchant;

    private String base64Code = null;  //从服务端获取的base64格式的图片信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);

        initView();

        //queryMerchantInfo();

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

    public void queryMerchantInfo(){
        HttpUtils.getInfo(HttpUtils.merchantHomeUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: ");
                String responseContent = response.body().string();
                Log.d(TAG, "onResponse: responseContent="+responseContent);

                String result = null;

                //使用JSONObject解析服务器返回的json字符串
                JSONObject jsonObject = new JSONObject();
                try {
                    result = jsonObject.getString("result");

                    if(!result.equals("自动登陆成功")){
                        base64Code = jsonObject.getString("image");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onResponse: 使用JSONObject解析数据时出错！");
                    Toast.makeText(MerchantActivity.this, "从服务器获取数据失败!", Toast.LENGTH_SHORT).show();
                }

                //根据服务器返回的结果来选择加载的碎片
                if(!TextUtils.isEmpty(result)){
                    if(result.equals("自动登陆成功")){
                        setFragment(new MerchantHomeFragment());
                    }else if(result.equals("已注册")){
                        setFragment(new MerchantLoginFragment());
                    }else if(result.equals("未注册")){
                        setFragment(new MerchantRegisterFragment());
                    }
                }


            }
        });
    }

    public String getToken(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MerchantActivity.this);
        String tokenContent = preferences.getString("token", "empty");

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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MerchantActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", tokenContent);
        editor.apply();
        Log.d(TAG, "setToken: "+preferences.getString("token", "Empty"));
    }

    public String getBase64Code(){
        return base64Code;
    }

}
