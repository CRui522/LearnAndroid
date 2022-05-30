package com.rui.learnandroid.ui.fragment.user.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.rui.learnandroid.MainActivity;
import com.rui.learnandroid.Pref;
import com.rui.learnandroid.R;
import com.rui.learnandroid.bean.user.Data;
import com.rui.learnandroid.databinding.ActivityLoginBinding;
import com.rui.learnandroid.net;
import com.rui.learnandroid.ui.main.MainActivity2;

import java.util.List;


public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private List<View> aList;
    private TextInputLayout textInputLayout;
    private static final String TAG = "LoginActivity";
    final String[] tabstrs = new String[]{"关注", "推荐", "最新"};
    static ViewPager2 vpagerTwo;

    @SuppressLint("HandlerLeak")
    final Handler myHandler = new Handler() {
        @Override
        //重写handleMessage方法,根据msg中what的值判断是否执行后续操作
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                jump(1);

            }
        }
    };


    @SuppressLint("InflateParams")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vpagerTwo = binding.vpagerTwo;
//        aList = new ArrayList<View>();
//
//        aList.add(getLayoutInflater().inflate(R.layout.login_view, binding.getRoot(), false));
//        aList.add(getLayoutInflater().inflate(R.layout.login_view, null, false));
//        textInputLayout = new TextInputLayout(getContext());
//        textInputLayout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
//        textInputEditText = new TextInputEditText(getContext());
//        textInputEditText.setHint("验证");
//        textInputLayout.addView(textInputEditText);
//
//        binding.inputGroup.addView(textInputLayout);
//        LoginPagerAdapter mAdapter = new LoginPagerAdapter(this);
//        LoginPagerAdapter mAdapter = new LoginPagerAdapter();
//        binding.vpagerTwo.setAdapter(mAdapter);

//        LoginPagerAdapter loginPagerAdapter = new LoginPagerAdapter(new LoginFragment());
        binding.vpagerTwo.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return new LoginFragment(position);
            }

            @Override
            public int getItemCount() {
                return 2;
            }

        });
        addTab();

        binding.back.setOnClickListener(view -> finish());
        binding.vpagerTwo.setOnClickListener(view -> {
            Log.d(TAG, "i am onclick");
        });
        binding.vpagerTwo.registerOnPageChangeCallback(changeCallback);

    }

    private ViewPager2.OnPageChangeCallback changeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            if (position == 0) {

 /*               binding.vpagerTwo.getRootView().findViewById(R.id.register_button).setOnClickListener(view ->{
                    binding.vpagerTwo.post(()-> binding.vpagerTwo.setCurrentItem(1));

                });*/
                binding.vpagerTwo.postDelayed(() -> {
                    TextInputEditText input = binding.vpagerTwo.getRootView().findViewById(R.id.account);
                    String user = Pref.getUser();
                    if (user != null) {
                        Log.d(TAG, user);
                        input.setText(user);
                    }
                }, 1000);


            }
            Log.d(TAG, position + "当前选择");
        }

    };

    public void addTab() {
        new TabLayoutMediator(binding.tabs, binding.vpagerTwo, (tab, position) -> {
            TextView tabView = new TextView(this);

            int[][] states = new int[2][];
            states[0] = new int[]{android.R.attr.state_selected};
            states[1] = new int[]{};

            tabView.setText(tabstrs[position]);
            tabView.setTextSize(14);
            tabView.setTextColor(Color.parseColor("#666666"));

            tab.setCustomView(tabView);
        }).attach();
    }

    public static void jump(int position) {
        vpagerTwo.post(() -> vpagerTwo.setCurrentItem(position));

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
