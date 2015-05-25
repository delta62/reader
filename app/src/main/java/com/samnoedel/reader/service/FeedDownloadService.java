package com.samnoedel.reader.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.samnoedel.reader.models.DatabaseHelper;
import com.samnoedel.reader.models.RssFeedItem;
import com.samnoedel.reader.rss.RssFeedItemDownloader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

/**
 * Handles downloading & saving of new feed content
 */
public class FeedDownloadService extends Service {

    private static final String TAG = FeedDownloadService.class.getName();

    /**
     * Since this is a local service (it runs in the same process as the hosting application), no
     * IPC fanciness is required and a global can be used to determine if the service is already
     * running.
     */
    private static boolean SERVICE_IS_RUNNING = false;

    private DatabaseHelper mDatabaseHelper;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (SERVICE_IS_RUNNING) {
            // TODO: This is definitely not a good way to prevent the service from being started again.
            return START_NOT_STICKY;
        }

        SERVICE_IS_RUNNING = true;
        Log.d(TAG, "Feed auto-download service started");

        Bundle b = intent.getExtras();
        RssFeedItem feedItem = (RssFeedItem)b.getSerializable(RssFeedItemDownloader.EXTRA_ITEM_URL);
        RssFeedItemDownloader downloader = new RssFeedItemDownloader(getApplicationContext());

        // TODO: Android is going to flip if this is done on the main thread. Start it in a new one.
        try {
            downloader.download(feedItem);
            feedItem.setDownloadDate(new Date());
            DatabaseHelper dbHelper = getDatabaseHelper();
            Dao<RssFeedItem, String> dao = dbHelper.getRssFeedItemDao();
            dao.update(feedItem);
        } catch (IOException ex) {
            Log.e(TAG, "Error while downloading feed index", ex);
        } catch (SQLException ex) {
            Log.e(TAG, "Error while updating feed item with downloaded files", ex);
        }

        return START_NOT_STICKY;
    }

    private DatabaseHelper getDatabaseHelper() {
        if (mDatabaseHelper == null) {
            mDatabaseHelper = OpenHelperManager.getHelper(getApplicationContext(), DatabaseHelper.class);
        }
        return mDatabaseHelper;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Feed auto-download service closing");
        if (mDatabaseHelper != null) {
            OpenHelperManager.releaseHelper();
            mDatabaseHelper = null;
        }
        SERVICE_IS_RUNNING = false;
    }
}
