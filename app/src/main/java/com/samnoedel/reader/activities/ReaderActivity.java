package com.samnoedel.reader.activities;

import android.app.Fragment;

import com.samnoedel.reader.fragments.JsonReaderFragment;

public class ReaderActivity extends FullscreenActivity {

    @Override
    protected Fragment getFragment() {
        return JsonReaderFragment.newInstance("foo");
    }

}
