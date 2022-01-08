package com.dannyweston.mdp_cw3.services;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class LocListener implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        Log.d("cw3", location.getLatitude() + " " + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    // information about the signal, i.e. number of satellites
        Log.d("cw3", "onStatusChanged: " + provider + " " + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
    // the user enabled (for example) the GPS
        Log.d("cw3", "onProviderEnabled: " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
    // the user disabled (for example) the GPS
        Log.d("cw3", "onProviderDisabled: " + provider);
    }
}
