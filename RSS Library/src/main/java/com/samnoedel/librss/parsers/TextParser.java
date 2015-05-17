package com.samnoedel.librss.parsers;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class TextParser implements IXmlTagParser<String> {

    private String mTagName;
    private String mParsedText;

    public TextParser(String tagName) {
        mTagName = tagName;
    }

    public String getStartTag() {
        return mTagName;
    }

    public String getParsedElement() {
        return mParsedText;
    }

    public String parse(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.next();
        mParsedText = parser.getText();
        parser.next(); // Move to end tag
        parser.next(); // Move past end tag
        return mParsedText;
    }
}
