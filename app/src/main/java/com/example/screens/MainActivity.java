package com.example.screens;

import static com.example.screens.service.DATA.personalData;
import static com.example.screens.service.DATA.userID;

import android.Manifest;
import android.os.Bundle;

import com.example.screens.service.BaseActivity;
import com.example.screens.service.Service;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Service.post(() -> {
            Service.init(this);
            String data = Service.readFromFile("DATA");

            if (data != null) {
                personalData = data.split(" ");
                userID = Integer.parseInt(personalData[personalData.length - 1]);
            }

            TedPermission.create()
                    .setPermissionListener(new PermissionListener() { // создаёт окно для запроса
                        @Override
                        public void onPermissionGranted() {
                        }

                        @Override
                        public void onPermissionDenied(List<String> deniedPermissions) {
                            makeToast("Доступ запрещён");
                            finish();
                        }
                    })
                    .setDeniedMessage("Для использования приложения нужно предоставить доступ к вашему местоположению") // текст с вопросом
                    .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION) // как я понял, это разрешение передать в настройках местоположение для приложения
                    .check();
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commitNow();
        }
    }
}