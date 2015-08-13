package com.samnoedel.reader.artparse;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class HeadParser {

    private static final String JSON_KEY_URL = "url";
    private static final String JSON_KEY_TITLE = "title";
    private static final String JSON_KEY_VERSION = "version";
    private static final int ARTPARSE_API_VERSION = 1;

    private JSONObject mEnvelope;

    public JSONObject parse(URL url) throws JSONException {
        mEnvelope = new JSONObject();
        mEnvelope.put(JSON_KEY_URL, url.toString());
        mEnvelope.put(JSON_KEY_VERSION, ARTPARSE_API_VERSION);

        parseTitle("foo");

        return mEnvelope;
    }

    private void parseTitle(String titleText) throws JSONException {
        mEnvelope.put(JSON_KEY_TITLE, titleText);
    }
}
