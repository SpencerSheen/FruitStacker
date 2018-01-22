package com.game.spshe.smoothieclubgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by spshe on 2/17/2017.
 */



public class Connect extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "user.db";

    //High scores
    public static final String TABLE_NAME = "highscore";
    public static final String COL_1 = "id";
    public static final String COL_2 = "score";
    public static final String COL_3 = "date";

    //Total point tracker
//    public static final String TABLE_NAME1 = "user_table";
//    public static final String COL_1_1 = "id1";
//    public static final String COL_2_1 = "totalpoints";



    public Connect(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, SCORE INTEGER, DATE TEXT)");
        //db.execSQL("create table " + TABLE_NAME1 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TOTALPOINTS INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);

    }

    public boolean insertData(String score, String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, score);
        contentValues.put(COL_3, date);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }



    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;

    }

    public Cursor topFiveScores()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " order by " + COL_2 + " desc limit 5", null);
        return res;
    }


    public Integer deleteData ()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, null, null);
    }

}
