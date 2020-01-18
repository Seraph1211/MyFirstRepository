package com.example.carboncreditapplication.beans;

import com.google.gson.annotations.SerializedName;

public class UserInfoBean {
    @SerializedName("user_id")
    private int userId;
    @SerializedName("result")
    private UserInfoResultBean resultBean;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserInfoResultBean getResultBean() {
        return resultBean;
    }

    public void setResultBean(UserInfoResultBean resultBean) {
        this.resultBean = resultBean;
    }

    public static class UserInfoResultBean{
        @SerializedName("nickname")
        private String nickname;  //用户昵称
        @SerializedName("user_image_path")
        private String userImagePath;  //用户头像路径
        @SerializedName("city_id")
        private int cityId; //城市id
        @SerializedName("city_name")
        private String cityName;  //城市名称
        @SerializedName("user_level")
        private int userLevel;  //用户等级
        @SerializedName("sign_in_number")
        private int signInNumber;  //累计签到天数
        @SerializedName("carbon_credits_month")
        private int carbonCreditsMonth; //本月获取的碳积分
        @SerializedName("user_rank")
        private int userRank;  //本月个人碳积分所在城市排行

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUserImagePath() {
            return userImagePath;
        }

        public void setUserImagePath(String userImagePath) {
            this.userImagePath = userImagePath;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public int getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(int userLevel) {
            this.userLevel = userLevel;
        }

        public int getSignInNumber() {
            return signInNumber;
        }

        public void setSignInNumber(int signInNumber) {
            this.signInNumber = signInNumber;
        }

        public int getCarbonCreditsMonth() {
            return carbonCreditsMonth;
        }

        public void setCarbonCreditsMonth(int carbonCreditsMonth) {
            this.carbonCreditsMonth = carbonCreditsMonth;
        }

        public int getUserRank() {
            return userRank;
        }

        public void setUserRank(int userRank) {
            this.userRank = userRank;
        }
    }
}
