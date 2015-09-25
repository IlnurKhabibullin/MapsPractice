package com.example.notificationspractice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Ильнур on 24.09.2015.
 */
public class AlarmNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar now = GregorianCalendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        switch (hour) {
            case 20:
            case 15:
                int minutes = now.get(Calendar.MINUTE);
                if (12 == minutes) {
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Wake up, Neo")
                            .setContentText("Time is " + hour + ":" + minutes)
                            .setColor(ContextCompat.getColor(context, R.color.alarm_notification));
                    Intent resultIntent = new Intent(context, MainActivity.class);
                    TaskStackBuilder.create(context)
                    .addParentStack(MainActivity.class)
                    .addNextIntent(resultIntent);
                    ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                            .notify(2, mBuilder.build());
                }
                break;
        }
    }
}
