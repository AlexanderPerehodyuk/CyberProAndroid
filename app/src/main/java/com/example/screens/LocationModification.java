package com.example.screens;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.example.screens.service.DATA.latitude;
import static com.example.screens.service.DATA.longitude;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class LocationModification implements LocationListener {
    private final Runnable runnable;
    private boolean start;

    public LocationModification(Context context, Runnable onFirstRun) {
        this.runnable = onFirstRun;
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED) {
            ((LocationManager) context.getSystemService(LOCATION_SERVICE))
                    .requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1, this);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        if (!start) {
            start = true;

            runnable.run();
        }
    }
}
