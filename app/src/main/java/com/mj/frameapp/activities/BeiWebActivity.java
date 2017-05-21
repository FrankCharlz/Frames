package com.mj.frameapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.mj.frameapp.R;
import com.mj.frameapp.web.WebAppInterface;

public class BeiWebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bei_web);

        WebView webView  = (WebView) findViewById(R.id.webview_registration);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");

        webView.loadUrl("file:///android_asset/framesapp/image-details.html");

    }
}
