package com.samnoedel.reader.reader;

import java.net.URL;
import java.util.Collection;

public class ReaderPage {

    private String mTitle;
    private URL mUrl;
    private Collection<ReaderElement> mElements;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public URL getUrl() {
        return mUrl;
    }

    public void setUrl(URL url) {
        mUrl = url;
    }

    public Collection<ReaderElement> getElements() {
        return mElements;
    }

    void setElements(Collection<ReaderElement> elements) {
        mElements = elements;
    }
}
