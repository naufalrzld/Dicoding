package com.naufalrzld.kamus.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.naufalrzld.kamus.model.KamusModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.naufalrzld.kamus.db.KamusContract.KamusEnIdColumns.MEANING;
import static com.naufalrzld.kamus.db.KamusContract.KamusEnIdColumns.WORD;
import static com.naufalrzld.kamus.db.KamusContract.TABLE_EN_ID;
import static com.naufalrzld.kamus.db.KamusContract.TABLE_ID_EN;

public class KamusHelper {
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public KamusHelper(Context context) {
        this.context = context;
    }

    public KamusHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<KamusModel> getDataByWord(String tableName, String word) {
        Cursor cursor = database.query(tableName, null, WORD + " LIKE ?",
                new String[]{"%" + word + "%"}, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        KamusModel kamusModel;
        if (cursor.getCount() > 0) {
            do {
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                kamusModel.setArti(cursor.getString(cursor.getColumnIndexOrThrow(MEANING)));

                arrayList.add(kamusModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<KamusModel> getAllData(String tableName){
        Cursor cursor = database.query(tableName,null,null,null,
                null,null,_ID+ " ASC",null);
        cursor.moveToFirst();
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        KamusModel kamusModel;
        if (cursor.getCount()>0) {
            do {
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                kamusModel.setArti(cursor.getString(cursor.getColumnIndexOrThrow(MEANING)));


                arrayList.add(kamusModel);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public void insertTransactionENID(KamusModel kamusModel){
        String sql = "INSERT INTO "+TABLE_EN_ID+" ("+WORD+", "+MEANING
                +") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, kamusModel.getKata());
        stmt.bindString(2, kamusModel.getArti());
        stmt.execute();
        stmt.clearBindings();

    }

    public void insertTransactionIDEN(KamusModel kamusModel){
        String sql = "INSERT INTO "+TABLE_ID_EN+" ("+WORD+", "+MEANING
                +") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, kamusModel.getKata());
        stmt.bindString(2, kamusModel.getArti());
        stmt.execute();
        stmt.clearBindings();

    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }
}
