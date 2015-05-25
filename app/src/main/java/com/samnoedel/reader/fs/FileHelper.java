package com.samnoedel.reader.fs;

import android.content.Context;

import com.samnoedel.reader.models.RssFeed;
import com.samnoedel.reader.models.RssFeedItem;

import java.io.File;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileHelper {

    private static final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private Context mContext;

    public FileHelper(Context context) {
        mContext = context;
    }

    public File getFeedItemDirectory(RssFeedItem feedItem) {
        String itemPath = Path.join(
                mContext.getFilesDir().toString(),
                getDirectoryName(feedItem.getRssFeed()),
                getDirectoryName(feedItem));
        return new File(itemPath);
    }

    /**
     * Hash a string and base16 encode it to ensure that it doesn't include any special characters
     * @return A unique string that may safely be used as a filename
     */
    public static String safeName(URL url) {
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

    private static String getDirectoryName(RssFeed feed) {
        return safeName(feed.getUrl());
    }

    private static String getDirectoryName(RssFeedItem feedItem) {
        return safeName(feedItem.getLink());
    }

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
