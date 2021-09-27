package com.example.screens;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.example.screens.MapScreen.mapview;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.map.CameraPosition;

import java.util.List;
import java.util.Locale;

public class MainScreen extends AppCompatActivity implements LocationListener {
    private LinearLayout add_problem;
    private LinearLayout all_problem_list;
    private LinearLayout my_problem;
    public static double latitude;
    public static double longitude;
    TextView textView_location;
    boolean checkCoords = false;

    final String MAPKIT_API_KEY = "61db36cd-2c66-4ba0-a8dc-686b6a0515b8";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        MapKitFactory.setApiKey(MAPKIT_API_KEY); // ключ апи в профиле у вовы он нужен для получения количества запроосов и д.р
        MapKitFactory.initialize(this);

        getLocation();

        textView_location = findViewById(R.id.text_location);


//        button_location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getLocation();
//            }
//        });

        add_problem = findViewById(R.id.add_problem_ll);
        all_problem_list = findViewById(R.id.problem_ll);
        my_problem = findViewById(R.id.mapview);
        add_problem.setOnClickListener(new View.OnClickListener() {
            /*  Пока ничего, но потом должно кидать на экран добваления проблемы */
            @Override
            public void onClick(View v) {

            }
        });
        /* Переход на экран картой */
        findViewById(R.id.map_ll).setOnClickListener(v -> startActivity(new Intent(MainScreen.this, MapScreen.class)));
//        all_problem_list.setOnClickListener(new View.OnClickListener() {
//            /*  Пока ничего, но потом должно кидать на список всех проблем */
//            @Override
//            public void onClick(View v) {
//            }
//        });
//        my_problem.setOnClickListener(new View.OnClickListener() {
//            /*  Пока ничего, но потом должно кидать на список проблем добавленных пользователем */
//            @Override
//            public void onClick(View v) {
//            }
//        });
//    }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            new Thread(() -> {
                Time.sleep(200);

                PermissionListener permissionlistener = new PermissionListener() { // создаёт окно для запроса
                    @Override
                    public void onPermissionGranted() { // если доступ предоставили то перезод на другую активность(сейчас переходим на MainScree где у нас главное меню)
                        getLocation();
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) { // если не дали разрешение то выход из приложения
                        Toast.makeText(MainScreen.this, "Доступ запрещён", Toast.LENGTH_SHORT).show();
                        exit();
                    }
                };
                TedPermission.create()
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("Для использования приложения нужно предоставить доступ к вашему местоположению") // текст с вопросом
                        .setPermissions(ACCESS_FINE_LOCATION) // как я понял, это разрешение передать в настройках местоположение для при проложения
                        .check();
            }).start();

        } else {
            ((LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE))
                    .requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 0, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            List<Address> addresses = new Geocoder(this, Locale.getDefault())
                    .getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            textView_location.setText(address);
            if (!checkCoords) {
                mapview.getMap().move( // при запуске приложения переносимя на координаты которые прописаны в Point, в дальнейшем вместо них будут переменные для местоположения
                        new CameraPosition(new com.yandex.mapkit.geometry.Point(latitude, longitude), 16.5f, 0.0f, 0.0f),
                        new Animation(Animation.Type.SMOOTH, 1),
                        null);
                checkCoords = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        fullscreen();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fullscreen();
    }

    public void fullscreen() {
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }

    public void exit() {
        ((ActivityManager) getSystemService(ACTIVITY_SERVICE)).killBackgroundProcesses(getApplication().getPackageName());
        finishAndRemoveTask();
        System.exit(0);
    }
}



