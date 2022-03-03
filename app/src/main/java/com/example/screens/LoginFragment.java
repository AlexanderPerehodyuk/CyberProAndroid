package com.example.screens;


import static com.example.screens.service.Service.print;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.screens.service.ClientServer;
import com.example.screens.service.Service;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

/**
 * Fragment representing the login screen for Shrine.
 */
public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        final TextInputLayout passwordTextInput = view.findViewById(R.id.password_text_input);
        final EditText passwordEditText = view.findViewById(R.id.password_edit_text);
        final EditText editMail = view.findViewById(R.id.editMail);
        MaterialButton nextButton = view.findViewById(R.id.next_button);
        TextView textRegistration = view.findViewById(R.id.textRegistration);
        textRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedInstanceState == null) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, RegisterFragment.newInstance())
                            .commitNow();
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Service.post(() -> {
                    String mail = editMail.getText().toString();
                    String password = passwordEditText.getText().toString();
                    if (mail.length() == 0 || !mail.contains("@")) {
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

                    try {
                        JSONObject jsonObject = new JSONObject().put("email", mail).put("password", password);

                        jsonObject = ClientServer.post("login", jsonObject);
                        if (jsonObject.has("status")) {
//                    print(ClientServer.post("user", new JSONObject().put("user_id", jsonObject.getInt("id"))));startActivity(MainScreen.class);
                            startActivity(new Intent(getContext(), MapScreen.class));
                        } else {
                            if (jsonObject.getString("error").equals("User not registered")) {
                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(getActivity(), "Вы не зарегистрированы!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(getActivity(), "Ошибка при обращении к серверу", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    } catch (Exception e) {
                        print(e);
                    }
                });
            }
        });

        // Clear the error once more than 8 characters are typed.
        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(null); //Clear the error
                }
                return false;
            }
        });
        return view;
    }

    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }


    protected String clearSpacebars(EditText editText) {
        if (editText != null) {
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
        }

        return "";
    }

    protected String getText(EditText editText) {
        return editText.toString();
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

}
