package com.ermile.salamquran;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Nav_Mag extends Fragment {

    WebView mag_webview;

    public Nav_Mag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View nav_mag = inflater.inflate(R.layout.nav_mag, container, false);

        mag_webview = nav_mag.findViewById(R.id.mag_webview);

        mag_webview.loadUrl("https://salamquran.com/fa/mag");


        return nav_mag;
    }

}
