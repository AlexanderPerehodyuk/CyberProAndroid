package com.example.screens;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public void startActivity(Class<?> cls) {
        runOnUiThread(() -> {
            Intent intent = new Intent(getApplicationContext(), cls);
            startActivity(intent);
            finish();
        });
    }

    public void makeToast(Object object) {
        runOnUiThread(() -> Toast.makeText(getApplicationContext(), object.toString(), Toast.LENGTH_LONG).show());
    }
}
