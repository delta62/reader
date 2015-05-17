package com.samnoedel.reader;

import android.support.annotation.Nullable;

import java.net.MalformedURLException;
import java.net.URL;

public class URLParser {

    public static boolean isValidURL(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException ex) {
            return false;
        }
    }

    @Nullable
    public static URL parseURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException ex) {
            return null;
        }
    }
}
