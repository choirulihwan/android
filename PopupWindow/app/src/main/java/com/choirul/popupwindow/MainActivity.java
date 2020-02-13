package com.choirul.popupwindow;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {

    private Button btnShowPopup;
    private PopupWindow popupWindow;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShowPopup = (Button) findViewById(R.id.btnShowPopup);

        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);

        btnShowPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popup_window, null);

                popupWindow = new PopupWindow(popupView, ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT);

                popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);
                btnShowPopup.setVisibility(View.INVISIBLE);

                Button btnClose = (Button) popupView.findViewById(R.id.btnClose);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                        btnShowPopup.setVisibility(View.VISIBLE);
                    }
                });

            }
        });
    }
}
