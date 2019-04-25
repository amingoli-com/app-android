package com.ermile.salamquran;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ermile.salamquran.network.AppContoroler;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Locale;

public class splash extends AppCompatActivity {

    String versionName = null ;
    int versionCode = 0 ;
    String language_device = Locale.getDefault().getLanguage();

    /*  Language SharedPreferences  */
    SharedPreferences language = getSharedPreferences("language", MODE_PRIVATE);
    SharedPreferences.Editor language_editor = language.edit();
    String get_language = language.getString("app_language", "en");
    boolean get_changelanguage = language.getBoolean("language_change", false);






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = pInfo.versionName;
            versionCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (language_device.equals("fa")){
            language_editor.putString("app_language","fa");
            language_editor.putBoolean("language_change",true);
            language_editor.apply();
            CHECK_INTERNET();

        }else {
            CHECK_INTERNET();
        }



    }



    public void CHECK_INTERNET(){

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "http://mimsg.ir/api/v6/language.json", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject responses) {
                        try {
                            Boolean ok = responses.getBoolean("ok");
                            if (ok) {

                                CHECK_VERSION();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (get_changelanguage){
                    NEXT_ACTIVITY();

                }else {
                    CHOSE_LANGUAGE();
                }

            }
        });
        AppContoroler.getInstance().addToRequestQueue(req);


    }


    private void CHECK_VERSION(){
        boolean deprecated = false;
        if (deprecated){
            Toast.makeText(this, "deprecated", Toast.LENGTH_SHORT).show();
        }else {
            ADD_USER();
        }
    }


    private void ADD_USER(){
        boolean user_added = false;
        if (user_added){
            Toast.makeText(this, "user is login", Toast.LENGTH_SHORT).show();
            NEXT_ACTIVITY();
        }else {
            Toast.makeText(this, "add user to server", Toast.LENGTH_SHORT).show();
            CHOSE_LANGUAGE();
        }
    }


    private void CHOSE_LANGUAGE() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "http://mimsg.ir/api/v6/language.json", null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject responses) {
                try {
                    Boolean ok = responses.getBoolean("ok");
                    if (ok) {
                        Toast.makeText(splash.this, "online chose language", Toast.LENGTH_SHORT).show();

                        /*by on click on btn lanuage > NEXT_ACTIVITY();*/

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(splash.this, "offline chose language", Toast.LENGTH_SHORT).show();
                /*by on click on btn lanuage > NEXT_ACTIVITY();*/
            }
        });
        AppContoroler.getInstance().addToRequestQueue(req);


    }



    private void NEXT_ACTIVITY(){
        startActivity(new Intent(getApplicationContext() , MainActivity.class));
    }

}
