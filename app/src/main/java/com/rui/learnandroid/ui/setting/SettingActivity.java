package com.rui.learnandroid.ui.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SettingActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String a = intent.getStringExtra("a");
        Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
    }
}
