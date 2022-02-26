package com.example.screens;

import static com.example.screens.service.Service.print;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.screens.service.BaseActivity;
import com.example.screens.service.ClientServer;
import com.example.screens.service.Service;

import org.json.JSONObject;

public class EntranceActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
        findViewById(R.id.textRegistration).setOnClickListener(view -> startActivity(RegisterActivity.class));
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter(v);
            }
        });
//        findViewById(R.id.button2).setOnClickListener(view -> startActivity(LoginActivity.class));
    }

    public void enter(View view) {
        Service.post(() -> {
            String mail = clearSpacebars(findViewById(R.id.et_email));
            String password = getText(findViewById(R.id.et_password));

            if (mail.length() == 0 || !mail.contains("@")) {
                makeToast("Некорректный адрес электронной почты!");
                return;
            }
            if (password.length() == 0) {
                makeToast("Введите пароль!");
                return;
            }

            try {
                JSONObject jsonObject = new JSONObject().put("email", mail).put("password", password);

                jsonObject = ClientServer.post("login", jsonObject);
                if (jsonObject.has("status")) {
//                    print(ClientServer.post("user", new JSONObject().put("user_id", jsonObject.getInt("id"))));

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

    protected String clearSpacebars(EditText editText) {
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

    protected String getText(EditText editText) {
        return editText.getText().toString();
    }
}
