package com.samnoedel.reader.reader;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class JsonDeserializer {

    private static final String TAG = JsonDeserializer.class.getName();
    private static final String JSON_ENVELOPE_KEY = "envelope";
    private static final String JSON_CONTENT_KEY = "content";
    private static final String JSON_TITLE_KEY = "title";
    private static final String JSON_URL_KEY = "url";
    private static final String JSON_TYPE_KEY = "type";

    private JSONTokener mTokener;
    private ReaderPage mPage;

    public ReaderPage deserialize(String json) throws JSONException {
        mTokener = new JSONTokener(json);
        mPage = new ReaderPage();
        parseLetter();
        return mPage;
    }

    private void parseLetter() throws JSONException {
        JSONObject letter = (JSONObject)mTokener.nextValue();
        parseEnvelope(letter.getJSONObject(JSON_ENVELOPE_KEY));
        parseContents(letter.getJSONArray(JSON_CONTENT_KEY));
    }

    private void parseEnvelope(JSONObject envelope) throws JSONException {
        mPage.setTitle(envelope.getString(JSON_TITLE_KEY));
        try {
            URL pageUrl = new URL(envelope.getString(JSON_URL_KEY));
            mPage.setUrl(pageUrl);
        } catch (MalformedURLException ex) {
            Log.w(TAG, "Unable to determine URL of reader item", ex);
            mPage.setUrl(null);
        }
    }

    private void parseContents(JSONArray contents) throws JSONException {
        List<ReaderElement> pageContents = new LinkedList<>();

        for (int i = 0; i < contents.length(); i += 1) {
            ReaderElement element = parseContentItem(contents.getJSONObject(i));
            if (element.getElementType() != ReaderElementType.UNRECOGNIZED) {
                pageContents.add(element);
            }
        }

        mPage.setElements(pageContents);
    }

    private static ReaderElement parseContentItem(JSONObject item) throws JSONException {
        String itemTypeString = item.getString(JSON_TYPE_KEY);
        String itemContent = item.getString(JSON_CONTENT_KEY);

        ReaderElementType itemType = ReaderElement.getElementTypeByString(itemTypeString);
        return ReaderElement.createReaderElement(itemType, itemContent);
    }
}
