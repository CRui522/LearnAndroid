package com.rui.learnandroid.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.gson.Gson;
import com.rui.learnandroid.Pref;
import com.rui.learnandroid.R;
import com.rui.learnandroid.bean.user.Data;
import com.rui.learnandroid.bean.user.UserData;
import com.rui.learnandroid.databinding.ActivityMain2Binding;
import com.rui.learnandroid.net;
import com.rui.learnandroid.ui.fragment.user.login.LoginFragment;
import com.rui.learnandroid.ui.setting.SettingActivity;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;
    private static final String TAG = "MainActivity2";
    final private int RED = 110;
    final private int GREEN = 111;
    final private int BLUE = 112;
    final private int YELLOW = 113;
    final private int GRAY = 114;
    final private int CYAN = 115;
    final private int BLACK = 116;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main2);

        NavigationUI.setupWithNavController(binding.navView, navController);


        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {

            if (destination.getId() != R.id.navigation_user) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}