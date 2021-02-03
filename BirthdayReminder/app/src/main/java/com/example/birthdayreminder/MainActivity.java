package com.example.birthdayreminder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Calendar calendar;
    private int year, month, day;
    private EditText edtTgl;
    private CiSqliteHander ciSqliteHander;
    private List<People> allPeople;
    private ArrayList<Object> peopleNama;
    private ArrayAdapter<Object> arrayAdapter;
    private EditText edtNama;
    private EditText edtPanggilan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTgl = (EditText) findViewById(R.id.edtTgl);
        edtNama = (EditText) findViewById(R.id.edtName);
        edtPanggilan = (EditText) findViewById(R.id.edtPanggilan);

        Button btnPilih = (Button) findViewById(R.id.btnPilih);
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        ListView lvPeople = (ListView) findViewById(R.id.lv_people);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        // pilih tanggal
        btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });

        showDate(year, month+1, day);
        edtTgl.setEnabled(false);

        //get list people
        ciSqliteHander = new CiSqliteHander(MainActivity.this);
        try {
            allPeople = ciSqliteHander.getAllPeople();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        peopleNama = new ArrayList<>();

        if (allPeople.size() > 0) {
            for (int i=0; i < allPeople.size(); i++) {
                People people = allPeople.get(i);
                peopleNama.add(people.getPanggilan() + "-" + people.getTgl_lahir().toString());
            }
        }

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, peopleNama);
        lvPeople.setAdapter(arrayAdapter);

        //tambah reminder
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date dt_tgl_lahir = null;
                try {
                    dt_tgl_lahir = new SimpleDateFormat("yyyy-mm-dd").parse(edtTgl.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                People peole = new People(edtNama.getText().toString(), edtPanggilan.getText().toString(), dt_tgl_lahir);
            }
        });
    }

    private void setDate(View view) {
        showDialog(999);
//        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            showDate(year, month+1, dayOfMonth);
        }
    };

    private void showDate(int year, int i, int day) {
        edtTgl.setText(Integer.toString(year) + "-" + Integer.toString(month+1) + "-" + Integer.toString(day));
    }
}
