package com.example.screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainScreen extends AppCompatActivity {
    private LinearLayout add_problem;
    private LinearLayout map;
    private LinearLayout all_problem_list;
    private LinearLayout my_problem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
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
                Intent intent = new Intent(MainScreen.this, MainScreen.class);
                startActivity(intent);
            }
        });
        all_problem_list.setOnClickListener(new View.OnClickListener() {
            /*  Пока ничего, но потом должно кидать на список всех проблем */
            @Override
            public void onClick(View v) {

            }
        });
        my_problem.setOnClickListener(new View.OnClickListener() {
            /*  Пока ничего, но потом должно кидать на список проблем добавленных пользователем */
            @Override
            public void onClick(View v) {

            }
        });
    }
}
