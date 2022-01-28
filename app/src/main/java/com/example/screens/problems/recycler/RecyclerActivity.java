package com.example.screens.problems.recycler;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.screens.R;
import com.example.screens.service.BaseActivity;

public class RecyclerActivity extends BaseActivity {
    protected RecyclerView recyclerView;
    protected RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problems);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (adapter != null) adapter.dispose();
    }
}
