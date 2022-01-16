package com.dannyweston.mdp_cw3.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.views.RunningActivity;

class RunnerServiceNotifManager {
    public static final String EXTRA_STOP_RUN = "StopRun";
    private final NotificationManager notifManager;

    public RunnerServiceNotifManager(NotificationManager manager){
        this.notifManager = manager;
    }

    public Notification displayForegroundNotif(RunnerService rs, String channelId, String title, String content){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(rs, channelId)
                .setSmallIcon(R.drawable.run_icon_img)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(openRunActivityIntent(rs))
                .addAction(R.drawable.ic_launcher_foreground, rs.getString(R.string.txtNotifEndRun), stopRunActivityIntent(rs))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSilent(true);

        Notification notif = builder.build();
        notifManager.notify(RunnerService.RUNNER_SERVICE_ID, notif);

        return notif;
    }

    private PendingIntent openRunActivityIntent(RunnerService rs){
        Intent intent = new Intent(rs, RunningActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        return PendingIntent.getActivity(rs, 0, intent,  PendingIntent.FLAG_IMMUTABLE);
    }

    private PendingIntent stopRunActivityIntent(RunnerService rs){
        Intent intent = new Intent(rs, RunnerService.class)
                .putExtra(EXTRA_STOP_RUN, true);

        return PendingIntent.getService(rs, 0, intent,  PendingIntent.FLAG_IMMUTABLE);
    }

    public void addChannel(String id, String name, String desc, int importance){
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        channel.setDescription(desc);
        notifManager.createNotificationChannel(channel);
    }
}
