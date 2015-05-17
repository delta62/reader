package com.samnoedel.librss.parsers;

import com.samnoedel.librss.models.RssFeed;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class RssFeedParser extends AbstractXmlParser implements IXmlTagParser<RssFeed> {

    private static final String TAG_NAME = "rss";
    private static final String CHANNEL_TAG_NAME = "channel";

    private RssFeedItemParser mItemParser;
    private TextParser mTitleParser;
    private TextParser mDescriptionParser;
    private UrlParser mLinkParser;

    public RssFeedParser(XmlPullParser parser, RssFeedItemParser itemParser) {
        super(parser);
        mItemParser = itemParser;
    }

    public String getStartTag() {
        return TAG_NAME;
    }

    public RssFeed getParsedElement() {
        RssFeed parsedFeed = new RssFeed();
        parsedFeed.setTitle(mTitleParser.getParsedElement());
        parsedFeed.setDescription(mDescriptionParser.getParsedElement());
        parsedFeed.setUrl(mLinkParser.getParsedElement());
        return parsedFeed;
    }

    public RssFeed parse(XmlPullParser parser) throws IOException, XmlPullParserException {
        skipWrappers();
        initializeChildParsers();
        registerChildParsers();
        parseChildren();
        return getParsedElement();
    }

    private void skipWrappers() throws IOException, XmlPullParserException {
        mParser.require(XmlPullParser.START_TAG, null, TAG_NAME);
        mParser.nextTag();
        mParser.require(XmlPullParser.START_TAG, null, CHANNEL_TAG_NAME);
        mParser.nextTag();
    }

    private void initializeChildParsers() {
        mTitleParser = new TextParser("title");
        mDescriptionParser = new TextParser("description");
        mLinkParser = new UrlParser("link");
    }

    private void registerChildParsers() {
        registerChildParser(mTitleParser);
        registerChildParser(mDescriptionParser);
        registerChildParser(mLinkParser);
        registerChildParser(mItemParser);
    }
}
