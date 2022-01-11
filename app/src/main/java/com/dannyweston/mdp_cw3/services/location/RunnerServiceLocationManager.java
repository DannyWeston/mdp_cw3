package com.dannyweston.mdp_cw3.services.location;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import com.dannyweston.mdp_cw3.dao.LocationUpdate;
import com.dannyweston.mdp_cw3.dao.Run;
import com.dannyweston.mdp_cw3.dao.repositories.LocUpdateRepository;
import com.dannyweston.mdp_cw3.dao.repositories.RunRepository;

public class RunnerServiceLocationManager implements LocationListener {
    private final LocUpdateRepository _posRepo;
    private final RunRepository _runRepo;

    private final LocationManager _lm;

    public RunnerServiceLocationManager(LocationManager lm, LocUpdateRepository posRepo, RunRepository routeRepo){
        this._posRepo = posRepo;
        this._runRepo = routeRepo;

        this._lm = lm;

        try {
            _lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 5, this);
        }
        catch (SecurityException e) {
            Log.d("mdpcw3", e.toString());
        }
    }

    private long activeRunId = -1;

    public boolean runInProgress(){
        return activeRunId >= 0;
    }

    public void startNewRun(){
        if (runInProgress()){
            Log.d("mdpcw3", "A run is already in progress");
            return;
        }

        // Create new run and set according id
        Run newRun = new Run("TestRun");

        activeRunId = _runRepo.addNewRun(newRun);
    }

    public void stopRun(){
        // TODO: Disable location gathering when not on an active run
        activeRunId = -1;
    }

    private Location lastVisited; // Last visited location

    @Override
    public void onLocationChanged(Location location) {
        // Don't update if not in a run
        if (!runInProgress()) return;

        // We can add a location update for the active running session
        // TODO: Ignore non-possible distances and add log
        if (lastVisited != null) {
            double travelled = lastVisited.distanceTo(location);

            LocationUpdate update = new LocationUpdate(
                    activeRunId,
                    location.getLatitude(),
                    location.getLongitude(),
                    travelled);

            _posRepo.addPos(update);
        }

        // Set last visited location to current if valid
        lastVisited = location;
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
