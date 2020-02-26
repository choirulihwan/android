package com.choirul.martialartclubsqllite.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

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
        sqLiteDatabase.execSQL("drop table if exists" + MARTIAL_ARTS_TABLE);
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


}
