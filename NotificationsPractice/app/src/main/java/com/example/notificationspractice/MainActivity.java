package com.example.notificationspractice;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    EditText mEditWifiName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.justButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder notification = new NotificationCompat.Builder(MainActivity.this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Warning")
                        .setContentText("You've pressed a button!")
                        .setColor(ContextCompat.getColor(MainActivity.this
                                , R.color.button_notification));
                TaskStackBuilder.create(MainActivity.this).addParentStack(MainActivity.this);
                ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
                        .notify(0, notification.build());
            }
        });
        mEditWifiName = (EditText) findViewById(R.id.wifi_text);
        findViewById(R.id.wifi_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditWifiName.getText().toString() != null &&
                        !mEditWifiName.getText().toString().equals("")) {
                    WifiNotification mWifiNotification = new WifiNotification();
                    LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(
                            mWifiNotification, new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION));
                    Intent intent = new Intent(WifiManager.NETWORK_STATE_CHANGED_ACTION);
                    intent.putExtra("wifi", mEditWifiName.getText().toString());
                    LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, 60);
        long firstTime = c.getTimeInMillis();
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, firstTime, PendingIntent.getBroadcast(
                this, 0, new Intent(this, AfterExitNotification.class), 0));
        super.onDestroy();
    }
}
