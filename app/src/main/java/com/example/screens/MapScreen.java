package com.example.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

public class MapScreen extends AppCompatActivity {

    private MapView mapview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey("61db36cd-2c66-4ba0-a8dc-686b6a0515b8"); // ключ апи в профиле у меня(у вовы) он нужен для получения количества запроосов и д.рю
        MapKitFactory.initialize(this);


        setContentView(R.layout.activity_main);
        mapview = (MapView)findViewById(R.id.mapview); // находим нашу карту в loyout


        mapview.getMap().move( // при запуске приложения переносимя на координаты которые прописаны в Point, в дальнейшем вместо них будут переменные для местоположения
                new CameraPosition(new com.yandex.mapkit.geometry.Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);

    }
    // отображение карты и остановка обработки карты, когда Activity с картой становится невидимым для пользователя
    @Override
    protected void onStop() {
        super.onStop();
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapview.onStart();
        MapKitFactory.getInstance().onStart();
    }
}




