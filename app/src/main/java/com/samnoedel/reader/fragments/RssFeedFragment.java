package com.samnoedel.reader.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samnoedel.reader.R;
import com.samnoedel.reader.models.RssFeed;

import java.net.MalformedURLException;
import java.net.URL;

public class RssFeedFragment extends Fragment {

    public static final String EXTRA_FEED_URL = "com.samnoedel.reader.feed_url";

    private RssFeed mFeed;

    public RssFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String feedUrl = getActivity().getIntent().getStringExtra(EXTRA_FEED_URL);
        mFeed = new RssFeed();
        mFeed.setTitle("Test Feed");
        mFeed.setDescription("Test Feed Description");
        try {
            mFeed.setUrl(new URL(feedUrl));
        } catch (MalformedURLException ignored) { }

        getActivity().setTitle("Test Feed");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rss_feed, container, false);

        TextView nameTextView = (TextView) v.findViewById(R.id.feedName);
        nameTextView.setText(mFeed.getTitle());
        TextView descriptionTextView = (TextView) v.findViewById(R.id.feedDescription);
        descriptionTextView.setText(mFeed.getDescription());
        TextView linkTextView = (TextView) v.findViewById(R.id.feedLink);
        linkTextView.setText(mFeed.getUrl().toString());

        return v;
    }
}
