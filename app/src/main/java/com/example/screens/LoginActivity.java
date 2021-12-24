package com.example.screens;

import static com.example.screens.service.Service.print;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.screens.service.BaseActivity;
import com.example.screens.service.ClientServer;
import com.example.screens.service.Service;

import org.json.JSONObject;

public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void enter(View view) {
        Service.post(() -> {
            String mail = clearSpacebars(findViewById(R.id.editMail));
            String password = getText(findViewById(R.id.editPassword));

            if (mail.length() == 0 || !mail.contains("@")) {
                makeToast("Некорректный адрес электронной почты!");
                return;
            }
            if (password.length() == 0) {
                makeToast("Введите пароль!");
                return;
            }

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("email", mail);
                jsonObject.put("password", password);

                jsonObject = ClientServer.post("login", jsonObject);

                if (jsonObject.has("status")) {
//                    надо получить фамилию, имя и т.д. из новой функции
                    startActivity(MainScreen.class);
                } else {
                    if (jsonObject.getString("error").equals("User not registered")) {
                        makeToast("Вы не зарегистрированы!");
                    } else {
                        makeToast("Ошибка при обращении к серверу");
                    }
                }
            } catch (Exception e) {
                print(e);
            }
        });
    }

    private String clearSpacebars(EditText editText) {
        String message = getText(editText);

        if (message.length() != 0) {
            String[] list = message.split(" ");
            StringBuilder stringBuilder = new StringBuilder();

            for (String s : list) {
                if (!s.equals("")) {
                    stringBuilder.append(s).append(" ");
                }
            }

            return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
        }

        return "";
    }

    private String getText(EditText editText) {
        return editText.getText().toString();
    }
}
