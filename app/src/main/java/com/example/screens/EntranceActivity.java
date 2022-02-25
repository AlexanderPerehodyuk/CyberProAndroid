package com.example.screens;

import android.os.Bundle;

import com.example.screens.service.BaseActivity;

public class EntranceActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);

        findViewById(R.id.buttonRegistration).setOnClickListener(view -> startActivity(RegisterActivity.class));
//        findViewById(R.id.button2).setOnClickListener(view -> startActivity(LoginActivity.class));
    }
}
