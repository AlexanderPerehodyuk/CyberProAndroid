package com.example.screens.problems;

import static com.example.screens.service.Service.post;
import static com.example.screens.service.Service.print;

import android.os.Bundle;

import com.example.screens.problems.recycler.RecyclerActivity;
import com.example.screens.problems.recycler.RecyclerAdapter;
import com.example.screens.service.ClientServer;

import org.json.JSONObject;

public class AllProblemsActivity extends RecyclerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
}
