package com.example.baskara.customlauncher;

import android.net.http.SslError;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Articles");
        setSupportActionBar(toolbar);

        String url = null;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            url = extras.getString("asin");
            url = "https://articles.integ.amazon.com/kindle-dbs/arp/" + url;
        }

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setAppCacheMaxSize( 10 * 1024 * 1024 );
        webView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath() );
        webView.getSettings().setAllowFileAccess( true );
        webView.getSettings().setAppCacheEnabled( true );
        webView.getSettings().setJavaScriptEnabled( true );
        webView.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT );
        webView.setWebViewClient(new WebViewClient() {@Override public void onReceivedSslError
                (WebView v, SslErrorHandler handler, SslError er){ handler.proceed(); }});
        webView.loadUrl(url);

    }

}