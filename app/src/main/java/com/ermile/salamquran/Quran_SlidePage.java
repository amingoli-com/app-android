package com.ermile.salamquran;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ermile.salamquran.adapter.QuranPage_adapter;
import com.ermile.salamquran.item.QuranList_item;
import com.ermile.salamquran.item.QuranPage_item;
import com.ermile.salamquran.network.AppContoroler;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Quran_SlidePage extends AppCompatActivity {

    String d_line_type = null;
    String d_name = null;
    String page = "1";


    RecyclerViewPager recyclerViewPager_QuranPage;
    QuranPage_adapter quranPage_adapter;

    String word_quran = "";


    /**
     * onCreate Method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // XML
        setContentView(R.layout.quran_slidepage);


        final ArrayList<QuranPage_item> quranPage_items = new ArrayList<>();

        recyclerViewPager_QuranPage = findViewById(R.id.recyclerViewPager_QuranPage);
        quranPage_adapter = new QuranPage_adapter(quranPage_items,getApplicationContext());
        final LinearLayoutManager LayoutManager = new LinearLayoutManager
                (getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);





        String page = "https://salamquran.com/fa/api/v6/page/wbw?index=1";
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, page, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject responses) {
                        try {
                            Boolean ok = responses.getBoolean("ok");
                            if (ok) {
                                JSONArray result = responses.getJSONArray("result");
                                for (int i = 0 ; i<= result.length(); i++) {
                                    JSONObject lines = result.getJSONObject(i);




                                    JSONObject detail = lines.getJSONObject("detail");
                                    if (!detail.isNull("line_type")){
                                        d_line_type = detail.getString("line_type");

                                    }


                                    if (d_line_type.equals("start_sura")){
                                        if (!detail.isNull("name")){
                                            d_name = detail.getString("name");

                                            quranPage_items.add(new QuranPage_item(QuranPage_item.TITLE_SUREH,0,
                                                    word_quran,
                                                    "",
                                                    d_name));
                                            recyclerViewPager_QuranPage.setLayoutManager(LayoutManager);
                                            recyclerViewPager_QuranPage.setItemAnimator(new DefaultItemAnimator());


                                        }

                                    }else if (d_line_type.equals("besmellah")){
                                        quranPage_items.add(new QuranPage_item(QuranPage_item.BESMELLAH,0,
                                                word_quran,
                                                "﷽",
                                                ""));
                                        recyclerViewPager_QuranPage.setLayoutManager(LayoutManager);
                                        recyclerViewPager_QuranPage.setItemAnimator(new DefaultItemAnimator());

                                    }else {
                                        JSONArray word = lines.getJSONArray("word");
                                        Log.i("amin","line word");

                                        for (int a = 0 ; a <= word.length() ; a++) {

                                            if(!word.isNull(a)){
                                                JSONObject c_word = word.getJSONObject(a);
                                                String text_aya = c_word.getString("text");
                                                String text_ayaa = c_word.getString("aya");

                                                if (text_aya.equals("null")){
                                                    word_quran = "( " + text_ayaa + " )";
                                                }else {
                                                    word_quran = " "+ text_aya;
                                                }

                                                quranPage_items.add(new QuranPage_item(QuranPage_item.WORD,0,
                                                        word_quran,
                                                        "",
                                                        ""));

                                                Log.i("amin",text_aya );
                                                recyclerViewPager_QuranPage.setLayoutManager(LayoutManager);
                                                recyclerViewPager_QuranPage.setItemAnimator(new DefaultItemAnimator());

                                            }

                                        }




                                    }







                                }
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



        recyclerViewPager_QuranPage.setAdapter(quranPage_adapter);



    }



}
