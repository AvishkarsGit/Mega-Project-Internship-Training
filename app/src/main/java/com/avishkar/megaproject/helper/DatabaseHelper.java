package com.avishkar.megaproject.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

import com.avishkar.megaproject.constants.Utils;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context){
        super(context, Utils.DATABASE_NAME,null,Utils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table
        String query ="CREATE TABLE "+Utils.TABLE_NAME+
                "("+Utils.COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                Utils.COL_NAME+" TEXT,"+Utils.COL_EMAIL+" TEXT,"+
                Utils.COL_PASSWORD+" TEXT,"+
                Utils.COL_PHONE+" TEXT);";

        db.execSQL(query);
        /*
            CREATE TABLE register(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,email TEXT,password TEXT,phone TEXT);
         */

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Utils.TABLE_NAME);
        onCreate(db);
    }

    public Boolean register(String name, String email, String password, String phone ) {
        SQLiteDatabase database = this.getWritableDatabase(); // open db into the write mode

        ContentValues values = new ContentValues();
        values.put(Utils.COL_NAME,name);
        values.put(Utils.COL_EMAIL,email);
        values.put(Utils.COL_PASSWORD,password);
        values.put(Utils.COL_PHONE,phone);

        long result = database.insert(Utils.TABLE_NAME,null,values);
        if (result == -1){
            return false;
        }
        else {
            return true;
        }
    }
}
