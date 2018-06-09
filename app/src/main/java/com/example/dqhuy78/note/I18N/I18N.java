package com.example.dqhuy78.note.I18N;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class I18N {

    // Get localization
    public static void getLocalization(Context context) {
        SharedPreferences sharePref = context.getSharedPreferences("I18N", context.MODE_PRIVATE);
        String lang = sharePref.getString("LANG", "DEFAULT");
        Resources resources = context.getResources();
        Configuration config= resources.getConfiguration();
        if (!lang.equals("DEFAULT")) {
            Locale locale = new Locale(lang);
            config.setLocale(locale);
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        } else {
            Locale locale = new Locale("en");
            config.setLocale(locale);
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        }
    }

    // Change localization
    public static void changeLocalization(Context context, String lang) {
        SharedPreferences preferences = context.getSharedPreferences("I18N", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("LANG", lang);
        editor.commit();
        getLocalization(context);
    }
}
