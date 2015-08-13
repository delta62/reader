package com.samnoedel.reader.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.samnoedel.reader.R;
import com.samnoedel.reader.models.RssFeedItem;
import com.samnoedel.reader.reader.JsonDeserializer;
import com.samnoedel.reader.reader.ReaderElement;
import com.samnoedel.reader.reader.ReaderPage;

import org.json.JSONException;

import java.sql.SQLException;

public class JsonReaderFragment extends OrmLiteFragment {

    private static final String EXTRA_RSS_FEED_ITEM_URL = "feed_item_url";
    private static final String TAG = JsonReaderFragment.class.getName();

    private String mFeedItemUrl;
    private ReaderPage mPage;

    public static JsonReaderFragment newInstance(String feedItemUrl) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_RSS_FEED_ITEM_URL, feedItemUrl);
        //noinspection deprecation
        JsonReaderFragment fragment = new JsonReaderFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Deprecated
    public JsonReaderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mFeedItemUrl = getArguments().getString(EXTRA_RSS_FEED_ITEM_URL);
        mPage = getReaderPage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_json_reader, container, false);

        TextView titleText = (TextView)view.findViewById(R.id.title);
        titleText.setText(mPage.getTitle());

        for (ReaderElement element : mPage.getElements()) {
            View elementView = element.getView(getActivity());
            container.addView(elementView);
        }

        return view;
    }

    private ReaderPage getReaderPage() {
        try {
            Dao<RssFeedItem, String> feedItemDao = getDatabaseHelper().getRssFeedItemDao();
            JsonDeserializer deserializer = new JsonDeserializer();
            String json = feedItemDao.queryForId(mFeedItemUrl).getItemContent();
            return deserializer.deserialize(json);
        } catch (SQLException ex) {
            Log.e(TAG, "Unable to find RSS feed item", ex);
        } catch (JSONException ex) {
            Log.e(TAG, "Unable to parse stored JSON string", ex);
        }
        return null;
    }
}
