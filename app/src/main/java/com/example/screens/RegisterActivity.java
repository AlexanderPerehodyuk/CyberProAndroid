package com.example.screens;

import static com.example.screens.service.DATA.personalData;
import static com.example.screens.service.DATA.userID;
import static com.example.screens.service.Service.print;
import static com.example.screens.service.Service.sleep;
import static com.example.screens.service.Service.writeToFile;

import android.os.Bundle;
import android.view.View;

import com.example.screens.service.ClientServer;
import com.example.screens.service.Service;

import org.json.JSONObject;

public class RegisterActivity extends LoginActivity {
    private String allMails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Service.post(() -> {
            try {
                allMails = (ClientServer.get("all_users")).getJSONArray("users").toString();
                print(allMails);
            } catch (Exception e) {
                print(e);
            }
        });
    }

    @Override
    public void enter(View view) {
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
            if (!correctEmail(mail)) {
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

            while (allMails == null) {
                sleep(250);
                print("wait");
            }
            try {
                if (allMails.contains(mail)) {
                    makeToast("Данная почта уже зарегистрирована!");
                    return;
                }

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", name);
                jsonObject.put("surname", surname);
                jsonObject.put("email", mail);
                jsonObject.put("password", password);

                jsonObject = ClientServer.post("register", jsonObject);

                if (jsonObject.has("id")) {
                    makeToast("Регистрация успешна!");

                    userID = jsonObject.getInt("id");

                    writeToFile("DATA", name + " " + surname + " " + mail + " " + password + " " + userID);

                    personalData = new String[]{name, surname, mail, password, String.valueOf(userID)};

                    startActivity(MainScreen.class);
                } else {
                    makeToast("Ошибка при обращении к серверу");
                }
            } catch (Exception e) {
                print(e);
            }
        });
    }

    private boolean correctEmail(String email) {
        int len = email.length();

        if (len == 0) return false;
        if (numCharacter(email, '@') != 1) return false;
        if (numCharacter(email, '.') == 0) return false;
        if (email.indexOf('.') + 1 == len) return false;
        return email.charAt(email.indexOf('@') + 1) != '.';
    }

    private int numCharacter(String input, char character) {
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == character) count++;
        }
        return count;
    }
}
