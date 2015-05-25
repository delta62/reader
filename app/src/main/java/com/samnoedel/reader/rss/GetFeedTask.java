package com.samnoedel.reader.rss;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.samnoedel.reader.models.RssFeed;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class GetFeedTask extends AsyncTask<URL, Void, RssFeed> {

    private static final String TAG = GetFeedTask.class.getName();

    @Override
    protected RssFeed doInBackground(URL... params) {
        Log.i(TAG, "Fetching RSS feed");
        if (!validateTaskParams(params)) {
            return null;
        }
        return getFeed(params[0]);
    }

    @Nullable
    private static RssFeed getFeed(URL feedUrl) {
        InputStream inputStream = null;
        try {
            inputStream = getFeedInputStream(feedUrl);
            return new FeedParser().parseFeed(inputStream);
        } catch (IOException | XmlPullParserException ex) {
            Log.e(TAG, "Error while fetching RSS feed", ex);
            return null;
        }
        finally {
            tryCloseInputStream(inputStream);
        }
    }

    private static void tryCloseInputStream(InputStream inputStream) {
        try {
            inputStream.close();
        } catch (IOException ignored) { }
    }

    private static boolean validateTaskParams(URL... params) {
        if (params.length != 1) {
            Log.e(TAG, "GetFeedTask must be called with exactly one URL.");
            return false;
        }
        if (params[0] == null) {
            Log.e(TAG, "GetFeedTask called with null URL");
            return false;
        }
        return true;
    }

    @Nullable
    private static BufferedInputStream getFeedInputStream(URL feedUrl) throws IOException {
        URLConnection connection = feedUrl.openConnection();
        InputStream inputStream = connection.getInputStream();
        return new BufferedInputStream(inputStream);
    }
}
