package com.samnoedel.reader.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.samnoedel.reader.R;

public class HtmlReaderFragment extends Fragment {

    public static final String EXTRA_HTML_CONTENT = "com.samnoedel.reader.content";

    private static final String HTML_MIME_TYPE = "text/html";
    private static final String HTML_ENCODING = "utf-8";

    public HtmlReaderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_html_reader, container, false);

        WebView webView = (WebView)v.findViewById(R.id.webView);
        webView.loadData("<h1>Article Reader View</h1><p>Article Content</p>", HTML_MIME_TYPE, HTML_ENCODING);

        return v;
    }
}
