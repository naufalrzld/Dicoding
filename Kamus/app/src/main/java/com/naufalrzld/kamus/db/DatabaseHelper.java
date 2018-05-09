package com.naufalrzld.kamus.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.naufalrzld.kamus.db.KamusContract.KamusEnIdColumns.MEANING;
import static com.naufalrzld.kamus.db.KamusContract.KamusEnIdColumns.WORD;
import static com.naufalrzld.kamus.db.KamusContract.TABLE_EN_ID;
import static com.naufalrzld.kamus.db.KamusContract.TABLE_ID_EN;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "dbkamus";

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_EN_ID = "create table "+TABLE_EN_ID+
            " ("+_ID+" integer primary key autoincrement, " +
            WORD+" text not null, " +
            MEANING+" text not null);";

    public static String CREATE_TABLE_ID_EN = "create table "+TABLE_ID_EN+
            " ("+_ID+" integer primary key autoincrement, " +
            WORD+" text not null, " +
            MEANING+" text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_EN_ID);
        sqLiteDatabase.execSQL(CREATE_TABLE_ID_EN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_EN_ID);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_ID_EN);
    }
}
