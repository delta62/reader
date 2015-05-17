package com.samnoedel.librss.parsers;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;

public abstract class AbstractXmlParser {

    private int mDepth;

    protected XmlPullParser mParser;
    protected HashMap<String, IXmlTagParser> mChildParsers;

    public AbstractXmlParser(XmlPullParser parser) {
        mDepth = 0;
        mParser = parser;
        mChildParsers = new HashMap<>();
    }

    /**
     * Register a parser that will be run on all children of the current tag. The parser will be
     * automatically invoked whenever a child tag matching getStartTag() matches. The parser will
     * not be run on descendants of the current tag that are not direct children - in other words,
     * the document will not be parsed recursively.
     *
     * If you need to recursively parse the document, register a child parser that in turn registers
     * more child parsers.
     *
     * @param parser The parser to use on matching child elements within the current tag
     */
    protected void registerChildParser(IXmlTagParser parser) {
        mChildParsers.put(parser.getStartTag(), parser);
    }

    /**
     * Visit each child tag, executing a registered child parser if one matches, or skipping the tag
     * entirely if no matching parsers are set up
     */
    protected void parseChildren() throws IOException, XmlPullParserException {
        while(mParser.getEventType() != XmlPullParser.END_TAG) {
            if (mParser.getEventType() != XmlPullParser.START_TAG) {
                mParser.next();
                continue;
            }
            String tagName = mParser.getName();
            if (isParserRegistered(tagName)) {
                parseChildTag(tagName);
            } else {
                skipTag();
            }
        }
    }

    private boolean isParserRegistered(String tagName) {
        return mChildParsers.containsKey(tagName);
    }

    private void skipTag() throws IOException, XmlPullParserException {
        mParser.next();
        while (true) {
            int eventType = mParser.getEventType();
            mParser.next();
            if (eventType == XmlPullParser.START_TAG) {
                mDepth += 1;
            } else if (eventType == XmlPullParser.END_TAG) {
                mDepth -= 1;
                if (mDepth == 0) {
                    return;
                }
            }
        }
    }

    private void parseChildTag(String tagName) throws IOException, XmlPullParserException {
        IXmlTagParser parser = mChildParsers.get(tagName);
        parser.parse(mParser);
    }
}
