package com.samnoedel.reader.fragments;

import android.accounts.NetworkErrorException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.samnoedel.reader.rss.GetFeedTask;
import com.samnoedel.reader.R;
import com.samnoedel.reader.models.RssFeed;
import com.samnoedel.reader.service.FeedDownloadService;
import com.samnoedel.reader.validation.URLParser;

import java.net.URL;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class RssOmnibarFragment extends OrmLiteFragment {

    private static final String TAG = RssOmnibarFragment.class.getName();

    private Button mAddButton;
    private EditText mAddText;

    public static RssOmnibarFragment getInstance() {
        //noinspection deprecation
        return new RssOmnibarFragment();
    }

    @Deprecated
    public RssOmnibarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rss_omnibar, container, false);

        mAddText = (EditText)v.findViewById(R.id.omnibar_input);
        mAddText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAddButton.setEnabled(URLParser.isValidURL(mAddText.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mAddButton = (Button)v.findViewById(R.id.omnibar_submit);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlText = mAddText.getText().toString();
                URL url = URLParser.parseURL(urlText);
                getFeed(url);
            }
        });

        return v;
    }

    private void getFeed(URL url) {
        try {
            GetFeedTask feedTask = new GetFeedTask();
            RssFeed feed = feedTask.execute(url).get();
            if (feed == null) {
                throw new NetworkErrorException("Unable to fetch RSS file");
            }

            Dao<RssFeed, String> dao = getDatabaseHelper().getRssFeedDao();
            dao.create(feed);
            Log.i(TAG, "Persisted new feed");

            FeedDownloadService.triggerFeedDownload(getActivity(), feed);
        } catch (SQLException ex) {
            Log.e(TAG, "Unable to save feed to the database", ex);
        } catch (InterruptedException|ExecutionException |NetworkErrorException ex) {
            Log.e(TAG, "Error while retrieving RSS feed", ex);
            Toast.makeText(getActivity(), R.string.fetch_rss_error, Toast.LENGTH_SHORT).show();
        }
    }
}
