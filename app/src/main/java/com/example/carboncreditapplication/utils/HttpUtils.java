package com.example.carboncreditapplication.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HttpUtils {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = "HttpUtils";
    public static String basicUrl = "http：//121.36.4.52:8090/carbon_credits_system";
    public static final String carbonCreditsInfoUrl = "http://121.36.4.52:8090/carbon_credits_system/carbonCredits/getCreditsInfo?user_id="+UserInfo.userId;
    public static final String userInfoUrl = "http://121.36.4.52:8090/carbon_credits_system/user/getUserInfo";
    public static final String getCarbonCreditsUrl = "http://121.36.4.52:8090/carbon_credits_system/carbonCredits/receiveCarbonCredits";
    public static final String monthlyReportInfoUrl = "http://121.36.4.52:8090/carbon_credits_system/user/getMonthlyReport";
    public static final String userRankingInfoUrl = "http://121.36.4.52:8090/carbon_credits_system/user/getUserRankingList";
    public static final String cardPackageUrl = "http://121.36.4.52:8090/carbon_credits_system/user/getUserCoupon?user_id="+UserInfo.userId+"&page_no=1&page_size=50";
    public static final String friendsInfoUrl = "http://121.36.4.52:8090/carbon_credits_system/carbon_credits_system/user/getFriendInfo?user_id="+UserInfo.userId;
    public static final String teamInfoUrl = "http://121.36.4.52:8090/carbon_credits_system/user/getTeamInfo?user_id="+UserInfo.userId;
    public static final String deleteTeamMemberUrl = "http://121.36.4.52:8090/carbon_credits_system/user/deleteUserFromTeam";
    public static final String createTeamUrl = "http://121.36.4.52:8090/carbon_credits_system/user/createTeam";
    public static final String userSignInUrl = "http://121.36.4.52:8090/carbon_credits_system/user/signIn";
    public static final String addUserToTeamUrl = "http://121.36.4.52:8090/carbon_credits_system/user/addUserToTeam";
    public static final String giveAwayCreditsUrl = "http://121.36.4.52:8090/carbon_credits_system/user/giveAway";
    public static final String commodityInfoUrl = "http://121.36.4.52:8090/carbon_credits_system/good/getGoods?page_no=1&page_size=10&good_type=2";
    public static final String merchantHomeUrl = "http://121.36.4.52:8090/carbon_credits_system/Merchant/home?userId="+UserInfo.userId;
    public static final String merchantModifyUrl = "http://121.36.4.52:8090/carbon_credits_system/Merchant/modify";
    public static final String merchantInfoUrl = "http://121.36.4.52:8090/carbon_credits_system/Merchant/getInfo";
    public static final String merchantModifyPasswordUrl = "http://121.36.4.52:8090/carbon_credits_system/Merchant/modifyPassword?userId="+UserInfo.userId;
    public static final String emailSecurityCodeUrl = "http://121.36.4.52:8090/carbon_credits_system/Merchant/emailCode";
    public static final String merchantSignUpUrl = "http://121.36.4.52:8090/carbon_credits_system/Merchant/signUp";
    public static final String merchantLoginUrl = "http://121.36.4.52:8090/carbon_credits_system/Merchant/login";

    //private static final TAG = "HttpUtils";
    public static void postJson(){
        OkHttpClient client = new OkHttpClient();

        HashMap<String, String> map = new HashMap<>();
        map.put("user_id","1");
        //map.put("city","上海");

        Gson gson = new Gson();
        String data = gson.toJson(map);

        /*
        Log.d(TAG, "postJson: gson:"+gson.toString());
        Log.d(TAG, "postJson: data:"+data);
        */


        HashMap<String, Integer> map2 = new HashMap<>();


        RequestBody formBody = RequestBody.create(JSON, data);
        /*
        RequestBody formBody = new FormBody.Builder()
                .add("user_id", "1")
                .build();
               */

        Request request = new Request.Builder()
                .post(formBody)
                .url("http://121.36.4.52:8080/app/user/home")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("onFailure", "onFailure: 连接失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("Http", "onResponse: 连接成功");
                String responseContent =  response.body().string();
                Log.d("http", "onResponse: responseContent:"+responseContent);
            }
        });
    }

    //post Bean
    public static <T> void postBean(String address, T bean, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        String JSONString = new Gson().toJson(bean);
        RequestBody formBody = RequestBody.create(JSON, JSONString);
        Log.d(TAG, "postBean: "+JSONString);
        Request request = new Request.Builder()
                .url(address)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(callback);
    }

    //将数据封装成HashMap发送给服务器
    public static void postMap(String address, HashMap map, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();

        String JSONString = new Gson().toJson(map);

        RequestBody formBody = RequestBody.create(JSON, JSONString);

        Log.d(TAG, "postMap: "+JSONString);

        Request request = new Request.Builder()
                .url(address)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void post(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = RequestBody.create(null, "");

        Request request = new Request.Builder()
                .url(address)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(callback);

    }

    public static void post(String address, int userId, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();

       RequestBody formBody = new FormBody.Builder()
               .add("userId", String.valueOf(userId))
               .build();

        Request request = new Request.Builder()
                .url(address)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(callback);

    }

    //异步请求
    public static void getInfo(String address, int userId, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address+userId)
                .get()
                .build();

        client.newCall(request).enqueue(callback);
    }


    public static void getInfo(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .get()
                .build();

        client.newCall(request).enqueue(callback);
    }

    /**
     * 同步请求
     * @param address  url
     * @param userId  用户id
     * @return 数据请求成功，返回Json字符串。反之，返回null
     */
    public static String getInfo(String address, int userId){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address+userId)
                .get()
                .build();

        Call requestCall = client.newCall(request);

        Response response = null;


        String responseContent = null;
        if(response.isSuccessful()){  //请求成功
            try {
                responseContent = response.body().string();
            } catch (IOException e) {
                Log.d(TAG, "getInfo: response.body.string()失败！");
                e.printStackTrace();
            }
        }

        return responseContent;
    }
}
