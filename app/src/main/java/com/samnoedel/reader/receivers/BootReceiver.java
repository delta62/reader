package com.samnoedel.reader.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Handles the initial scheduling of feed update checks. For efficiency, nothing is actually done
 * at startup other than scheduling a future check for updated feeds.
 */
public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = BootReceiver.class.getName();
    private static final int CHECK_INTERVAL = 1000 * 60 * 60; // 1 hour in milliseconds

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Hello world! Scheduling initial feed updates.");
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent i = new Intent(context, AlarmReceiver.class);
        PendingIntent pending = PendingIntent.getBroadcast(context, 0, i, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, CHECK_INTERVAL, pending);
    }
}
