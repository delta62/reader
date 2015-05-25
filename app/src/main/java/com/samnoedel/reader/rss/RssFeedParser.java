package com.samnoedel.reader.rss;

import com.samnoedel.reader.storage.RssFeed;
import com.samnoedel.reader.storage.RssFeedItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class RssFeedParser extends AbstractXmlParser implements IXmlTagParser<RssFeed> {

    private static final String TAG_NAME = "rss";
    private static final String CHANNEL_TAG_NAME = "channel";

    private RssFeedItemParser mItemParser;
    private TextParser mTitleParser;
    private TextParser mDescriptionParser;
    private UrlParser mLinkParser;
    private RssFeed mParsedFeed;
    private List<RssFeedItem> mFeedItems;

    public RssFeedParser(XmlPullParser parser, RssFeedItemParser itemParser) {
        super(parser);
        mItemParser = itemParser;
    }

    public String getStartTag() {
        return TAG_NAME;
    }

    public RssFeed getParsedElement() {
        if (mParsedFeed == null) {
            mParsedFeed = new RssFeed();
            mParsedFeed.setTitle(mTitleParser.getParsedElement());
            mParsedFeed.setDescription(mDescriptionParser.getParsedElement());
            mParsedFeed.setUrl(mLinkParser.getParsedElement());
            mParsedFeed.setFeedItems(mFeedItems);
        }
        return mParsedFeed;
    }

    public RssFeed parse(XmlPullParser parser) throws IOException, XmlPullParserException {
        mFeedItems = new LinkedList<>();
        skipWrappers();
        initializeChildParsers();
        registerChildParsers();
        parseChildren();
        return getParsedElement();
    }

    @Override
    protected void onChildElementParsed(String tagName, Object parsedElement) {
        if (!tagName.equals("item")) {
            return;
        }
        mFeedItems.add((RssFeedItem)parsedElement);
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
