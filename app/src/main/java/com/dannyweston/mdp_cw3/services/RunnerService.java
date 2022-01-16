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
import com.dannyweston.mdp_cw3.repositories.LocUpdateRepository;
import com.dannyweston.mdp_cw3.repositories.RunRepository;

import java.util.Timer;
import java.util.TimerTask;

public class RunnerService extends Service implements LocationListener {
    // Notification manager
    private RunnerServiceNotifManager _notifManager;

    private LocUpdateRepository _locRepo;
    private RunRepository _runRepo;

    // Timer for keeping track of run time
    private Timer _timer;

    private Location _lastLocation;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getBooleanExtra(getString(R.string.extraStopRun), false)) {
            this.endRun();
            Log.d("mdpcw3", "Run finished");
        }

        return Service.START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        activeRunId = -1;

        _runRepo = new RunRepository(getApplication());
        _locRepo = new LocUpdateRepository(getApplication());

        // Start the service in the foreground and pass in the required notification
        // Show background service notification
        if (_notifManager == null) {
            _notifManager = new RunnerServiceNotifManager(getSystemService(NotificationManager.class));
            Log.d("mdpcw3", "Notification manager setup for RunnerService");

        }
    }

    public void endRun(){
        _timer.cancel();

        setActiveRunId(-1);

        setFinished(true);

        getSystemService(LocationManager.class).removeUpdates(this);

        stopForeground(true);
    }

    public long startNewRun() {
        if (getActiveRunId() >= 0){
            Log.d("mdpcw3", "A run is already in progress");
            return -1;
        }

        setFinished(false);

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

        // Create a notification because we want to start this service foregrounded
        // The user must be able to use another application whilst using the app
        Notification n = _notifManager.displayForegroundNotif(this);

        // Setup timer for keeping track of total run time
        _timer = new Timer();

        // Create timer to update run timer
        _timer.schedule(new TimerTask() {
            @Override
            public void run() {
                _runRepo.setRunDuration(getActiveRunId(), System.currentTimeMillis() - startTime); }
        }, 0, getResources().getInteger(R.integer.timerRunUpdateInterval));

        startForeground(getResources().getInteger(R.integer.serviceRunnerId), n);

        // Return the ID of the created run (active run)
        return getActiveRunId();
    }

    private long activeRunId;
    private void setActiveRunId(long id) {
        this.activeRunId = id;
    }
    public long getActiveRunId(){
        return activeRunId;
    }

    // Keep track of elapsed time
    private final MutableLiveData<Boolean> _finished = new MutableLiveData<>();
    public LiveData<Boolean> getFinished(){ return _finished; }
    public void setFinished(boolean _finished){ this._finished.setValue(_finished); }

    // Keep track of distance
    private final MutableLiveData<Double> _distance = new MutableLiveData<>();
    public LiveData<Double> getTotalDistance(){ return _distance;}
    public void setTotalDistance(double distance){ _distance.setValue(distance); }

    // Keep track of elapsed time
    private final MutableLiveData<Long> _elapsed = new MutableLiveData<>();
    public LiveData<Long> getElapsed(){ return _elapsed; }
    public void setElapsed(long _elapsed){ this._elapsed.setValue(_elapsed); }

    @Override
    public void onLocationChanged(Location location) {
        // Don't update if not in a run
        if (getActiveRunId() < 0) return;

        // Add new record if possible
        if (_lastLocation != null){
            double dist = location.distanceTo(_lastLocation);

            LocationUpdate update = new LocationUpdate(activeRunId, dist);

            Double soFar = getTotalDistance().getValue();
            if (soFar == null){ return; }

            double newDist = soFar + dist;
            setTotalDistance(newDist);

            // Add update to db
            _runRepo.setRunDistance(getActiveRunId(), newDist);
            _locRepo.addPos(update);
        }

        _lastLocation = location;
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("cw3", "onProviderEnabled: " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("cw3", "onProviderDisabled: " + provider);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new RunnerServiceBinder(this);
    }
}