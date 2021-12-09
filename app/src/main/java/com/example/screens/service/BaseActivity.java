package com.example.screens.service;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    public void startActivity(Class<?> cls) {
        runOnUiThread(() -> {
            Intent intent = new Intent(getApplicationContext(), cls);
            startActivity(intent);
            finish();
        });
    }

    public void makeToast(Object object) {
        runOnUiThread(() -> Toast.makeText(this, object.toString(), Toast.LENGTH_LONG).show());
    }

    public void makeFastToast(Object object) {
        runOnUiThread(() -> Toast.makeText(this, object.toString(), Toast.LENGTH_SHORT).show());
    }
}
