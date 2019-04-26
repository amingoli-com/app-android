package com.ermile.salamquran.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.engine.Resource;
import com.ermile.salamquran.R;
import com.ermile.salamquran.item.QuranList_item;

import java.util.ArrayList;

public class QuranList_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<QuranList_item> QuranList;
    Context mContext;
    int total_types;


    public static class SurahTypeViewHolder extends RecyclerView.ViewHolder {


        TextView number_surah,title_surah,decs_surah,page_surah;

        public SurahTypeViewHolder(View itemView) {
            super(itemView);

            this.number_surah = (TextView) itemView.findViewById(R.id.number_surah);
            this.title_surah = (TextView) itemView.findViewById(R.id.title_surah);
            this.decs_surah = (TextView) itemView.findViewById(R.id.decs_surah);
            this.page_surah = (TextView) itemView.findViewById(R.id.page_surah);

        }

    }

    public static class JuzTypeViewHolder extends RecyclerView.ViewHolder {


        TextView title_juz , page_juz;

        public JuzTypeViewHolder(View itemView) {
            super(itemView);

            this.title_juz = (TextView) itemView.findViewById(R.id.title_juz);
            this.page_juz = (TextView) itemView.findViewById(R.id.page_juz);

        }

    }

    public static class HezbTypeViewHolder extends RecyclerView.ViewHolder {


        TextView number_hezb, title_hezb, decs_hezb, page_hezb;

        public HezbTypeViewHolder(View itemView) {
            super(itemView);

            this.number_hezb = (TextView) itemView.findViewById(R.id.number_hezb);
            this.title_hezb = (TextView) itemView.findViewById(R.id.title_hezb);
            this.decs_hezb = (TextView) itemView.findViewById(R.id.decs_hezb);
            this.page_hezb = (TextView) itemView.findViewById(R.id.page_hezb);

        }

    }

    public QuranList_adapter(ArrayList<QuranList_item> data, Context context) {
        this.QuranList = data;
        this.mContext = context;
        total_types = QuranList.size();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case QuranList_item.SURAH_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.surah_modleitem, parent, false);
                return new QuranList_adapter.SurahTypeViewHolder(view);
            case QuranList_item.JUZ_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.juz_modleitem, parent, false);
                return new QuranList_adapter.JuzTypeViewHolder(view);
            case QuranList_item.HEZB_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hezb_modleitem, parent, false);
                return new QuranList_adapter.HezbTypeViewHolder(view);
        }
        return null;


    }


    @Override
    public int getItemViewType(int position) {

        switch (QuranList.get(position).type) {
            case 0:
                return QuranList_item.SURAH_TYPE;
            case 1:
                return QuranList_item.JUZ_TYPE;
            case 2:
                return QuranList_item.HEZB_TYPE;
            default:
                return -1;
        }


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        QuranList_item object = QuranList.get(listPosition);
        if (object != null) {
            switch (object.type) {
                case QuranList_item.SURAH_TYPE:
                    ((QuranList_adapter.SurahTypeViewHolder) holder).number_surah.setText(object.numbers);
                    ((QuranList_adapter.SurahTypeViewHolder) holder).title_surah.setText(object.titles);
                    ((QuranList_adapter.SurahTypeViewHolder) holder).decs_surah.setText(object.desc);
                    ((QuranList_adapter.SurahTypeViewHolder) holder).page_surah.setText(object.page);

                    break;
                case QuranList_item.JUZ_TYPE:
                    ((QuranList_adapter.JuzTypeViewHolder) holder).title_juz.setText(object.titles);
                    ((QuranList_adapter.JuzTypeViewHolder) holder).page_juz.setText(object.page);

                    break;
                case QuranList_item.HEZB_TYPE:

                    ((QuranList_adapter.HezbTypeViewHolder) holder).number_hezb.setText(object.hezb);
                    ((QuranList_adapter.HezbTypeViewHolder) holder).title_hezb.setText(object.titles);
                    ((QuranList_adapter.HezbTypeViewHolder) holder).decs_hezb.setText(object.desc);
                    ((QuranList_adapter.HezbTypeViewHolder) holder).page_hezb.setText(object.page);
                    ((HezbTypeViewHolder) holder).number_hezb.setBackgroundResource(object.bg_hezb);




                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return QuranList.size();
    }


}

