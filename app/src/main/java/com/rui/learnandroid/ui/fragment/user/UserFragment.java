package com.rui.learnandroid.ui.fragment.user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.rui.learnandroid.Pref;
import com.rui.learnandroid.R;

import com.rui.learnandroid.databinding.FragmentUserBinding;

import com.rui.learnandroid.ui.fragment.user.login.LoginActivity;
import com.rui.learnandroid.ui.setting.SettingActivity;

import java.util.List;

public class UserFragment extends Fragment {

    private static final String TAG = "UserFragment";
    private FragmentUserBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        setHasOptionsMenu(true);
        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.title.setText("个人中心");
        binding.toolbar.inflateMenu(R.menu.item_menu);
        binding.toolbar.setOnMenuItemClickListener(this::onOptionsItemSelected);
        initview();
        initItem();
        binding.include1.login.setOnClickListener(view -> {
            //binding.login.setVisibility(View.INVISIBLE);
            startActivity(new Intent(getContext(), LoginActivity.class));
            onDestroy();
        });
        return root;
    }

    private void initview() {

        if (isLogin()) {
            binding.include1.getRoot().setVisibility(View.GONE);
            binding.include2.getRoot().setVisibility(View.VISIBLE);
            setHead();
        }

    }

    @SuppressLint("SetTextI18n")
    private void setHead() {
        String user = Pref.getUser();
        String email = Pref.getEmail();
        binding.include2.nameText.setText(user);
        binding.include2.emailText.setText("Email:" + email);
    }

    private boolean isLogin() {
        String token = Pref.getToken();
        if (token != null) {
            return true;
        }
        return false;
    }

    private void initAvatar() {

    }

    private void initItem() {
        binding.one.setAdapter(new BaseAdapter() {

            private List<UserViewModel.Item> mData;

            {
                mData = UserViewModel.getItems();
            }

            @Override
            public int getCount() {
                return mData.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @SuppressLint("ViewHolder")
            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.user_item, viewGroup, false);
                ImageView imageView = view.findViewById(R.id.mImage);
                TextView title = view.findViewById(R.id.mTitle);
                imageView.setImageAlpha(mData.get(i).getImageId());
                title.setText(mData.get(i).getTitle());
                return view;
            }

        });
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, String.valueOf(item));
        int id = item.getItemId();
        switch (id) {
            case R.id.send:
                sendMessage();
                break;
            case R.id.jump:
                Intent intent = new Intent(getContext(), SettingActivity.class);
                intent.putExtra("a", "云从龙，风从虎");
                startActivity(intent);
                break;
            case R.id.exit:
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void sendMessage() {

        int status = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS);
        if (status < 0) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 1);
            Toast.makeText(getContext(), "请允许发送短信权限", Toast.LENGTH_SHORT).show();
            return;
        }
        View inflate = View.inflate(getContext(), R.layout.send_input, null);
        final SmsManager smsManager = SmsManager.getDefault();
        AlertDialog.Builder input_dialog = new AlertDialog.Builder(getContext())
                .setView(inflate)
                .setNegativeButton("取消", (dialogInterface, i) -> {
                })
                .setPositiveButton("发送", (dialogInterface, i) -> {
                    EditText phone = inflate.findViewById(R.id.phone);
                    EditText content = inflate.findViewById(R.id.content);
                    String edit_phone = phone.getText().toString();
                    String contentText = content.getText().toString();
                    if (!edit_phone.equals("") && !contentText.equals("")) {
                        smsManager.sendTextMessage(edit_phone, null, contentText, null, null);
                        Toast.makeText(getContext(), "短信发送成功", Toast.LENGTH_LONG).show();
                    } else Toast.makeText(getContext(), "未输入", Toast.LENGTH_SHORT).show();
                });
        input_dialog.create().show();
    }

    @Override
    public void onResume() {
        super.onResume();
        initview();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "user is destroy");
        //binding = null;
    }
}
