package com.choirul.computersdata;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.choirul.computersdata.Model.Computer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String uniqeCompId;
    Button btnSend;
    TextView txtData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText edtCompName = (EditText) findViewById(R.id.edtCompName);
        final EditText edtCompPower = (EditText) findViewById(R.id.edtCompPower);
        final EditText edtCompSpeed = (EditText) findViewById(R.id.edtCompSpeed);
        final EditText edtCompRam = (EditText) findViewById(R.id.edtCompRam);
        final EditText edtCompScreen = (EditText) findViewById(R.id.edtCompScreen);
        final EditText edtCompKeyboard = (EditText) findViewById(R.id.edtCompKeyboard);
        final EditText edtCompCpu = (EditText) findViewById(R.id.edtCompCPU);

        btnSend = (Button) findViewById(R.id.btnSend);
        Button btnGet = (Button) findViewById(R.id.btnGet);
        txtData = (TextView) findViewById(R.id.txtComputer);

        changeSendButtonText();

        firebaseDatabase = FirebaseDatabase.getInstance();
        //create category
        databaseReference = firebaseDatabase.getReference("Computers");
        //databaseReference.child("key").setValue("value");
        firebaseDatabase.getReference("APPLICATION_NAME").setValue("Computers Data");

        firebaseDatabase.getReference("APPLICATION_NAME").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String applicationName = dataSnapshot.getValue(String.class);
                getSupportActionBar().setTitle(applicationName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String compName = edtCompName.getText().toString();
                String compPower = edtCompPower.getText().toString();
                String compSpeed = edtCompSpeed.getText().toString();
                String compRam = edtCompRam.getText().toString();
                String compScreen = edtCompScreen.getText().toString();
                String compKeyboard = edtCompKeyboard.getText().toString();
                String compCpu = edtCompCpu.getText().toString();

                int compPowerInt = 0;
                int compSpeedInt = 0;
                int compRamInt = 0;

                try {
                    compPowerInt = Integer.parseInt(compPower);
                    compSpeedInt = Integer.parseInt(compSpeed);
                    compRamInt = Integer.parseInt(compRam);
                } catch (Exception e) {
                    Log.i("TAG", e.getMessage());
                }

                if(TextUtils.isEmpty(uniqeCompId)){
                    saveCompToDatabase(compName, compScreen, compCpu,
                            compKeyboard, compPowerInt, compSpeedInt,
                            compRamInt);
                    edtCompName.setText("");
                    edtCompScreen.setText("");
                    edtCompCpu.setText("");
                    edtCompKeyboard.setText("");
                    edtCompPower.setText("");
                    edtCompSpeed.setText("");
                    edtCompRam.setText("");
                } else {
                    modifyComputer(compName, compScreen, compCpu,
                            compKeyboard, compPower, compSpeed,
                            compRam);
                }
            }
        });

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uniqeCompId == null) {
                    return;
                }

                computerDataChangeListener();
            }
        });
    }

    private void saveCompToDatabase(String compName, String compScreen, String compCPU,
                                    String compKeyboard, int compPower, int compSpeed,
                                    int compRam){

        if (TextUtils.isEmpty(uniqeCompId)){
            uniqeCompId = databaseReference.push().getKey();
        }

        Computer computer = new Computer(compName, compScreen, compCPU,
                compKeyboard, compPower, compSpeed, compRam);
        databaseReference.child(uniqeCompId).setValue(computer);

        changeSendButtonText();

    }

    private void changeSendButtonText() {
        if (TextUtils.isEmpty(uniqeCompId)){
            btnSend.setText("Produce a new computer");
        } else {
            btnSend.setText("Modify a computer data");
        }
    }

    private void modifyComputer(String compName, String compScreen, String compCPU,
                                String compKeyboard, String compPower, String compSpeed,
                                String compRam) {
        if (!TextUtils.isEmpty(compName)){
            databaseReference.child(uniqeCompId).child("compName").setValue(compName);
        }

        if (!TextUtils.isEmpty(compPower)){
            try {
                int compPowerInt = Integer.parseInt(compPower);
                databaseReference.child(uniqeCompId).child("compPower").setValue(compPowerInt);
            } catch (Exception e) {
                Log.i("TAG", e.getMessage());
            }
        }

        if (!TextUtils.isEmpty(compSpeed)){
            try {
                int compSpeedInt = Integer.parseInt(compSpeed);
                databaseReference.child(uniqeCompId).child("compSpeed").setValue(compSpeedInt);
            } catch (Exception e) {
                Log.i("TAG", e.getMessage());
            }
        }

        if (!TextUtils.isEmpty(compRam)){
            try {
                int compRamInt = Integer.parseInt(compRam);
                databaseReference.child(uniqeCompId).child("compRam").setValue(compRamInt);
            } catch (Exception e) {
                Log.i("TAG", e.getMessage());
            }
        }

        if (!TextUtils.isEmpty(compScreen)){
            databaseReference.child(uniqeCompId).child("compScreen").setValue(compScreen);
        }

        if (!TextUtils.isEmpty(compKeyboard)){
            databaseReference.child(uniqeCompId).child("compKeyboard").setValue(compKeyboard);
        }

        if (!TextUtils.isEmpty(compCPU)){
            databaseReference.child(uniqeCompId).child("compCPU").setValue(compCPU);
        }

    }

    private void computerDataChangeListener(){
        databaseReference.child(uniqeCompId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Computer computer = dataSnapshot.getValue(Computer.class);
                if (computer == null)
                    return;
                txtData.setText("Computer name:" + computer.getCompName() + "-" +
                        "Computer power:" + computer.getCompPower() + "-" +
                        "Computer speed:" + computer.getCompSpeed() + "-" +
                        "Computer RAM:" + computer.getCompRam() + "-" +
                        "Computer Screen:" + computer.getCompScreen() + "-" +
                        "Computer Keyboard:" + computer.getCompKeyboard() + "-" +
                        "Computer CPU:" + computer.getCompCPU());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
