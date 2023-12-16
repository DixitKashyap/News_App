package com.example.news_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class NewsActivity extends AppCompatActivity {
    WebView webView;
    ProgressBar progressBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        Intent i = getIntent();
        String url = i.getStringExtra("url");
        progressBar = findViewById(R.id.progress_bar);
        webView = findViewById(R.id.web_views);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebChromeClient(new chromeClientFullScreen(this));

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public class chromeClientFullScreen extends WebChromeClient {
        private Context context;
        private View mCustomView;
        private CustomViewCallback mCustomViewCallback;
        private int mOriginalSystemUiVisibility;
        int orientation = getResources().getConfiguration().orientation;

        chromeClientFullScreen(Context context) {
            this.context = context;
        }

        public Bitmap getDefaultVideoPoster() {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(context.getResources(), 2130837573);
        }
        @Override
        public void onHideCustomView() {
            ((FrameLayout) ((Activity) context).getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            ((Activity) context).setRequestedOrientation(orientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }
        @Override
        public void onShowCustomView(View paramView, CustomViewCallback paramCustomViewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = ((Activity) context).getWindow().getDecorView().getSystemUiVisibility();
            ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout) ((Activity) context).getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(3846);
        }
    }

}