package com.rui.learnandroid.ui.fragment.user.login;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;
import com.rui.learnandroid.R;


public class CustomTextInputLayout extends TextInputLayout {

    public CustomTextInputLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.text_input, this);
    }

    public CustomTextInputLayout(Context context) {
        super(context);
        View.inflate(context, R.layout.text_input, this);
    }
}


