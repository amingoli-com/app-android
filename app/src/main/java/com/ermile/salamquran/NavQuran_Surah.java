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


/**
 * A simple {@link Fragment} subclass.
 */
public class NavQuran_Surah extends Fragment {

    public NavQuran_Surah() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View navquran_surah = inflater.inflate(R.layout.navquran_surah, container, false);


        final ArrayList<QuranList_item> quranlist= new ArrayList<>();

//        quranlist.add(new QuranList_item(QuranList_item.HEZB_TYPE,0,null,"بسم الله الرحمن الرحیم","Surah Al-Fatehe, Aya 1","1","11"));

        final RecyclerView recylerview_surah =  navquran_surah.findViewById(R.id.recylerview_surah);
        final QuranList_adapter quranList_adapter = new QuranList_adapter(quranlist,getContext());
        final LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);







        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
                "https://salamquran.com/fa/api/v6/detail/juz-sura", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject responses) {
                        try {
                            Boolean ok = responses.getBoolean("ok");
                            if (ok) {
                                JSONArray result = responses.getJSONArray("result");

                                for (int i = 0 ; i<= result.length(); i++) {
                                    JSONObject juz = result.getJSONObject(i);

                                    int number_juz = juz.getInt("index");
                                    int page_juz = juz.getInt("startpage");

                                    quranlist.add(new QuranList_item(QuranList_item.JUZ_TYPE,0,null,
                                            "Juz "+String.valueOf(number_juz),null,
                                            String.valueOf(page_juz),null,0));
                                    recylerview_surah.setLayoutManager(LayoutManager);
                                    recylerview_surah.setItemAnimator(new DefaultItemAnimator());



                                    if (!juz.isNull("sura")){
                                        String number_surah = null ;
                                        String title_surah = null ;
                                        String aya_surah = null ;
                                        String MadeIn_surah = null ;
                                        String page_surah = null ;
                                        Log.i("Quran","sura not null");
                                        JSONArray surah_array = juz.getJSONArray("sura");
                                        for (int vers = 0 ; vers <= surah_array.length();vers++){

                                            if (!surah_array.isNull(vers)){
                                                JSONObject surah = surah_array.getJSONObject(vers);
                                                number_surah = surah.getString("index");
                                                title_surah = surah.getString("tname");
                                                aya_surah = surah.getString("ayas");
                                                MadeIn_surah = surah.getString("type");
                                                page_surah = surah.getString("startpage");
                                                quranlist.add(new QuranList_item(QuranList_item.SURAH_TYPE,0,
                                                        number_surah,
                                                        title_surah,
                                                        MadeIn_surah+ " - "+aya_surah+" aya",
                                                        page_surah,null,0));
                                                recylerview_surah.setLayoutManager(LayoutManager);
                                                recylerview_surah.setItemAnimator(new DefaultItemAnimator());

                                            }


                                            Log.i("Quran","number_surah= "+number_surah);



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



        recylerview_surah.setAdapter(quranList_adapter);







        return navquran_surah;
    }

}
