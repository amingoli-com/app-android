package com.ermile.salamquran;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.sax.RootElement;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.duolingo.open.rtlviewpager.RtlViewPager;
import com.ermile.salamquran.network.AppContoroler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Quran_SlidePage extends AppCompatActivity {
    String lineType_detail = null;
    String nameSurah_json = null;
    String page = "1";



    public Handler mHandler;
    public boolean continue_or_stop;

    ViewpagersAdapter PagerAdapter;  // for View page
    RtlViewPager viewpager; //  for dots & Button in XML
    private TextView number_pageQuran , number_juzQuran,title_surahQuran;
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
        number_pageQuran = findViewById(R.id.number_pageQuran);
        number_juzQuran = findViewById(R.id.number_juzQuran);
        title_surahQuran = findViewById(R.id.title_surahQuran);
        viewpager = findViewById(R.id.view_pagers); // view page in XML

        // set
        PagerAdapter = new ViewpagersAdapter(this); // add Adapter (in line 55)
        viewpager.setAdapter(PagerAdapter); // set Adapter to View pager in XML



        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(final int position) {
                number_pageQuran.setText(String.valueOf(position));

                page = "https://salamquran.com/fa/api/v6/page/wbw?index="+String.valueOf(position);


                JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, page, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject responses) {
                                try {
                                    Boolean ok = responses.getBoolean("ok");
                                    String textTop_surah , textTop_juz ;

                                    if (ok) {
                                        JSONArray result = responses.getJSONArray("result");
                                        for (int i = 0 ; i<= result.length(); i++) {
                                            JSONObject get_linesQuran = result.getJSONObject(i);


                                            JSONObject detail_jsonObject = get_linesQuran.getJSONObject("detail");
                                            if (!detail_jsonObject.isNull("line_type")){
                                                lineType_detail = detail_jsonObject.getString("line_type");

                                            }


                                            /* Title Header Surah */
                                            if (lineType_detail.equals("start_sura")){
                                                if (!detail_jsonObject.isNull("tname")){
                                                    textTop_surah = detail_jsonObject.getString("tname");
                                                    title_surahQuran.setText(textTop_surah);

                                                }



                                            }
                                            /* Besmellah */
                                            else if (lineType_detail.equals("besmellah")){

                                            }else  {
                                                JSONArray wordQuran_jsonArray = get_linesQuran.getJSONArray("word");

                                                /*Get Word's of Quran */
                                                for (int a = 0 ; a <= wordQuran_jsonArray.length() ; a++) {

                                                    if(!wordQuran_jsonArray.isNull(a)){
                                                        JSONObject object_wordQuran_JsonArray = wordQuran_jsonArray.getJSONObject(a);
                                                        textTop_juz = object_wordQuran_JsonArray.getString("juz");
                                                        number_juzQuran.setText("Juz"+textTop_juz);

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



            }

            @Override
            public void onPageScrollStateChanged(int i) {




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





            final Typeface font_nabi=ResourcesCompat.getFont(context, R.font.font_nabi);
            final Typeface font_bismellah =ResourcesCompat.getFont(context, R.font.bismillah);
            page = "https://salamquran.com/fa/api/v6/page/wbw?index="+String.valueOf(position);

            final ArrayList<String> TAG_WBW = new ArrayList<String>();


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
                                        final View parrentView = background_slide.getChildAt(getCount());

                                        LinearLayout lineLayout_surah_besmellah = new LinearLayout(view.getContext());
                                        background_slide.addView(lineLayout_surah_besmellah);
                                        lineLayout_surah_besmellah.setLayoutParams(new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT));
                                        lineLayout_surah_besmellah.setOrientation(LinearLayout.HORIZONTAL);
                                        ViewCompat.setLayoutDirection(lineLayout_surah_besmellah,ViewCompat.LAYOUT_DIRECTION_RTL);



                                        JSONObject detail_jsonObject = get_linesQuran.getJSONObject("detail");
                                        if (!detail_jsonObject.isNull("line_type")){
                                            lineType_detail = detail_jsonObject.getString("line_type");

                                        }


                                        /* Title Header Surah */
                                        switch (lineType_detail) {
                                            case "start_sura":
                                                if (!detail_jsonObject.isNull("name")) {
                                                    nameSurah_json = detail_jsonObject.getString("name");

                                                    final AppCompatTextView TitleSurah_textView = new AppCompatTextView(view.getContext());
                                                    lineLayout_surah_besmellah.addView(TitleSurah_textView);
                                                    LinearLayout.LayoutParams layoutParams_titleSurah = new LinearLayout.LayoutParams(
                                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                                            ViewGroup.LayoutParams.WRAP_CONTENT,
                                                            Gravity.CENTER
                                                    );
                                                    TitleSurah_textView.setTextSize(16);
                                                    TitleSurah_textView.setTextColor(Color.parseColor("#000000"));
                                                    TitleSurah_textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                                    TitleSurah_textView.setTypeface(font_nabi);
                                                    TitleSurah_textView.setLayoutParams(layoutParams_titleSurah);
                                                    TitleSurah_textView.setText("  " + nameSurah_json + "  ");
                                                    TitleSurah_textView.setBackgroundResource(R.drawable.surh_header);
                                                    TitleSurah_textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                                    TitleSurah_textView.setTextSize(17);
                                                }


                                                break;
                                            /* Besmellah */
                                            case "besmellah":
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
                                                lineLayout_surah_besmellah.addView(TitleBesmellah_textview);
                                                /*Set Text <Besmellah>*/
                                                TitleBesmellah_textview.setText("﷽");
                                                break;

                                            /*get Word Quran*/
                                            default:
                                                JSONArray wordQuran_jsonArray = get_linesQuran.getJSONArray("word");

                                                final LinearLayout linearLayout_wordQuran = new LinearLayout(view.getContext());
                                                linearLayout_wordQuran.setLayoutParams(new LinearLayout.LayoutParams(
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                                        Gravity.CENTER_HORIZONTAL));
                                                linearLayout_wordQuran.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                                linearLayout_wordQuran.setOrientation(LinearLayout.HORIZONTAL);
                                                ViewCompat.setLayoutDirection(linearLayout_wordQuran,
                                                        ViewCompat.LAYOUT_DIRECTION_RTL);
                                                lineLayout_surah_besmellah.addView(linearLayout_wordQuran);

                                                /*Get Word's of Quran */
                                                for (int a = 0; a <= wordQuran_jsonArray.length(); a++) {
                                                    final TextView TextQuran_textview = new TextView(view.getContext());
                                                    TextQuran_textview.setTextSize(12.1f);
                                                    TextQuran_textview.setTextColor(Color.parseColor("#000000"));
                                                    TextQuran_textview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                                    TextQuran_textview.setTypeface(font_nabi);
                                                    linearLayout_wordQuran.addView(TextQuran_textview);


                                                    if (!wordQuran_jsonArray.isNull(a)) {
                                                        JSONObject object_wordQuran_JsonArray = wordQuran_jsonArray.getJSONObject(a);


                                                        String textQuran_json =
                                                                " " +
                                                                        object_wordQuran_JsonArray.getString("text")
                                                                + " ";
                                                        String numberAya_json =
                                                                " (" +
                                                                        object_wordQuran_JsonArray.getString("aya")
                                                                + ") ";

                                                        if (textQuran_json.equals(" null ")) {
                                                            TextQuran_textview.setText(numberAya_json);
                                                        } else {
                                                            TextQuran_textview.setText(textQuran_json);

                                                        }
                                                        TextQuran_textview.setTag(object_wordQuran_JsonArray.getString("aya"));
                                                        /*TextQuran_textview.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                String getTag_textQuran = TextQuran_textview.getTag().toString();

                                                                LinearLayout ins = (LinearLayout) background_slide.getChildAt(1);
                                                                ins.getChildAt(2).setBackgroundColor(Color.RED);



                                                                for (int row_quran = 0; row_quran < background_slide.getChildCount(); row_quran++)
                                                                {
                                                                    for (int word_quran = 0; word_quran < linearLayout_wordQuran.getChildCount(); word_quran++)
                                                                    {
                                                                        if (linearLayout_wordQuran.getChildAt(word_quran).getTag() != null &&
                                                                                linearLayout_wordQuran.getChildAt(word_quran).getTag().equals(getTag_textQuran))
                                                                        {
                                                                            linearLayout_wordQuran.getChildAt(word_quran).setBackgroundColor(Color.BLUE);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        });*/

                                                    }

                                                }


                                                break;
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