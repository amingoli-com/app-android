package com.ermile.salamquran.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ermile.salamquran.Quran_SlidePage;
import com.ermile.salamquran.R;
import com.ermile.salamquran.item.QuranPage_item;

import java.util.ArrayList;

public class QuranPage_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<QuranPage_item> QuranPage;
    Context mContext;
    int total_types;

    public static class TitleSurahTypeViewHolder extends RecyclerView.ViewHolder {


        TextView TitleName_sureh;

        public TitleSurahTypeViewHolder(View itemView) {
            super(itemView);

            this.TitleName_sureh = itemView.findViewById(R.id.TitleName_sureh);

        }

    }

    public static class BesmellahTypeViewHolder extends RecyclerView.ViewHolder {


        TextView Besmillah;

        public BesmellahTypeViewHolder(View itemView) {
            super(itemView);

            this.Besmillah = itemView.findViewById(R.id.Besmillah);

        }

    }

    public static class WordsTypeViewHolder extends RecyclerView.ViewHolder {


        TextView word_of_quran;

        public WordsTypeViewHolder(View itemView) {
            super(itemView);

            this.word_of_quran = itemView.findViewById(R.id.word_of_quran);



        }

    }

    public QuranPage_adapter(ArrayList<QuranPage_item> data, Context context ) {
        this.QuranPage = data;
        this.mContext = context;
        total_types = QuranPage.size();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case QuranPage_item.TITLE_SUREH:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.titlesureh_pageitem, parent, false);
                return new QuranPage_adapter.TitleSurahTypeViewHolder(view);

            case QuranPage_item.BESMELLAH:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.besmellah_pageitem, parent, false);
                return new QuranPage_adapter.BesmellahTypeViewHolder(view);

            case QuranPage_item.WORD:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.word_pageitem, parent, false);
                return new QuranPage_adapter.WordsTypeViewHolder(view);
        }
        return null;


    }


    @Override
    public int getItemViewType(int position) {

        switch (QuranPage.get(position).type) {
            case 0:
                return QuranPage_item.TITLE_SUREH;
            case 1:
                return QuranPage_item.BESMELLAH;
            case 2:
                return QuranPage_item.WORD;
            default:
                return -1;
        }


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {
        final Intent open_page = new Intent(mContext, Quran_SlidePage.class);/*Intent for Go to Page Quran*/

        final QuranPage_item object = QuranPage.get(listPosition);
        if (object != null) {
            switch (object.type) {
                case QuranPage_item.TITLE_SUREH:
                    ((QuranPage_adapter.TitleSurahTypeViewHolder) holder).TitleName_sureh.setText(object.titleName_surah);


                    break;

                case QuranPage_item.BESMELLAH:
                    ((QuranPage_adapter.BesmellahTypeViewHolder) holder).Besmillah.setText(object.word_besmellah);


                    break;

                case QuranPage_item.WORD:
                    ((WordsTypeViewHolder) holder).word_of_quran.setText(object.wordOfQuran_ITEM);


                    break;

            }
        }

    }

    @Override
    public int getItemCount() {
        return QuranPage.size();
    }


}
