package com.example.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;

public class WelcomeScreen extends AppCompatActivity {
// класс для создания окна с просьбой предоставить своё местоположение
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome); // указываем лаяут тут же будет вопрос с запросом местоположения
        Thread thread = new Thread() {
            public void run() {
                super.run();
                try {
                    sleep(3000); // задержка перед появлением запроса
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    PermissionListener permissionlistener = new PermissionListener() { // создаёт окно для запроса
                        @Override
                        public void onPermissionGranted() { // если доступ предоставили то перезод на другую активность(сейчас переходим на MainScree где у нас главное меню)
                            Intent intent = new Intent(WelcomeScreen.this, MainScreen.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onPermissionDenied(List<String> deniedPermissions) { // если не дали разрешение то выход из приложения
                            Toast.makeText(WelcomeScreen.this, "Доступ запрещён\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                        }

                    };
                    TedPermission.create()
                            .setPermissionListener(permissionlistener)
                            .setDeniedMessage("Для использования приложения нужно предоставить доступ к вашему местоположению") // текст с вопросом
                            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION) // как я понял, это разрешение передать в настройках местоположение для при проложения
                            .check();
                }
            }
        };
        thread.start();
    }

}