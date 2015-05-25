package com.samnoedel.reader.fragments;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.samnoedel.reader.R;
import com.samnoedel.reader.activities.HtmlReaderActivity;
import com.samnoedel.reader.models.RssFeedItem;

import java.util.ArrayList;

public class RssFeedItemListFragment extends ListFragment {

    private ArrayList<RssFeedItem> mFeedItems;

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
        mFeedItems = new ArrayList<>();
        for (int i = 0; i < 25; i += 1) {
            RssFeedItem feedItem = new RssFeedItem();
            feedItem.setTitle("Feed item #" + i);
            feedItem.setDescription("Feed item description #" + i);
            mFeedItems.add(i, feedItem);
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
