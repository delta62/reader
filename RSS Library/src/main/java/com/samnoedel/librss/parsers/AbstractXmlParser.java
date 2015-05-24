package com.samnoedel.librss.parsers;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;

public abstract class AbstractXmlParser {

    protected XmlPullParser mParser;
    protected HashMap<String, IXmlTagParser> mChildParsers;

    public AbstractXmlParser(XmlPullParser parser) {
        mParser = parser;
        mChildParsers = new HashMap<>();
    }

    /**
     * Called on implementors whenever an item has been parsed from the document
     * @param tagName The XML tag that was just parsed into an object
     * @param parsedElement The element that was parsed
     */
    protected void onChildElementParsed(String tagName, Object parsedElement) {
        // Default is a no-op
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

        // Skip over element's closing tag
        mParser.next();
    }

    private boolean isParserRegistered(String tagName) {
        return mChildParsers.containsKey(tagName);
    }

    private void skipTag() throws IOException, XmlPullParserException {
        mParser.require(XmlPullParser.START_TAG, null, null);
        mParser.next();
        int depth = 1;

        while (true) {
            int eventType = mParser.getEventType();
            mParser.next();
            if (eventType == XmlPullParser.START_TAG) {
                depth += 1;
            } else if (eventType == XmlPullParser.END_TAG) {
                depth -= 1;
                if (depth == 0) {
                    return;
                }
            }
        }
    }

    private void parseChildTag(String tagName) throws IOException, XmlPullParserException {
        IXmlTagParser parser = mChildParsers.get(tagName);
        Object parsedItem = parser.parse(mParser);
        onChildElementParsed(parser.getStartTag(), parsedItem);
    }
}
