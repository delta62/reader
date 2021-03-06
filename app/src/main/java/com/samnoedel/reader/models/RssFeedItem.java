package com.samnoedel.reader.models;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

@DatabaseTable(tableName = "rss_feed_items")
public class RssFeedItem {

    public static final String COLUMN_RSS_FEED_ID = "rss_feed_url";

    private static final String TAG = RssFeedItem.class.getName();

    @DatabaseField(columnName = COLUMN_RSS_FEED_ID, foreign = true, foreignAutoRefresh = true)
    private RssFeed mRssFeed;

    @DatabaseField(columnName = "title", canBeNull = false)
    private String mTitle;

    @DatabaseField(columnName = "description", canBeNull = false)
    private String mDescription;

    @DatabaseField(id = true, columnName = "url", canBeNull = false)
    private String mLinkText;

    @DatabaseField(columnName = "download_date", canBeNull = true)
    private Date mDownloadDate;

    @DatabaseField(columnName = "viewed_on", canBeNull = true)
    private Date mViewedOn;

    @DatabaseField(columnName = "item_content", canBeNull = true)
    private String mItemContent;

    public String getTitle() {
        return mTitle;
    }

    public RssFeed getRssFeed() {
        return mRssFeed;
    }

    public void setRssFeed(RssFeed rssFeed) {
        mRssFeed = rssFeed;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public URL getLink() {
        if (mLinkText == null) {
            return null;
        }
        try {
            return new URL(mLinkText);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error while attempting to implicitly create feed item url");
            return null;
        }
    }

    public void setDownloadDate(Date downloadDate) {
        mDownloadDate = downloadDate;
    }

    public void setLink(URL link) {
        mLinkText = link.toString();
    }

    public boolean isNew() {
        return mViewedOn == null;
    }

    public void setViewed() {
        mViewedOn = new Date();
    }

    public String getItemContent() {
        return mItemContent;
    }
}
