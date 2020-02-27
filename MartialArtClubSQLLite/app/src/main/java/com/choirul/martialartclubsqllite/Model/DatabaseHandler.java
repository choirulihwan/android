package com.choirul.martialartclubsqllite.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "martialartdatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String MARTIAL_ARTS_TABLE = "martialart";
    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "name";
    private static final String PRICE_KEY = "price";
    private static final String COLOR_KEY = "color";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createDatabaseSQL = "create table " + MARTIAL_ARTS_TABLE +
                "(" + ID_KEY + " integer primary key autoincrement, " +
                        NAME_KEY + " text, " + PRICE_KEY + " real," +
                        COLOR_KEY + " text)";
        sqLiteDatabase.execSQL(createDatabaseSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + MARTIAL_ARTS_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void addMartialArt(MartialArt martialArt){
        SQLiteDatabase db = getWritableDatabase();
        String addMartialArtSql = "insert into " + MARTIAL_ARTS_TABLE +
                                    " values(null, '" + martialArt.getMartialArtName() + "','" +
                                    martialArt.getMartialArtPrice() + "','" + martialArt.getMartialArtColor() +
                                    "')";
        db.execSQL(addMartialArtSql);
        db.close();
    }

    public void deleteMartialArt(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String deleteMartialArtSql = "delete from " + MARTIAL_ARTS_TABLE +
                                    " where " + ID_KEY + " = " + id;
        db.execSQL(deleteMartialArtSql);
        db.close();
    }

    public void updateMartialArt(int id, String name, double price, String color) {
        SQLiteDatabase db = getWritableDatabase();
        String updateMartialArtSql = "update " + MARTIAL_ARTS_TABLE + " set " + NAME_KEY + " = '" +
                name + "', " + PRICE_KEY + " = " + price + ", " + COLOR_KEY + " = '" + color +
                "' WHERE " + ID_KEY + " = " + id;
        db.execSQL(updateMartialArtSql);
        db.close();
    }

    public ArrayList<MartialArt> getAllMartialArt() {
        SQLiteDatabase db = getWritableDatabase();
        String getAllMartialArtSql = "select * from " + MARTIAL_ARTS_TABLE;
        Cursor cursor = db.rawQuery(getAllMartialArtSql, null);

        ArrayList<MartialArt> martialArts = new ArrayList<>();
        while (cursor.moveToNext()) {
            MartialArt current = new MartialArt(cursor.getInt(0),
                                                cursor.getString(1),
                                                cursor.getDouble(2),
                                                cursor.getString(3));
            martialArts.add(current);
        }

        db.close();
        return martialArts;
    }

    public MartialArt getMartialArtById(int id){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "select * from " + MARTIAL_ARTS_TABLE + " where " + ID_KEY + " = " + id;
        Cursor cursor = db.rawQuery(sql, null);

        MartialArt martialArt = null;
        if (cursor.moveToFirst()) {
            martialArt = new MartialArt(cursor.getInt(0), cursor.getString(1),
                    cursor.getDouble(2), cursor.getString(3));
        }

        db.close();
        return martialArt;
    }
}
