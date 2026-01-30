package com.avishkar.megaproject.holders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avishkar.megaproject.R;

public class ProductsViewHolder extends RecyclerView.ViewHolder {
    public ImageView imgProduct;
    public TextView txtTitle,txtDesc,txtOldPrice,txtPrice,txtDiscount,txtQuantityMinus,txtQuantity,txtQuantityPlus;
    public ImageButton btnAddToCart;
    public LinearLayout quantityLayout;
    public ProductsViewHolder(@NonNull View itemView) {
        super(itemView);
        imgProduct = itemView.findViewById(R.id.imgProduct);
        txtTitle = itemView.findViewById(R.id.txtTitle);
        txtDesc = itemView.findViewById(R.id.txtDesc);
        txtOldPrice = itemView.findViewById(R.id.txtOldPrice);
        txtPrice = itemView.findViewById(R.id.txtPrice);
        txtDiscount = itemView.findViewById(R.id.txtDiscount);
        txtQuantityMinus = itemView.findViewById(R.id.txtQuantityMinus);
        txtQuantity = itemView.findViewById(R.id.txtQuantity);
        txtQuantityPlus = itemView.findViewById(R.id.txtQuantityPlus);
        quantityLayout = itemView.findViewById(R.id.quantityLayout);
        btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
    }
}
