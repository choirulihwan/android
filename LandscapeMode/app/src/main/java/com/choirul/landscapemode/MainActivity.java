package com.choirul.landscapemode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView txtInclude = (TextView) findViewById(R.id.txtInclude);
        Button btnInclude = (Button) findViewById(R.id.btnInclude);
        final CheckBox chxInclude = (CheckBox) findViewById(R.id.chxInclude);

        btnInclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtInclude.setText("The text is changed");
                chxInclude.setChecked(true);
                chxInclude.setText("The value of this checkbox is true");
            }
        });
    }
}
