package com.samnoedel.reader.reader;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class ReaderUnknownElement extends ReaderElement {

    private String mContent;

    public ReaderUnknownElement(String content) {
        mContent = content;
    }

    @Override
    public ReaderElementType getElementType() {
        return ReaderElementType.UNRECOGNIZED;
    }

    @Override
    public View getView(Context context) {
        TextView view = new TextView(context);
        view.setText(mContent);
        return view;
    }
}
