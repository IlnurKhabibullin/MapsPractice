package com.example.notificationspractice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * Created by Ильнур on 25.09.2015.
 */
public class BatteryNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        if (level != -1 && scale != -1) {
            float batteryPct = level / (float) scale;
            if (batteryPct < 90) {
                Intent okIntent = new Intent(ActionButtonReceiver.ACTION_BUTTON);
                okIntent.putExtra(ActionButtonReceiver.ACTION, 1);
                PendingIntent okPendingIntent = PendingIntent.getBroadcast(context, 0, okIntent, 0);
                Intent settings = new Intent(Intent.ACTION_POWER_USAGE_SUMMARY);
                PendingIntent settingsIntent = PendingIntent.getActivity(context, 0, settings, 0);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Feed your phone!")
                        .setContentText("Your battery has only 30% charge")
                        .addAction(R.mipmap.ic_launcher, "Settings", settingsIntent)
                        .addAction(R.mipmap.ic_launcher, "Ok", okPendingIntent);
                Intent resultIntent = new Intent(context, MainActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                        0, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);
                ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                        .notify(3, mBuilder.build());
            }
        }
    }
}
