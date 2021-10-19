package com.example.screens;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;

public class LoadingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(() -> {
            PermissionListener permissionlistener = new PermissionListener() { // создаёт окно для запроса
                @Override
                public void onPermissionGranted() { // если доступ предоставили то перезод на другую активность(сейчас переходим на MainScree где у нас главное меню)
                    new LocationModification(getApplicationContext(), () -> {
                        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(intent);
                        finish();
                    });
                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) { // если не дали разрешение то выход из приложения
                    Toast.makeText(getApplicationContext(), "Доступ запрещён", Toast.LENGTH_SHORT).show();
                    finish();
                }
            };
            TedPermission.create()
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("Для использования приложения нужно предоставить доступ к вашему местоположению") // текст с вопросом
                    .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION) // как я понял, это разрешение передать в настройках местоположение для при проложения
                    .check();
        }).start();
    }
}
