package com.samnoedel.reader.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.samnoedel.reader.models.DatabaseHelper;
import com.samnoedel.reader.models.RssFeed;
import com.samnoedel.reader.models.RssFeedItem;
import com.samnoedel.reader.rss.RssFeedItemDownloader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Handles downloading & saving of new feed content
 */
public class FeedDownloadService extends Service {
    // TODO: Batching requests
    // TODO: Logic for downloading only updated feed items
    // TODO: Logic for deleting old feed items

    private static final String EXTRA_FEED_URL = "feed_url";
    private static final String TAG = FeedDownloadService.class.getName();

    /**
     * Since this is a local service (it runs in the same process as the hosting application), no
     * IPC fanciness is required and a global can be used to determine if the service is already
     * running.
     */
    private static boolean SERVICE_IS_RUNNING;

    private DatabaseHelper mDatabaseHelper;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (SERVICE_IS_RUNNING) {
            // TODO: This is definitely not a good way to prevent the service from being started again.
            Log.w(TAG, "Skipping download service invocation - it was already running");
            return START_NOT_STICKY;
        }

        SERVICE_IS_RUNNING = true;
        Log.d(TAG, "Feed auto-download service started");

        Bundle bundle = intent.getExtras();
        String feedUrl = bundle.getString(EXTRA_FEED_URL);
        List<RssFeedItem> feedItems = getRssFeedItems(feedUrl);
        RssFeedItemDownloader feedDownloader = new RssFeedItemDownloader(getApplicationContext());
        for (RssFeedItem feedItem : feedItems) {
            try {
                feedDownloader.download(feedItem);
                feedItem.setDownloadDate(new Date());
                DatabaseHelper dbHelper = getDatabaseHelper();
                Dao<RssFeedItem, String> dao = dbHelper.getRssFeedItemDao();
                dao.update(feedItem);
            } catch (IOException ex) {
                Log.e(TAG, "Error while downloading feed index", ex);
            } catch (SQLException ex) {
                Log.e(TAG, "Error while updating feed item with downloaded files", ex);
            }
        }

        return START_NOT_STICKY;
    }

    /**
     * Begin downloading an RSS feed from the feed download service.
     * @param context The context from which to launch the service. Usually the activity making this
     *                call.
     * @param feed The feed to download pages for
     */
    public static void triggerFeedDownload(Context context, RssFeed feed) {
        // TODO: If the service is already running, just add more stuff to download
        Intent intent = new Intent(context, FeedDownloadService.class);
        intent.putExtra(EXTRA_FEED_URL, feed.getUrl().toString());
        context.startService(intent);
    }

    private List<RssFeedItem> getRssFeedItems(String feedUrl) {
        try {
            Dao<RssFeedItem, String> itemDao = getDatabaseHelper().getRssFeedItemDao();
            return itemDao.queryForEq(RssFeedItem.COLUMN_RSS_FEED_ID, feedUrl);
        } catch (SQLException ex) {
            Log.e(TAG, "Error fetching feed items", ex);
            return new LinkedList<>();
        }
    }

    private DatabaseHelper getDatabaseHelper() {
        if (mDatabaseHelper == null) {
            mDatabaseHelper = OpenHelperManager.getHelper(getApplicationContext(), DatabaseHelper.class);
        }
        return mDatabaseHelper;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Feed download service closing");
        if (mDatabaseHelper != null) {
            OpenHelperManager.releaseHelper();
            mDatabaseHelper = null;
        }
        SERVICE_IS_RUNNING = false;
    }
}
