package com.choirul.martialartclubsqllite;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.choirul.martialartclubsqllite.Model.DatabaseHandler;
import com.choirul.martialartclubsqllite.Model.MartialArt;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UpdateMartialArtActivity extends AppCompatActivity {

    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_martial_art);

        databaseHandler = new DatabaseHandler(UpdateMartialArtActivity.this);
        updateUserInterface();
    }

    private void updateUserInterface() {

        ArrayList<MartialArt> martialArt = databaseHandler.getAllMartialArt();
        if(martialArt.size() > 0) {

            ScrollView scrollView = new ScrollView(UpdateMartialArtActivity.this);
            GridLayout gridLayout = new GridLayout(UpdateMartialArtActivity.this);
            gridLayout.setRowCount(martialArt.size());
            gridLayout.setColumnCount(5);

            TextView[] idTextViews = new TextView[martialArt.size()];
            EditText[][] edtElements = new EditText[martialArt.size()][3];
            Button[] btnEdit = new Button[martialArt.size()];

            Point screenSize = new Point();
            getWindowManager().getDefaultDisplay().getSize(screenSize);

            int screenWidth = screenSize.x;
            int i = 0;

            for (MartialArt martialArts : martialArt){
                idTextViews[i] = new TextView(UpdateMartialArtActivity.this);
                idTextViews[i].setText(martialArts.getMartialArtId() + "");
                idTextViews[i].setGravity(Gravity.CENTER);

                edtElements[i][0] = new EditText(UpdateMartialArtActivity.this);
                edtElements[i][1] = new EditText(UpdateMartialArtActivity.this);
                edtElements[i][2] = new EditText(UpdateMartialArtActivity.this);

                edtElements[i][0].setText(martialArts.getMartialArtName());
                edtElements[i][1].setText(martialArts.getMartialArtPrice() + "");
                edtElements[i][1].setInputType(InputType.TYPE_CLASS_NUMBER);
                edtElements[i][2].setText(martialArts.getMartialArtColor());

                edtElements[i][0].setId(martialArts.getMartialArtId() + 10);
                edtElements[i][1].setId(martialArts.getMartialArtId() + 20);
                edtElements[i][2].setId(martialArts.getMartialArtId() + 30);

                btnEdit[i] = new Button(UpdateMartialArtActivity.this);
                btnEdit[i].setText("Update");
                btnEdit[i].setId(martialArts.getMartialArtId());
                btnEdit[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int martialArtObjId = view.getId();
                        EditText edtName = (EditText) findViewById(martialArtObjId + 10);
                        EditText edtPrice = (EditText) findViewById(martialArtObjId + 20);
                        EditText edtColor = (EditText) findViewById(martialArtObjId + 30);

                        String strName = edtName.getText().toString();
                        String strPrice = edtPrice.getText().toString();
                        String strColor = edtColor.getText().toString();

                        try {
                            double dblPrice = Double.parseDouble(strPrice);
                            databaseHandler.updateMartialArt(martialArtObjId, strName, dblPrice, strColor);
                            Toast.makeText(UpdateMartialArtActivity.this, "Update successfull", Toast.LENGTH_SHORT).show();
                        } catch (NumberFormatException e) {

                        }

                    }
                });

                gridLayout.addView(idTextViews[i], (int) (screenWidth * 0.05), ViewGroup.LayoutParams.WRAP_CONTENT);
                gridLayout.addView(edtElements[i][0], (int) (screenWidth * 0.30), ViewGroup.LayoutParams.WRAP_CONTENT);
                gridLayout.addView(edtElements[i][1], (int) (screenWidth * 0.20), ViewGroup.LayoutParams.WRAP_CONTENT);
                gridLayout.addView(edtElements[i][2], (int) (screenWidth * 0.20), ViewGroup.LayoutParams.WRAP_CONTENT);
                gridLayout.addView(btnEdit[i], (int) (screenWidth * 0.25), ViewGroup.LayoutParams.WRAP_CONTENT);

                i++;
            }

            scrollView.addView(gridLayout);
            setContentView(scrollView);
        }
    }
}
