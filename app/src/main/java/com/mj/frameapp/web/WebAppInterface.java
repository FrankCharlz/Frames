package com.mj.frameapp.web;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.mj.frameapp.CurrentItemInfo;
import com.mj.frameapp.EditorActivity;
import com.mj.frameapp.activities.ChooseImageActivity;
import com.tumblr.remember.Remember;

/**
 * Created by Frank on 21-May-17.
 */

public class WebAppInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    public WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void alert(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void register(String name, String phone) {
        Remember.putString("insta-name", name);
        Remember.putString("user-phone", phone);
        Remember.putBoolean("done_registration", true);

        Toast.makeText(
                mContext,
                "Congrats, you are now all set.",
                Toast.LENGTH_SHORT
        ).show();

        mContext.startActivity(new Intent(mContext, ChooseImageActivity.class));
        ((Activity) mContext).finish();
    }

    @JavascriptInterface
    public void detailsSubmit(String jina, String bei) {
        CurrentItemInfo.name = jina;
        CurrentItemInfo.bei = bei;

        mContext.startActivity(new Intent(mContext, EditorActivity.class));
        ((Activity) mContext).finish();
    }




}