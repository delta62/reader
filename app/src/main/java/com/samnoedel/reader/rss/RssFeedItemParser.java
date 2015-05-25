package com.samnoedel.reader.rss;

import com.samnoedel.reader.models.RssFeedItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class RssFeedItemParser extends AbstractXmlParser implements IXmlTagParser<RssFeedItem> {

    private static final String TAG_NAME = "item";

    private TextParser mTitleParser;
    private TextParser mDescriptionParser;
    private UrlParser mLinkParser;

    public RssFeedItemParser(XmlPullParser parser) {
        super(parser);
    }

    public String getStartTag() {
        return TAG_NAME;
    }

    public RssFeedItem getParsedElement() {
        RssFeedItem parsedItem = new RssFeedItem();
        parsedItem.setTitle(mTitleParser.getParsedElement());
        parsedItem.setDescription(mDescriptionParser.getParsedElement());
        parsedItem.setLink(mLinkParser.getParsedElement());
        return parsedItem;
    }

    public RssFeedItem parse(XmlPullParser parser) throws IOException, XmlPullParserException {
        skipWrapper();
        initializeParsers();
        registerChildParsers();
        parseChildren();
        return getParsedElement();
    }

    private void skipWrapper() throws IOException, XmlPullParserException {
        mParser.require(XmlPullParser.START_TAG, null, TAG_NAME);
        mParser.next();
    }

    private void initializeParsers() {
        mTitleParser = new TextParser("title");
        mLinkParser = new UrlParser("link");
        mDescriptionParser = new TextParser("description");
    }

    private void registerChildParsers() {
        registerChildParser(mTitleParser);
        registerChildParser(mLinkParser);
        registerChildParser(mDescriptionParser);
    }
}
