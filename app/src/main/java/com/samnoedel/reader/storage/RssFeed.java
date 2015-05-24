package com.samnoedel.reader.storage;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "rss_feeds")
public class RssFeed {

    @DatabaseField(id = true, columnName = "url", canBeNull = false)
    private String mUrl;

    @DatabaseField(columnName = "title", canBeNull = false)
    private String mName;

    @DatabaseField(columnName = "description", canBeNull = false)
    private String mDescription;

    public RssFeed() { }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public String toString() {
        return mName;
    }

    public static RssFeed fromLibraryFeed(com.samnoedel.librss.models.RssFeed feed) {
        RssFeed mappedFeed = new RssFeed();
        mappedFeed.setName(feed.getTitle());
        mappedFeed.setDescription(feed.getDescription());
        mappedFeed.setUrl(feed.getUrl().toString());

        return mappedFeed;
    }
}
