package com.choirul.martialartclubsqllite;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.choirul.martialartclubsqllite.Model.DatabaseHandler;
import com.choirul.martialartclubsqllite.Model.MartialArt;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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


        }
        return super.onOptionsItemSelected(item);
    }
}
