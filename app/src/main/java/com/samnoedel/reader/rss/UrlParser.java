package com.samnoedel.reader.rss;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URL;

public class UrlParser implements IXmlTagParser<URL> {
    private String mTagName;
    private URL mParsedUrl;

    public UrlParser(String tagName) {
        mTagName = tagName;
    }

    public String getStartTag() {
        return mTagName;
    }

    public URL getParsedElement() {
        return mParsedUrl;
    }

    public URL parse(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.next();
        mParsedUrl = new URL(parser.getText());
        parser.next(); // Move to end tag
        parser.next(); // Move past end tag
        return mParsedUrl;
    }
}
