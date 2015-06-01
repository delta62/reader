package com.samnoedel.reader.models;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

@DatabaseTable(tableName = "rss_feeds")
public class RssFeed {

    private static final String TAG = RssFeed.class.getName();

    @DatabaseField(id = true, columnName = "url", canBeNull = false)
    private String mUrlText;

    @DatabaseField(columnName = "title", canBeNull = false)
    private String mTitle;

    @DatabaseField(columnName = "description", canBeNull = false)
    private String mDescription;

    @ForeignCollectionField(eager = false)
    private Collection<RssFeedItem> mFeedItems;

    public RssFeed() { }

    public URL getUrl() {
        if (mUrlText == null) {
            return null;
        }

        try {
            return new URL(mUrlText);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error while attempting to implicitly create feed url");
            return null;
        }
    }

    public void setUrl(URL url) {
        mUrlText = url.toString();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String name) {
        mTitle = name;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Collection<RssFeedItem> getFeedItems() {
        return mFeedItems;
    }

    public void setFeedItems(List<RssFeedItem> feedItems) {
        mFeedItems = feedItems;
    }

    public int getUnreadCount() {
        int unreadCount = 0;
        for (RssFeedItem feedItem : mFeedItems) {
            if (feedItem.isNew()) { unreadCount += 1; }
        }
        return unreadCount;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}
