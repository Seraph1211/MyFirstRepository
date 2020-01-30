package com.example.carboncreditapplication.bottomnavigation.userinfo.merchant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.carboncreditapplication.R;

public class MerchantLoginFragment extends Fragment {
    View view;
    private EditText editPasswordLogin;
    private EditText editSecurityCodeLogin;
    private ImageView imageSecurityCodeLogin;
    private Button buttonMerchantLogin;

    private String passwordLogin;
    private String securityCodeLogin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_merchant_login, container, false);


        return view;
    }

    //初始化各控件
    public void initView(){
        editPasswordLogin = view.findViewById(R.id.editMerchantPasswordLogin);
        editSecurityCodeLogin = view.findViewById(R.id.editSecurityCodeLogin);
        buttonMerchantLogin = view.findViewById(R.id.buttonLoginMerchant);

        /**
         * 注册监听事件
         * 将验证码和密码提交给服务端，根据服务端返回的信息执行相应的操作
         */
        buttonMerchantLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //获取EditText中的数据
    public void getEditTextData(){
        passwordLogin = editPasswordLogin.getText().toString();
        securityCodeLogin = editSecurityCodeLogin.getText().toString();
    }

    //从服务端获取验证码图片的64base字符串，并转换成图片加载到image中, 异步
    public void getSecurityCodeImage(){

    }

}
