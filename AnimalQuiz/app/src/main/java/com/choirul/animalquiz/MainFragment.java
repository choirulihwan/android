package com.choirul.animalquiz;

import android.animation.Animator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements MyDialogFragment.MyDialogListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //added by choirul
    private static int NUMBER_OF_ANIMAL_IN_QUIZ;

    private List<String> allAnimalsNameList;
    private List<String> animalsNameQuizList;
    private Set<String> animalTypesInQuiz;
    private String correctAnimalAnswer;
    public static int numberOfAllGuesses;
    public static int numberofRightAnswers, numberOfAnimalGuessRows;
    private SecureRandom secureRandomNumber;
    private Handler handler;
    private Animation wrongAnswerAnimation;

    private LinearLayout animalQuizLinearLayout;
    private TextView txtQuestionNumber, txtAnswers;
    private ImageView imgAnimal;
    private LinearLayout[] rowsOfGuessButton;



    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        allAnimalsNameList = new ArrayList<>();
        animalsNameQuizList = new ArrayList<>();
        secureRandomNumber = new SecureRandom();
        handler = new Handler();

        wrongAnswerAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.wrong_answer_animation);
        wrongAnswerAnimation.setRepeatCount(1);

        animalQuizLinearLayout = (LinearLayout) view.findViewById(R.id.animalQuizLinearLayout);
        txtQuestionNumber = (TextView) view.findViewById(R.id.txtQuestionNumber);
        imgAnimal = (ImageView) view.findViewById(R.id.imgAnimal);
        rowsOfGuessButton = new LinearLayout[3];
        rowsOfGuessButton[0] = (LinearLayout) view.findViewById(R.id.firstRowLinearLayout);
        rowsOfGuessButton[1] = (LinearLayout) view.findViewById(R.id.secondRowLinearLayout);
        rowsOfGuessButton[2] = (LinearLayout) view.findViewById(R.id.thirdRowLinearLayout);
        txtAnswers = (TextView) view.findViewById(R.id.txtAnswer);

        for (LinearLayout row: rowsOfGuessButton) {
            for (int column=0; column < row.getChildCount(); column++){
                Button btnGuess = (Button) row.getChildAt(column);
                btnGuess.setTextSize(24);
                btnGuess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button btnGuess = ((Button) view);
                        String guessValue = btnGuess.getText().toString();
                        String answerValue = getTheExactAnimalName(correctAnimalAnswer);
                        ++numberOfAllGuesses;

                        if (guessValue.equals(answerValue)){
                            ++numberofRightAnswers;
                            txtAnswers.setText(answerValue + " is RIGHT");
                            disableQuizGuessButton();
                            if (numberofRightAnswers == NUMBER_OF_ANIMAL_IN_QUIZ){

                                FragmentManager fm = getFragmentManager();
                                MyDialogFragment myDialogFragment = MyDialogFragment.newInstance();
                                myDialogFragment.setTargetFragment(MainFragment.this, 300);
                                myDialogFragment.show(fm, "AnimalQuizResults");

                            } else {
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        animateAnimalQuiz(true);
                                    }
                                }, 1000);
                            }
                        } else {
                            imgAnimal.startAnimation(wrongAnswerAnimation);
                            txtAnswers.setText(R.string.wrong_answer_message);
                            btnGuess.setEnabled(false);
                        }
                    }
                });
            }
        }

        txtQuestionNumber.setText(getString(R.string.question_text, 1, NUMBER_OF_ANIMAL_IN_QUIZ));
        return view;
    }



    private String getTheExactAnimalName(String animalName) {
        return animalName.substring(animalName.indexOf('-') + 1).replace('_', ' ');
    }

    private void disableQuizGuessButton(){
        for (int row=0;row < numberOfAnimalGuessRows; row++){
            LinearLayout guessRowLinearLayout = rowsOfGuessButton[row];
            for (int buttonIndex = 0; buttonIndex < guessRowLinearLayout.getChildCount(); buttonIndex++){
                guessRowLinearLayout.getChildAt(buttonIndex).setEnabled(false);
            }
        }
    }

    public void resetAnimalQuiz(){
        AssetManager assets = getActivity().getAssets();
        allAnimalsNameList.clear();

        try{
            for (String animalType: animalTypesInQuiz){
                String[] animalImagePaths = assets.list(animalType);
                for (String animalImagePath : animalImagePaths){
                    allAnimalsNameList.add(animalImagePath.replace(".png", ""));
                }

            }
        } catch (IOException e) {
            Log.e("Animal Quiz", "Error", e);
        }
        numberofRightAnswers = 0;
        numberOfAllGuesses = 0;
        animalsNameQuizList.clear();

        int counter = 1;
        int numberOfAvailableAnimals = allAnimalsNameList.size();

        while (counter <= NUMBER_OF_ANIMAL_IN_QUIZ) {
            int randomIndex = secureRandomNumber.nextInt(numberOfAvailableAnimals);
            String animalImageName = allAnimalsNameList.get(randomIndex);
            if (!animalsNameQuizList.contains(animalImageName)) {
                animalsNameQuizList.add(animalImageName);
                ++counter;
            }
        }

        showNextAnimal();
    }

    private void animateAnimalQuiz(boolean animateOutAnimalImage){
        if (numberofRightAnswers == 0){
            return;
        }

        int xTopLeft = 0;
        int yTopLeft = 0;

        int xBottomRight = animalQuizLinearLayout.getLeft() + animalQuizLinearLayout.getRight();
        int yBottomRight = animalQuizLinearLayout.getTop() + animalQuizLinearLayout.getBottom();

        //radius
        int radius = Math.max(animalQuizLinearLayout.getWidth(), animalQuizLinearLayout.getHeight());

        Animator animator;
        if (animateOutAnimalImage) {
            animator = ViewAnimationUtils.createCircularReveal(animalQuizLinearLayout, xBottomRight, yBottomRight, radius, 0);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    showNextAnimal();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        } else {
            animator = ViewAnimationUtils.createCircularReveal(animalQuizLinearLayout, xTopLeft, yTopLeft, 0, radius);
        }

        animator.setDuration(700);
        animator.start();
    }

    private void showNextAnimal() {
        String nextAnimalImageName = animalsNameQuizList.remove(0);
        correctAnimalAnswer = nextAnimalImageName;
        txtAnswers.setText("");

        txtQuestionNumber.setText(getString(R.string.question_text, (numberofRightAnswers+1), NUMBER_OF_ANIMAL_IN_QUIZ));

        String animalType = nextAnimalImageName.substring(0, nextAnimalImageName.indexOf("-"));

        AssetManager assets = getActivity().getAssets();
        try (InputStream stream = assets.open(animalType + "/" + nextAnimalImageName + ".png")) {
            Drawable animalImage = Drawable.createFromStream(stream, nextAnimalImageName);
            imgAnimal.setImageDrawable(animalImage);
            animateAnimalQuiz(false);

        } catch (IOException e) {
            Log.e("AnimalQuiz", "There is error in getting " + nextAnimalImageName, e);
        }

        Collections.shuffle(allAnimalsNameList);

        int correctAnimalNameIndex = allAnimalsNameList.indexOf(correctAnimalAnswer);
        String correctAnimalName = allAnimalsNameList.remove(correctAnimalNameIndex);
        allAnimalsNameList.add(correctAnimalName);

        for (int row = 0; row < numberOfAnimalGuessRows; row++){
            for (int column = 0;column < rowsOfGuessButton[row].getChildCount(); column++){
                Button btnGuess = (Button) rowsOfGuessButton[row].getChildAt(column);
                btnGuess.setEnabled(true);

                String animalImageName = allAnimalsNameList.get((row*2) + column);
                btnGuess.setText(getTheExactAnimalName(animalImageName));
            }
        }

        int row = secureRandomNumber.nextInt(numberOfAnimalGuessRows);
        int column = secureRandomNumber.nextInt(2);
        LinearLayout randomRow = rowsOfGuessButton[row];
        String correctAnimalImageName = getTheExactAnimalName(correctAnimalAnswer);
        ((Button) randomRow.getChildAt(column)).setText(correctAnimalImageName);
    }

    public void modifyAnimalGuessRows(SharedPreferences sharedPreferences){
        final String NUMBER_OF_GUESS_OPTIONS = sharedPreferences.getString(MainActivity.GUESSES, null);
        numberOfAnimalGuessRows = Integer.parseInt(NUMBER_OF_GUESS_OPTIONS)/2;

        for(LinearLayout horizontalLinearLayout : rowsOfGuessButton){
            horizontalLinearLayout.setVisibility(View.GONE);
        }

        for(int row = 0;row < numberOfAnimalGuessRows; row++){
            rowsOfGuessButton[row].setVisibility(View.VISIBLE);
        }

    }

    public void modifyQuestions(SharedPreferences sharedPreferences){
        final String NUMBER_OF_QUESTIONS = sharedPreferences.getString(MainActivity.QUESTIONS, null);
        NUMBER_OF_ANIMAL_IN_QUIZ = Integer.parseInt(NUMBER_OF_QUESTIONS);
    }

    public void modifyLanguages(SharedPreferences sharedPreferences){
        final String LANGUAGE_OPTIONS = sharedPreferences.getString(MainActivity.LANGUAGES, null);
        //System.out.println(LANGUAGE_OPTIONS);
        //switch(LANGUAGE_OPTIONS):
        //case "en":
         //   setNewLocale(, LocaleManager.ENGLISH);
        /*numberOfAnimalGuessRows = Integer.parseInt(NUMBER_OF_GUESS_OPTIONS)/2;

        for(LinearLayout horizontalLinearLayout : rowsOfGuessButton){
            horizontalLinearLayout.setVisibility(View.GONE);
        }

        for(int row = 0;row < numberOfAnimalGuessRows; row++){
            rowsOfGuessButton[row].setVisibility(View.VISIBLE);
        }*/
    }

    public void modifyTypeOfAnimalsInQuiz(SharedPreferences sharedPreferences){
        animalTypesInQuiz = sharedPreferences.getStringSet(MainActivity.ANIMALS_TYPE, null);
    }

    public void modifyQuizFont(SharedPreferences sharedPreferences){
        String fontStringValue = sharedPreferences.getString(MainActivity.QUIZ_FONT, null);

        switch (fontStringValue) {
            case "Chunkfive.otf":
                for (LinearLayout row: rowsOfGuessButton){
                    for (int column = 0;column < row.getChildCount(); column++){
                        Button button = (Button) row.getChildAt(column);
                        button.setTypeface(MainActivity.chunkfive);
                    }
                }
                break;

            case "FontleroyBrown.ttf":
                for (LinearLayout row: rowsOfGuessButton){
                    for (int column = 0;column < row.getChildCount(); column++){
                        Button button = (Button) row.getChildAt(column);
                        button.setTypeface(MainActivity.fontlerybrown);
                    }
                }
                break;

            case "Wonderbar Demo.otf":
                for (LinearLayout row: rowsOfGuessButton){
                    for (int column = 0;column < row.getChildCount(); column++){
                        Button button = (Button) row.getChildAt(column);
                        button.setTypeface(MainActivity.wonderbardemo);
                    }
                }
                break;
        }
    }

    public void modifyBackgroundColor(SharedPreferences sharedPreferences){
        String backgroundColor = sharedPreferences.getString(MainActivity.QUIZ_BACKGROUND_COLOR, null);

        switch (backgroundColor) {
            case "White":
                animalQuizLinearLayout.setBackgroundColor(Color.WHITE);
                for (LinearLayout row: rowsOfGuessButton){
                    for (int column = 0; column < row.getChildCount(); column++){
                        Button button = (Button) row.getChildAt(column);
                        button.setBackgroundColor(Color.BLUE);
                        button.setTextColor(Color.WHITE);
                    }
                }
                txtAnswers.setTextColor(Color.BLUE);
                txtQuestionNumber.setTextColor(Color.BLACK);
                break;

            case "Black":
                animalQuizLinearLayout.setBackgroundColor(Color.BLACK);
                for (LinearLayout row: rowsOfGuessButton){
                    for (int column = 0; column < row.getChildCount(); column++){
                        Button button = (Button) row.getChildAt(column);
                        button.setBackgroundColor(Color.YELLOW);
                        button.setTextColor(Color.BLACK);
                    }
                }
                txtAnswers.setTextColor(Color.WHITE);
                txtQuestionNumber.setTextColor(Color.WHITE);
                break;

            case "Green":
                animalQuizLinearLayout.setBackgroundColor(Color.GREEN);
                for (LinearLayout row: rowsOfGuessButton){
                    for (int column = 0; column < row.getChildCount(); column++){
                        Button button = (Button) row.getChildAt(column);
                        button.setBackgroundColor(Color.BLUE);
                        button.setTextColor(Color.WHITE);
                    }
                }
                txtAnswers.setTextColor(Color.WHITE);
                txtQuestionNumber.setTextColor(Color.YELLOW);
                break;

            case "Blue":
                animalQuizLinearLayout.setBackgroundColor(Color.BLUE);
                for (LinearLayout row: rowsOfGuessButton){
                    for (int column = 0; column < row.getChildCount(); column++){
                        Button button = (Button) row.getChildAt(column);
                        button.setBackgroundColor(Color.RED);
                        button.setTextColor(Color.WHITE);
                    }
                }
                txtAnswers.setTextColor(Color.WHITE);
                txtQuestionNumber.setTextColor(Color.YELLOW);
                break;

            case "Red":
                animalQuizLinearLayout.setBackgroundColor(Color.RED);
                for (LinearLayout row: rowsOfGuessButton){
                    for (int column = 0; column < row.getChildCount(); column++){
                        Button button = (Button) row.getChildAt(column);
                        button.setBackgroundColor(Color.BLUE);
                        button.setTextColor(Color.WHITE);
                    }
                }
                txtAnswers.setTextColor(Color.WHITE);
                txtQuestionNumber.setTextColor(Color.YELLOW);
                break;

            case "Yellow":
                animalQuizLinearLayout.setBackgroundColor(Color.YELLOW);
                for (LinearLayout row: rowsOfGuessButton){
                    for (int column = 0; column < row.getChildCount(); column++){
                        Button button = (Button) row.getChildAt(column);
                        button.setBackgroundColor(Color.BLACK);
                        button.setTextColor(Color.WHITE);
                    }
                }
                txtAnswers.setTextColor(Color.BLACK);
                txtQuestionNumber.setTextColor(Color.BLACK);
                break;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

