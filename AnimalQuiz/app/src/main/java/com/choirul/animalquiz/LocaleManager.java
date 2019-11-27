package com.choirul.animalquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

public class LocaleManager {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ ENGLISH, INDONESIAN })
    public @interface LocaleDef {
        String[] SUPPORTED_LOCALES = { ENGLISH, INDONESIAN };
    }

    static final String ENGLISH = "en";
    static final String INDONESIAN = "in";

    /**
     * SharedPreferences Key
     */
    private static final String LANGUAGE_KEY = "language_key";

    /**
    * Get saved Locale from SharedPreferences
    */
    public static String getLanguagePref(Context context){
        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        //return sharedPreferences.getString(MainActivity.LANGUAGES, null);
        return MainActivity.LANGUAGES;
    }

    private static void setLanguagePref(Context context, String language) {
        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mPreferences.edit().putString(LANGUAGE_KEY, language).apply();
    }

    /**
     * set current pref locale
     */
    public static Context setLocale(Context context) {
        return updateResources(context, getLanguagePref(context));
    }

    /**
     * Set new Locale with context
     */
    public static Context setNewLocale(Context mContext, @LocaleDef String language) {
        setLanguagePref(mContext, language);
        return updateResources(mContext, language);
    }

    private static Context updateResources(Context context, String languagePref) {

        Locale locale = new Locale(languagePref);
        locale.setDefault(locale);
        Resources resource = context.getResources();
        Configuration configuration = new Configuration(resource.getConfiguration());
        if (Build.VERSION.SDK_INT > 17){
            configuration.setLocale(locale);
            context = context.createConfigurationContext(configuration);
        } else {
            configuration.locale = locale;
            resource.updateConfiguration(configuration, resource.getDisplayMetrics());
        }
        return context;
    }

    /**
     * get current locale
     */
    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return Build.VERSION.SDK_INT >= 24 ? config.getLocales().get(0) : config.locale;
    }

}
