package com.example.screens;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;

public class SplashScreenActivity extends AppCompatActivity implements LocationListener {
    public static double latitude;
    public static double longitude;
    private boolean start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(() -> {
            PermissionListener permissionlistener = new PermissionListener() { // создаёт окно для запроса
                @Override
                public void onPermissionGranted() { // если доступ предоставили то перезод на другую активность(сейчас переходим на MainScree где у нас главное меню)
                    getLocation();
                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) { // если не дали разрешение то выход из приложения
                    Toast.makeText(SplashScreenActivity.this, "Доступ запрещён", Toast.LENGTH_SHORT).show();
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

    @SuppressLint("MissingPermission")
    public void getLocation() {
        try {
            LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        if (!start) {
            start = true;

            Intent intent = new Intent(this, MainScreen.class);
            startActivity(intent);
            finish();
        }
    }
}
