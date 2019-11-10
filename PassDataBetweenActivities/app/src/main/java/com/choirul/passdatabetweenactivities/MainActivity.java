package com.choirul.passdatabetweenactivities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtName, txtEmail, txtUserName, txtPassword;
    Button btnMove;

    private static final int REQ_CODE_MOVE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtPassword = (TextView) findViewById(R.id.txtPassword);

        findViewById(R.id.btnMove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivityForResult(intent, REQ_CODE_MOVE);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == REQ_CODE_MOVE) && (resultCode == RESULT_OK)) {

            String name = data.getStringExtra(SecondActivity.REQ_TEXT_NAME);
            String email = data.getStringExtra(SecondActivity.REQ_TEXT_EMAIL);
            String username = data.getStringExtra(SecondActivity.REQ_TEXT_USERNAME);
            String password = data.getStringExtra(SecondActivity.REQ_TEXT_PASSWORD);

            txtName.setText(name);
            txtEmail.setText(email);
            txtUserName.setText(username);
            txtPassword.setText(password);
        }

    }
}
