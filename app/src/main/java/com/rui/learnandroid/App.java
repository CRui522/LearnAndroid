package com.rui.learnandroid;

import android.app.Application;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.gson.Gson;
import com.rui.learnandroid.bean.user.UserData;

public class App extends Application {

    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        Pref.setAppContext(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Toast.makeText(this, "夜间模式", Toast.LENGTH_SHORT).show();
        //Pref.setToken("vsfbf");
        ;
//        String name = Pref.getUser();
//        String password = Pref.getPassword();
//        if (name != null && password != null) {
//            Log.d(TAG, name + password);
//        }
//        String token = Pref.getToken();
//        if (token != null) {
//            Log.d(TAG, token);
//        }
        String token = Pref.getToken();
        if (token != null) {
            Log.d(TAG, token);
            getInfo(token);
        }
    }

    public void getInfo(String token) {
        new Thread(() -> {
            Looper.prepare();
            String data = net.get("http://124.93.196.45:10001/prod-api/api/common/user/getInfo", token);
            UserData data1 = new Gson().fromJson(data, UserData.class);
            if (data1.getCode() == 200) {
//                String userName = data1.getUser().getUserName();
                String email = data1.getUser().getEmail();
//                Pref.setUser(userName);
                Pref.setEmail(email);
            } else {
                Toast.makeText(this, data1.getMsg(), Toast.LENGTH_SHORT).show();
            }
            Looper.loop();
        }).start();
    }
}
