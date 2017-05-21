package com.mj.frameapp.web;

import android.content.Context;
import android.text.InputType;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.webkit.WebView;

/**
 * Created by Frank on 21-May-17.
 */

public class DopeWebView extends WebView {

    public DopeWebView(Context context) {
        super(context);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        BaseInputConnection ic = new BaseInputConnection(this, true);
        outAttrs.inputType = InputType.TYPE_CLASS_NUMBER; // Tells the keyboard to show the number pad
        return ic;
    }
}
