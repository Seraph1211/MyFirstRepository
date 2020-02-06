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
import android.widget.Toast;

import com.example.carboncreditapplication.R;
import com.example.carboncreditapplication.beans.MerchantBean;
import com.example.carboncreditapplication.utils.Base64Utils;

import java.util.HashMap;

public class MerchantRegisterFragment extends Fragment {
    View view;
    private EditText editMerchantName;  //商家名称
    private EditText editMerchantPassword1;  //密码
    private EditText editMerchantPassword2;  //确认密码
    private EditText editMerchantPhoneNumber;  //联系电话
    private EditText editMerchantEmail;  //电子邮箱
    private EditText editMerchantAddress;  //联系地址
    private EditText editMerchantIntroduce;  //简介
    private EditText editImageSecurityCodeRegister; //图片验证码
    private EditText editEmailSecurityCode;  //邮箱验证码
    private ImageView imageSecurityCodeRegister; //验证码图片
    private Button buttonRegisterMerchant;  //注册按钮
    private Button buttonSendEamilCode;  //发送邮箱验证码

    private MerchantBean merchantBean;

    private int merchantId = 0;
    private int userId = 1;
    private String merchantName;
    private String merchantPassword;
    private String merchantPassword2;
    private String merchantPhoneNumber;
    private String merchantEmail;
    private String merchantAddress;
    private String merchantIntroduce;
    private String merchantImage = "";  //商家图片
    private String emailCode;  //邮箱验证码
    private String imageCode;  //图片验证码

    private String base64Code = null;  //从服务端获取的base64格式的图片信息

    private MerchantActivity activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_merchant_register, container, false);


        initView();


        return view;
    }

    //初始化各控件
    public void initView(){
        activity = (MerchantActivity)getActivity();

        editMerchantName = view.findViewById(R.id.editMerchantName);
        editMerchantPassword1 = view.findViewById(R.id.editMerchantPassword1);
        editMerchantPassword2 = view.findViewById(R.id.editMerchantPassword2);
        editMerchantPhoneNumber = view.findViewById(R.id.editMerchantPhoneNumber);
        editMerchantEmail = view.findViewById(R.id.editMerchantEmail);
        editMerchantAddress = view.findViewById(R.id.editMerchantAddress);
        editMerchantIntroduce = view.findViewById(R.id.editMerchantIntroduce);
        editImageSecurityCodeRegister = view.findViewById(R.id.editSecurityCodeRegister);
        editEmailSecurityCode = view.findViewById(R.id.editEmailSecurityCode);
        imageSecurityCodeRegister = view.findViewById(R.id.imageSecurityCodeRegister);
        buttonRegisterMerchant = view.findViewById(R.id.buttonRegisterMerchant);
        buttonSendEamilCode = view.findViewById(R.id.buttonSendEmailCode);

        //向服务端获取验证码图片的64base字符串，并转换为图片加载到image中
        if(activity.getBase64Code()!=null){
            Base64Utils.loadBase64Image(activity.getBase64Code(), imageSecurityCodeRegister);
        }


        /**
         * 注册监听事件
         * 先比较两次输入的密码是否一致，如果不一致则Toast提醒用户重新输入密码
         * 若密码一致，则将注册信息上传给服务端：bean+验证码, 同步访问
         * 根据服务端返回的信息执行相应的操作
         */
        buttonRegisterMerchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditTextData();
                if(!merchantPassword.equals(merchantPassword2)){  //两次输入密码不一致
                    Toast.makeText(getContext(), "两次输入密码不一致，请重新输入！", Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        });
    }

    //获取EditText中的数据（注册信息）
    public void getEditTextData(){
        merchantName = editMerchantName.getText().toString();
        merchantPassword = editMerchantPassword1.getText().toString();
        merchantPassword2 = editMerchantPassword2.getText().toString();
        merchantPhoneNumber = editMerchantPhoneNumber.getText().toString();
        merchantEmail = editMerchantEmail.getText().toString();
        merchantAddress = editMerchantAddress.getText().toString();
        merchantIntroduce = editMerchantIntroduce.getText().toString();
        imageCode = editImageSecurityCodeRegister.getText().toString();
        emailCode = editEmailSecurityCode.getText().toString();
    }


    public HashMap getRegisterInfoMap(){
        HashMap<String, Object> map = new HashMap<>();

        map.put("merchantId", merchantId);
        map.put("userId", userId);
        map.put("merchantPassword", merchantPassword);
        map.put("merchantPhoneNumber", merchantPhoneNumber);
        map.put("merchantEmail", merchantEmail);
        map.put("merchantName",merchantName);
        map.put("merchantAddress", merchantAddress);
        map.put("merchantIntroduce", merchantIntroduce);
        map.put("merchantImage",merchantImage);
        map.put("emailCode", emailCode);
        map.put("imageCode", imageCode);

        return map;
    }


}
