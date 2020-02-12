package com.example.carboncreditapplication.beans;

import java.io.Serializable;

public class MerchantBean implements Serializable {

    /**
     * merchantId : 0
     * userId : 1
     * merchantPassword : 123456zyx
     * merchantPhoneNumber : 11111111111
     * merchantEmail : 738667591@qq.com
     * merchantName : zyx
     * merchantAddress : 某某大街
     * merchantIntroduce : 贩卖啥啥啥
     * merchantImage : asdfgasfds.jpg
     */
    private int merchantId;
    private int userId;
    private String merchantPassword;
    private String merchantPhoneNumber;
    private String merchantEmail;
    private String merchantName;
    private String merchantAddress;
    private String merchantIntroduce;
    private String merchantImage;

    public MerchantBean(){
        merchantId = -1;
        userId = -1;
        merchantPassword = "";
        merchantPhoneNumber = "";
        merchantEmail = "";
        merchantName = "";
        merchantAddress = "";
        merchantIntroduce = "";
        merchantImage = "";
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMerchantPassword() {
        return merchantPassword;
    }

    public void setMerchantPassword(String merchantPassword) {
        this.merchantPassword = merchantPassword;
    }

    public String getMerchantPhoneNumber() {
        return merchantPhoneNumber;
    }

    public void setMerchantPhoneNumber(String merchantPhoneNumber) {
        this.merchantPhoneNumber = merchantPhoneNumber;
    }

    public String getMerchantEmail() {
        return merchantEmail;
    }

    public void setMerchantEmail(String merchantEmail) {
        this.merchantEmail = merchantEmail;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public String getMerchantIntroduce() {
        return merchantIntroduce;
    }

    public void setMerchantIntroduce(String merchantIntroduce) {
        this.merchantIntroduce = merchantIntroduce;
    }

    public String getMerchantImage() {
        return merchantImage;
    }

    public void setMerchantImage(String merchantImage) {
        this.merchantImage = merchantImage;
    }
}
