package com.avishkar.megaproject.constants;

public class Utils {
    // Shared Preferences Variable
    public static final String SHARED_PREF_NAME="mega_project";
    public static final String KEY_LOGIN="isLoggedIn";

    //SQLite Database Variable
    public static final String DATABASE_NAME="mega_project";
    public static final int DATABASE_VERSION=2;

    //Register Table
    public static final String TABLE_NAME="register";
    public static final String COL_ID="id";
    public static final String COL_NAME="name";
    public static final String COL_EMAIL="email";
    public static final String COL_PASSWORD="password";
    public static final String COL_PHONE="phone";

    //Products table
    public static final String TABLE_PRODUCT="product";
    public static final String COL_PRODUCT_ID="product_id";
    public static final String COL_PRODUCT_TITLE="product_title";
    public static final String COL_PRODUCT_IMAGE="product_image";
    public static final String COL_PRODUCT_DESCRIPTION="product_desc";
    public static final String COL_PRODUCT_PRICE="product_price";
    public static final String COL_PRODUCT_DISCOUNT="product_discount";
    public static final String COL_PRODUCT_DISCOUNT_PERCENT="product_discount_percent";
    public static final String COL_TAX_INCLUDED="tax_included";
    public static final String COL_PRODUCT_TAX="product_tax";
    public static final String COL_PRODUCT_QTY="product_quantity";
    public static final String COL_IS_CART="is_cart";



}
