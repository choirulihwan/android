package com.choirul.boxerapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText edtId = (EditText) findViewById(R.id.edtId);
        final EditText edtName = (EditText) findViewById(R.id.edtName);
        final EditText edtPower = (EditText) findViewById(R.id.edtPower);
        final EditText edtSpeed = (EditText) findViewById(R.id.edtSpeed);
        final EditText edtStamina = (EditText) findViewById(R.id.edtStamina);

        Button btnSend = (Button) findViewById(R.id.btnSend);
        Button btnGet = (Button) findViewById(R.id.btnGet);


        //first step to write db
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReferenceFromUrl("https://boxerapp-8191a.firebaseio.com/");

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uniqueId = databaseReference.push().getKey();

                databaseReference.child(uniqueId).child("Boxer Name").setValue(edtName.getText().toString());
                databaseReference.child(uniqueId).child("Boxer Punch Power").setValue(edtPower.getText().toString());
                databaseReference.child(uniqueId).child("Boxer Punch Speed").setValue(edtSpeed.getText().toString());
                databaseReference.child(uniqueId).child("Boxer Punch Stamina").setValue(edtStamina.getText().toString());

                /*
                databaseReference.child(edtId.getText().toString()).child("Boxer Name").setValue(edtName.getText().toString());
                databaseReference.child(edtId.getText().toString()).child("Boxer Punch Power").setValue(edtPower.getText().toString());
                databaseReference.child(edtId.getText().toString()).child("Boxer Punch Speed").setValue(edtSpeed.getText().toString());
                databaseReference.child(edtId.getText().toString()).child("Boxer Punch Stamina").setValue(edtStamina.getText().toString());
                */

                Toast.makeText(MainActivity.this, "Insert data success", Toast.LENGTH_SHORT).show();
                edtId.setText("");
                edtName.setText("");
                edtPower.setText("");
                edtSpeed.setText("");
                edtStamina.setText("");

            }
        });

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database.getReference(edtId.getText().toString()).child("Boxer Name").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        TextView txtName = (TextView) findViewById(R.id.txtName);
                        txtName.setText(value);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                database.getReference(edtId.getText().toString()).child("Boxer Punch Power").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        TextView txtPower = (TextView) findViewById(R.id.txtPower);
                        txtPower.setText(value);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                database.getReference(edtId.getText().toString()).child("Boxer Punch Speed").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        TextView txtSpeed = (TextView) findViewById(R.id.txtSpeed);
                        txtSpeed.setText(value);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                database.getReference(edtId.getText().toString()).child("Boxer Punch Stamina").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        TextView txtStamina = (TextView) findViewById(R.id.txtStamina);
                        txtStamina.setText(value);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
