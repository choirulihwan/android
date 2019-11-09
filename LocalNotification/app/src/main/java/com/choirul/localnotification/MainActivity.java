package com.choirul.localnotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtTitle;
    EditText edtContent;
    Button btnNotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtContent = (EditText) findViewById(R.id.edtContent);

        btnNotify = (Button) findViewById(R.id.btnNotify);
        btnNotify.setOnClickListener(MainActivity.this);
    }

    private void showNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "");
        builder.setSmallIcon(R.drawable.penguin);
        builder.setContentText(edtContent.getText().toString());
        builder.setContentTitle(edtTitle.getText().toString());

        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify("My Notify", 123, builder.build());
    }

    @Override
    public void onClick(View view) {
        if (edtContent.getText().toString().matches("") || edtTitle.getText().toString().matches("")) {
            return;
        }

        showNotification();
    }
}
