package com.avishkar.megaproject.holders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avishkar.megaproject.R;

public class ProductsViewHolder extends RecyclerView.ViewHolder {
    public ImageView imgProduct;
    public TextView txtTitle,txtDesc,txtOldPrice,txtPrice,txtDiscount;
    public Button btnAddToCart;
    public ProductsViewHolder(@NonNull View itemView) {
        super(itemView);
        imgProduct = itemView.findViewById(R.id.imgProduct);
        txtTitle = itemView.findViewById(R.id.txtTitle);
        txtDesc = itemView.findViewById(R.id.txtDesc);
        txtOldPrice = itemView.findViewById(R.id.txtOldPrice);
        txtPrice = itemView.findViewById(R.id.txtPrice);
        txtDiscount = itemView.findViewById(R.id.txtDiscount);
        btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
    }
}
