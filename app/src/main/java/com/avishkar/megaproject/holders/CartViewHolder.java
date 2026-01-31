package com.avishkar.megaproject.holders;



import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.avishkar.megaproject.R;


public class CartViewHolder extends RecyclerView.ViewHolder {
    public ImageView cartImg;
    public TextView cartTextView;
    public TextView cartPrice;
    public ImageButton btnRemove;
    public ImageButton btnadd;
    public TextView quantitytext;
    public TextView remove;


    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        cartImg = itemView.findViewById(R.id.cartImg);
        cartTextView = itemView.findViewById(R.id.product_title);
        cartPrice = itemView.findViewById(R.id.product_price);
        btnRemove = itemView.findViewById(R.id.btnRemove);
        btnadd = itemView.findViewById(R.id.btnadd);
        remove = itemView.findViewById(R.id.remove_button);
        quantitytext = itemView.findViewById(R.id.quantity_text);
    }
}
