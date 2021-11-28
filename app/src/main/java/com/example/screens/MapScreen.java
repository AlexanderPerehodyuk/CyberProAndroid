package com.example.screens;

import static com.example.screens.service.DATA.latitude;
import static com.example.screens.service.DATA.longitude;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.screens.service.BaseActivity;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.layers.GeoObjectTapEvent;
import com.yandex.mapkit.layers.GeoObjectTapListener;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.GeoObjectSelectionMetadata;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

import java.util.ArrayList;

public class MapScreen extends BaseActivity implements GeoObjectTapListener, UserLocationObjectListener {
    private String[] listItems;
    private boolean[] checkedItems;
    private final ArrayList<Integer> mUserItems = new ArrayList<>();
    private MapView mapview;
    private UserLocationLayer userLocationLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapview = findViewById(R.id.mapview); // находим нашу карту в loyout

        resetPos();

        mapview.getMap().addTapListener(this);
        userLocationLayer = MapKitFactory.getInstance().createUserLocationLayer(mapview.getMapWindow());

        findViewById(R.id.findYourLocationView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPos();

                mapview.getMap().getMapObjects().addPlacemark(new com.yandex.mapkit.geometry.Point(latitude, longitude),
                        ImageProvider.fromResource(getBaseContext(), R.drawable.problem));
            }
        });
        findViewById(R.id.add_problem_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                startActivity(intent);
            }
        });

        userLocationLayer.setVisible(true);

        listItems = getResources().getStringArray(R.array.problems_item);
        checkedItems = new boolean[listItems.length];

        findViewById(R.id.bntOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MapScreen.this);
                mBuilder.setTitle("Выберите проблему");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if (isChecked) {
                            mUserItems.add(position);
                        } else {
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", null);
                mBuilder.setNegativeButton("Отменить", null);
                mBuilder.setNeutralButton("Очистить всё", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                        }
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }

    @Override
    public boolean onObjectTap(@NonNull GeoObjectTapEvent geoObjectTapEvent) {
        GeoObjectSelectionMetadata selectionMetadata = geoObjectTapEvent
                .getGeoObject()
                .getMetadataContainer()
                .getItem(GeoObjectSelectionMetadata.class);

        if (selectionMetadata != null) {
            mapview.getMap().selectGeoObject(selectionMetadata.getId(), selectionMetadata.getLayerId());
        }

        return selectionMetadata != null;
    }


    @Override
    public void onObjectAdded(@NonNull UserLocationView userLocationView) {
        userLocationLayer.setAnchor(
                new PointF((float) (mapview.getWidth() * 0.5), (float) (mapview.getHeight() * 0.5)),
                new PointF((float) (mapview.getWidth() * 0.5), (float) (mapview.getHeight() * 0.83)));

        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE & 0x99ffffff);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapview.onStart();
        MapKitFactory.getInstance().onStart();
    }

    private void resetPos() {
        mapview.getMap().move( // при запуске приложения переносимя на координаты которые прописаны в Point, в дальнейшем вместо них будут переменные для местоположения
                new CameraPosition(new com.yandex.mapkit.geometry.Point(latitude, longitude), 16.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 1),
                null);
    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {
    }

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull ObjectEvent objectEvent) {
    }
}



