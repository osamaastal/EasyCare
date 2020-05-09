package com.osamayastal.easycare.Model.Classes;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageHelper {

    public static void changeLocale(Resources res, String locale) {

        Configuration config;
        config = new Configuration(res.getConfiguration());

        switch (locale) {
            case "ar":
                config.locale = new Locale("ar");
                config.setLayoutDirection(new Locale("ar"));
                break;
            case "en":
                config.locale = Locale.ENGLISH;
                config.setLayoutDirection(new Locale("en"));
                break;
        }
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    public static Locale getLocale(Resources res) {
        Configuration config;
        config = new Configuration(res.getConfiguration());
        return config.locale;
    }
}
