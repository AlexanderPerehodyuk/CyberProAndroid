package com.example.screens;

import static com.example.screens.service.Service.post;
import static com.example.screens.service.Service.print;

import android.os.Bundle;

import com.example.screens.service.BaseActivity;
import com.example.screens.service.ClientServer;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProblemsActivity extends BaseActivity {
    private JSONArray allProblems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problems);

        post(() -> {
            try {
                getAllProblems();
            } catch (Exception e) {
                print(e);
            }
        });
    }

    private void getAllProblems() throws Exception {
        JSONObject jsonObject = ClientServer.get("all_problems");

        if (!jsonObject.has("status") || !jsonObject.getString("status").equals("OK")) {
            makeToast("Ошибка при обращении к серверу");
            return;
        }

        print(jsonObject);

        allProblems = jsonObject.getJSONArray("problems");

        print("   ");
        for (int i = 0; i < allProblems.length(); i++) {
            print(allProblems.getJSONObject(i));
        }

        makeToast("Успешно!");
    }
}
