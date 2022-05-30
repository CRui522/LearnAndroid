package com.rui.learnandroid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import com.rui.learnandroid.bean.news.News;
import com.rui.learnandroid.bean.news.NewsAdapter;
import com.rui.learnandroid.ui.setting.SettingActivity;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;


import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    //private ActivityMainBinding binding;
    OkHttpClient client = new OkHttpClient();
    private RecyclerView recycler;
    private Banner homeBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        setContentView(R.layout.activity_main);
        initView();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("https://img2.baidu.com/it/u=3302184040,3713353210&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500");
        strings.add("https://img1.baidu.com/it/u=700675537,3936578503&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500");
        strings.add("https://img1.baidu.com/it/u=3384796346,381674655&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500");

        //homeBanner.setAdapter(new ImageAdapter(strings)).setIndicator(new CircleIndicator(this));
        homeBanner.setAdapter(new BannerImageAdapter<String>(strings) {
            @Override
            public void onBindView(BannerImageHolder holder, String data, int position, int size) {

                Glide.with(holder.itemView)
                        .load(data)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);

            }

        }).setIndicator(new CircleIndicator(this));

        new Thread(() -> {
            try {
                String datas = run("http://124.93.196.45:10001/prod-api/press/press/list");
                Gson gson = new Gson();
                News rows = gson.fromJson(datas, News.class);
                recycler.post(() -> {
                    recycler.setLayoutManager(new LinearLayoutManager(this));
                    recycler.setAdapter(new NewsAdapter(rows.getRows()));
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        /*OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://124.93.196.45:10001/prod-api/press/press/list")
                .method("GET", null)
                .build();*/
        //Response response = client.newCall(request).execute();//同步获取==》代码简单==》但是，如果服务器不在线的话，就会卡住app
        /*client.newCall(request).enqueue(new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {
                //获取数据不成功，执行这个
                Log.d("error", "onFailure: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取数据成功，执行这个
                //数据在哪儿了==》response
                String string = response.body().string();
                Log.d("datas", "onResponse: " + string);
                //第二步：数据展示==》数据要放在recycleview这个组件（控件）上
                //我们目前数据给是是字符串==》转成java类==》用类的对象来存储字符串==》用对象获取特定的数据，很简单
                Gson gson = new Gson();
                News news = gson.fromJson(string, News.class);
                //[]==》数组；{}==》对象
                //Log.d(TAG, "onResponse: "+news.getRows().get(0).getTitle());
                //第三步：数据都准备好了，界面设计来了==》放一个recycleview组件
                //第四步：回归到后台==》获取组件id
                //第五步：通过id将数据放到recycleview中
                //设置数据展示布局
                //只要涉及到控件的操作一定要放在主线程中
                recycler.post(new Runnable() {
                    @Override
                    public void run() {
                        recycler.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false));//recycleview数据展现形式==》线性的垂直结构
                        //第六步：真正的将数据放到组件上==>如果你基础不好==》去看下java基础==》创建类==》匿名内部类
                        recycler.setAdapter(new NewsAdapter(news.getRows()));
                        //第七步：先得到一个viewholder==》设计每一项的数据展示结构==》写一个布局文件==》每一项的数据展示结构==>根据布局，生成viewholder==>大家还要安装一个插件
                    }
                });


                //最后一步：绑定数据
            }

        });*/
    }

    public String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).method("GET", null).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.send:
                sendMessage();
                break;
            case R.id.jump:
                Intent intent = new Intent(this, SettingActivity.class);
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
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        View inflate = View.inflate(this, R.layout.send_input, null);
        final SmsManager smsManager = SmsManager.getDefault();
        AlertDialog.Builder input_dialog = new AlertDialog.Builder(this)
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
                        Toast.makeText(MainActivity.this, "短信发送成功", Toast.LENGTH_LONG).show();
                    } else Toast.makeText(this, "未输入", Toast.LENGTH_SHORT).show();
                });
        input_dialog.create().show();
    }

    private void initView() {
        recycler = (RecyclerView) findViewById(R.id.recycler);
        homeBanner = (Banner) findViewById(R.id.home_banner);
    }
}