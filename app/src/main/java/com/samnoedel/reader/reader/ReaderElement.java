package com.samnoedel.reader.reader;

import android.content.Context;
import android.util.Log;
import android.view.View;

public abstract class ReaderElement {

    private static final String TAG = ReaderElement.class.getName();

    public static ReaderElement createReaderElement(ReaderElementType type, String content) {
        switch (type) {
            case IMAGE:
                return new ReaderImageElement();
            case HEADING_1:
            case HEADING_2:
            case PARAGRAPH:
                return new ReaderTextElement(type, content);
            default:
                return new ReaderUnknownElement(content);
        }
    }

    public abstract ReaderElementType getElementType();

    public abstract View getView(Context context);

    static ReaderElementType getElementTypeByString(String elementName) {
        switch (elementName.toLowerCase()) {
            case "heading1":
                return ReaderElementType.HEADING_1;
            case "heading2":
                return ReaderElementType.HEADING_2;
            case "paragraph":
                return ReaderElementType.PARAGRAPH;
            case "image":
                return ReaderElementType.IMAGE;
            default:
                Log.w(TAG, "unrecognized content type received: " + elementName);
                return ReaderElementType.UNRECOGNIZED;
        }
    }
}
