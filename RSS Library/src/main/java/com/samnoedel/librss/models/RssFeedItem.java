package com.samnoedel.librss.models;

import java.net.URL;

public class RssFeedItem {

    private String mTitle;
    private String mDescription;
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
    }
}
