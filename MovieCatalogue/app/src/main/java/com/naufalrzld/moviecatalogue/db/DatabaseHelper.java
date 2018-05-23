package com.naufalrzld.moviecatalogue.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbmovie";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVOURITE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s INTEGER NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s REAL NOT NULL)",
            DatabaseContract.TABLE_FAVOURITE,
            DatabaseContract.FavouriteColumns._ID,
            DatabaseContract.FavouriteColumns.MOVIE_ID,
            DatabaseContract.FavouriteColumns.TITLE,
            DatabaseContract.FavouriteColumns.OVERVIEW,
            DatabaseContract.FavouriteColumns.POSTER_PATH,
            DatabaseContract.FavouriteColumns.RELEASE_DATE,
            DatabaseContract.FavouriteColumns.POPULARITY
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVOURITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FAVOURITE);
        onCreate(db);
    }
}
