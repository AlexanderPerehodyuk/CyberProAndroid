package com.example.screens.service;

import static com.example.screens.service.Service.print;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class ClientServer {
    private static final OkHttpClient client = new OkHttpClient();

    public static JSONObject post(String url, JSONObject jsonObject) {
        return call(client.newCall(new Request.Builder()
                .url("http://people-eye.herokuapp.com/api/" + url)
                .post(RequestBody.create(jsonObject.toString(), MediaType.parse("application/json; charset=utf-8")))
                .build()));
    }

    public static JSONObject get(String url) {
        return call(client.newCall(new Request.Builder()
                .url("http://people-eye.herokuapp.com/api/" + url)
                .get()
                .build()));
    }

    private static JSONObject call(Call call) {
        try {
            Response response = call.execute();

            String s = response.body().string();
            print(s);

            JSONObject answer = new JSONObject(s);

            response.close();

            return answer;
        } catch (Exception e) {
            print("Call: " + e);
        }

        return new JSONObject();
    }
}
