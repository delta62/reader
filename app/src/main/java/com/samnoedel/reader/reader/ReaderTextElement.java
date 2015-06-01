package com.samnoedel.reader.reader;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class ReaderTextElement extends ReaderElement {

    private ReaderElementType mElementType;
    private String mTextContents;

    ReaderTextElement(ReaderElementType elementType, String textContents) {
        mElementType = elementType;
        mTextContents = textContents;
    }

    @Override
    public ReaderElementType getElementType() {
        return mElementType;
    }

    @Override
    public View getView(Context context) {
        TextView view = new TextView(context);
        view.setText(mTextContents);
        return view;
    }
}
