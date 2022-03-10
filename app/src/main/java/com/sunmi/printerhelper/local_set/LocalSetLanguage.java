package com.sunmi.printerhelper.local_set;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LocalSetLanguage {

    Context ctx;

    public static void LocalSet(String lang,Context ctx) {

        Locale myLocale = new Locale(lang);
        Resources res = ctx.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
}
