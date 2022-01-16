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
    private final NotificationManager notifManager;

    public RunnerServiceNotifManager(NotificationManager manager){
        this.notifManager = manager;
    }

    public Notification displayForegroundNotif(RunnerService rs){

        String channelId = rs.getString(R.string.generic_notif_channel_id); // Channel Id
        String channelName = rs.getString(R.string.generic_notif_channel_name); // Channel Name
        String channelDesc = rs.getString(R.string.generic_notif_channel_desc); // Channel name

        // Add notification channel
        addChannel(channelId, channelName, channelDesc, NotificationManager.IMPORTANCE_DEFAULT);

        // Create a new notification
        // Has a title, description, and single button to end the current active run
        // Clicking on the notification banner itself will open the active run activity (RunningActivity)
        NotificationCompat.Builder builder = new NotificationCompat.Builder(rs, channelId)
                .setSmallIcon(R.drawable.run_icon_img)
                .setContentTitle(rs.getString(R.string.txtNotifTitle))
                .setContentText(rs.getString(R.string.txtNotifDescription))
                .setContentIntent(openRunActivityIntent(rs))
                .addAction(R.drawable.ic_launcher_foreground, rs.getString(R.string.txtNotifEndRun), stopRunActivityIntent(rs))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSilent(true);

        Notification notif = builder.build();
        notifManager.notify(rs.getResources().getInteger(R.integer.serviceRunnerId), notif);

        rs.getString(R.string.extraStopRun);

        return notif;
    }

    private PendingIntent openRunActivityIntent(RunnerService rs){
        Intent intent = new Intent(rs, RunningActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        return PendingIntent.getActivity(rs, 0, intent,  PendingIntent.FLAG_IMMUTABLE);
    }

    private PendingIntent stopRunActivityIntent(RunnerService rs){
        Intent intent = new Intent(rs, RunnerService.class)
                .putExtra(rs.getString(R.string.extraStopRun), true);

        return PendingIntent.getService(rs, 0, intent,  PendingIntent.FLAG_IMMUTABLE);
    }

    private void addChannel(String id, String name, String desc, int importance){
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        channel.setDescription(desc);
        notifManager.createNotificationChannel(channel);
    }
}
