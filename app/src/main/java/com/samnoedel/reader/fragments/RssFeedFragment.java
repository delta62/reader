package com.samnoedel.reader.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.samnoedel.reader.R;
import com.samnoedel.reader.models.RssFeed;

import java.sql.SQLException;

public class RssFeedFragment extends OrmLiteFragment {

    private static final String ARG_FEED_URL = "feed_url";
    private static final String TAG = RssFeedFragment.class.getName();

    private RssFeed mFeed;

    public static RssFeedFragment newInstance(String feedUrl) {
        RssFeedFragment fragment = new RssFeedFragment();

        Bundle args = new Bundle();
        args.putString(ARG_FEED_URL, feedUrl);
        fragment.setArguments(args);

        return fragment;
    }

    public RssFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String feedUrl = getArguments().getString(ARG_FEED_URL);
        try {
            Dao<RssFeed, String> feedDao = getDatabaseHelper().getRssFeedDao();
            mFeed = feedDao.queryForId(feedUrl);
            getActivity().setTitle(mFeed.getTitle());
        } catch (SQLException ex) {
            Log.e(TAG, "Unable to fetch feed with URL of " + feedUrl);
            mFeed = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rss_feed, container, false);

        // TODO: Does this make sense? What should happen if the feed can't be found?
        if (mFeed == null) {
            return v;
        }

        TextView nameTextView = (TextView) v.findViewById(R.id.feedName);
        nameTextView.setText(mFeed.getTitle());
        TextView linkTextView = (TextView) v.findViewById(R.id.feedLink);
        linkTextView.setText(mFeed.getUrl().getAuthority());

        return v;
    }
}
