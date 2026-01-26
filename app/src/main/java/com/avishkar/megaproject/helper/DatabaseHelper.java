package com.avishkar.megaproject.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

import com.avishkar.megaproject.constants.Query;
import com.avishkar.megaproject.constants.Utils;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context){
        super(context, Utils.DATABASE_NAME,null,Utils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table
        db.execSQL(Query.register);
        /*
            CREATE TABLE register(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,email TEXT,password TEXT,phone TEXT);
         */

        db.execSQL(Query.products);

        /*
            CREATE TABLE IF NOT EXISTS product(product_id INTEGER PRIMARY KEY AUTOINCREMENT,product_title TEXT,product_image TEXT,product_desc TEXT,product_price TEXT,product_discount INTEGER DEFAULT 0,product_discount_percent INTEGER,tax_included INTEGER DEFAULT 0,product_tax INTEGER);
         */

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("VERSION", "db version old: "+oldVersion);
        Log.d("VERSION", "db current version: "+newVersion);

        if (oldVersion < newVersion) {
            db.execSQL(Query.products);
        }
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

    public Boolean login(String email, String password) {
        SQLiteDatabase database = this.getReadableDatabase();

        String query ="SELECT * FROM "+Utils.TABLE_NAME+" WHERE "+Utils.COL_EMAIL+" =  ?"+
                " and "+Utils.COL_PASSWORD+" = ?";

        /*
            SELECT * FROM register WHERE email = ? and password = ?;
         */
        Cursor cursor = database.rawQuery(query,new String[] {email,password});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean addProduct(String title,String description, String image,String price, boolean isDiscount, int discount_percent, boolean isTax){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utils.COL_PRODUCT_TITLE,title);
        values.put(Utils.COL_PRODUCT_DESCRIPTION,description);
        values.put(Utils.COL_PRODUCT_IMAGE,image);
        values.put(Utils.COL_PRODUCT_PRICE,price);
//        if (isDiscount) {
//            values.put(Utils.COL_PRODUCT_DISCOUNT,1);
//        }
//        else {
//
//            values.put(Utils.COL_PRODUCT_DISCOUNT,0);
//        }
        values.put(Utils.COL_PRODUCT_DISCOUNT,(isDiscount ? 1 : 0));
        values.put(Utils.COL_PRODUCT_DISCOUNT_PERCENT,discount_percent);
        values.put(Utils.COL_TAX_INCLUDED,(isTax ? 1 : 0));
        values.put(Utils.COL_PRODUCT_TAX, (isTax ? 18 : 5));
        values.put(Utils.COL_IS_CART,0);
        values.put(Utils.COL_PRODUCT_QTY,0);

        long result = database.insert(Utils.TABLE_PRODUCT,null,values);
        return result != -1;
    }




}
