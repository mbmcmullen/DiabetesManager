package com.example.mcmull27.diabetesmanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "diabetesManagerDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ACTIVITIES = "activities";
    private static final String ID = "id";
    private static final String TYPE = "type";
    private static final String DESCRIPTION = "description";
    private static final String TIMESTAMP = "timestamp";
    private static final String AMOUNT = "amount";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlcreate = "create table" + TABLE_ACTIVITIES + "(" + ID;
        sqlcreate += "integer primary key autoincrement,"+ TYPE + "text,";
        sqlcreate +=  DESCRIPTION+ "text," + AMOUNT + "text,";
        sqlcreate +=  TIMESTAMP+"real)";
        db.execSQL(sqlcreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_ACTIVITIES);
        onCreate(db);
    }

    public void insert(Act a){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "insert into " + TABLE_ACTIVITIES;
        sqlInsert+= "values(null, "+a.getType();
        sqlInsert+= ", "+a.getDescription();
        sqlInsert+= ", "+a.getAmount();
        sqlInsert+=", "+a.getTimestamp().toString();
        db.execSQL(sqlInsert);
        db.close();
    }

    public void updateById(int id, String desc, double amt, Date time){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlUpdate = "update "+ TABLE_ACTIVITIES;
        sqlUpdate +=" set "+DESCRIPTION +"="+ desc+", ";
        sqlUpdate += AMOUNT+"="+amt+", ";
        sqlUpdate += TIMESTAMP+"="+time.toString();
        sqlUpdate += "where "+ID+"="+id;
        db.execSQL(sqlUpdate);
        db.close();
    }

    public Act selectById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlSelect = "select from "+TABLE_ACTIVITIES;
        sqlSelect+= " where "+ID+"="+id;
        Cursor cursor = db.rawQuery(sqlSelect,null);
        Act ret = null;
        try {
            if (cursor.moveToFirst())
                ret = new Act(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        Double.parseDouble((cursor.getString(3))),
                        new SimpleDateFormat("dd/MM/yyyy").parse(cursor.getString(4)));
        }catch(ParseException e){
            Log.e("DatabaseManager", "parse exception caught in selectById. Activity database id:"+id);
        }
        return ret;
    }

}
