package com.rui.learnandroid.ui.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.rui.learnandroid.ui.main.MainActivity2;
import com.rui.learnandroid.Pref;
import com.rui.learnandroid.R;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnPageChangeListener;

import java.util.ArrayList;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private Banner banner;
    private Button begin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        if (Pref.isFirstUsing()) {
            initView();
            main();
            Pref.noFirst();
        } else {

            jump();
        }
    }

    private void main() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi-1.lanrentuku.com%2F2020%2F7%2F10%2Fb87c8e05-344a-48d1-869f-ef6929fc8b17.jpg&refer=http%3A%2F%2Fi-1.lanrentuku.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654428220&t=87a2508ffcfc1a854a5d98ad64cfc1dc");
        strings.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi-1.lanrentuku.com%2F2020%2F7%2F3%2Fc428aa47-0933-433a-be80-3d4ec5a89ec7.jpg&refer=http%3A%2F%2Fi-1.lanrentuku.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654428220&t=80e7902dab6ffc36cee17713f6ff4bd1");
        strings.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2Ftp06%2F20111116192A364-0-lp.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654428220&t=3a9143235f933908950296ddd01f93e9");
        banner.setAdapter(new BannerImageAdapter<String>(strings) {
            @Override
            public void onBindView(BannerImageHolder holder, String data, int position, int size) {

                Glide.with(holder.itemView)
                        .load(data)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);

            }

        }, false).setIndicator(new CircleIndicator(this)).isAutoLoop(false);
        banner.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 2) {
                    begin.setVisibility(View.VISIBLE);
                } else {
                    begin.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        begin.setOnClickListener(view -> {
            jump();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void jump() {
        startActivity(new Intent(SplashActivity.this, MainActivity2.class));
        finish();
    }

    private void initView() {
        banner = (Banner) findViewById(R.id.banner);
        begin = (Button) findViewById(R.id.begin);
    }
}
