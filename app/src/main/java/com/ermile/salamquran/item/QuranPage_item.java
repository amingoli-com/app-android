package com.ermile.salamquran.item;

public class QuranPage_item {
    public static final int TITLE_SUREH=0;
    public static final int BESMELLAH=1;
    public static final int WORD=2;


    public int type;
    public int data;
    public String wordOfQuran_ITEM,
            word_besmellah,
            titleName_surah;


    public QuranPage_item(int type, int data, String wordOfQuran_ITEM, String word_besmellah, String titleName_surah) {
        this.type = type;
        this.data = data;
        this.wordOfQuran_ITEM = wordOfQuran_ITEM;
        this.word_besmellah = word_besmellah;
        this.titleName_surah = titleName_surah;
    }
}
