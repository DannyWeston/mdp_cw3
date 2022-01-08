package com.dannyweston.mdp_cw3.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.dannyweston.mdp_cw3.R;

public class RunnerService extends Service {

    private static final int RUNNER_SERVICE_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();

        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        LocListener locationListener = new LocListener();

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 5, locationListener);
        }
        catch (SecurityException e) {
            Log.d("cw3", e.toString());
        }

        Notification n = displayServiceNotif("Some content text");

        // Start the service in the foreground and pass in its notification
        startForeground(RUNNER_SERVICE_ID, n);
    }

    // Notification specifics
    private Notification displayServiceNotif(String contentText){
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        String channelId = "Channel1";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Tracker";
            String description = "Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Application")
                    .setContentText(contentText)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            Notification notif = builder.build();

            // Display built notification
            notificationManager.notify(RUNNER_SERVICE_ID, notif);

            return notif;
        }

        return null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new RunnerServiceBinder();
    }

    public static class RunnerServiceBinder extends Binder implements IInterface {
        @Override
        public IBinder asBinder() {
            return this;
        }
    }
}
