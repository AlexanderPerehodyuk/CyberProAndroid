package com.example.screens;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import static com.example.screens.service.DATA.personalData;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.screens.service.BaseActivity;
import com.yandex.mapkit.MapKitFactory;

import java.io.IOException;

public class MainScreen extends BaseActivity {
    private static final int IMAGE_PICK_CODE = 1;
    AnimationDrawable mDrawable;
    ConstraintLayout mLayout;
    ImageView photo;
    Uri ImagePhoto;
    private static final int PICK_IMAGE = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        MapKitFactory.setApiKey("61db36cd-2c66-4ba0-a8dc-686b6a0515b8");

        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 100);

        findViewById(R.id.add_problem_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainScreen.this, AddProblem.class));
            }
        });
        findViewById(R.id.map_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainScreen.this, MapScreen.class));
            }
        });

        findViewById(R.id.problem_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainScreen.this, ProblemsActivity.class));
            }
        });
        findViewById(R.id.profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainScreen.this, ProfileActivity.class));
            }
        });
        mLayout = (ConstraintLayout) findViewById(R.id.layout);
        mDrawable = (AnimationDrawable) mLayout.getBackground();
        mDrawable.setEnterFadeDuration(2000);
        mDrawable.setExitFadeDuration(2000);
        mDrawable.start();
        ((TextView) findViewById(R.id.textName)).setText((personalData[0] + " " + personalData[1]));

        photo = (ImageView) findViewById(R.id.photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageFromGallery();
            }
        });
    }
    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE &&  resultCode == RESULT_OK) {
            ImagePhoto = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), ImagePhoto);
                photo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
