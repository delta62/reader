package com.samnoedel.reader.fragments;

import android.app.Fragment;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.samnoedel.reader.models.DatabaseHelper;

public class OrmLiteFragment extends Fragment {

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
