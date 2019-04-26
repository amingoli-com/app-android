package com.ermile.salamquran;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

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
                    .addToBackStack(null)
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
                fragment = new Nav_Learn();
                break;
            case R.id.nav_meg:
                fragment = new Nav_Mag();
                break;
            case R.id.nav_quran:
                fragment = new Nav_Quran();
                break;
            case R.id.nav_search:
                fragment = new Nav_Serach();
                break;
            case R.id.nav_setting:
                fragment = new Nav_Setting();
                break;

        }

        return loadFragment(fragment);
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
