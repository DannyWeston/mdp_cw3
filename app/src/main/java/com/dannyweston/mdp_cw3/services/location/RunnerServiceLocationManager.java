package com.dannyweston.mdp_cw3.services.location;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.util.Stack;

public class RunnerServiceLocationManager implements LocationListener {

    public RunnerServiceLocationManager(LocationManager lm){
        try {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 5, this);
        }
        catch (SecurityException e) {
            Log.d("mdpcw3_d", e.toString());
        }
    }

    private final Stack<Location> visited = new Stack<>();

    public Location getLocation(){
        return visited.peek();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("cw3", location.getLatitude() + " " + location.getLongitude());

        visited.add(location);
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
