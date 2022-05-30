package com.rui.learnandroid.ui.fragment.user;

import androidx.lifecycle.ViewModel;

import com.rui.learnandroid.R;

import java.util.ArrayList;
import java.util.List;

public class UserViewModel extends ViewModel {
    public UserViewModel() {

    }

    public static List<Item> getItems() {
        List<Item> objects = new ArrayList<>();
        objects.add(new Item("账户信息", R.drawable.user_data));
        objects.add(new Item("浏览记录", R.drawable.browsing_records));
        objects.add(new Item("我的收藏", R.drawable.collection));
        objects.add(new Item("联系客服", R.drawable.customer_service));
        return objects;
    }

    static class Item {
        private String title;
        private int imageId;

        public Item(String title, int imageId) {
            this.title = title;
            this.imageId = imageId;
        }

        public String getTitle() {
            return title;
        }

        public int getImageId() {
            return imageId;
        }
    }
}

