package com.example.screens;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public class ProfileActivity extends AppCompatActivity {

    AnimationDrawable mDrawable;
    ConstraintLayout mLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayout = (ConstraintLayout) findViewById(R.id.profile_layout);
        setContentView(R.layout.activity_profile);
    }


}