package com.lichao.chaovr.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lichao.chaovr.R;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class JSVRActivity extends AppCompatActivity {

    private WebView tencent_webview;
    private String url = "file:///android_asset/admin.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_vr);
        initView();
    }

    private void initView() {
        tencent_webview = findViewById(R.id.webJS);
        tencent_webview.loadUrl(url);
        WebSettings webSettings = tencent_webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        tencent_webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
    }

}
