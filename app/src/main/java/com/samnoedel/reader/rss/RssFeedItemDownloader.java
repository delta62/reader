package com.samnoedel.reader.rss;

import android.content.Context;
import android.util.Log;

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

    public static final String EXTRA_ITEM_URL = TAG + ".item_url";

    private Context mContext;

    public RssFeedItemDownloader(Context context) {
        mContext = context;
    }

    public File download(RssFeedItem feedItem) throws IOException {
        String filesDirPath = mContext.getFilesDir().toString();
        String feedDir = getUrlDigest(feedItem.getRssFeed().getUrl());
        String itemDir = getUrlDigest(feedItem.getLink());
        String itemStorageDirPath = filesDirPath + '/' + feedDir + '/' + itemDir;

        File itemStorageDir = new File(itemStorageDirPath);
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

    private static String getUrlDigest(URL url) {
        // TODO: It is assumed that every device in existence will support MD5.
        // https://stackoverflow.com/questions/3683302/how-to-find-out-what-algorithm-encryption-are-supported-by-my-jvm
        try {
            MessageDigest digester = MessageDigest.getInstance("MD5");
            byte[] urlBytes = url.toString().getBytes();
            byte[] digestion = digester.digest(urlBytes);
            return byteArrayToHexString(digestion);
        } catch (NoSuchAlgorithmException ignored) {
            return "content";
        }
    }

    private static final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static String byteArrayToHexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int v;

        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }

        return new String(hexChars);
    }
}
