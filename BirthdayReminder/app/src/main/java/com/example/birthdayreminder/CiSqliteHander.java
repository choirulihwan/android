package com.example.birthdayreminder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class CiSqliteHander extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "people.db";
    private static final String TABLE_PEOPLE = "people";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "nama";
    private static final String COLUMN_NICK_NAME = "panggilan";
    private static final String COLUMN_TGL_LAHIR = "tgl_lahir";

    String CREATE_TABLE = "CREATE TABLE " + TABLE_PEOPLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_NICK_NAME + " TEXT,"
            + COLUMN_TGL_LAHIR + " DATE)"
            ;

    public CiSqliteHander(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEOPLE);
        onCreate(db);
    }

    public List<People> getAllPeople() throws ParseException {
        List<People> peopleList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_PEOPLE;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do{

                People people = new People();
                people.setId(Integer.parseInt(cursor.getString(0)));
                people.setNama(cursor.getString(1));
                people.setPanggilan(cursor.getString(2));

                Date dt_tgl_lahir = new SimpleDateFormat("yyyy-mm-dd").parse(cursor.getString(3));
                people.setTgl_lahir(dt_tgl_lahir);

                peopleList.add(people);
            } while (cursor.moveToNext());
        }

        return peopleList;
    }
}
