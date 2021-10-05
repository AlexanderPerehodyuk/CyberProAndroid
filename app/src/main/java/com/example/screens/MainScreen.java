package com.example.screens;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.yandex.mapkit.MapKitFactory;

import java.util.List;

public class MainScreen extends AppCompatActivity {

    private LinearLayout add_problem;
    private LinearLayout map;
    private LinearLayout all_problem_list;
    private LinearLayout my_problem;


    //    TextView textView_location;
//    boolean checkCoords = false;

//    final String MAPKIT_API_KEY = "61db36cd-2c66-4ba0-a8dc-686b6a0515b8";
//




    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks());
        setContentView(R.layout.main_screen);



        MapKitFactory.setApiKey("61db36cd-2c66-4ba0-a8dc-686b6a0515b8");
//        MapKitFactory.initialize(this);






        if (ContextCompat.checkSelfPermission(MainScreen.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainScreen.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }


        add_problem = findViewById(R.id.add_problem_ll);
        map = findViewById(R.id.map_ll);
        all_problem_list = findViewById(R.id.problem_ll);
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

        all_problem_list.setOnClickListener(new View.OnClickListener() {
            /*  Пока ничего, но потом должно кидать на список всех проблем */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, ProblemsActivity.class);
                startActivity(intent);
            }
        });


    }
    private static final class MyActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

        public void onActivityCreated(Activity activity, Bundle bundle) {
            Log.e("","onActivityCreated:" + activity.getLocalClassName());
        }

        public void onActivityDestroyed(Activity activity) {
            Log.e("","onActivityDestroyed:" + activity.getLocalClassName());
//
        }

        public void onActivityPaused(Activity activity) {
            Log.e("","onActivityPaused:" + activity.getLocalClassName());
        }

        public void onActivityResumed(Activity activity) {
            Log.e("","onActivityResumed:" + activity.getLocalClassName());
        }

        public void onActivitySaveInstanceState(Activity activity,
                                                Bundle outState) {
            Log.e("","onActivitySaveInstanceState:" + activity.getLocalClassName());
        }

        public void onActivityStarted(Activity activity) {
            Log.e("","onActivityStarted:" + activity.getLocalClassName());

        }

        public void onActivityStopped(Activity activity) {
            Log.e("","onActivityStopped:" + activity.getLocalClassName());
        }
    }

}


