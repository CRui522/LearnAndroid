package com.rui.learnandroid.ui.fragment.news;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.rui.learnandroid.bean.news.NewsAdapter;
import com.rui.learnandroid.databinding.FragmentNewsBinding;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private static final String TAG = "NewsFragment";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NewsViewModel newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Banner newsBanner = binding.newsBanner;
        newsBanner.setAdapter(new BannerImageAdapter<String>(newsViewModel.getCarousel()) {
            @Override
            public void onBindView(BannerImageHolder holder, String data, int position, int size) {

                Glide.with(holder.itemView)
                        .load(data)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);

            }

        }).setIndicator(new CircleIndicator(getContext()));

        RecyclerView newsList = binding.newsList;

        Log.d(TAG, "getList");

        new Thread(() -> {
            try {
                NewsAdapter newsList1 = newsViewModel.getNewsList();
                newsList.post(() -> {
                    newsList.setLayoutManager(new LinearLayoutManager(getContext()));
                    newsList.setAdapter(newsList1);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "news is destroy");
        binding = null;
    }
}
