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
        mDrawable = (AnimationDrawable) mLayout.getBackground();
        mDrawable.setEnterFadeDuration(2000);
        mDrawable.setExitFadeDuration(2000);
        mDrawable.start();
        setContentView(R.layout.activity_profile);
    }


}