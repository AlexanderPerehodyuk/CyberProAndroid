package com.example.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        startActivity(MainScreen.class);
    }

    public void register(View view) {
        String mail = clearSpacebars(findViewById(R.id.editMail));
        String password = getText(findViewById(R.id.editPassword));
        String passwordAgain = getText(findViewById(R.id.editPasswordAgain));

        ThreadPool.post(() -> {
            if (password.equals(passwordAgain)) {
                if (mail.contains("@")) {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("email", mail);
                        jsonObject.put("password", password);
                        jsonObject.put("name", clearSpacebars(findViewById(R.id.editName)));
                        jsonObject.put("surname", clearSpacebars(findViewById(R.id.editSurname)));

                        Response response = client.newCall(new Request.Builder()
                                .url("http://people-eye.herokuapp.com/api/register")
                                .post(RequestBody.create(jsonObject.toString(), MediaType.parse("application/json; charset=utf-8")))
                                .build()).execute();
                        JSONObject answer = new JSONObject(response.body().string());
                        response.close();

                        if (answer.has("id")) {
                            makeToast("Регистрация успешна!");
                            startActivity(MainScreen.class);
                        } else {
                            makeToast(answer.getString("error"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    makeToast("Некорректный адрес электронной почты!");
                }
            } else {
                makeToast("Пароли не совпадают!");
            }
        });
    }

    private String clearSpacebars(EditText editText) {
        String[] list = getText(editText).split(" ");
        StringBuilder stringBuilder = new StringBuilder();

        for (String s : list) {
            if (!s.equals("")) {
                stringBuilder.append(s);
                stringBuilder.append(" ");
            }
        }

        return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
    }

    private String getText(EditText editText) {
        return editText.getText().toString();
    }
}
