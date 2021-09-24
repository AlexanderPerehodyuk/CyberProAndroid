package com.example.screens;



import static com.example.screens.MainScreen.latitude;
import static com.example.screens.MainScreen.longitude;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.geo.XYPoint;
import com.yandex.mapkit.layers.GeoObjectTapEvent;
import com.yandex.mapkit.layers.GeoObjectTapListener;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CompositeIcon;
import com.yandex.mapkit.map.GeoObjectSelectionMetadata;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.RotationType;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.places.panorama.IconMarker;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

import java.util.ArrayList;
import java.util.Objects;

public class MapScreen extends AppCompatActivity implements GeoObjectTapListener, UserLocationObjectListener {
    boolean checkTheCurrentPosition = false;
    Button m0rder;
    TextView mITemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    static MapView mapview;
    private UserLocationLayer userLocationLayer;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);



        mapview = (MapView)findViewById(R.id.mapview); // находим нашу карту в loyout
//
//        mapview.getMap().move( // при запуске приложения переносимя на координаты которые прописаны в Point, в дальнейшем вместо них будут переменные для местоположения
//                new CameraPosition(new com.yandex.mapkit.geometry.Point(latitude, longitude), 17.0f, 0.0f, 0.0f),
//                new Animation(Animation.Type.SMOOTH, 1),
//                null);
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//
//        mapview.getMap().move( // при запуске приложения переносимя на координаты которые прописаны в Point, в дальнейшем вместо них будут переменные для местоположения
//                new CameraPosition(new Point(latitude, longitude), 10.0f, 0.0f, 0.0f),
//                new Animation(Animation.Type.SMOOTH, 1),
//                null);
//            }
//        }, 9000); //specify the number of milliseconds

        mapview.getMap().addTapListener(this);
        MapKit mapKit = MapKitFactory.getInstance();
        userLocationLayer = mapKit.createUserLocationLayer(mapview.getMapWindow());
        imageView = (ImageView) findViewById(R.id.add_problem_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                startActivity(intent);
            }
        });

        userLocationLayer.setVisible(true);
//        userLocationLayer.setHeadingEnabled(true);
//        if (!checkTheCurrentPosition) {
////            userLocationLayer.setEnabled(false);
////            userLocationLayer.setHeadingEnabled(true);
//            userLocationLayer.setObjectListener(this);
//            checkTheCurrentPosition = true;
//
//        }

//        userLocationLayer.setHeadingEnabled(true);
//        userLocationLayer.setObjectListener(this);

//        userLocationLayer.isAutoZoomEnabled();
        m0rder = (Button) findViewById(R.id.bntOrder);
        mITemSelected = (TextView) findViewById(R.id.tvItemSelected);


        listItems = getResources().getStringArray(R.array.problems_item);
        checkedItems = new boolean[listItems.length];
//        moveCameraToPosition(Objects.requireNonNull(userLocationLayer.cameraPosition()).getTarget());
        m0rder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder  = new AlertDialog.Builder(MapScreen.this);
                mBuilder.setTitle("Выберите проблему");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {

                        if(isChecked){
                            mUserItems.add(position);
                        }else{
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for(int i = 0; i < mUserItems.size(); i++){
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        mITemSelected.setText(item);
                    }
                });
                mBuilder.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mBuilder.setNeutralButton("Очистить всё", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedItems.length; i++){
                            checkedItems[i] = false;
                            mUserItems.clear();
                            mITemSelected.setText("");
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
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


    @Override
    public boolean onObjectTap(@NonNull GeoObjectTapEvent geoObjectTapEvent) {
        final GeoObjectSelectionMetadata selectionMetadata = geoObjectTapEvent
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
                new PointF((float)(mapview.getWidth() * 0.5), (float)(mapview.getHeight() * 0.5)),
                new PointF((float)(mapview.getWidth() * 0.5), (float)(mapview.getHeight() * 0.83)));

        userLocationView.getArrow().setIcon(ImageProvider.fromResource(
                this, R.drawable.user_place));

//        CompositeIcon pinIcon = userLocationView.getPin().useCompositeIcon();
//
//
//
//        pinIcon.setIcon(
//                "pin",
//                ImageProvider.fromResource(this, R.drawable.search_result),
//                new IconStyle().setAnchor(new PointF(0.5f, 0.5f))
//                        .setRotationType(RotationType.ROTATE)
//                        .setZIndex(1f)
//                        .setScale(0.5f)
//        );

        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE & 0x99ffffff);
    }
    public void moveCameraToPosition(@NonNull Point target) {
        mapview.getMap().move(
                new CameraPosition(target, 15.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 2), null);
    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {

    }

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull ObjectEvent objectEvent) {
    }
}




