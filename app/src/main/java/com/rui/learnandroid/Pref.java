package com.rui.learnandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

@SuppressLint("CommitPrefEdits")
public class Pref {
    private static final String TAG = "Pref";
    @SuppressLint("StaticFieldLeak")
    static Context context = null;
    private static final String FIRST_USING = "first_using";

    private static SharedPreferences def() {
        Log.d(TAG, String.valueOf(Pref.context));
        return PreferenceManager.getDefaultSharedPreferences(Pref.context);
    }

    static void setAppContext(Context context) {
        Pref.context = context;
    }

    public static boolean isFirstUsing() {
        return def().getBoolean(FIRST_USING, true);
    }


    public static void noFirst() {
        def().edit().putBoolean(FIRST_USING, false).apply();
    }


    public static void setToken(String token) {
        def().edit().putString("token", token).apply();
    }

    public static String getToken() {
        return def().getString("token", null);
    }


    public static void setUser(String user) {
        def().edit().putString("user", user).apply();
    }

    public static String getUser() {
        return def().getString("user", null);
    }

    public static void setPassword(String password) {
        def().edit().putString("password", password).apply();
    }

    public static String getPassword() {
        return def().getString("password", null);
    }

    public static void setEmail(String password) {
        def().edit().putString("email", password).apply();
    }

    public static String getEmail() {
        return def().getString("email", null);
    }
}
