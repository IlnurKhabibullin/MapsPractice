package com.example.notificationspractice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * Created by Ильнур on 25.09.2015.
 */
public class WifiNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String name = intent.getStringExtra("wifi");
        if (null != name && getWifiName(context).contains(name)) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Wifi connected")
                    .setContentText("You've connected to chosen wifi");
            Intent resultIntent = new Intent(context, MainActivity.class);
            TaskStackBuilder.create(context)
                    .addParentStack(MainActivity.class)
                    .addNextIntent(resultIntent);
            ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                    .notify(5, mBuilder.build());
        }

    }

    private String getWifiName(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getSSID();
    }
}
