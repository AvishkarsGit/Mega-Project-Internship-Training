package com.avishkar.megaproject.adapters;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avishkar.megaproject.R;
import com.avishkar.megaproject.activities.AddCartActivity;
import com.avishkar.megaproject.constants.Global;
import com.avishkar.megaproject.helper.DatabaseHelper;
import com.avishkar.megaproject.holders.CartViewHolder;
import com.avishkar.megaproject.holders.ProductsViewHolder;
import com.avishkar.megaproject.models.ProductsModel;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {


    private Context context;
    private ArrayList<ProductsModel> cartList;
    private AddCartActivity cartActivity;
    private DatabaseHelper helper;

    public CartAdapter(Context context, ArrayList<ProductsModel> cartList,AddCartActivity cartActivity) {
        this.context = context;
        this.cartList = cartList;
        this.cartActivity = cartActivity;
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
        holder.txtCartDesc.setText(product.getProductDescription());

        try {
            Bitmap bitmap = BitmapFactory.decodeFile(product.getProductImage());
            holder.cartImg.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (product.isDiscount()){

            holder.txtCartDiscount.setVisibility(VISIBLE);
            holder.txtCartOldPrice.setVisibility(VISIBLE);
            holder.txtCartDiscount.setText(product.getProductDiscount()+"%");
            holder.txtCartOldPrice.setText("₹"+product.getProductPrice()+".00");
            holder.txtCartOldPrice.setPaintFlags(holder.txtCartOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else {
            holder.txtCartDiscount.setVisibility(GONE);
            holder.txtCartOldPrice.setVisibility(GONE);

        }
        updatePrice(
                holder,
                product.getQuantity(),
                Integer.parseInt(product.getProductPrice()),
                product.isDiscount(),
                product.getProductDiscount()
        );


        holder.btnadd.setOnClickListener(v -> {
            int qty = product.getQuantity();
            qty++;
            product.setQuantity(qty);
            notifyItemChanged(position);
            helper.updateQuantity(product.getId() , qty);
            updatePrice(holder,qty,Integer.parseInt(product.getProductPrice()),product.isDiscount(),product.getProductDiscount());
            cartActivity.calculateTotal();
        });

        holder.btnRemove.setOnClickListener(v -> {

            int qty = product.getQuantity();
            if (qty>1){
                qty--;
                product.setQuantity(qty);
                helper.updateQuantity(product.getId(), qty);
                notifyItemChanged(position);
                updatePrice(holder,qty,Integer.parseInt(product.getProductPrice()),product.isDiscount(),product.getProductDiscount());
                cartActivity.calculateTotal();

            }else {
                product.setQuantity(1);
                helper.updateQuantity(product.getId() , 0);
                boolean isRemove = helper.addAndRemoveItemFromCart(product.getId() , false);

                if (isRemove){
                    product.setCart(false);
                    cartList.remove(position);
                    notifyItemRemoved(position);
                    cartActivity.calculateTotal();
                    cartActivity.loadCartProducts();

                }else{
                    Toast.makeText(context, "Failed to remove item", Toast.LENGTH_SHORT).show();
                }
            }

        });

        holder.remove.setOnClickListener(v -> {

            boolean isRemoved = helper.addAndRemoveItemFromCart(product.getId(), false);

            if (isRemoved) {
                product.setCart(false);
                cartList.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(context, "Item removed from cart", Toast.LENGTH_SHORT).show();
            }

            else {
                Toast.makeText(context, "Failed to remove item", Toast.LENGTH_SHORT).show();
            }

        });


    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }


    private void updatePrice(CartViewHolder holder, int quantity, int price, boolean isDiscount, int discount) {
        int totalPrice = calculateTotalPrice(price,quantity,isDiscount,discount);
        holder.cartPrice.setText("₹"+totalPrice+".00");
    }

    private int calculateTotalPrice(int price, int quantity, boolean isDiscount, int discount){
        if (isDiscount) {
            int totalDiscount = Global.calculateDiscount(price,discount);
            int discountPrice = price - totalDiscount;
            return discountPrice * quantity;
        }
        else {
            return price * quantity;
        }
    }

}

