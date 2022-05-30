package com.rui.learnandroid.ui.fragment.user.login;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.rui.learnandroid.Pref;
import com.rui.learnandroid.R;
import com.rui.learnandroid.bean.user.Data;
import com.rui.learnandroid.net;

import java.util.HashMap;
import java.util.List;

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";
    LinearLayout chipGroup;
    Button mButton;
    View itemView;
    int position;

    private TextInputEditText reName;
    private TextInputEditText reAccount;
    private TextInputEditText reEmail;
    private TextInputEditText rePassword;
    private TextInputEditText rePasswordAgain;

    public LoginFragment(int i) {
        this.position = i;
    }

    public LoginFragment() {

    }

    public void loginData(String account, String password) {
        if (account.equals("") || password.equals("")) {
            Toast.makeText(getContext(), "账号或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> users = new HashMap<>();
        users.put("username", account);
        users.put("password", password);
        String userJson = new Gson().toJson(users);
        login(userJson);
    }

    public void login(String body) {
        new Thread(() -> {
            Looper.prepare();
            String data = net.post("http://124.93.196.45:10001/prod-api/api/login", body);
            Data data1 = new Gson().fromJson(data, Data.class);
            if (data1.getCode() == 200) {
                Toast.makeText(getContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                Pref.setToken(data1.getToken());

                onDestroy();
            } else {
                Toast.makeText(getContext(), data1.getMsg(), Toast.LENGTH_SHORT).show();
            }
            Looper.loop();
        }).start();
    }

    public void register(String body, HashMap<String, String> userdata) {
        new Thread(() -> {
            Looper.prepare();
            String data = net.post("http://124.93.196.45:10001//prod-api/api/register", body);
            Data data1 = new Gson().fromJson(data, Data.class);
            if (data1.getCode() == 200) {
                Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();
                Pref.setUser(userdata.get("userName"));
                Pref.setPassword(userdata.get("password"));
                Pref.setEmail(userdata.get("email"));
//                onDestroy();
                LoginActivity.vpagerTwo.post(()-> {
                    Log.d(TAG, "current");
                    LoginActivity.vpagerTwo.setCurrentItem(0);
                });
            } else {
                Toast.makeText(getContext(), data1.getMsg(), Toast.LENGTH_SHORT).show();
            }

            Looper.loop();
        }).start();
    }

    @SuppressLint("StaticFieldLeak")
    public void registerData() {
        reName = itemView.findViewById(R.id.re_name);
        reAccount = itemView.findViewById(R.id.re_account);
        reEmail = itemView.findViewById(R.id.re_email);
        rePassword = itemView.findViewById(R.id.re_password);
        rePasswordAgain = itemView.findViewById(R.id.re_password_again);
        String value = reName.getText().toString();
        String value1 = reAccount.getText().toString();
        String value2 = reEmail.getText().toString();
        String password = rePassword.getText().toString();
        String mpassword = rePasswordAgain.getText().toString();
        if (value.equals("") || value1.equals("") || value2.equals("") || password.equals("")) {
            Toast.makeText(getContext(), "注册信息不能有空", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> users = new HashMap<>();
        users.put("userName", value);
        users.put("phonenumber", value1);
        users.put("email", value2);

        if (!password.equals(mpassword)) {
            new AlertDialog.Builder(getContext())
                    .setTitle("两次密码不一样，请重新输入")
                    .setPositiveButton("确定", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    }).show();
            return;
        }
        users.put("password", password);
        String userJson = new Gson().toJson(users);
        register(userJson, users);
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_view, container, false);
        chipGroup = view.findViewById(R.id.inputGroup);
        mButton = view.findViewById(R.id.login_button);


        if (position == 0) {
//            if (Pref.getUser() != null) {
//                account.setText(Pref.getUser());
//            }
//            itemView = LayoutInflater.from(getContext()).inflate(R.layout.login_item, view.findViewById(R.id.register_view), false);
            itemView = inflater.inflate(R.layout.login_item, container, true);
            TextInputEditText viewById = itemView.findViewById(R.id.account);
            String name = Pref.getUser();
            if (name != null) {
                viewById.setText(name);
            }
        } else {
//            itemView = LayoutInflater.from(getContext()).inflate(R.layout.login_item, container, true);
            itemView = inflater.inflate(R.layout.register_item, container, true);
            mButton.setText("注册");
            //mButton.setId(R.id.register_button);
        }

        chipGroup.addView(itemView);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mButton.setOnClickListener(view1 -> {
            if (position == 0) {
                TextInputEditText account = view.findViewById(R.id.account);
                TextInputEditText password = view.findViewById(R.id.password);

                loginData(account.getText().toString(), password.getText().toString());

            } else {
                registerData();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "finish");
        getActivity().finish();
    }

}
