package com.ermile.salamquran;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Nav_Mag extends Fragment {

    WebView mag_webview;
    SwipeRefreshLayout swipeRefresh_navmag;

    String URL_MAG = "https://salamquran.com/fa/mag";

    public Nav_Mag() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View nav_mag = inflater.inflate(R.layout.nav_mag, container, false);

        /*send Header to server and get app style*/
        final Map<String, String> send_headers = new HashMap<String, String>();
        send_headers.put("x-app-request", "android");

        mag_webview = nav_mag.findViewById(R.id.webView_navmag);
        swipeRefresh_navmag = nav_mag.findViewById(R.id.swipeRefresh_navmag);

        /*swipe Refresh*/
        swipeRefresh_navmag.setRefreshing(true);
        swipeRefresh_navmag.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mag_webview.loadUrl(mag_webview.getUrl(),send_headers);
            }
        });

        /*Web View*/
        WebSettings webViewSettings = mag_webview.getSettings();
        webViewSettings.setJavaScriptEnabled(true); /* set JavaScript for webview*/

        mag_webview.loadUrl(URL_MAG,send_headers);
        mag_webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){
                Toast.makeText(getContext(), "Error page Load !", Toast.LENGTH_SHORT).show();
            }
            // in refresh send header
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                HashMap<String, String> headerMap = new HashMap<>();
                headerMap.put("x-app-request", "android");
                view.loadUrl(url, headerMap);


                return false;

            }
            /*swipe Refresh*/
            @Override
            public void onPageFinished(WebView view, String url) {
                swipeRefresh_navmag.setRefreshing(false);
            }});



        return nav_mag;
    }

}
