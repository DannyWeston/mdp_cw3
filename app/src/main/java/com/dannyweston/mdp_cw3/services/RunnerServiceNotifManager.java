package com.dannyweston.mdp_cw3.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import androidx.core.app.NotificationCompat;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.services.RunnerService;

class RunnerServiceNotifManager {
    private final NotificationManager notifManager;

    public RunnerServiceNotifManager(NotificationManager manager){
        this.notifManager = manager;
    }

    public Notification displayForegroundNotif(RunnerService rs, String channelId, String title, String content){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(rs, channelId)
                .setSmallIcon(R.drawable.run_icon_img)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSilent(true);

        Notification notif = builder.build();
        notifManager.notify(RunnerService.RUNNER_SERVICE_ID, notif);

        return notif;
    }

    public void addChannel(String id, String name, String desc, int importance){
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        channel.setDescription(desc);
        notifManager.createNotificationChannel(channel);
    }
}
