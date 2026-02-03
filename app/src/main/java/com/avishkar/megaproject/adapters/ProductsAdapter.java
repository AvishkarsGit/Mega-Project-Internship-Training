package com.avishkar.megaproject.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avishkar.megaproject.MainActivity;
import com.avishkar.megaproject.R;
import com.avishkar.megaproject.activities.AddProductActivity;
import com.avishkar.megaproject.constants.Global;
import com.avishkar.megaproject.helper.DatabaseHelper;
import com.avishkar.megaproject.holders.ProductsViewHolder;
import com.avishkar.megaproject.models.ProductsModel;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsViewHolder> {
    private Context context;
    private ArrayList<ProductsModel> productList;
    private DatabaseHelper helper;
    private MainActivity mainActivity;
    public ProductsAdapter(Context context,ArrayList<ProductsModel> productList, MainActivity mainActivity) {
        this.context = context;
        this.productList = productList;
        helper = new DatabaseHelper(context);
        this.mainActivity = mainActivity;
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

        }
        else {
            //show only normal price
            holder.txtDiscount.setVisibility(View.GONE);
            holder.txtOldPrice.setVisibility(View.GONE);
        }

        updatePrice(
                holder,
                model.getQuantity() > 0 ? model.getQuantity() : 1,
                Integer.parseInt(model.getProductPrice()),
                model.isDiscount(),
                model.getProductDiscount()
        );

        //check if item is in the cart
        if (model.isCart()) {
            holder.btnAddToCart.setVisibility(View.GONE);
            holder.quantityLayout.setVisibility(View.VISIBLE);
            holder.txtQuantity.setText(""+model.getQuantity());
        }
        else {
            holder.btnAddToCart.setVisibility(View.VISIBLE);
            holder.quantityLayout.setVisibility(View.GONE);
        }

        holder.btnAddToCart.setOnClickListener(v->{

            int productId = model.getId();
            boolean isAdded = helper.addAndRemoveItemFromCart(productId,true);
            if (isAdded){
                Toast.makeText(context , "Product added " , Toast.LENGTH_SHORT).show();
                model.setCart(true);
                model.setQuantity(1);
                helper.updateQuantity(model.getId(),1);
                mainActivity.updateCartCount();
                notifyItemChanged(position);

            }
            else {
                Toast.makeText(context , "Something went wrong" , Toast.LENGTH_SHORT).show();

            }

        });

        holder.txtQuantityPlus.setOnClickListener(v -> {
            int qty = model.getQuantity();
            qty++;
            model.setQuantity(qty);
            notifyItemChanged(position);
            helper.updateQuantity(model.getId(),qty);
            updatePrice(holder,qty,Integer.parseInt(model.getProductPrice()),model.isDiscount(),model.getProductDiscount());
        });

       holder.txtQuantityMinus.setOnClickListener(v -> {
            int qty = model.getQuantity();
            if (qty > 1) {
                qty--;
                model.setQuantity(qty);
                helper.updateQuantity(model.getId(),qty); updatePrice(holder,qty,Integer.parseInt(model.getProductPrice()),model.isDiscount(),model.getProductDiscount());
            }
            else {
                model.setQuantity(1);
                helper.updateQuantity(model.getId(),0);
                helper.addAndRemoveItemFromCart(model.getId(),false);
                mainActivity.updateCartCount();
                model.setCart(false);
            }
            notifyItemChanged(position);
        });

       holder.itemView.setOnClickListener(v->{
           Intent i=new Intent(context, AddProductActivity.class);
           i.putExtra("product",model);
           i.putExtra("isEdited",true);
           context.startActivity(i);
       });

       holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               new AlertDialog.Builder(context)
                       .setTitle("Delete Item")
                       .setMessage("Are you sure you want to delete this product?")
                       .setCancelable(false)
                       .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               //delete
                               boolean isDeleted = helper.deleteProduct(model.getId());
                               if (isDeleted) {
                                   productList.remove(position);
                                   notifyItemRemoved(position);
                                   notifyItemRangeRemoved(position,productList.size());
                                   Toast.makeText(context, "Product deleted Successfully.", Toast.LENGTH_SHORT).show();
                               }
                               else {
                                   Toast.makeText(context, "Failed to delete product", Toast.LENGTH_SHORT).show();
                               }
                           }
                       })
                       .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                           }
                       }).show();
               return true;
           }
       });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private void updatePrice(ProductsViewHolder holder, int quantity, int price,boolean isDiscount, int discount) {
        int totalPrice = calculateTotalPrice(price,quantity,isDiscount,discount);
        holder.txtPrice.setText("₹"+totalPrice+".00");
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


    public void updateList( ArrayList<ProductsModel> list){
        this.productList = list ;
        notifyDataSetChanged();

    }
}
