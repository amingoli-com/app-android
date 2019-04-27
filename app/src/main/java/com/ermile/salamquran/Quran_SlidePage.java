package com.ermile.salamquran;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.duolingo.open.rtlviewpager.RtlViewPager;
import com.ermile.salamquran.network.AppContoroler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Quran_SlidePage extends AppCompatActivity {
    String dtail_line_type = null;
    String dtail_nameSurah = null;
    String page = "1";



    public Handler mHandler;
    public boolean continue_or_stop;

    ViewpagersAdapter PagerAdapter;  // for View page
    RtlViewPager viewpager; //  for dots & Button in XML
    private LinearLayout dotsLayout; // dots in XML
    public int count = 605; // Slide number


    /**
     * onCreate Method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // XML
        setContentView(R.layout.quran_slidepage);

        // Chang ID XML
        dotsLayout = findViewById(R.id.layoutDots); // OOOO
        ViewCompat.setLayoutDirection(dotsLayout,ViewCompat.LAYOUT_DIRECTION_LTR);
        viewpager = findViewById(R.id.view_pagers); // view page in XML

        // set
        PagerAdapter = new ViewpagersAdapter(this); // add Adapter (in line 55)
        viewpager.setAdapter(PagerAdapter); // set Adapter to View pager in XML



        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                Log.i("amin","onPageScrolled== "+i+"\n"+"float== "+v+"\n"+i1);

            }

            @Override
            public void onPageSelected(final int position) {
                Log.i("amin","onPageSelected*********************************************"+position);

                // changing the next button text 'NEXT' / 'GOT IT'
                String url = "https://khadije.com/api/v5/android";


            }

            @Override
            public void onPageScrollStateChanged(int i) {
                Log.i("amin","onPageScrollStateChanged==================="+i);




            }
        });


        viewpager.setCurrentItem(Integer.valueOf(getIntent().getStringExtra("open_page")));




    }

    /**
     * This Moder Adapter
     * View Pager Adapter
     */
    public class ViewpagersAdapter extends PagerAdapter {

        private Context context;
        private LayoutInflater inflater;
        ViewpagersAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount()
        {
            return count;
        }


        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
            // Static Methods
            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.modle_quran_slidepage , container , false);
            final LinearLayout background_slide = view.findViewById(R.id.background_slide);




            final Typeface font_nabi=ResourcesCompat.getFont(context, R.font.nabi);
            final Typeface font_bismellah =ResourcesCompat.getFont(context, R.font.bismillah);
            page = "https://salamquran.com/fa/api/v6/page/wbw?index="+String.valueOf(position);


            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, page, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject responses) {
                            try {
                                Boolean ok = responses.getBoolean("ok");
                                if (ok) {
                                    JSONArray result = responses.getJSONArray("result");
                                    for (int i = 0 ; i<= result.length(); i++) {
                                        JSONObject get_linesQuran = result.getJSONObject(i);

                                        LinearLayout detial_lineLayout = new LinearLayout(view.getContext());
                                        detial_lineLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT));
                                        detial_lineLayout.setOrientation(LinearLayout.HORIZONTAL);
                                        ViewCompat.setLayoutDirection(detial_lineLayout,ViewCompat.LAYOUT_DIRECTION_RTL);

                                        final TextView dtail_textview = new TextView(view.getContext());
                                        dtail_textview.setTextSize(16);
                                        dtail_textview.setTextColor(Color.parseColor("#000000"));
                                        dtail_textview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                        dtail_textview.setTypeface(font_nabi);


                                        background_slide.addView(detial_lineLayout);
                                        detial_lineLayout.addView(dtail_textview);


                                        JSONObject detail_lineQuarn = get_linesQuran.getJSONObject("detail");
                                        if (!detail_lineQuarn.isNull("line_type")){
                                            dtail_line_type = detail_lineQuarn.getString("line_type");

                                        }


                                        if (dtail_line_type.equals("start_sura")){
                                            if (!detail_lineQuarn.isNull("name")){
                                                dtail_nameSurah = detail_lineQuarn.getString("name");

                                                LinearLayout.LayoutParams layoutParams_titleSurah = new LinearLayout.LayoutParams(
                                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                                        Gravity.CENTER
                                                );
                                                dtail_textview.setLayoutParams(layoutParams_titleSurah);
                                                dtail_textview.setText("  " + dtail_nameSurah + "  ");
                                                dtail_textview.setBackgroundResource(R.drawable.surh_header);
                                                dtail_textview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                                dtail_textview.setTextSize(17);


                                            }

                                        }else if (dtail_line_type.equals("besmellah")){

                                            final TextView TitleBesmellah_textview = new TextView(view.getContext());
                                            LinearLayout.LayoutParams layoutParams_besmellah = new LinearLayout.LayoutParams(
                                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                                    Gravity.CENTER
                                            );
                                            layoutParams_besmellah.topMargin = 5;
                                            layoutParams_besmellah.bottomMargin = 5;
                                            TitleBesmellah_textview.setTextSize(35);
                                            TitleBesmellah_textview.setTextColor(Color.parseColor("#000000"));
                                            TitleBesmellah_textview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                            TitleBesmellah_textview.setLayoutParams(layoutParams_besmellah);
                                            TitleBesmellah_textview.setTypeface(font_bismellah);
                                            detial_lineLayout.addView(TitleBesmellah_textview);
                                            /*Set Text <Besmellah>*/
                                            TitleBesmellah_textview.setText("﷽");

                                        }else  {
                                            JSONArray word = get_linesQuran.getJSONArray("word");
                                            Log.i("amin","line word");

                                            for (int a = 0 ; a <= word.length() ; a++) {
                                                LinearLayout layout3 = new LinearLayout(view.getContext());
                                                layout3.setLayoutParams(new LinearLayout.LayoutParams(
                                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                                        LinearLayout.LayoutParams.WRAP_CONTENT));
                                                layout3.setOrientation(LinearLayout.HORIZONTAL );
                                                layout3.setWeightSum(1);
                                                ViewCompat.setLayoutDirection(layout3,ViewCompat.LAYOUT_DIRECTION_RTL);


                                                final TextView tv2 = new TextView(view.getContext());
                                                tv2.setTextSize(12.9f);
                                                tv2.setTextColor(Color.parseColor("#000000"));
                                                tv2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                                tv2.setTypeface(font_nabi);
                                                detial_lineLayout.addView(layout3);
                                                layout3.addView(tv2);

                                                if(!word.isNull(a)){
                                                    JSONObject c_word = word.getJSONObject(a);
                                                    String text_aya = c_word.getString("text");
                                                    String text_ayaa = c_word.getString("aya");
                                                    if (text_aya.equals("null")){
                                                        tv2.append("(" + text_ayaa + ")");
                                                    }else {
                                                        tv2.append(" "+ text_aya);
                                                    }
                                                    Log.i("amin",text_aya );

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


            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView( (LinearLayout) object);
        }
    }



}