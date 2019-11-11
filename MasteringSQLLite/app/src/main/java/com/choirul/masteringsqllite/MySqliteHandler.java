package com.choirul.masteringsqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MySqliteHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "computer.db";
    private static final String TABLE_COMPUTER = "computers";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_COMPUTER_NAME = "computer_name";
    private static final String COLUMN_COMPUTER_TYPE = "computer_type";

    String CREATE_TABLE = "CREATE TABLE " + TABLE_COMPUTER + "(" + COLUMN_ID +
            " INTEGER PRIMARY KEY, " + COLUMN_COMPUTER_NAME + " TEXT, " +
            COLUMN_COMPUTER_TYPE + " TEXT)";

    public MySqliteHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPUTER);
        onCreate(sqLiteDatabase);
    }

    //insert computer
    public void addComputer(Computer computer){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put(COLUMN_COMPUTER_NAME, computer.getComputerName());
        contentValues.put(COLUMN_COMPUTER_TYPE, computer.getComputerType());
        sqLiteDatabase.insert(TABLE_COMPUTER, null, contentValues);
        sqLiteDatabase.close();
    }


    public Computer getComputer(int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_COMPUTER, new String[]
                        {COLUMN_ID, COLUMN_COMPUTER_NAME, COLUMN_COMPUTER_TYPE},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);

        Computer computer = new Computer(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        return computer;

    }

    public List<Computer> getAllComputers(){
        List<Computer> computerList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_COMPUTER;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do{
                Computer computer = new Computer();
                computer.setId(Integer.parseInt(cursor.getString(0)));
                computer.setComputerName(cursor.getString(1));
                computer.setComputerType(cursor.getString(2));

                computerList.add(computer);
            } while (cursor.moveToNext());
        }

        return computerList;
    }

    public int updateComputer(Computer computer){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COMPUTER_NAME, computer.getComputerName());
        contentValues.put(COLUMN_COMPUTER_TYPE, computer.getComputerType());
        return sqLiteDatabase.update(TABLE_COMPUTER, contentValues, COLUMN_ID + "=?",
                new String[] {String.valueOf(computer.getId())});
    }

    public void deleteComputer(Computer computer){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_COMPUTER, COLUMN_ID + "=?", new String[] {String.valueOf(computer.getId())});
        sqLiteDatabase.close();
    }

    public int getComputersCount(){
        String query = "SELECT * FROM " + TABLE_COMPUTER;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.close();

        return cursor.getCount();
    }
}
