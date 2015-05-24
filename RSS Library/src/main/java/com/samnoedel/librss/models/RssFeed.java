package com.samnoedel.librss.models;

import java.net.URL;

public class RssFeed {

    private URL mUrl;
    private String mTitle;
    private String mDescription;
    private RssFeedItem[] mFeedItems;

    public URL getUrl() {
        return mUrl;
    }

    public void setUrl(URL url) {
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
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

    public RssFeedItem[] getFeedItems() {
        return mFeedItems;
    }

    public void setFeedItems(RssFeedItem[] feedItems) {
        mFeedItems = feedItems;
    }
}
