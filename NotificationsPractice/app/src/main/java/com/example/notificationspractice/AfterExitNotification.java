package com.example.notificationspractice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;

/**
 * Created by Ильнур on 24.09.2015.
 */
public class AfterExitNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Hey, buddy!")
                .setContentText("You didn't visit my app for a whole minute! Shame on you!")
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, R.color.after_exit_notification));
        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context)
                .addParentStack(MainActivity.class)
                .addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(1, mBuilder.build());
    }
}
