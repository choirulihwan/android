package com.choirul.animalquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener {

    public static final String GUESSES = "settings_numberofguesses";
    public static final String ANIMALS_TYPE = "settings_animalstype";
    public static final String QUIZ_BACKGROUND_COLOR = "settings_backgroundcolor";
    public static final String QUIZ_FONT = "settings_fonts";
    public static final String QUESTIONS = "settings_numberofquestions";
    public static final String LANGUAGES = "settings_languages";

    private boolean isSettingChanged = false;

    static Typeface chunkfive;
    static Typeface fontlerybrown;
    static Typeface wonderbardemo;

    MainFragment myAnimalQuizFragment;
    SettingsActivityFragment myAnimalQuizSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        chunkfive = Typeface.createFromAsset(getAssets(), "fonts/Chunkfive.otf");
        fontlerybrown = Typeface.createFromAsset(getAssets(), "fonts/FontleroyBrown.ttf");
        wonderbardemo = Typeface.createFromAsset(getAssets(), "fonts/Wonderbar Demo.otf");

        PreferenceManager.setDefaultValues(this, R.xml.quiz_preferences, false);
        //mencatat perubahan setting
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(settingChangeListener);

        myAnimalQuizFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.animalQuizFragment);
        //myAnimalQuizSetting = (SettingsActivityFragment) getSupportFragmentManager().findFragmentById(R.id.animalSettingFragment);

        myAnimalQuizFragment.modifyAnimalGuessRows(PreferenceManager.getDefaultSharedPreferences(MainActivity.this));
        myAnimalQuizFragment.modifyTypeOfAnimalsInQuiz(PreferenceManager.getDefaultSharedPreferences(MainActivity.this));
        myAnimalQuizFragment.modifyQuizFont(PreferenceManager.getDefaultSharedPreferences(MainActivity.this));
        myAnimalQuizFragment.modifyBackgroundColor(PreferenceManager.getDefaultSharedPreferences(MainActivity.this));
        myAnimalQuizFragment.modifyQuestions(PreferenceManager.getDefaultSharedPreferences(MainActivity.this));
        myAnimalQuizFragment.modifyLanguages(PreferenceManager.getDefaultSharedPreferences(MainActivity.this));
        myAnimalQuizFragment.resetAnimalQuiz();
        isSettingChanged = false;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent preferencesIntent = new Intent(this, SettingsActivity.class);
        startActivity(preferencesIntent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private SharedPreferences.OnSharedPreferenceChangeListener settingChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            isSettingChanged = true;
            if (s.equals(GUESSES)){
                myAnimalQuizFragment.modifyAnimalGuessRows(sharedPreferences);
                myAnimalQuizFragment.resetAnimalQuiz();
            } else if(s.equals(ANIMALS_TYPE)) {
                Set<String> animalTypes = sharedPreferences.getStringSet(ANIMALS_TYPE, null);
                if (animalTypes != null && animalTypes.size() > 0){
                    myAnimalQuizFragment.modifyTypeOfAnimalsInQuiz(sharedPreferences);
                    myAnimalQuizFragment.resetAnimalQuiz();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    animalTypes.add(getString(R.string.default_animal_type));
                    editor.putStringSet(ANIMALS_TYPE, animalTypes);
                    editor.apply();

                    Toast.makeText(MainActivity.this, R.string.toast_message, Toast.LENGTH_SHORT).show();
                }
            } else if(s.equals(QUIZ_FONT)){
                myAnimalQuizFragment.modifyQuizFont(sharedPreferences);
                myAnimalQuizFragment.resetAnimalQuiz();
            } else if(s.equals(QUIZ_BACKGROUND_COLOR)){
                myAnimalQuizFragment.modifyBackgroundColor(sharedPreferences);
                myAnimalQuizFragment.resetAnimalQuiz();
            } else if (s.equals(QUESTIONS)) {
                myAnimalQuizFragment.modifyQuestions(sharedPreferences);
                myAnimalQuizFragment.resetAnimalQuiz();
            } else if (s.equals(LANGUAGES)) {
                myAnimalQuizFragment.modifyLanguages(sharedPreferences);
                myAnimalQuizFragment.resetAnimalQuiz();

            }

            Toast.makeText(MainActivity.this, R.string.change_message, Toast.LENGTH_SHORT).show();
        }
    };
}
