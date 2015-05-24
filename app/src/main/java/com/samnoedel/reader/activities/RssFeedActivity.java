package com.samnoedel.reader.activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.samnoedel.reader.R;
import com.samnoedel.reader.fragments.RssFeedFragment;
import com.samnoedel.reader.fragments.RssFeedItemListFragment;
import com.samnoedel.reader.storage.DatabaseHelper;

public class RssFeedActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_feed);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .add(R.id.fragmentContainer, new RssFeedFragment())
                .add(R.id.listContainer, new RssFeedItemListFragment())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rss_feed, menu);
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
