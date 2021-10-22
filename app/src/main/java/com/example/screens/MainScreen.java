package com.example.screens;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.yandex.mapkit.MapKitFactory;

public class MainScreen extends BaseActivity {
    private LinearLayout add_problem;
    private LinearLayout map;
    private LinearLayout all_problem_list;
    private LinearLayout my_problem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        MapKitFactory.setApiKey("61db36cd-2c66-4ba0-a8dc-686b6a0515b8");

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
}


