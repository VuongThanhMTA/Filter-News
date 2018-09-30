package com.vuongthanh.t3h.newsnews;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dmax.dialog.SpotsDialog;

public class WebViewActivity extends Activity {
    private WebView webView;
    public AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    private  SpotsDialog spotsDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_webview);
        webView = findViewById(R.id.webview);
        getIntenData();

    }

    private void getIntenData() {

        Intent intent = getIntent();
        if (intent != null) {
            String link = intent.getStringExtra("Link");

            webView.setWebViewClient(new webViewClient());
            webView.loadUrl(link);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
        }
    }

    public class webViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressDialog = new ProgressDialog(WebViewActivity.this);
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Please wait ...");
            progressDialog.show();
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progressDialog.dismiss();
            super.onPageFinished(view, url);
        }
    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else
            super.onBackPressed();
    }
}
