package com.project.schedulerapplication.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.schedulerapplication.beans.Schedules;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    public DatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE SCHEDULE(code INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, title TEXT, contents TEXT);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insert(Schedules schedules) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("insert into SCHEDULE values(null, '" + schedules.date + "', '" + schedules.title + "', '" + schedules.contents + "');");
        db.close();
    }

    public void delete(Schedules schedules) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from SCHEDULE where code = '" + schedules.code + "';");
    }

    public ArrayList<Schedules> select(Schedules schedules) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Schedules> list = new ArrayList<>();

        String str = "";

        Cursor cursor = db.rawQuery("select * from SCHEDULE where date = '" + schedules.date + "'", null);
        while(cursor.moveToNext()) {
            Schedules schedules1 = new Schedules();
            schedules1.code = cursor.getString(0);
            schedules1.date = cursor.getString(1);
            schedules1.title = cursor.getString(2);
            schedules1.contents = cursor.getString(3);

            list.add(schedules1);
        }

        return list;
    }
}
