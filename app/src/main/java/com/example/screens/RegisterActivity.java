package com.example.screens;

import static com.example.screens.Service.print;
import static com.example.screens.Service.sleep;
import static com.example.screens.Service.writeToFile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity {
    private final OkHttpClient client = new OkHttpClient();
    private JSONArray jsonArray;
    public static String personalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (personalData == null) {
        setContentView(R.layout.activity_register);

        Service.post(() -> {
            Response response = null;

            try {
                response = client.newCall(new Request.Builder()
                        .url("http://people-eye.herokuapp.com/api/all_users")
                        .get()
                        .build()).execute();

                jsonArray = (JSONArray) (new JSONObject(response.body().string()).get("users"));
            } catch (Exception e) {
                print(e);
            }

            closeResponse(response);
        });
//
//            return;
//        }
//
//        setContentView(R.layout.activity_login);
    }

    public void register(View view) {
        String name = clearSpacebars(findViewById(R.id.editName));
        String surname = clearSpacebars(findViewById(R.id.editSurname));
        String mail = clearSpacebars(findViewById(R.id.editMail));
        String password = getText(findViewById(R.id.editPassword));
        String passwordAgain = getText(findViewById(R.id.editPasswordAgain));

        Service.post(() -> {
            while (jsonArray == null) {
                sleep(250);
            }

            Response response = null;

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

                response = client.newCall(new Request.Builder()
                        .url("http://people-eye.herokuapp.com/api/register")
                        .post(RequestBody.create(jsonObject.toString(), MediaType.parse("application/json; charset=utf-8")))
                        .build()).execute();

                jsonObject = new JSONObject(response.body().string());

                if (jsonObject.has("id")) {
                    makeToast("Регистрация успешна!");

                    writeToFile("DATA", name + " " + surname + " " + mail
                            + " " + password + " " + jsonObject.get("id"));

                    startActivity(MainScreen.class);
                } else {
                    makeToast("Ошибка при обращении к серверу");
                }
            } catch (Exception e) {
                print(e);
            }

            closeResponse(response);
        });
    }

    private String clearSpacebars(EditText editText) {
        String message = getText(editText);

        if (message.length() != 0) {
            String[] list = message.split(" ");
            StringBuilder stringBuilder = new StringBuilder();

            for (String s : list) {
                if (!s.equals("")) {
                    stringBuilder.append(s);
                    stringBuilder.append(" ");
                }
            }

            return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
        }

        return "";
    }

    private String getText(EditText editText) {
        return editText.getText().toString();
    }

    private void closeResponse(Response response) {
        if (response != null) {
            response.close();
        }
    }
}
