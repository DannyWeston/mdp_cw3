package com.dannyweston.mdp_cw3.services.location;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.room.Room;

import com.dannyweston.mdp_cw3.BuildConfig;
import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.dao.AppDatabase;
import com.dannyweston.mdp_cw3.dao.Position;
import com.dannyweston.mdp_cw3.dao.PositionDao;

public class RunnerService extends Service{
    protected static final int RUNNER_SERVICE_ID = 1;

    // Notification manager
    private RunnerServiceNotifManager notifManager;
    private RunnerServiceLocationManager locManager;

    @Override
    public void onCreate() {
        super.onCreate();

        if (this.notifManager == null) {
            this.notifManager = new RunnerServiceNotifManager(
                    (NotificationManager) getSystemService(NotificationManager.class)
            );

            // Add notification channels
            notifManager.addChannel(
                    getString(R.string.generic_notif_channel_id),
                    getString(R.string.generic_notif_channel_name),
                    getString(R.string.generic_notif_channel_desc),
                    NotificationManager.IMPORTANCE_DEFAULT
            );
        }

        if (locManager == null) {
            locManager = new RunnerServiceLocationManager((LocationManager)getSystemService(LocationManager.class));
        }

        // Start the service in the foreground and pass in the required notification
        Notification n = notifManager.displayForegroundNotif(
                this,
                getString(R.string.generic_notif_channel_id),
                getString(R.string.running_in_foreground_title),
                getString(R.string.running_in_foreground_desc)
        );

        startForeground(RUNNER_SERVICE_ID, n);
    }

    public int getInt(int key){
        return this.getResources().getInteger(key);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new RunnerServiceBinder();
    }
}