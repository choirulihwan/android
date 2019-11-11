package com.choirul.masteringsqllite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText edtComputerName, edtComputerType;
    Button btnAdd, btnDelete;
    ListView listView;

    List<Computer> allComputers;
    ArrayList<String> computersName;
    MySqliteHandler mySqliteHandler;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtComputerName = (EditText) findViewById(R.id.edtComputerName);
        edtComputerType = (EditText) findViewById(R.id.edtComputerType);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        listView = (ListView) findViewById(R.id.listView);

        mySqliteHandler = new MySqliteHandler(MainActivity.this);
        allComputers = mySqliteHandler.getAllComputers();
        computersName = new ArrayList<>();

        if (allComputers.size() > 0){
            for (int i=0; i<allComputers.size(); i++){
                Computer computer = allComputers.get(i);
                computersName.add(computer.getComputerName() + "-" + computer.getComputerType());
            }
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, computersName);
        listView.setAdapter(arrayAdapter);



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Computer computer = new Computer(edtComputerName.getText().toString(), edtComputerType.getText().toString());
                if (edtComputerName.getText().toString().matches("") || edtComputerType.getText().toString().matches("")) {
                    return;
                }
                allComputers.add(computer);
                mySqliteHandler.addComputer(computer);
                computersName.add(computer.getComputerName() + " - " + computer.getComputerType());
                edtComputerName.setText("");
                edtComputerType.setText("");


            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(allComputers.size() > 0){
                    computersName.remove(0);
                    mySqliteHandler.deleteComputer(allComputers.get(0));
                    allComputers.remove(0);
                } else {
                    return;
                }

                arrayAdapter.notifyDataSetChanged();
            }
        });
    }
}
