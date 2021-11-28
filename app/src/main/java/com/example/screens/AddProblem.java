package com.example.screens;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import static com.example.screens.service.DATA.IMAGE_PICK_CODE;
import static com.example.screens.service.DATA.PERMISSION_CODE;
import static com.example.screens.service.DATA.idProblems;
import static com.example.screens.service.DATA.userID;
import static com.example.screens.service.Service.post;
import static com.example.screens.service.DATA.longitude;
import static com.example.screens.service.DATA.latitude;
import static com.example.screens.service.Service.print;
import static com.example.screens.service.Service.sleep;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.screens.service.BaseActivity;
import com.example.screens.service.ClientServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class AddProblem extends BaseActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);

        imageView = findViewById(R.id.imageView);

        findViewById(R.id.buttonChooseImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check runtime permission
                if (checkSelfPermission(READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    //permission not granted, request it.
                    //show popup for runtime permission
                    requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
                } else {
                    //permission already granted
                    pickImageFromGallery();
                }
            }
        });

        findViewById(R.id.buttonPost).setOnClickListener(view -> {
            postProblem();

            post(() -> {
                sleep(6000);

                print(ClientServer.get("all_problem"));
            });
        });
    }

    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    //handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //permission was granted
                pickImageFromGallery();
            } else {
                //permission was denied
                makeToast("Permission denied!");
            }
        }
    }

    //handle result of picked image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            //set image to image view
            imageView.setImageURI(data.getData());
        }
    }

    private void postProblem() {
        post(() -> {
            try {
                String name = getText(findViewById(R.id.editTitle));

                if (name.length() == 0) {
                    makeToast("Введите название проблемы!");
                    return;
                }

                postImg();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", name);
                jsonObject.put("description", getText(findViewById(R.id.editDescription)));
                jsonObject.put("coordinates", latitude + "," + longitude);
                jsonObject.put("user_id", userID);
                jsonObject.put("category", "Экологическая");

                print(jsonObject);

                jsonObject = ClientServer.post("add_problem", jsonObject);

                if (jsonObject.has("id_problem")) {
                    idProblems.add((Integer) jsonObject.get("id_problem"));

                    makeToast("Проблема отправлена");
                } else {
                    makeToast("Ошибка при обращении к серверу");
                }
            } catch (Exception e) {
                print("Post problem " + e);
            }
        });
    }

    private void postImg() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ((BitmapDrawable) imageView.getDrawable()).getBitmap()
                .compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        jsonObject.put("files", byteArrayOutputStream.toByteArray());

        ClientServer.post("add_problem", jsonObject);
    }

    private String getText(EditText editText) {
        return editText.getText().toString();
    }
}




