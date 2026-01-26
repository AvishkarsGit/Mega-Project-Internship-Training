package com.avishkar.megaproject.constants;

public class Query {

    public static final String register ="CREATE TABLE IF NOT EXISTS "+Utils.TABLE_NAME+
            "("+Utils.COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            Utils.COL_NAME+" TEXT,"+Utils.COL_EMAIL+" TEXT,"+
            Utils.COL_PASSWORD+" TEXT,"+
            Utils.COL_PHONE+" TEXT);";


    public static final String products ="CREATE TABLE IF NOT EXISTS "+Utils.TABLE_PRODUCT+
            "("+Utils.COL_PRODUCT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            Utils.COL_PRODUCT_TITLE+" TEXT,"+
            Utils.COL_PRODUCT_IMAGE+" TEXT,"+
            Utils.COL_PRODUCT_DESCRIPTION+" TEXT,"+
            Utils.COL_PRODUCT_PRICE+" TEXT,"+
            Utils.COL_PRODUCT_DISCOUNT+" INTEGER DEFAULT 0,"+
            Utils.COL_PRODUCT_DISCOUNT_PERCENT+" INTEGER,"+
            Utils.COL_TAX_INCLUDED+" INTEGER DEFAULT 0,"+
            Utils.COL_PRODUCT_QTY+" INTEGER,"+
            Utils.COL_IS_CART+" INTEGER DEFAULT 0,"+
            Utils.COL_PRODUCT_TAX+" INTEGER);";

    public static final String cart ="SELECT * FROM "+Utils.TABLE_PRODUCT+
            "WHERE "+Utils.COL_IS_CART+" = 1";
//    SELECT * FROM products WHERE is_cart = 1;

    /*
        CART
        -------
        userId -> User
        quantity
        productId -> Product
        totalPrice
     */
}


/*
*/