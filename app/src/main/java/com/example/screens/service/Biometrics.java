package com.example.screens.service;

import static com.example.screens.service.Service.activity;
import static com.example.screens.service.Service.post;
import static com.example.screens.service.Service.print;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

public final class Biometrics {
    private final Runnable runnable;

    public Biometrics(Runnable runnable) {
        this.runnable = runnable;
    }

    public void start() {
        activity.runOnUiThread(this::showBiometricPrompt);
    }

    private void showBiometricPrompt() {
        new BiometricPrompt((FragmentActivity) activity, Service.threadPool, new BiometricsCallback()).authenticate(
                new BiometricPrompt.PromptInfo.Builder()
                        .setDescription("ИСпользуйте вашу биометрические данные")
                        .setTitle("Подтвердите вашу личность")
                        .setNegativeButtonText("Отменить")
                        .build());
    }

    private class BiometricsCallback extends BiometricPrompt.AuthenticationCallback {
        @Override
        public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
            post(runnable);
        }

        @Override
        public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
            print(errString);
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
        }
    }
}
