package com.samnoedel.reader.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.samnoedel.reader.R;
import com.samnoedel.reader.fragments.HtmlReaderFragment;

public class HtmlReaderActivity extends FragmentActivity {

    public static final String EXTRA_FEED_ITEM_URL = "feed_item_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_reader);

        String feedItemUrl = getIntent().getStringExtra(EXTRA_FEED_ITEM_URL);
        Fragment readerFragment = HtmlReaderFragment.newInstance(feedItemUrl);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .add(R.id.fragment, readerFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_html_reader, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
