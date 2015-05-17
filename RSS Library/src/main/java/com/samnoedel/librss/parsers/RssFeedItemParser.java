package com.samnoedel.librss.parsers;

import com.samnoedel.librss.models.RssFeedItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class RssFeedItemParser implements IXmlTagParser<RssFeedItem> {

    private static final String TAG_NAME = "item";

    public String getStartTag() {
        return TAG_NAME;
    }

    public RssFeedItem getParsedElement() {
        return new RssFeedItem();
    }

    public RssFeedItem parse(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.next();
        return new RssFeedItem();
    }
}
