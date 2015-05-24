package com.samnoedel.reader.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.samnoedel.reader.service.FeedDownloadService;

/**
 * Check for updated RSS feeds if there is an appropriate network connection.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = AlarmReceiver.class.getName();
    private static final int CHECK_INTERVAL = 1000 * 60 * 60; // 1 hour in milliseconds

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Alarm triggered. Beginning feed update");
        if (hasNetwork(context)) {
            Log.i(TAG, "Network connection detected. Beginning download of RSS feeds");
            startUpdateFeedService(context);
        } else {
            Log.i(TAG, "No network connectivity; skipping feed download");
        }

        scheduleFeedUpdate(context);
    }

    private static boolean hasNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private static void startUpdateFeedService(Context context) {
        Intent i = new Intent(context, FeedDownloadService.class);
        context.startService(i);
    }

    private static void scheduleFeedUpdate(Context context) {
        Log.d(TAG, "Scheduling next feed update");
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent i = new Intent(context, AlarmReceiver.class);
        PendingIntent pending = PendingIntent.getBroadcast(context, 0, i, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, CHECK_INTERVAL, pending);
    }
}
