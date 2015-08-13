package com.samnoedel.reader.artparse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Objects;

public class BodyParser {

    private static final String JSON_KEY_TYPE = "type";
    private static final String JSON_KEY_CONTENT = "content";
    private final XmlPullParser mParser;
    private JSONArray mContent;

    public BodyParser(XmlPullParser parser) {
        mParser = parser;
    }

    public JSONArray parse() throws XmlPullParserException, IOException, JSONException {
        mContent = new JSONArray();
        do {
            mParser.next();
            processElement();
        } while (!isFinished());

        return mContent;
    }

    private void processElement() throws XmlPullParserException, IOException, JSONException {
        if (mParser.getEventType() != XmlPullParser.START_TAG) {
            return;
        }

        String type;
        String content;
        switch (mParser.getName()) {
            case "p":
                type = "paragraph";
                content = getText();
                break;
            case "h1":
                type = "heading1";
                content = getText();
                break;
            case "h2":
                type = "heading2";
                content = getText();
                break;
            case "img":
                type = "image";
                content = getAttribute("src");
                break;
            default:
                return;
        }
        JSONObject append = createContentElement(type, content);
        mContent.put(append);
    }

    private String getText() throws XmlPullParserException, IOException {
        mParser.nextText();
        return mParser.getText();
    }

    private String getAttribute(String attrName) {
        return mParser.getAttributeValue(null, attrName);
    }

    private static JSONObject createContentElement(String type, String content) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put(JSON_KEY_TYPE, type);
        obj.put(JSON_KEY_CONTENT, content);
        return obj;
    }

    private boolean isFinished() throws XmlPullParserException {
        return mParser.getEventType() == XmlPullParser.END_DOCUMENT
            || mParser.getEventType() == XmlPullParser.END_TAG && Objects.equals(mParser.getName(), "body");
    }
}
