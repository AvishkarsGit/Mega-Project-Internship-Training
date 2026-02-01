package com.avishkar.megaproject.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avishkar.megaproject.R;
import com.avishkar.megaproject.helper.DatabaseHelper;
import com.avishkar.megaproject.holders.CartViewHolder;
import com.avishkar.megaproject.models.ProductsModel;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private Context context;
    private ArrayList<ProductsModel> cartList;
    private DatabaseHelper helper;

    public CartAdapter(Context context, ArrayList<ProductsModel> cartList) {
        this.context = context;
        this.cartList = cartList;
        helper = new DatabaseHelper(context);
    }



    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.addtocart_row_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        ProductsModel product = cartList.get(position);

        holder.cartTextView.setText(product.getProductTitle());
        holder.cartPrice.setText(product.getProductPrice());
        holder.quantitytext.setText(String.valueOf(product.getQuantity()));

        try {
            Bitmap bitmap = BitmapFactory.decodeFile(product.getProductImage());
            holder.cartImg.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.btnRemove.setOnClickListener(v -> {

        });

        holder.btnadd.setOnClickListener(v -> {

        });

        holder.remove.setOnClickListener(v -> {
            helper.addAndRemoveItemFromCart(product.getId(),false);
        });


    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}

