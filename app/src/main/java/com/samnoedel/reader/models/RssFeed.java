package com.samnoedel.reader.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

@DatabaseTable(tableName = "rss_feeds")
public class RssFeed implements Serializable {

    @DatabaseField(id = true, columnName = "url", canBeNull = false)
    private String mUrlText;
    private URL mUrl;

    @DatabaseField(columnName = "title", canBeNull = false)
    private String mTitle;

    @DatabaseField(columnName = "description", canBeNull = false)
    private String mDescription;

    private List<RssFeedItem> mFeedItems;

    public RssFeed() { }

    public URL getUrl() {
        return mUrl;
    }

    public void setUrl(URL url) {
        mUrl = url;
        mUrlText = url.toString();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String name) {
        mTitle = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public List<RssFeedItem> getFeedItems() {
        return mFeedItems;
    }

    public void setFeedItems(List<RssFeedItem> feedItems) {
        mFeedItems = feedItems;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}
