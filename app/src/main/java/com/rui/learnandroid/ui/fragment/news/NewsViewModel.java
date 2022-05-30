package com.rui.learnandroid.ui.fragment.news;

import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.rui.learnandroid.bean.news.News;
import com.rui.learnandroid.bean.news.NewsAdapter;
import com.rui.learnandroid.net;

import java.io.IOException;
import java.util.ArrayList;


public class NewsViewModel extends ViewModel {
    public NewsViewModel() {

    }

    public ArrayList<String> getCarousel() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("https://img2.baidu.com/it/u=3302184040,3713353210&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500");
        strings.add("https://img1.baidu.com/it/u=700675537,3936578503&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500");
        strings.add("https://img1.baidu.com/it/u=3384796346,381674655&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500");
        return strings;
    }

    public NewsAdapter getNewsList() throws IOException {
        String datas = null;
        datas = net.get("http://124.93.196.45:10001/prod-api/press/press/list");
        Gson gson = new Gson();
        News rows = gson.fromJson(datas, News.class);
        return new NewsAdapter(rows.getRows());
    }
}
