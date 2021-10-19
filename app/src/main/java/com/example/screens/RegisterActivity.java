package com.example.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {
        String name = ((EditText) findViewById(R.id.editName)).getText().toString();
        String surname = ((EditText) findViewById(R.id.editSurname)).getText().toString();
        String mail = ((EditText) findViewById(R.id.editMail)).getText().toString();
        String password = ((EditText) findViewById(R.id.editPassword)).getText().toString();
        String passwordAgain = ((EditText) findViewById(R.id.editPasswordAgain)).getText().toString();

        Intent intent = new Intent(getApplicationContext(), MainScreen.class);
        startActivity(intent);
        finish();
    }
}
