package com.choirul.passdatabetweenactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtName, edtUserName, edtEmail, edtPassword;
    Button btnDone;

    public static final String REQ_TEXT_NAME = "REQUEST_NAME";
    public static final String REQ_TEXT_EMAIL = "REQUEST_EMAIL";
    public static final String REQ_TEXT_USERNAME = "REQUEST_USERNAME";
    public static final String REQ_TEXT_PASSWORD = "REQUEST_PASSWORD";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        edtName = (EditText) findViewById(R.id.edtName);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        btnDone = (Button) findViewById(R.id.btnDone);
        btnDone.setOnClickListener(SecondActivity.this);
    }

    @Override
    public void onClick(View view) {
        Intent data = new Intent();
        data.putExtra(REQ_TEXT_NAME, edtName.getText().toString());
        data.putExtra(REQ_TEXT_EMAIL, edtEmail.getText().toString());
        data.putExtra(REQ_TEXT_USERNAME, edtUserName.getText().toString());
        data.putExtra(REQ_TEXT_PASSWORD, edtPassword.getText().toString());
        setResult(RESULT_OK, data);
        finish();
    }
}
