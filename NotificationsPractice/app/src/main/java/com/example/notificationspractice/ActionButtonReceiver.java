package com.example.notificationspractice;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Ильнур on 25.09.2015.
 */
public class ActionButtonReceiver extends BroadcastReceiver {

    public static final String ACTION = "action_button";
    public static final String ACTION_BUTTON = "com.example.notificationspractice.ActionButtonReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra(ACTION, -1);
        if (id != -1) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(id);
        }
    }
}