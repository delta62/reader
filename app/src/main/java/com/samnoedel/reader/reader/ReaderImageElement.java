package com.samnoedel.reader.reader;

import android.content.Context;
import android.view.View;

public class ReaderImageElement extends ReaderElement {

    @Override
    public ReaderElementType getElementType() {
        return ReaderElementType.IMAGE;
    }

    @Override
    public View getView(Context context) {
        return null;
    }
}
