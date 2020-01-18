package com.example.carboncreditapplication.utils;

import android.util.Log;

import com.google.gson.Gson;

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
    public static String basicUrl = "http：//121.36.4.52:8090";
    public static final String carbonCreditsInfoUrl = "http://121.36.4.52:8090/carbonCredits/getCreditsInfo?user_id=";
    public static final String userInfoUrl = "http://121.36.4.52:8090/user/getUserInfo?user_id=";

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