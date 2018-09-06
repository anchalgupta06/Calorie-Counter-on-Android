package com.cc.anchal.caloriecounterpackage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Anchal on 12-10-2017.
 */

public class HelperClass extends SQLiteOpenHelper {
    public static final String DB_NAME="app_db";
    public static final int DB_VERSION=1;

    public HelperClass(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE User_Data ('date' TEXT PRIMARY KEY,'suggested_calories' NUMBER,'consumed_calories' NUMBER, 'protein' NUMBER, 'carbs' NUMBER, 'fat' NUMBER);");
        db.execSQL("CREATE TABLE User_Consumption ('date' TEXT ,'item' TEXT,'quantity' NUMBER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS User_Data");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS User_Consumption");
        onCreate(db);
    }
}
