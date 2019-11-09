package com.choirul.saveandrestoreinfo;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtHi;
    Button btnHi;
    private String stringValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        letsCreateAToast("on create method is called");

        txtHi = (TextView) findViewById(R.id.txtHi);
        btnHi = (Button) findViewById(R.id.btnHi);

        btnHi.setOnClickListener(MainActivity.this);

    }

    @Override
    public void onClick(View view) {
        stringValue = "Hi";
        txtHi.setText(stringValue);
    }

    public void letsCreateAToast(String message){
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        letsCreateAToast("on start method is called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        letsCreateAToast("on resume method is called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        letsCreateAToast("on pause method is called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        letsCreateAToast("on destroy method is called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        letsCreateAToast("on save instance method");
        outState.putString("STRING_VALUE", stringValue);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        letsCreateAToast("on restore instance state method");

        stringValue = savedInstanceState.getString("STRING_VALUE");
        txtHi.setText(stringValue);
    }
}
