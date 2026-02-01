package com.avishkar.megaproject.models;

import java.io.Serializable;

public class ProductsModel implements Serializable {

    String productTitle,productDescription, productPrice, productImage;
    int id,productDiscount,tax,quantity;
    boolean isDiscount, isTax, isCart;

    public ProductsModel() {
        //default constructor
    }

    public ProductsModel(int id,String productTitle, String productDescription, String productPrice, String productImage, int productDiscount, int tax, int quantity, boolean isDiscount, boolean isTax, boolean isCart) {
        this.id = id;
        this.productTitle = productTitle;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.productDiscount = productDiscount;
        this.tax = tax;
        this.quantity = quantity;
        this.isDiscount = isDiscount;
        this.isTax = isTax;
        this.isCart = isCart;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(int productDiscount) {
        this.productDiscount = productDiscount;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isDiscount() {
        return isDiscount;
    }

    public void setDiscount(boolean discount) {
        isDiscount = discount;
    }

    public boolean isTax() {
        return isTax;
    }

    public void setTax(boolean tax) {
        isTax = tax;
    }

    public boolean isCart() {
        return isCart;
    }

    public void setCart(boolean cart) {
        isCart = cart;
    }
}
