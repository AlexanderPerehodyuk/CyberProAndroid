package com.example.screens.service;

import java.util.ArrayList;

public final class DATA {
    public static final int IMAGE_PICK_CODE = 1000;
    public static final int PERMISSION_CODE = 1001;

    public static volatile double latitude, longitude;

    public static String personalData;

    public static volatile int userID;

    public static ArrayList<Integer> idProblems = new ArrayList<>(0);

    public static final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
}
