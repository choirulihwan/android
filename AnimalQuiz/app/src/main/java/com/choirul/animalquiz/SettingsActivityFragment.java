package com.choirul.animalquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingsActivityFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.quiz_preferences);
    }

    public void modifyLanguages(SharedPreferences sharedPreferences){
        final String LANGUAGE_OPTIONS = sharedPreferences.getString(MainActivity.LANGUAGES, null);
        System.out.println(LANGUAGE_OPTIONS);
        //switch(LANGUAGE_OPTIONS):
        //case "en":
        //   setNewLocale(, LocaleManager.ENGLISH);

    }

}
