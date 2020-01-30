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
import com.example.carboncreditapplication.beans.MerchantBean;

public class MerchantRegisterFragment extends Fragment {
    View view;
    private EditText editMerchantName;  //商家名称
    private EditText editMerchantPassword1;  //密码
    private EditText editMerchantPassword2;  //确认密码
    private EditText editMerchantPhoneNumber;  //联系电话
    private EditText editMerchantEmail;  //电子邮箱
    private EditText editMerchantAddress;  //联系地址
    private EditText editMerchantIntroduce;  //简介
    private EditText editSecurityCodeRegister; //验证码
    private ImageView imageSecurityCodeRegister; //验证码图片
    private Button buttonRegisterMerchant;  //注册按钮

    private MerchantBean merchantBean;

    private String merchantName;
    private String merchantPassword1;
    private String merchantPassword2;
    private String merchantPhoneNumber;
    private String merchantEmail;
    private String merchantAddress;
    private String merchantIntroduce;
    private String registerSecurityCode;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_merchant_register, container, false);


        return view;
    }

    //初始化各控件
    public void initView(){
        editMerchantName = view.findViewById(R.id.editMerchantName);
        editMerchantPassword1 = view.findViewById(R.id.editMerchantPassword1);
        editMerchantPassword2 = view.findViewById(R.id.editMerchantPassword2);
        editMerchantPhoneNumber = view.findViewById(R.id.editMerchantPhoneNumber);
        editMerchantEmail = view.findViewById(R.id.editMerchantEmail);
        editMerchantAddress = view.findViewById(R.id.editMerchantAddress);
        editMerchantIntroduce = view.findViewById(R.id.editMerchantIntroduce);
        editSecurityCodeRegister = view.findViewById(R.id.editSecurityCodeRegister);
        imageSecurityCodeRegister = view.findViewById(R.id.imageSecurityCodeRegister);
        buttonRegisterMerchant = view.findViewById(R.id.buttonRegisterMerchant);

        /**
         * 注册监听事件
         * 先比较两次输入的密码是否一致，如果不一致则Toast提醒用户重新输入密码
         * 若密码一致，则将注册信息上传给服务端：bean+验证码, 同步访问
         * 根据服务端返回的信息执行相应的操作
         */
        buttonRegisterMerchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //获取EditText中的数据（注册信息）
    public void getEditTextData(){
        merchantName = editMerchantName.getText().toString();
        merchantPassword1 = editMerchantPassword1.getText().toString();
        merchantPassword2 = editMerchantPassword2.getText().toString();
        merchantPhoneNumber = editMerchantPhoneNumber.getText().toString();
        merchantEmail = editMerchantEmail.getText().toString();
        merchantAddress = editMerchantAddress.getText().toString();
        merchantIntroduce = editMerchantIntroduce.getText().toString();
        registerSecurityCode = editSecurityCodeRegister.getText().toString();
    }

    //向服务端获取验证码图片的64base字符串，并转换为图片加载到image中
    public void getSecurityCodeImage(){

    }

}
