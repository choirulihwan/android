package com.choirul.martialartclubsqllite;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.choirul.martialartclubsqllite.Model.DatabaseHandler;
import com.choirul.martialartclubsqllite.Model.MartialArt;

import java.text.NumberFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseHandler databaseHandler;
    private double totalPrice;
    private ScrollView scrollView;
    private int buttonWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHandler = new DatabaseHandler(MainActivity.this);
        totalPrice = 0.0;
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        buttonWidth = screenSize.x / 2;

        modifyUserInterface();

    }

    private void modifyUserInterface() {
        ArrayList<MartialArt> martialArts = databaseHandler.getAllMartialArt();

        scrollView.removeAllViewsInLayout();

        if(martialArts.size() > 0) {

            GridLayout gridLayout = new GridLayout(MainActivity.this);
            gridLayout.setRowCount((martialArts.size() + 1)/2);
            gridLayout.setColumnCount(2);

            MartialArtButton[] martialArtButtons = new MartialArtButton[martialArts.size()];
            int index = 0;

            for (MartialArt martialArt : martialArts){
                martialArtButtons[index] = new MartialArtButton(MainActivity.this, martialArt);
                martialArtButtons[index].setText(martialArt.getMartialArtId() + "\n" + martialArt.getMartialArtName() + "\n"
                                                + martialArt.getMartialArtPrice());

                switch(martialArt.getMartialArtColor()) {
                    case "Red":
                        martialArtButtons[index].setBackgroundColor(Color.RED);
                        break;

                    case "Blue":
                        martialArtButtons[index].setBackgroundColor(Color.BLUE);
                        break;

                    case "Black":
                        martialArtButtons[index].setBackgroundColor(Color.BLACK);
                        break;

                    case "Yellow":
                        martialArtButtons[index].setBackgroundColor(Color.YELLOW);
                        break;

                    case "Purple":
                        martialArtButtons[index].setBackgroundColor(Color.CYAN);
                        break;

                    case "Green":
                        martialArtButtons[index].setBackgroundColor(Color.GREEN);
                        break;

                    case "White":
                        martialArtButtons[index].setBackgroundColor(Color.WHITE);
                        break;

                    default:
                        martialArtButtons[index].setBackgroundColor(Color.GRAY);
                        break;

                }

                martialArtButtons[index].setOnClickListener(MainActivity.this);
                gridLayout.addView(martialArtButtons[index], buttonWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            }

            scrollView.addView(gridLayout);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){

            case R.id.add_martial_art:
                Intent addMartialArtIntent = new Intent(MainActivity.this, AddMartialArtActivity.class);
                startActivity(addMartialArtIntent);
                return true;

            case R.id.delete_martial_art:
                Intent deleteMartialArtIntent = new Intent(MainActivity.this, DeleteMartialArtActivity.class);
                startActivity(deleteMartialArtIntent);
                return true;

            case R.id.update_martial_art:
                Intent updateMartialArtIntent = new Intent(MainActivity.this, UpdateMartialArtActivity.class);
                startActivity(updateMartialArtIntent);
                return true;

            case R.id.reset_martial_art_price:
                totalPrice = 0.0;
                Toast.makeText(MainActivity.this, "Total price " + totalPrice, Toast.LENGTH_SHORT).show();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        MartialArtButton martialArtButton = (MartialArtButton) v;
        totalPrice = totalPrice + martialArtButton.getMartialArtPrice();
        String priceFormatted = NumberFormat.getCurrencyInstance().format(totalPrice);
        Toast.makeText(MainActivity.this, priceFormatted, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        modifyUserInterface();
    }
}
