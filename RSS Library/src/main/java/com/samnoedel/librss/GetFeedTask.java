package com.samnoedel.librss;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.samnoedel.librss.models.RssFeed;
import com.samnoedel.librss.parsers.FeedParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class GetFeedTask extends AsyncTask<URL, Void, RssFeed> {

    public Exception mException;

    @Override
    protected RssFeed doInBackground(URL... params) {
        if (!validateTaskParams(params)) {
            return null;
        }
        return getFeed(params[0]);
    }

    @Nullable
    private RssFeed getFeed(URL feedUrl) {
        InputStream inputStream = null;
        try {
            inputStream = getFeedInputStream(feedUrl);
            return new FeedParser().parseFeed(inputStream);
        } catch (IOException | XmlPullParserException ex) {
            mException = ex;
            return null;
        }
        finally {
            tryCloseInputStream(inputStream);
        }
    }

    private void tryCloseInputStream(InputStream inputStream) {
        try {
            inputStream.close();
        } catch (IOException ex) {
            mException = ex;
        }
    }

    private boolean validateTaskParams(URL... params) {
        if (params.length != 1) {
            mException = new Exception("GetFeedTask must be called with exactly one URL.");
            return false;
        }
        if (params[0] == null) {
            mException = new NullPointerException("GetFeedTask called with null URL");
            return false;
        }
        return true;
    }

    @Nullable
    private BufferedInputStream getFeedInputStream(URL feedUrl) {
        try {
            URLConnection connection = feedUrl.openConnection();
            InputStream inputStream = connection.getInputStream();
            return new BufferedInputStream(inputStream);
        } catch (Exception ex) {
            return null;
        }
    }
}
