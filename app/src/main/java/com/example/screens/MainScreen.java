package com.example.screens;

import static com.example.screens.MapScreen.mapview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.map.CameraPosition;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

public class MainScreen extends AppCompatActivity implements LocationListener {
    private LinearLayout add_problem;
    private LinearLayout map;
    private LinearLayout all_problem_list;
    private LinearLayout my_problem;
    public static double latitude;
    public static double longitude;
//    TextView textView_location;
    LocationManager locationManager;
//    boolean checkCoords = false;

    final String MAPKIT_API_KEY = "61db36cd-2c66-4ba0-a8dc-686b6a0515b8";
    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,10,MainScreen.this);


        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void onLocationChanged(Location location) {

        try {

//            Geocoder geocoder = new Geocoder(MainScreen.this, Locale.getDefault());
//            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
//            String address = addresses.get(0).getAddressLine(0);
            latitude = location.getLatitude();
            longitude = location.getLongitude();


        }catch (Exception e){
            e.printStackTrace();

        }


    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        MapKitFactory.setApiKey(MAPKIT_API_KEY); // ключ апи в профиле у вовы он нужен для получения количества запроосов и д.р
        MapKitFactory.initialize(this);

        Thread thread = new Thread() {
            public void run() {
                super.run();
                try {
                    sleep(1000); // задержка перед появлением запроса
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
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
                }
            }
        };

        thread.start();
//        textView_location = findViewById(R.id.text_location);

        //Runtime permissions
        if (ContextCompat.checkSelfPermission(MainScreen.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainScreen.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
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

}



