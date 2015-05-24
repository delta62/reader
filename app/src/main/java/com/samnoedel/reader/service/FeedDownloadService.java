package com.samnoedel.reader.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.samnoedel.reader.R;

/**
 * Handles downloading & saving of new feed content
 */
public class FeedDownloadService extends Service {

    private static final String TAG = FeedDownloadService.class.getName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Starting FeedDownloadService");
        Toast.makeText(this, R.string.service_started, Toast.LENGTH_SHORT).show();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Stopping FeedDownloadService");
        Toast.makeText(this, R.string.service_stopped, Toast.LENGTH_SHORT).show();
    }
}
