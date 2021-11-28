package com.example.screens;

import static com.example.screens.service.Service.print;
import static com.example.screens.service.Service.sleep;
import static com.example.screens.service.Service.writeToFile;
import static com.example.screens.service.DATA.userID;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.screens.service.BaseActivity;
import com.example.screens.service.ClientServer;
import com.example.screens.service.Service;

import org.json.JSONArray;
import org.json.JSONObject;

public class RegisterActivity extends BaseActivity {
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Service.post(() -> {
            try {
                jsonArray = (JSONArray) (ClientServer.get("all_users")).get("users");
            } catch (Exception e) {
                print(e);
            }
        });
    }

    public void register(View view) {
        Service.post(() -> {
            String name = clearSpacebars(findViewById(R.id.editName));
            String surname = clearSpacebars(findViewById(R.id.editSurname));
            String mail = clearSpacebars(findViewById(R.id.editMail));
            String password = getText(findViewById(R.id.editPassword));
            String passwordAgain = getText(findViewById(R.id.editPasswordAgain));

            if (name.length() == 0) {
                makeToast("Введите ваше имя!");
                return;
            }
            if (surname.length() == 0) {
                makeToast("Введите вашу фамилию!");
                return;
            }
            if (mail.length() == 0 || !mail.contains("@")) {
                makeToast("Некорректный адрес электронной почты!");
                return;
            }
            if (password.length() == 0) {
                makeToast("Введите пароль!");
                return;
            }
            if (!password.equals(passwordAgain)) {
                makeToast("Пароли не совпадают!");
                return;
            }

            while (jsonArray == null) {
                sleep(250);
                print("wait");
            }
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    if (jsonArray.getString(i).equals(mail)) {
                        makeToast("Данная почта уже зарегистрирована!");
                        return;
                    }
                }

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", name);
                jsonObject.put("surname", surname);
                jsonObject.put("email", mail);
                jsonObject.put("password", password);

                jsonObject = ClientServer.post("register", jsonObject);

                if (jsonObject.has("id")) {
                    makeToast("Регистрация успешна!");

                    writeToFile("DATA", name + " " + surname + " " + mail
                            + " " + password + " " + jsonObject.get("id"));

                    print(jsonObject);
                    userID = (int) jsonObject.get("id");

                    startActivity(MainScreen.class);
                } else {
                    makeToast("Ошибка при обращении к серверу");
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
