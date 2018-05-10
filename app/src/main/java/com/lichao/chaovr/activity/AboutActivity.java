package com.lichao.chaovr.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import com.lichao.chaovr.R;

public class AboutActivity extends AppCompatActivity {

    WebViewWrapper webViewWrapper;

    private String url = "file:///android_asset/about.html";

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();
        initToolBar();
        webViewWrapper.loadUrl(url);
    }

    private void initView() {
        webViewWrapper = (WebViewWrapper) findViewById(R.id.webViewWrapper);
    }

    private void initToolBar() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        webViewWrapper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webViewWrapper.onPause();
    }

    @Override
    protected void onDestroy() {
        webViewWrapper.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webViewWrapper.goBack()) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
