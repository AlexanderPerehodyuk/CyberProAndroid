package com.example.screens;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.yandex.mapkit.MapKitFactory;

import java.util.List;

public class MainScreen extends AppCompatActivity implements LocationListener {
    private LinearLayout add_problem;
    private LinearLayout map;
    private LinearLayout all_problem_list;
    private LinearLayout my_problem;
    public static double latitude;
    public static double longitude;
    //    TextView textView_location;
//    boolean checkCoords = false;

    final String MAPKIT_API_KEY = "61db36cd-2c66-4ba0-a8dc-686b6a0515b8";

    @SuppressLint("MissingPermission")
    public void getLocation() {
        try {
            LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, MainScreen.this);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        MapKitFactory.setApiKey(MAPKIT_API_KEY); // ключ апи в профиле у вовы он нужен для получения количества запроосов и д.р
        MapKitFactory.initialize(this);


        new Thread(() -> {
            PermissionListener permissionlistener = new PermissionListener() { // создаёт окно для запроса
                @Override
                public void onPermissionGranted() { // если доступ предоставили то перезод на другую активность(сейчас переходим на MainScree где у нас главное меню)
                    getLocation();
                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) { // если не дали разрешение то выход из приложения
                    Toast.makeText(MainScreen.this, "Доступ запрещён\n", Toast.LENGTH_SHORT).show();
                    finishAffinity();
                }

            };
            TedPermission.create()
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("Для использования приложения нужно предоставить доступ к вашему местоположению") // текст с вопросом
                    .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION) // как я понял, это разрешение передать в настройках местоположение для при проложения
                    .check();
        }).start();
//        textView_location = findViewById(R.id.text_location);

        //Runtime permissions
        if (ContextCompat.checkSelfPermission(MainScreen.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainScreen.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }


        add_problem = findViewById(R.id.add_problem_ll);
        map = findViewById(R.id.map_ll);
        all_problem_list = findViewById(R.id.problem_ll);
        my_problem = findViewById(R.id.mapview);
        add_problem.setOnClickListener(new View.OnClickListener() {
            /*  Пока ничего, но потом должно кидать на экран добваления проблемы */
            @Override
            public void onClick(View v) {

            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            /* Переход на экран картой */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, MapScreen.class);
                startActivity(intent);

            }
        });
        //        button_location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getLocation();
//            }
//        });
        all_problem_list.setOnClickListener(new View.OnClickListener() {
            /*  Пока ничего, но потом должно кидать на список всех проблем */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, ProblemsActivity.class);
                startActivity(intent);
            }
        });
//        my_problem.setOnClickListener(new View.OnClickListener() {
//            /*  Пока ничего, но потом должно кидать на список проблем добавленных пользователем */
//            @Override
//            public void onClick(View v) {
//            }
//        });
//    }


    }

}


