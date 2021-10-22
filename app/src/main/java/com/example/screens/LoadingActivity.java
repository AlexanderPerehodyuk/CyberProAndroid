package com.example.screens;

import android.Manifest;
import android.os.Bundle;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;

public class LoadingActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ThreadPool.post(() -> {
            PermissionListener permissionlistener = new PermissionListener() { // создаёт окно для запроса
                @Override
                public void onPermissionGranted() {
                    new LocationModification(getApplicationContext(), () -> startActivity(RegisterActivity.class));
                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) { // если не дали разрешение то выход из приложения
                    makeToast("Доступ запрещён");
                    finish();
                }
            };
            TedPermission.create()
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("Для использования приложения нужно предоставить доступ к вашему местоположению") // текст с вопросом
                    .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION) // как я понял, это разрешение передать в настройках местоположение для при проложения
                    .check();
        });
    }
}
