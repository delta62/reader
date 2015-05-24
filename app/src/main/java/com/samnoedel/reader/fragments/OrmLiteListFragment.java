package com.samnoedel.reader.fragments;

import android.app.ListFragment;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.samnoedel.reader.storage.DatabaseHelper;

public class OrmLiteListFragment extends ListFragment {
    private DatabaseHelper mDatabaseHelper;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDatabaseHelper != null) {
            OpenHelperManager.releaseHelper();
            mDatabaseHelper = null;
        }
    }

    protected DatabaseHelper getDatabaseHelper() {
        if (mDatabaseHelper == null) {
            mDatabaseHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return mDatabaseHelper;
    }
}
