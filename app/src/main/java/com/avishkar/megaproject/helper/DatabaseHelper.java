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
import com.avishkar.megaproject.models.ProductsModel;

import java.util.ArrayList;

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

    public ArrayList<ProductsModel> getAllProducts(){
        ArrayList<ProductsModel> modelsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase(); //open db in read mode
        Cursor cursor = db.rawQuery("SELECT * FROM "+Utils.TABLE_PRODUCT,null);
        while(cursor.moveToNext()) {
            ProductsModel model = new ProductsModel();
            int colIndex = cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_ID);
            int id = cursor.getInt(colIndex);
            model.setId(id);
            model.setProductTitle(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_TITLE)));
            model.setProductDescription(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_DESCRIPTION)));
            model.setProductImage(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_IMAGE)));
            model.setProductPrice(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_PRICE)));
            int discount = cursor.getInt(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_DISCOUNT));
            int isCart = cursor.getInt(cursor.getColumnIndexOrThrow(Utils.COL_IS_CART));
            model.setCart(isCart == 1);
            model.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_QTY)));
            model.setDiscount(discount == 1);
            model.setProductDiscount(cursor.getInt(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_DISCOUNT_PERCENT)));
            int tax = cursor.getInt(cursor.getColumnIndexOrThrow(Utils.COL_TAX_INCLUDED));
            model.setTax(tax==1);
            model.setTax(cursor.getInt(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_TAX)));
            modelsList.add(model);

        }
        cursor.close();
        return modelsList;
    }

    public ArrayList<ProductsModel> searchProduct(String keyword) {

        ArrayList<ProductsModel> filterList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        //SELECT * FROM products WHERE product_title LIKE '%T%'
        String args[] = new String[] {"%"+keyword+"%"};
        Cursor cursor = db.rawQuery("SELECT * FROM "+Utils.TABLE_PRODUCT+" WHERE "+Utils.COL_PRODUCT_TITLE+" LIKE ?",args );
        while (cursor.moveToNext()) {
            ProductsModel model = new ProductsModel();
            int colIndex = cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_ID);
            int id = cursor.getInt(colIndex);
            model.setId(id);
            model.setProductTitle(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_TITLE)));
            model.setProductDescription(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_DESCRIPTION)));
            model.setProductImage(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_IMAGE)));
            model.setProductPrice(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_PRICE)));
            int discount = cursor.getInt(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_DISCOUNT));
            model.setDiscount(discount == 1);
            model.setProductDiscount(cursor.getInt(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_DISCOUNT_PERCENT)));
            int tax = cursor.getInt(cursor.getColumnIndexOrThrow(Utils.COL_TAX_INCLUDED));
            model.setTax(tax==1);
            model.setTax(cursor.getInt(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_TAX)));

            filterList.add(model);

        }
        return filterList;
    }

    public Boolean addAndRemoveItemFromCart(int productId, boolean isAdded){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utils.COL_IS_CART , (isAdded ? 1 : 0));

       int result =  db.update(
                Utils.TABLE_PRODUCT, values , Utils.COL_PRODUCT_ID + " = ? " , new String[]{String.valueOf(productId)}
        );
       return result > 0;

    }

    public ArrayList<ProductsModel> getCartProducts(){

        ArrayList<ProductsModel> modals = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utils.TABLE_PRODUCT + " WHERE " + Utils.COL_IS_CART + " = 1" , null);
        while (cursor.moveToNext()){

            ProductsModel model = new ProductsModel();
            int colIndex = cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_ID);
            int id = cursor.getInt(colIndex);
            model.setId(id);
            model.setProductTitle(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_TITLE)));
            model.setProductDescription(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_DESCRIPTION)));
            model.setProductImage(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_IMAGE)));
            model.setProductPrice(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_PRICE)));
            int discount = cursor.getInt(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_DISCOUNT));
            int isCart = cursor.getInt(cursor.getColumnIndexOrThrow(Utils.COL_IS_CART));
            model.setCart(isCart == 1);
            model.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_QTY)));
            model.setDiscount(discount == 1);
            model.setProductDiscount(cursor.getInt(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_DISCOUNT_PERCENT)));
            int tax = cursor.getInt(cursor.getColumnIndexOrThrow(Utils.COL_TAX_INCLUDED));
            model.setTax(tax==1);
            model.setTax(cursor.getInt(cursor.getColumnIndexOrThrow(Utils.COL_PRODUCT_TAX)));
            modals.add(model);

        }
        return modals;
    }

    public void updateQuantity(int id,int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utils.COL_PRODUCT_QTY,quantity);
        db.update(Utils.TABLE_PRODUCT,values,Utils.COL_PRODUCT_ID+" = ?",new String[]{String.valueOf(id)});
    }

    public boolean updateProduct(int id,String title,String description, String image,String price, boolean isDiscount, int discount_percent, boolean isTax) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utils.COL_PRODUCT_TITLE,title);
        values.put(Utils.COL_PRODUCT_DESCRIPTION,description);
        values.put(Utils.COL_PRODUCT_IMAGE,image);
        values.put(Utils.COL_PRODUCT_PRICE,price);
        values.put(Utils.COL_PRODUCT_DISCOUNT,(isDiscount ? 1 : 0));
        values.put(Utils.COL_PRODUCT_DISCOUNT_PERCENT,discount_percent);
        values.put(Utils.COL_TAX_INCLUDED,(isTax ? 1 : 0));
        values.put(Utils.COL_PRODUCT_TAX, (isTax ? 18 : 5));
        values.put(Utils.COL_IS_CART,0);
        values.put(Utils.COL_PRODUCT_QTY,0);

        long result = database.update(Utils.TABLE_PRODUCT,values,Utils.COL_PRODUCT_ID+" = ?",new String[]{String.valueOf(id)});
        return result > 0;
    }

    public boolean deleteProduct(int id){
        SQLiteDatabase db =this.getWritableDatabase();
        int result = db.delete(Utils.TABLE_PRODUCT,Utils.COL_PRODUCT_ID+" = ?",new String[]{String.valueOf(id)});
        return result > 0;
    }
}
