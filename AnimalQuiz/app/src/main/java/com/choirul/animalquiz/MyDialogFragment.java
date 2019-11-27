package com.choirul.animalquiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {

    public static MyDialogFragment newInstance() {
        MyDialogFragment fragment = new MyDialogFragment();
        return fragment;
    }

    public interface MyDialogListener{
        void resetAnimalQuiz();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setMessage(getString(R.string.result_string_value, MainFragment.numberOfAllGuesses, (1000/(double) MainFragment.numberOfAllGuesses)));
        builder.setMessage(getString(R.string.result_string_value, MainFragment.numberOfAllGuesses, 100 * (double) MainFragment.numberofRightAnswers / (double) MainFragment.numberOfAllGuesses));

        builder.setPositiveButton(R.string.reset_animal_quiz, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDialogListener listener = (MyDialogListener) getTargetFragment();
                listener.resetAnimalQuiz();
                dismiss();
            }
        });

        return builder.create();
    }


}