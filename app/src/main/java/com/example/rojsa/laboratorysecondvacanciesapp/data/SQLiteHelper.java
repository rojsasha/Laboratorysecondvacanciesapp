package com.example.rojsa.laboratorysecondvacanciesapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class SQLiteHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "DB_VACANCIES";
    private final static int DB_VERSION = 2;
    private final static String ID = "_id";
    private final static String TABLE_VIEWED = "TABLE_VIEWED";
    private final static String ID_VIEWED_VACANCY = "ID_VIEWED_VACANCY";

    private final String CREATED_VIEWED_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_VIEWED + "(" +
            ID + " INTEGER_PRIMARY_KEY, "+
            ID_VIEWED_VACANCY + " TEXT " +
            ");";

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATED_VIEWED_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_VIEWED);
        onCreate(sqLiteDatabase);
    }
    public void saveViewed(String key){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ID_VIEWED_VACANCY,key);
        long rowId = db.insert(TABLE_VIEWED,null,cv);
        Log.d("inserted rows", "rows" + rowId);
        db.close();
    }

    public ArrayList<String> getViewed(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_VIEWED,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()){
            int id_vacancy = cursor.getColumnIndex(ID_VIEWED_VACANCY);

            do {
                list.add(cursor.getString(id_vacancy));

            }while (cursor.moveToNext());
                Log.d("row is getting", "ok");
            }else {
            Log.d("row is not getting", "fail");
        }

        cursor.close();
        db.close();
        return list;
    }
}
