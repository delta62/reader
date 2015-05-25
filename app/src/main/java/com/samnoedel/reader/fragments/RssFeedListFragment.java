package com.samnoedel.reader.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.samnoedel.reader.R;
import com.samnoedel.reader.activities.RssFeedActivity;
import com.samnoedel.reader.storage.RssFeed;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RssFeedListFragment extends OrmLiteListFragment {

    private static final String TAG = RssFeedListFragment.class.getName();

    private ArrayList<RssFeed> mFeeds;

    public RssFeedListFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.feeds_title);

        getSavedRssFeeds();
        RssFeedAdapter adapter = new RssFeedAdapter(mFeeds);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        RssFeed feed = ((RssFeedAdapter)getListAdapter()).getItem(position);
        Intent i = new Intent(getActivity(), RssFeedActivity.class);
        i.putExtra(RssFeedFragment.EXTRA_FEED_URL, feed.getUrl());
        startActivity(i);
    }

    private class RssFeedAdapter extends ArrayAdapter<RssFeed> {
        public RssFeedAdapter(ArrayList<RssFeed> feeds) {
            super(getActivity(), 0, feeds);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_rss_feed, null);
            }

            RssFeed feed = getItem(position);
            TextView titleTextView = (TextView)convertView.findViewById(R.id.rss_feed_list_item_title);
            titleTextView.setText(feed.getTitle());
            TextView descriptionTextView = (TextView)convertView.findViewById(R.id.rss_feed_list_item_description);
            descriptionTextView.setText(feed.getDescription());

            return convertView;
        }
    }

    private void getSavedRssFeeds() {
        try {
            Dao<RssFeed, String> dao = getDatabaseHelper().getRssFeedDao();
            List<RssFeed> savedFeeds = dao.queryForAll();
            mFeeds = new ArrayList<>(savedFeeds);
        } catch (SQLException ex) {
            mFeeds = new ArrayList<>();
            Log.e(TAG, "Error getting saved RSS feeds", ex);
        }
    }
}
