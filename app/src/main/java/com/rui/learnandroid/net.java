package com.rui.learnandroid;

import android.util.Log;
import android.widget.Toast;

import com.rui.learnandroid.ui.main.MainActivity2;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class net {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String TAG = "net";
    private static String mresponse;

    public static String get(String url) {
        try {
            Request request = new Request.Builder().url(url).method("GET", null).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                mresponse = response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mresponse;
    }
    public static String get(String url,String token) {
        try {
            Request request = new Request.Builder().url(url).method("GET", null)
                    .addHeader("Authorization",token)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                mresponse = response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mresponse;
    }
    public static String post(String url, String body) {
        try {
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody mbody = RequestBody.create(body, mediaType);
            Request request = new Request.Builder().url(url).method("POST", mbody).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                mresponse = response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mresponse;
    }
}
