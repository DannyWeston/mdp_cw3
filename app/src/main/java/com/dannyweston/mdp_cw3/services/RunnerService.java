package com.dannyweston.mdp_cw3.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.dao.LocationUpdate;
import com.dannyweston.mdp_cw3.dao.Run;
import com.dannyweston.mdp_cw3.dao.repositories.LocUpdateRepository;
import com.dannyweston.mdp_cw3.dao.repositories.RunRepository;

import java.util.Timer;
import java.util.TimerTask;

public class RunnerService extends Service implements LocationListener {
    protected static final int RUNNER_SERVICE_ID = 1;

    // Notification manager
    private RunnerServiceNotifManager _notifManager;

    private LocUpdateRepository _locRepo;
    private RunRepository _runRepo;

    // Timer for keeping track of run time
    private Timer _timer;

    private long activeRunId = -1;
    private Location lastLocation;

    @Override
    public void onCreate() {
        super.onCreate();

        _runRepo = new RunRepository(getApplication());
        _locRepo = new LocUpdateRepository(this.getApplication());

        // Start the service in the foreground and pass in the required notification
        // Show background service notification
        if (_notifManager == null) {
            _notifManager = new RunnerServiceNotifManager(getSystemService(NotificationManager.class));

            // Add notification channels
            _notifManager.addChannel(
                    getString(R.string.generic_notif_channel_id),
                    getString(R.string.generic_notif_channel_name),
                    getString(R.string.generic_notif_channel_desc),
                    NotificationManager.IMPORTANCE_DEFAULT
            );
        }
    }

    public void endRun(){
        _timer.cancel();

        setActiveRunId(-1);

        getSystemService(LocationManager.class).removeUpdates(this);

        stopForeground(true);
    }

    public long startNewRun() {
        // Setup timer for keeping track of total run time
        _timer = new Timer();

        if (activeRunId >= 0){
            Log.d("mdpcw3", "A run is already in progress");
            return -1;
        }

        // Create new run and set according id
        Run newRun = new Run("TestRun");
        setActiveRunId(_runRepo.addNewRun(newRun));

        // Reset distance and duration counters
        setTotalDistance(0);
        setElapsed(0);

        // Check if new run was made
        if (getActiveRunId() < 0){ return -1; }

        long startTime = System.currentTimeMillis();

        // Start listening for location service updates, if not successful, log and return
        try {
            getSystemService(LocationManager.class).requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5, 5, this);
        }
        catch (SecurityException e) {
            Log.d("mdpcw3", e.toString());
            return -1;
        }

        Notification n = _notifManager.displayForegroundNotif(
                this,
                getString(R.string.generic_notif_channel_id),
                getString(R.string.running_in_foreground_title),
                getString(R.string.running_in_foreground_desc)
        );

        // Create timer to update run timer
        _timer.schedule(new TimerTask() {
            @Override
            public void run() {
                _runRepo.setRunDuration(getActiveRunId(), System.currentTimeMillis() - startTime); }
        }, 0, 250);

        startForeground(RUNNER_SERVICE_ID, n);

        return getActiveRunId();
    }

    private void setActiveRunId(long id) {
        this.activeRunId = id;
    }

    public long getActiveRunId(){
        return activeRunId;
    }

    // Keep track of distance
    private final MutableLiveData<Double> totalDistance = new MutableLiveData<>();
    public LiveData<Double> getTotalDistance(){ return totalDistance;}
    public void setTotalDistance(double distance){ totalDistance.setValue(distance); }

    // Keep track of elapsed time
    private final MutableLiveData<Long> elapsed = new MutableLiveData<>();
    public LiveData<Long> getElapsed(){ return elapsed;}
    public void setElapsed(long elapsed){ this.elapsed.setValue(elapsed); }

    @Override
    public void onLocationChanged(Location location) {
        // Don't update if not in a run
        if (getActiveRunId() < 0) return;

        // Add new record if possible
        if (lastLocation != null){
            double dist = location.distanceTo(lastLocation);

            LocationUpdate update = new LocationUpdate(activeRunId, dist);

            Double soFar = getTotalDistance().getValue();
            if (soFar == null){ return; }

            double newDist = soFar + dist;
            setTotalDistance(newDist);

            // Add update to db
            _runRepo.setRunDistance(activeRunId, newDist);
            _locRepo.addPos(update);
        }

        lastLocation = location;
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new RunnerServiceBinder(this);
    }
}