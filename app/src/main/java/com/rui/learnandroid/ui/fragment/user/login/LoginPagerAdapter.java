package com.rui.learnandroid.ui.fragment.user.login;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class LoginPagerAdapter extends FragmentStateAdapter {
    private List<View> viewLists;

    public LoginPagerAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new LoginFragment(position);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

}
