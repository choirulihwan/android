package com.choirul.martialartclubsqllite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.choirul.martialartclubsqllite.Model.DatabaseHandler;
import com.choirul.martialartclubsqllite.Model.MartialArt;

public class AddMartialArtActivity extends AppCompatActivity {
    EditText edt_name, edt_color, edt_price;
    Button btn_add, btn_back;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_martial_art);

        //Toast.makeText(AddMartialArtActivity.this, "this is add martial", Toast.LENGTH_SHORT).show();
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_price = (EditText) findViewById(R.id.edt_price);
        edt_color = (EditText) findViewById(R.id.edt_color);

        btn_add = (Button) findViewById(R.id.btn_add);
        btn_back = (Button) findViewById(R.id.btn_back);

        db = new DatabaseHandler(AddMartialArtActivity.this);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMartialArtToDatabase();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addMartialArtToDatabase(){
        String name = edt_name.getText().toString();
        String price = edt_price.getText().toString();
        String color = edt_color.getText().toString();

        try {
            double ld_price = Double.parseDouble(price);

            MartialArt martialArt = new MartialArt(0, name, ld_price, color);
            db.addMartialArt(martialArt);
            Toast.makeText(AddMartialArtActivity.this,  martialArt + "\nsuccessfully inserted", Toast.LENGTH_SHORT).show();
            resetComponent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetComponent() {
        edt_name.setText("");
        edt_color.setText("");
        edt_price.setText("");
    }
}
