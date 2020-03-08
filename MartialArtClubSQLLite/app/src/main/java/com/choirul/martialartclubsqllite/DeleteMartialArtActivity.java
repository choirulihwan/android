package com.choirul.martialartclubsqllite;

import android.support.annotation.RestrictTo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.choirul.martialartclubsqllite.Model.DatabaseHandler;
import com.choirul.martialartclubsqllite.Model.MartialArt;

import java.util.ArrayList;

public class DeleteMartialArtActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_martial_art);

        db = new DatabaseHandler(DeleteMartialArtActivity.this);
        updateUserInterface();
    }

    private void updateUserInterface() {

        ArrayList<MartialArt> allMartialArts = db.getAllMartialArt();
        RelativeLayout relativeLayout = new RelativeLayout(DeleteMartialArtActivity.this);
        ScrollView scrollView = new ScrollView(DeleteMartialArtActivity.this);
        RadioGroup radioGroup = new RadioGroup(DeleteMartialArtActivity.this);

        for(MartialArt martialArt: allMartialArts){
            RadioButton radioButton = new RadioButton(DeleteMartialArtActivity.this);
            radioButton.setId(martialArt.getMartialArtId());
            radioButton.setText(martialArt.toString());
            radioGroup.addView(radioButton);
        }

        radioGroup.setOnCheckedChangeListener(DeleteMartialArtActivity.this);
        TextView txtKet = new TextView(DeleteMartialArtActivity.this);
        txtKet.setText("Click radio button to delete data");
        RelativeLayout.LayoutParams txtParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        txtParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        txtParam.addRule(RelativeLayout.CENTER_HORIZONTAL);

        relativeLayout.addView(txtKet, txtParam);
        //scrollView.addView(txtKet);
        scrollView.addView(radioGroup);

        Button btnBack = new Button(DeleteMartialArtActivity.this);
        btnBack.setText("Back");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        buttonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonParams.setMargins(0,0,0,70);

        relativeLayout.addView(btnBack, buttonParams);

        ScrollView.LayoutParams scrollviewParams = new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.WRAP_CONTENT
        );
        scrollviewParams.setMargins(0,70,0,0);

        relativeLayout.addView(scrollView, scrollviewParams);

        setContentView(relativeLayout);

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        db.deleteMartialArt(i);
        Toast.makeText(DeleteMartialArtActivity.this, "Delete process success", Toast.LENGTH_SHORT).show();
        updateUserInterface();
    }
}
