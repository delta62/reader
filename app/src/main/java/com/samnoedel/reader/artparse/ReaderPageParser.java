package com.samnoedel.reader.artparse;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

public class ReaderPageParser {

    private static final String TAG = ReaderPageParser.class.getName();

    private static final String JSON_KEY_ENVELOPE = "envelope";
    private static final String JSON_KEY_CONTENT = "content";
    private static final String JSON_KEY_TYPE = "type";

    private XmlPullParser mParser;
    private JSONObject mEnvelope;
    private JSONArray mContent;

    public JSONObject parsePage(InputStream inputStream) {
        try {
            mEnvelope = new JSONObject();
            mContent = new JSONArray();

            mParser = XmlPullParserFactory.newInstance().newPullParser();
            while (mParser.getEventType() != XmlPullParser.END_DOCUMENT) {
                mParser.next();
                processElement();
            }
            return finalizeReaderElement();
        } catch (XmlPullParserException ex) {
            Log.e(TAG, "Unable to create XML parser for article", ex);
            return null;
        } catch (IOException ex) {
            Log.e(TAG, "Error while fetching XML data", ex);
            return null;
        } catch (JSONException ex) {
            Log.e(TAG, "Error while building JSON document", ex);
            return null;
        }
    }

    private JSONObject finalizeReaderElement() throws JSONException {
        JSONObject finalizedElement = new JSONObject();
        finalizedElement.put(JSON_KEY_ENVELOPE, mEnvelope);
        finalizedElement.put(JSON_KEY_CONTENT, mContent);
        return finalizedElement;
    }


}
