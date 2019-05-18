package com.ermile.salamquran;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ermile.salamquran.network.AppContoroler;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    Fragment fragment = null;
    BottomNavigationViewEx main_bottomNavigation;
    FrameLayout fragment_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*Main Bottom Navigation */
        main_bottomNavigation = findViewById(R.id.main_bottomNavigation); /*sync id and input*/
        main_bottomNavigation.enableAnimation(false); /*remove anim for navigation*/
        main_bottomNavigation.enableShiftingMode(false);
        main_bottomNavigation.enableItemShiftingMode(false);
        main_bottomNavigation.setTextSize(10f); /*set size for title*/
        main_bottomNavigation.setIconSize(28,28); /*set icon size navigation*/
        main_bottomNavigation.setOnNavigationItemSelectedListener(this);
        main_bottomNavigation.setSelectedItemId(R.id.nav_quran); /*set selected item*/

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "https://salamquran.com/fa/api/v6/page/wbw?index=1#", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject responses) {
                        try {
                            Boolean ok = responses.getBoolean("ok");
                            if (ok) {


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        AppContoroler.getInstance().addToRequestQueue(req);




    }



    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    /*Bottom Navigation Item Selected*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_learn:
                CommingSoon_sankbar();
                break;
            case R.id.nav_meg:
                fragment = new Nav_Mag();
                break;
            case R.id.nav_quran:
                fragment = new Nav_Quran();
                break;
            case R.id.nav_search:
                CommingSoon_sankbar();
                break;
            case R.id.nav_setting:
                CommingSoon_sankbar();
                break;

        }

        return loadFragment(fragment);
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        Toast.makeText(this, ""+Boolean.toString(hasCapture), Toast.LENGTH_SHORT).show();

    }

    public void CommingSoon_sankbar(){
        fragment_container = findViewById(R.id.fragment_container);
        Snackbar snackbar = Snackbar.make(fragment_container, "صبور باشید ما توسعه دهنده ایم!", Snackbar.LENGTH_INDEFINITE);
        View sbView = snackbar.getView();
        LinearLayout.LayoutParams layoutParams_snakbar = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
        );
        sbView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        sbView.setLayoutParams(layoutParams_snakbar);
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        snackbar.setDuration(500);
        snackbar.show();
    }
}
