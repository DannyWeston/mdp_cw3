package com.dannyweston.mdp_cw3.services.location;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.dao.repositories.LocUpdateRepository;
import com.dannyweston.mdp_cw3.dao.repositories.RunRepository;

import java.util.Timer;
import java.util.TimerTask;

public class RunnerService extends Service {
    protected static final int RUNNER_SERVICE_ID = 1;

    // Notification manager
    private RunnerServiceNotifManager notifManager;
    private RunnerServiceLocationManager locManager;

    // Repositories
    private LocUpdateRepository _posRepo;
    private RunRepository _routeRepo;

    private Timer _timer;

    @Override
    public void onCreate() {
        super.onCreate();

        // Communicate with database
        _posRepo = new LocUpdateRepository(this.getApplication());
        _routeRepo = new RunRepository(this.getApplication());

        // Setup timer for keeping track of total run time
        _timer = new Timer();

        // Create location handler
        if (locManager == null) {
            locManager = new RunnerServiceLocationManager(
                    getSystemService(LocationManager.class),
                    _posRepo, _routeRepo);
        }

        // Start the service in the foreground and pass in the required notification
        // Show background service notification
        if (this.notifManager == null) {
            this.notifManager = new RunnerServiceNotifManager(
                    getSystemService(NotificationManager.class)
            );

            // Add notification channels
            notifManager.addChannel(
                    getString(R.string.generic_notif_channel_id),
                    getString(R.string.generic_notif_channel_name),
                    getString(R.string.generic_notif_channel_desc),
                    NotificationManager.IMPORTANCE_DEFAULT
            );
        }

        Notification n = notifManager.displayForegroundNotif(
                this,
                getString(R.string.generic_notif_channel_id),
                getString(R.string.running_in_foreground_title),
                getString(R.string.running_in_foreground_desc)
        );

        startForeground(RUNNER_SERVICE_ID, n);
    }

    public boolean alreadyRunning(){
        return locManager.runInProgress();
    }

    public void startNewRun(){
        // Do something to start a new run
        locManager.startNewRun();

        long startTime = System.currentTimeMillis();

        _timer.schedule(new TimerTask() {
            @Override
            public void run() { setTimeElapsed(System.currentTimeMillis() - startTime); }
        }, 0, 250);
    }

    public void stopRun(){
        _timer.cancel();

        locManager.stopRun();
    }

    // Time elapsed since run started
    private MutableLiveData<Long> timeElapsed = new MutableLiveData<>();
    public LiveData<Long> getTimeElapsed() { return this.timeElapsed; }
    private void setTimeElapsed(long elapsed) { timeElapsed.postValue(elapsed); }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new RunnerServiceBinder(this);
    }
}