package com.example.screens;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import static com.example.screens.service.DATA.personalData;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.screens.problems.AddProblem;
import com.example.screens.problems.AllProblemsActivity;
import com.example.screens.problems.MyProblemsActivity;
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

        /*  Экран добваления проблемы */
        findViewById(R.id.add_problem_ll).setOnClickListener(v -> startActivity(new Intent(this, AddProblem.class)));

        /* Переход на экран с картой */
        findViewById(R.id.map).setOnClickListener(v -> startActivity(new Intent(this, MapScreen.class)));

        /*  Список всех проблем */
        findViewById(R.id.problem_all).setOnClickListener(v -> startActivity(new Intent(this, AllProblemsActivity.class)));

        /*  Список моих проблем */
        findViewById(R.id.my_problem).setOnClickListener(view -> startActivity(new Intent(this, MyProblemsActivity.class)));

        /*  Имя + Фамилия */
        ((TextView) findViewById(R.id.textName)).setText((personalData[0] + " " + personalData[1]));
    }
}
