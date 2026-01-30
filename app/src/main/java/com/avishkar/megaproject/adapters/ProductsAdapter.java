package com.avishkar.megaproject.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avishkar.megaproject.R;
import com.avishkar.megaproject.holders.ProductsViewHolder;
import com.avishkar.megaproject.models.ProductsModel;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsViewHolder> {
    private Context context;
    private ArrayList<ProductsModel> productList;
    public ProductsAdapter(Context context,ArrayList<ProductsModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.products_row_layout,parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        final ProductsModel model = productList.get(position);
        holder.txtTitle.setText(model.getProductTitle());
        holder.txtDesc.setText(model.getProductDescription());
        holder.txtPrice.setText(model.getProductPrice());


        try {
            Bitmap bitmap = BitmapFactory.decodeFile(model.getProductImage());
            holder.imgProduct.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (model.isDiscount()) {
            //show discount price
            holder.txtDiscount.setVisibility(View.VISIBLE);
            holder.txtOldPrice.setVisibility(View.VISIBLE);
            holder.txtDiscount.setText(model.getProductDiscount()+"%");
            holder.txtOldPrice.setText("₹"+model.getProductPrice()+".00");
            holder.txtOldPrice.setPaintFlags(holder.txtOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            int discountPrice = calculateDiscount(Integer.parseInt(model.getProductPrice()),model.getProductDiscount());
            holder.txtPrice.setText("₹"+(Integer.parseInt(model.getProductPrice()) - discountPrice)+".00");

        }
        else {
            //show only normal price
            holder.txtDiscount.setVisibility(View.GONE);
            holder.txtOldPrice.setVisibility(View.GONE);
            holder.txtPrice.setText("₹"+model.getProductPrice()+".00");
        }

        holder.btnAddToCart.setOnClickListener(v->{
            //handle click on add to cart
            //show quantity layout
            holder.quantityLayout.setVisibility(View.VISIBLE);
            holder.txtQuantity.setText(""+(model.getQuantity()+1));
            holder.btnAddToCart.setVisibility(View.GONE);
        });

        holder.txtQuantityPlus.setOnClickListener(v -> {
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private int calculateDiscount(int amount, int discountPercent) {
        return (amount / 100) * discountPercent;
    }
}
