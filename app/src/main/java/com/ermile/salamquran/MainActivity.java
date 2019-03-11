package com.ermile.salamquran;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final SwipeRefreshLayout swipe = findViewById(R.id.swipref);
        final WebView webView = findViewById(R.id.webview);

        final String url = "https://salamquran.com/fa?dev=1";

        final Map<String, String> sernd_headers = new HashMap<String, String>();
        sernd_headers.put("x-app-request", "android");


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        swipe.setRefreshing(true);

        webView.loadUrl(url,sernd_headers);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl(webView.getUrl(),sernd_headers);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Toast.makeText(MainActivity.this, "اینترنت قطع شد", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                swipe.setRefreshing(false);
            }
        });




    }

    @Override
    public void onPause() {
        super.onPause();
        final WebView webView = findViewById(R.id.webview);
        webView.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        final WebView webView = findViewById(R.id.webview);
        final SwipeRefreshLayout swipe = findViewById(R.id.swipref);

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
