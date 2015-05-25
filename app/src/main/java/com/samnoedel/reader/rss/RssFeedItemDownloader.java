package com.samnoedel.reader.rss;

import android.content.Context;
import android.util.Log;

import com.samnoedel.reader.fs.FileHelper;
import com.samnoedel.reader.models.RssFeedItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

/**
 * Download a feed's HTML document and save it to the local filesystem
 */
public class RssFeedItemDownloader {

    private static final String TAG = RssFeedItemDownloader.class.getName();

    private Context mContext;

    public RssFeedItemDownloader(Context context) {
        mContext = context;
    }

    public File download(RssFeedItem feedItem) throws IOException {
        FileHelper fileHelper = new FileHelper(mContext);
        File itemStorageDir = fileHelper.getFeedItemDirectory(feedItem);

        if (!itemStorageDir.mkdirs() && !itemStorageDir.isDirectory()) {
            throw new FileNotFoundException("Unable to create feed storage directory");
        }

        File index = new File(itemStorageDir, "index.html");
        FileDownloadInfo dlInfo = new FileDownloadInfo(feedItem.getLink(), index);
        GetFileTask asyncTask = new GetFileTask();

        try {
            asyncTask.execute(dlInfo).get();
        } catch (InterruptedException|ExecutionException ex) {
            Log.e(TAG, "Unable to complete download task", ex);
        }

        return index;
    }
}
