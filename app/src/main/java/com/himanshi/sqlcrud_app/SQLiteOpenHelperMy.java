package com.himanshi.sqlcrud_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteOpenHelperMy extends SQLiteOpenHelper
{
    private static final String TAG = "hi";
    SQLiteDatabase db;
    String q = "create table IF NOT EXISTS customer (cusId INTEGER PRIMARY KEY AUTOINCREMENT, cusName TEXT, cusAddress TEXT, cusPhone TEXT)";
    SQLiteOpenHelperMy(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int dbVersion)
    {
        super(context, dbName, factory, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        this.db = db;
        db.execSQL(q);
        Log.e(TAG,"Database Created...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists customer");
        onCreate(db);
    }


    public boolean insertData(String cusName, String cusAddress, String cusPhone)
    {
        db = getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("cusName",cusName);
        contentValues.put("cusAddress",cusAddress);
        contentValues.put("cusPhone",cusPhone);
        long result = db.insert("customer",null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public String[] viewData(String cusId)
    {
        db = getReadableDatabase();
        StringBuilder s = new StringBuilder("");
        Cursor cursor= db.rawQuery("select * from customer where cusId=?",new String[]{cusId});
        if (cursor.moveToNext())
        {
            return new String[]{
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3)
            };
        }
        return new String[]{
                "No Data",
                "No Data",
                "No Data"
        };
    }
    public String viewAllData()
    {
        db = getReadableDatabase();
        StringBuilder s = new StringBuilder("");
        Cursor cursor= db.rawQuery("select * from customer",null);
        while (cursor.moveToNext())
        {
            s.append(cursor.getString(0));
            s.append(", "+cursor.getString(1));
            s.append(", "+cursor.getString(2));
            s.append(", "+cursor.getString(3)+"\n");
        }
        return s.toString();
    }

    public boolean deleteData(String cusId)
    {
        db = getWritableDatabase();
        int result = db.delete("customer","cusId = ?",new String[]{cusId});
        if(result==0)
            return false;
        else
            return true;
    }

    public boolean updateData(String cusId, String cusNewName, String cusNewAdd, String cusNewPhone)
    {
        db = getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("cusName",cusNewName);
        contentValues.put("cusAddress",cusNewAdd);
        contentValues.put("cusPhone",cusNewPhone);
        int result = db.update("customer",contentValues,"cusId = ?",new String[]{cusId});
        if(result>0)
            return true;
        else
            return false;
    }
}
