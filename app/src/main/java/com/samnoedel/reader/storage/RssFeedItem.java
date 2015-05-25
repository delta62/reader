package com.samnoedel.reader.storage;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.net.URL;

@DatabaseTable(tableName = "rss_feed_items")
public class RssFeedItem {

    @DatabaseField(columnName = "title", canBeNull = false)
    private String mTitle;
    @DatabaseField(columnName = "description", canBeNull = false)
    private String mDescription;
    @DatabaseField(columnName = "url", canBeNull = false)
    private String mLinkText;
    private URL mLink;

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

    public URL getLink() {
        return mLink;
    }

    public void setLink(URL link) {
        mLink = link;
        mLinkText = mLink.toString();
    }
}
