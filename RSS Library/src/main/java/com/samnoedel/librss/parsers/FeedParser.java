package com.samnoedel.librss.parsers;

import android.util.Xml;

import com.samnoedel.librss.models.RssFeed;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

public class FeedParser {

    private static final String ENCODING_UTF8 = "utf-8";

    private XmlPullParser mParser;

    public FeedParser() {
        mParser = Xml.newPullParser();
    }

    public RssFeed parseFeed(InputStream stream) throws XmlPullParserException, IOException {
        try {
            initializeParser(stream);
            RssFeedItemParser itemParser = new RssFeedItemParser(mParser);
            return new RssFeedParser(mParser, itemParser).parse(mParser);
        } finally {
            stream.close();
        }
    }

    private void initializeParser(InputStream stream) throws XmlPullParserException, IOException {
        mParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        mParser.setInput(stream, ENCODING_UTF8);
        mParser.nextTag();
    }
}
