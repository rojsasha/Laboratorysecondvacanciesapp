package com.example.rojsa.laboratorysecondvacanciesapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.rojsa.laboratorysecondvacanciesapp.data.model.VacanciesModel;

import java.util.ArrayList;
import java.util.List;


public class SQLiteHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "DB_VACANCIES";
    private final static int DB_VERSION = 2;
    private final static String ID = "_id";
    private final static String TABLE_VIEWED = "TABLE_VIEWED";
    private final static String TABLE_FAVORITE_VACANCY = "TABLE_FAVORITE_VACANCY";
    private final static String TABLE_VACANCIES_OVER_DAY = "TABLE_VACANCIES_OVER_DAY";
    private final static String PID = "PID";
    private final static String HEADER = "HEADER";
    private final static String PROFILE = "PROFILE";
    private final static String SALARY = "SALARY";
    private final static String TELEPHONE = "TELEPHONE";
    private final static String DATA = "DATA";
    private final static String PROFESSION = "PROFESSION";
    private final static String SITE_ADDRESS = "SITE_ADDRESS";
    private final static String BODY = "BODY";


    private final static String ID_VIEWED_VACANCY = "ID_VIEWED_VACANCY";

    private final String CREATED_VIEWED_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_VIEWED + "(" +
            ID + " INTEGER_PRIMARY_KEY, " +
            ID_VIEWED_VACANCY + " TEXT " +
            ");";

    private final String CREATE_FAVORITE_VACANCY = "CREATE TABLE IF NOT EXISTS " +
            TABLE_FAVORITE_VACANCY + "(" +
            PID + " TEXT, " +
            HEADER + " TEXT, " +
            PROFILE + " TEXT, " +
            SALARY + " TEXT, " +
            TELEPHONE + " TEXT, " +
            DATA + " TEXT, " +
            PROFESSION + " TEXT, " +
            SITE_ADDRESS + " TEXT, " +
            BODY + " TEXT " +
            ");";

    private final String CREATE_VACANCIES_OVER_DAY_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_VACANCIES_OVER_DAY + "(" +
            PID + " TEXT, " +
            HEADER + " TEXT, " +
            PROFILE + " TEXT, " +
            SALARY + " TEXT, " +
            TELEPHONE + " TEXT, " +
            DATA + " TEXT, " +
            PROFESSION + " TEXT, " +
            SITE_ADDRESS + " TEXT, " +
            BODY + " TEXT " +
            ");";

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATED_VIEWED_TABLE);
        sqLiteDatabase.execSQL(CREATE_FAVORITE_VACANCY);
        sqLiteDatabase.execSQL(CREATE_VACANCIES_OVER_DAY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_VIEWED);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE_VACANCY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_VACANCIES_OVER_DAY);
        onCreate(sqLiteDatabase);
    }

    public void saveViewed(String key) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ID_VIEWED_VACANCY, key);
        int update = db.update(TABLE_VIEWED, cv, ID_VIEWED_VACANCY + " = ?", new String[]{key});
        if (update <= 0) db.insert(TABLE_VIEWED, null, cv);
        Log.d("inserted rows", "rows" + update);
        db.close();
    }

    public ArrayList<String> getViewed() {
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

        if (cursor.moveToFirst()) {
            int id_vacancy = cursor.getColumnIndex(ID_VIEWED_VACANCY);

            do {
                list.add(cursor.getString(id_vacancy));

            } while (cursor.moveToNext());
            Log.d("row is getting", "ok");
        } else {
            Log.d("row is not getting", "fail");
        }

        cursor.close();
        db.close();
        return list;
    }

    public void saveFavoriteVacancy(VacanciesModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PID, model.getPid());
        cv.put(HEADER, model.getHeader());
        cv.put(PROFILE, model.getProfile());
        cv.put(SALARY, model.getSalary());
        cv.put(TELEPHONE, model.getTelephone());
        cv.put(DATA, model.getData());
        cv.put(PROFESSION, model.getProfession());
        cv.put(SITE_ADDRESS, model.getSiteAddress());
        cv.put(BODY, model.getBody());

        long rowsId = db.insert(TABLE_FAVORITE_VACANCY, null, cv);
        Log.d("saved vacancies", "rows" + rowsId + model.getPid());
        db.close();
    }

    public List<VacanciesModel> getFavoriteVacancy() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<VacanciesModel> list = new ArrayList<>();
        Cursor cursor = db.query(TABLE_FAVORITE_VACANCY,
                null,
                null,
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            int indexPid = cursor.getColumnIndex(PID);
            int indexHeader = cursor.getColumnIndex(HEADER);
            int indexProfile = cursor.getColumnIndex(PROFILE);
            int indexSalary = cursor.getColumnIndex(SALARY);
            int indexTelephone = cursor.getColumnIndex(TELEPHONE);
            int indexData = cursor.getColumnIndex(DATA);
            int indexProfession = cursor.getColumnIndex(PROFESSION);
            int indexSiteAddress = cursor.getColumnIndex(SITE_ADDRESS);
            int indexBody = cursor.getColumnIndex(BODY);

            do {
                VacanciesModel model = new VacanciesModel();
                model.setPid(cursor.getString(indexPid));
                model.setHeader(cursor.getString(indexHeader));
                model.setProfile(cursor.getString(indexProfile));
                model.setSalary(cursor.getString(indexSalary));
                model.setTelephone(cursor.getString(indexTelephone));
                model.setData(cursor.getString(indexData));
                model.setProfession(cursor.getString(indexProfession));
                model.setSiteAddress(cursor.getString(indexSiteAddress));
                model.setBody(cursor.getString(indexBody));

                list.add(model);
            } while (cursor.moveToNext());
            Log.d("getFavoriteVacancy", "is getting");

        } else {
            Log.d("getFavoriteVacancy", "failed");
        }

        cursor.close();
        db.close();
        return list;
    }

    public void deleteFavoriteVacancy(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITE_VACANCY, PID + "=?", new String[]{id});
        Log.d("deleteFavoriteVacancy", "ok" + id);
        db.close();

    }

    public void deleteAllVacanciesOverDay() {
        SQLiteDatabase db = this.getWritableDatabase();
        long rowId = db.delete(TABLE_VACANCIES_OVER_DAY, null, null);
        Log.d("database ","clear successful" + rowId);
        db.close();
    }

    public void saveAllVacanciesOverDay(List<VacanciesModel> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (int i = 0; i < list.size(); i++) {
            VacanciesModel model = list.get(i);
            cv.put(PID, model.getPid());
            cv.put(HEADER, model.getHeader());
            cv.put(PROFILE, model.getProfile());
            cv.put(SALARY, model.getSalary());
            cv.put(TELEPHONE, model.getTelephone());
            cv.put(DATA, model.getData());
            cv.put(PROFESSION, model.getProfession());
            cv.put(SITE_ADDRESS, model.getSiteAddress());
            cv.put(BODY, model.getBody());
            long rowsId = db.insert(TABLE_VACANCIES_OVER_DAY, null, cv);
            Log.d("saved vacancies", "rows" + rowsId + model.getPid());
        }
        db.close();
    }

    public List<VacanciesModel> getAllVacanciesOverDay() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<VacanciesModel> list = new ArrayList<>();
        Cursor cursor = db.query(TABLE_VACANCIES_OVER_DAY,
                null,
                null,
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            int indexPid = cursor.getColumnIndex(PID);
            int indexHeader = cursor.getColumnIndex(HEADER);
            int indexProfile = cursor.getColumnIndex(PROFILE);
            int indexSalary = cursor.getColumnIndex(SALARY);
            int indexTelephone = cursor.getColumnIndex(TELEPHONE);
            int indexData = cursor.getColumnIndex(DATA);
            int indexProfession = cursor.getColumnIndex(PROFESSION);
            int indexSiteAddress = cursor.getColumnIndex(SITE_ADDRESS);
            int indexBody = cursor.getColumnIndex(BODY);

            do {
                VacanciesModel model = new VacanciesModel();
                model.setPid(cursor.getString(indexPid));
                model.setHeader(cursor.getString(indexHeader));
                model.setProfile(cursor.getString(indexProfile));
                model.setSalary(cursor.getString(indexSalary));
                model.setTelephone(cursor.getString(indexTelephone));
                model.setData(cursor.getString(indexData));
                model.setProfession(cursor.getString(indexProfession));
                model.setSiteAddress(cursor.getString(indexSiteAddress));
                model.setBody(cursor.getString(indexBody));

                list.add(model);
            } while (cursor.moveToNext());
            Log.d("getAllVacanciesOverDay", "is getting");

        } else {
            Log.d("getAllVacanciesOverDay", "failed");
        }

        cursor.close();
        db.close();
        return list;
    }

}
