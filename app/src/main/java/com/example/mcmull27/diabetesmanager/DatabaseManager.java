package com.example.mcmull27.diabetesmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.ArrayList;
import java.util.Locale;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "diabetesManagerDB";
    private static final int DATABASE_VERSION = 1;
    //table
    private static final String TABLE_ACTIVITIES = "activities";
    private static final String TABLE_REGIMEN = "regimen";
    //columns
    private static final String ID = "id";
    private static final String TYPE = "type";
    private static final String DESCRIPTION = "description";
    private static final String TIMESTAMP = "timestamp";
    private static final String AMOUNT = "amount";
    private static final String ACTIVITY_COLUMNS = "(" + TextUtils.join(", ", Arrays.asList(ID, TYPE, DESCRIPTION, AMOUNT, TIMESTAMP)) + ")";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlcreate = "create table " + TABLE_ACTIVITIES + " (" + ID;
        sqlcreate += " integer primary key autoincrement, "+ TYPE + " text, ";
        sqlcreate +=  DESCRIPTION+ " text, " + AMOUNT + " text, ";
        sqlcreate +=  TIMESTAMP+ " text)";
        db.execSQL(sqlcreate);

        String sqlcreateReg = "create table " + TABLE_REGIMEN +" (" + ID;
        sqlcreateReg += " integer primary key autoincrement, "+ TYPE + " text, ";
        sqlcreateReg +=  DESCRIPTION+ " text, " + AMOUNT + " text, ";
        sqlcreateReg +=  TIMESTAMP+ " text)";
        db.execSQL(sqlcreateReg);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_ACTIVITIES);
        db.execSQL("drop table if exists " + TABLE_REGIMEN);
        onCreate(db);
    }

    private static String escape(String s) {
        return DatabaseUtils.sqlEscapeString(s);
    }

    public void insertAct(Act a){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "insert into " + TABLE_ACTIVITIES + " " + ACTIVITY_COLUMNS;
        sqlInsert+= " values(null, " + escape(a.getType());
        sqlInsert+= ", " + escape(a.getDescription());
        sqlInsert+= ", " + escape("" + a.getAmount());
        sqlInsert+=", " + escape(a.getTimestamp());
        sqlInsert+=")";
        db.execSQL(sqlInsert);
        db.close();
    }

    public void insertRegItem(Act a){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "insert into " + TABLE_REGIMEN + " " + ACTIVITY_COLUMNS;
        sqlInsert+= " values(null, " + escape(a.getType());
        sqlInsert+= ", " + escape(a.getDescription());
        sqlInsert+= ", " + escape("" + a.getAmount());
        sqlInsert+=", " + escape(a.getTimestamp());
        db.execSQL(sqlInsert);
        db.close();
    }

    public void updateActById(int id, String desc, double amt, Date time){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlUpdate = "update "+ TABLE_ACTIVITIES;
        sqlUpdate +=" set "+DESCRIPTION +"="+ desc+", ";
        sqlUpdate += AMOUNT+"="+amt+", ";
        sqlUpdate += TIMESTAMP+"="+time;
        sqlUpdate += " where "+ID+"="+id;
        db.execSQL(sqlUpdate);
        db.close();
    }

    public void updateRegItemById(int id, String desc, double amt, Date time){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlUpdate = "update "+ TABLE_REGIMEN;
        sqlUpdate +=" set "+DESCRIPTION +"="+ desc+", ";
        sqlUpdate += AMOUNT+"="+amt+", ";
        sqlUpdate += TIMESTAMP+"="+time;
        sqlUpdate += " where "+ID+"="+id;
        db.execSQL(sqlUpdate);
        db.close();
    }

    public Act selectActById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlSelect = "select from "+TABLE_ACTIVITIES;
        sqlSelect+= " where "+ID+"="+id;
        Cursor cursor = db.rawQuery(sqlSelect,null);
        Act ret = null;
        if (cursor.moveToFirst())
            ret = new Act(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getString(4));
        cursor.close();
        db.close();
        return ret;
    }

    public Act selectRegItemById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlSelect = "select from "+TABLE_REGIMEN;
        sqlSelect+= " where "+ID+"="+id;
        Cursor cursor = db.rawQuery(sqlSelect,null);
        Act ret = null;
        if (cursor.moveToFirst())
            ret = new Act(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getString(4));
        cursor.close();
        db.close();
        return ret;
    }

    public void deleteActByID(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlDelete = "delete from " + TABLE_ACTIVITIES;
        sqlDelete += " where " + ID + " = " + id;

        db.execSQL(sqlDelete);
        db.close();
    }

    public void deleteRegItemByID(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlDelete = "delete from " + TABLE_REGIMEN;
        sqlDelete += " where " + ID + " = " + id;

        db.execSQL(sqlDelete);
        db.close();
    }

    public ArrayList<Act> selectAllActs(){
        ArrayList<Act> activities = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String sqlQuery = "select * from " + TABLE_ACTIVITIES;
        Cursor cursor = db.rawQuery(sqlQuery, null);

        while (cursor.moveToNext()) {
            Act currentActivity = new Act(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    Double.parseDouble((cursor.getString(3))),
                    cursor.getString(4));

            activities.add(currentActivity);
        }
        db.close();
        cursor.close();

        return activities;
    }

    public ArrayList<Act> selectAllRegItems(){
        ArrayList<Act> activities = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String sqlQuery = "select * from " + TABLE_REGIMEN;
        Cursor cursor = db.rawQuery(sqlQuery, null);

        while (cursor.moveToNext()) {
            Act currentActivity = new Act(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    Double.parseDouble((cursor.getString(3))),
                    cursor.getString(4));

            activities.add(currentActivity);
        }
        db.close();
        cursor.close();

        return activities;
    }
}
