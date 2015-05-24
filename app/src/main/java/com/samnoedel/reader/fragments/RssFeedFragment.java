package com.samnoedel.reader.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samnoedel.reader.R;
import com.samnoedel.reader.storage.RssFeed;

public class RssFeedFragment extends Fragment {

    public static final String EXTRA_FEED_URL = "com.samnoedel.reader.feed_url";

    private TextView mLinkTextView;
    private TextView mDescriptionTextView;
    private TextView mNameTextView;
    private RssFeed mFeed;

    public RssFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String feedUrl = getActivity().getIntent().getStringExtra(EXTRA_FEED_URL);
        mFeed = new RssFeed();
        mFeed.setName("Test Feed");
        mFeed.setDescription("Test Feed Description");
        mFeed.setUrl(feedUrl);

        getActivity().setTitle("Test Feed");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rss_feed, container, false);

        mNameTextView = (TextView)v.findViewById(R.id.feedName);
        mNameTextView.setText(mFeed.getName());
        mDescriptionTextView = (TextView)v.findViewById(R.id.feedDescription);
        mDescriptionTextView.setText(mFeed.getDescription());
        mLinkTextView = (TextView)v.findViewById(R.id.feedLink);
        mLinkTextView.setText(mFeed.getUrl());

        return v;
    }
}
