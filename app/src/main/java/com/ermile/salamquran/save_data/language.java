package com.ermile.salamquran.save_data;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class language extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences language = PreferenceManager.getDefaultSharedPreferences(this);

        String app_language = language.getString("app_language", "en");
        Boolean language_change =  language.getBoolean("language_change", false);

    }
}