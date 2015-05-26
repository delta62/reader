package com.samnoedel.reader.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.j256.ormlite.dao.Dao;
import com.samnoedel.reader.R;
import com.samnoedel.reader.fs.FileHelper;
import com.samnoedel.reader.models.RssFeedItem;

import java.io.File;
import java.sql.SQLException;

public class HtmlReaderFragment extends OrmLiteFragment {

    private static final String EXTRA_FEED_ITEM_URL = "content_url";
    private static final String TAG = HtmlReaderFragment.class.getName();

    private String mIndexUrl;
    private RssFeedItem mFeedItem;

    public static HtmlReaderFragment newInstance(String feedItemUrl) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_FEED_ITEM_URL, feedItemUrl);
        //noinspection deprecation
        HtmlReaderFragment fragment = new HtmlReaderFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Deprecated
    public HtmlReaderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFeedItem();
        getFeedIndexPath();
        markFeedAsRead();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_html_reader, container, false);
        WebView webView = (WebView)v.findViewById(R.id.webView);
        // TODO: What if URL is not null?
        if (mIndexUrl != null) {
            webView.loadUrl(mIndexUrl, null);
        }
        return v;
    }

    private void getFeedIndexPath() {
        FileHelper fileHelper = new FileHelper(getActivity());
        File itemDirectory = fileHelper.getFeedItemDirectory(mFeedItem);
        mIndexUrl = "file://" + itemDirectory.getAbsolutePath() + "/index.html";
    }

    private void getFeedItem() {
        String feedUrl = getArguments().getString(EXTRA_FEED_ITEM_URL);
        try {
            Dao<RssFeedItem, String> feedItemDao = getDatabaseHelper().getRssFeedItemDao();
            mFeedItem = feedItemDao.queryForId(feedUrl);
        } catch (SQLException ex) {
            Log.e(TAG, "Unable to fetch feed item", ex);
        }
    }

    private void markFeedAsRead() {
        if (mFeedItem.isNew()) {
            mFeedItem.setViewed();
        }

        try {
            Dao<RssFeedItem, String> feedItemDao = getDatabaseHelper().getRssFeedItemDao();
            feedItemDao.update(mFeedItem);
        } catch (SQLException ex) {
            Log.e(TAG, "Unable to mark feed item as read", ex);
        }
    }
}
