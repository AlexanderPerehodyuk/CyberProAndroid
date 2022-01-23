package com.example.screens;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import static com.example.screens.service.DATA.personalData;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.screens.service.BaseActivity;
import com.yandex.mapkit.MapKitFactory;

public class MainScreen extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        MapKitFactory.setApiKey("61db36cd-2c66-4ba0-a8dc-686b6a0515b8");

        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 100);

        findViewById(R.id.add_problem_ll).setOnClickListener(new View.OnClickListener() {
            /*  Пока ничего, но потом должно кидать на экран добваления проблемы */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainScreen.this, AddProblem.class));
            }
        });
        findViewById(R.id.map_ll).setOnClickListener(new View.OnClickListener() {
            /* Переход на экран картой */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainScreen.this, MapScreen.class));
            }
        });

        findViewById(R.id.problem_all).setOnClickListener(new View.OnClickListener() {
            /*  Пока ничего, но потом должно кидать на список всех проблем */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainScreen.this, ProblemsActivity.class));
            }
        });

        ((TextView) findViewById(R.id.textName)).setText((personalData[0] + " " + personalData[1]));
    }
}
