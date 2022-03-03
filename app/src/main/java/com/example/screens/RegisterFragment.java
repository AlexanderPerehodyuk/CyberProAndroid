package com.example.screens;

import static com.example.screens.service.DATA.personalData;
import static com.example.screens.service.DATA.userID;
import static com.example.screens.service.Service.print;
import static com.example.screens.service.Service.writeToFile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.screens.service.ClientServer;
import com.example.screens.service.Service;
import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;

public class RegisterFragment extends LoginFragment {
    private String allMails;

    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.register_fragment, container, false);

        Service.post(() -> {
            try {
                allMails = (ClientServer.get("all_users")).getJSONArray("users").toString();
                print(allMails);
            } catch (Exception e) {
                print(e);
            }
        });
        MaterialButton buttonRegister = view.findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter(view);
            }
        });
        return view;
    }

    private void enter(View view) {
        Service.post(() -> {
            EditText editName = view.findViewById(R.id.editName);
            EditText editSurname = view.findViewById(R.id.editName);
            EditText editMail = view.findViewById(R.id.editName);
            EditText editPassword = view.findViewById(R.id.editName);
            EditText editPasswordAgain = view.findViewById(R.id.editName);
            String name = clearSpacebars(editName);
            String surname = clearSpacebars(editSurname);
            String mail = clearSpacebars(editMail);
            String password = getText(editPassword);
            String passwordAgain = getText(editPasswordAgain);
            if (mail.contains("@mailinator") || mail.contains("@maildrop") || mail.contains("@tempr")) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(), "Пожалуйста, укажите свою настоящую почту для регистрации в приложении!", Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }
            if (name.length() == 0) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(), "Введите ваше имя!", Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }
            if (surname.length() == 0) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(), "Введите вашу фамилию!", Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }
            if (!correctEmail(mail)) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(), "Некорректный адрес электронной почты!", Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }
            if (password.length() == 0) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(), "Введите пароль!", Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }
            if (!password.equals(passwordAgain)) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(), "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }

            while (allMails == null) {
                print("wait");
            }
            try {
                if (allMails.contains(mail)) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), "Данная почта уже зарегистрирована!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    return;
                }

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", name);
                jsonObject.put("surname", surname);
                jsonObject.put("email", mail);
                jsonObject.put("password", password);

                jsonObject = ClientServer.post("register", jsonObject);

                if (jsonObject.has("id")) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), "Регистрация успешна!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    userID = jsonObject.getInt("id");

                    writeToFile("DATA", name + " " + surname + " " + mail + " " + password + " " + userID);

                    personalData = new String[]{name, surname, mail, password, String.valueOf(userID)};

                    startActivity(new Intent(getContext(), MapScreen.class));
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), "Ошибка при обращении к серверу", Toast.LENGTH_SHORT).show();
                        }
                    });
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

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

}
