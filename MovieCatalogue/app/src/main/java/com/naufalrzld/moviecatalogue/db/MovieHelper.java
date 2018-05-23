package com.naufalrzld.moviecatalogue.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.BaseColumns._ID;
import static com.naufalrzld.moviecatalogue.db.DatabaseContract.FavouriteColumns.MOVIE_ID;
import static com.naufalrzld.moviecatalogue.db.DatabaseContract.TABLE_FAVOURITE;

public class MovieHelper {
    private static String DATABASE_TABLE = TABLE_FAVOURITE;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null,MOVIE_ID + " = ?",
                new String[]{id},null,null,null,null);
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE,null,null,null,
                null,null,_ID + " DESC");
    }

    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,MOVIE_ID + " = ?", new String[]{id});
    }
}
