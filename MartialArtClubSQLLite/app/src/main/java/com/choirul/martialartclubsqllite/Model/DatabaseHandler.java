package com.choirul.martialartclubsqllite.Model;

import android.content.Context;
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
}
