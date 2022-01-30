package com.example.screens;

import static com.example.screens.service.Service.post;
import static com.example.screens.service.Service.print;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.screens.service.BaseActivity;
import com.example.screens.service.ClientServer;

import org.json.JSONObject;

public class ProblemsActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problems);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

        adapter = new RecyclerAdapter(jsonObject.getJSONArray("problems"));
        runOnUiThread(() -> recyclerView.setAdapter(adapter));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (adapter != null) adapter.dispose();
    }
}
