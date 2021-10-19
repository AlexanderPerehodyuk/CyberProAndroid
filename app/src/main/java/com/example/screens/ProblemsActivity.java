package com.example.screens;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class ProblemsActivity extends AppCompatActivity {
    private static final String JSON_URL = "http://m1.maxfad.ru/api/users.json";// UTF-8
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problems);

        listView = (ListView) findViewById(R.id.listView);
    }

    public static String EncodingToUTF8(String response) {
        byte[] code = response.getBytes(StandardCharsets.ISO_8859_1);
        response = new String(code, StandardCharsets.UTF_8);
        return response;
    }
}