package com.samnoedel.reader.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.samnoedel.reader.R;
import com.samnoedel.reader.activities.HtmlReaderActivity;
import com.samnoedel.reader.models.RssFeed;
import com.samnoedel.reader.models.RssFeedItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RssFeedItemListFragment extends OrmLiteListFragment {

    private static final String EXTRA_FEED_URL = "feed_url";
    private static final String TAG = RssFeedItemListFragment.class.getName();

    private ArrayList<RssFeedItem> mFeedItems;

    public static RssFeedItemListFragment newInstance(String feedUrl) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_FEED_URL, feedUrl);
        //noinspection deprecation
        RssFeedItemListFragment fragment = new RssFeedItemListFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Deprecated
    public RssFeedItemListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeItems();

        RssFeedItemAdapter adapter = new RssFeedItemAdapter(mFeedItems);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        RssFeedItem feedItem = (RssFeedItem)getListAdapter().getItem(position);
        Intent i = new Intent(getActivity(), HtmlReaderActivity.class);
        i.putExtra(HtmlReaderFragment.EXTRA_HTML_CONTENT, "<h1>Hello reader</h1>");
        startActivity(i);
    }

    private void initializeItems() {
        String feedUrl = getArguments().getString(EXTRA_FEED_URL);

        try {
            Dao<RssFeed, String> feedDao = getDatabaseHelper().getRssFeedDao();
            Dao<RssFeedItem, String> feedItemDao = getDatabaseHelper().getRssFeedItemDao();

            RssFeed feed = feedDao.queryForId(feedUrl);
            List<RssFeedItem> feedItems = feedItemDao.queryForEq(RssFeedItem.COLUMN_RSS_FEED_ID, feedUrl);
            for (RssFeedItem item : feedItems) {
                item.setRssFeed(feed);
            }
            mFeedItems = new ArrayList<>(feedItems);
        } catch (SQLException ex) {
            Log.e(TAG, "Unable to create list of feed items", ex);
            mFeedItems = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rss_feed_item_list, container, false);
    }

    private class RssFeedItemAdapter extends ArrayAdapter<RssFeedItem> {
        public RssFeedItemAdapter(ArrayList<RssFeedItem> feedItems) {
            super(getActivity(), 0, feedItems);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_rss_feed_item, null);
            }

            RssFeedItem feedItem = getItem(position);
            TextView itemName = (TextView)convertView.findViewById(R.id.feedItemName);
            itemName.setText(feedItem.getTitle());
            TextView itemDescription = (TextView)convertView.findViewById(R.id.feedItemDescription);
            itemDescription.setText(feedItem.getDescription());

            return convertView;
        }
    }
}
