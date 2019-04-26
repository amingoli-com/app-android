package com.ermile.salamquran;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ermile.salamquran.adapter.QuranList_adapter;
import com.ermile.salamquran.item.QuranList_item;
import com.ermile.salamquran.network.AppContoroler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavQuran_Juz extends Fragment {
    int HIZB = 0;


    public NavQuran_Juz() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View navquran_juz = inflater.inflate(R.layout.navquran_juz, container, false);

        final ArrayList<QuranList_item> quranlist= new ArrayList<>();


        final RecyclerView recylerview_juz = (RecyclerView) navquran_juz.findViewById(R.id.recylerview_juz);
        final QuranList_adapter quranList_adapter = new QuranList_adapter(quranlist,getContext());
        final LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);





        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
                "https://salamquran.com/fa/api/v6/detail/juz-hizb", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject responses) {
                        try {
                            Boolean ok = responses.getBoolean("ok");
                            if (ok) {
                                JSONObject result = responses.getJSONObject("result");

                                String number_hezb = null;
                                String title_hezb = null;
                                String page_hezb = null;
                                String aya_hezb = null;
                                String sureh_hezb = null;
                                String hezb_hezb = null;
                                int bgHezb_hezb = 0;


                                for(Iterator<String> key_result = result.keys();key_result.hasNext();) {
                                    String get_keyResult = key_result.next();
                                    JSONObject juz = result.getJSONObject(get_keyResult);
                                    quranlist.add(new QuranList_item(QuranList_item.JUZ_TYPE,0,null,
                                            "Juz "+get_keyResult,null,
                                            "",null,0));
                                    recylerview_juz.setLayoutManager(LayoutManager);
                                    recylerview_juz.setItemAnimator(new DefaultItemAnimator());

                                    for(Iterator<String> key_juz = juz.keys();key_juz.hasNext();) {
                                        String get_keyhezb = key_juz.next();
                                        JSONObject hezb = juz.getJSONObject(get_keyhezb);
                                        HIZB++;

                                        for(Iterator<String> key_rubHezb = hezb.keys();key_rubHezb.hasNext();) {
                                            String get_keyrubHezb = key_rubHezb.next();
                                            JSONObject rubHezb = hezb.getJSONObject(get_keyrubHezb);


                                            if (!rubHezb.isNull("sura_detail")){
                                                JSONObject surehHezb = rubHezb.getJSONObject("sura_detail");
                                                if (!surehHezb.isNull("tname")){
                                                    sureh_hezb = surehHezb.getString("tname");
                                                }
                                            }

                                            if (rubHezb.getInt("index_rub") == 1){
                                                number_hezb = String.valueOf(HIZB);
                                            }else {
                                                number_hezb = "";
                                            }

                                            int getHezb = rubHezb.getInt("index_rub");

                                            switch (getHezb){
                                                case 1:
                                                    bgHezb_hezb = R.drawable.hezb_1;
                                                    break;
                                                case 2:
                                                    bgHezb_hezb = R.drawable.hezb_2;
                                                    break;
                                                case 3:
                                                    bgHezb_hezb = R.drawable.hezb_3;
                                                    break;
                                                case 4:
                                                    bgHezb_hezb = R.drawable.hezb_4;
                                                    break;
                                            }
                                            if (!rubHezb.isNull("first_word")){
                                                title_hezb = rubHezb.getString("first_word");
                                            }
                                            if (!rubHezb.isNull("page")){
                                                page_hezb = rubHezb.getString("page");
                                            }
                                            if (!rubHezb.isNull("aya")){
                                                aya_hezb = rubHezb.getString("aya");
                                            }

                                            quranlist.add(new QuranList_item(QuranList_item.HEZB_TYPE,0,
                                                    null,
                                                    title_hezb,
                                                    "Surah "+sureh_hezb+ ", Aya "+aya_hezb,
                                                    page_hezb,
                                                    number_hezb,
                                                    bgHezb_hezb));

                                            recylerview_juz.setLayoutManager(LayoutManager);
                                            recylerview_juz.setItemAnimator(new DefaultItemAnimator());

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

        recylerview_juz.setAdapter(quranList_adapter);



    return navquran_juz;
    }

}
