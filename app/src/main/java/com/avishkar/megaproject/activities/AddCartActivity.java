package com.avishkar.megaproject.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.avishkar.megaproject.MainActivity;
import com.avishkar.megaproject.R;
import com.avishkar.megaproject.adapters.CartAdapter;
import com.avishkar.megaproject.constants.Global;
import com.avishkar.megaproject.databinding.ActivityAddCartBinding;
import com.avishkar.megaproject.helper.DatabaseHelper;
import com.avishkar.megaproject.models.ProductsModel;


import java.util.ArrayList;

public class AddCartActivity extends AppCompatActivity  {

   ImageButton button ;

   private RecyclerView addCartRv ;
   private ArrayList<ProductsModel> cartProductList;
    private DatabaseHelper databaseHelper;

    private TextView tvTotalPrice, tvTotalTax, tvFinalPrice;

    private ActivityAddCartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        init();

        binding.CartbackIb.setOnClickListener(v -> {
            finish();
        });

        loadCartProducts();
    }

    private void init() {
        databaseHelper = new DatabaseHelper(this);
        binding.addCartRv.setLayoutManager(new LinearLayoutManager(this));
    }

    public void loadCartProducts() {
        cartProductList = databaseHelper.getCartProducts();
        if (!cartProductList.isEmpty()) {
            CartAdapter adapter = new CartAdapter(this , cartProductList,this);
            binding.addCartRv.setAdapter(adapter);
            calculateTotal();
        }
        else {
            Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
        }

    }

    public void calculateTotal() {
        int sum = 0;
        int totalTax=0;
        for (ProductsModel model: cartProductList) {
            int basePrice = Integer.parseInt(model.getProductPrice());
            int unitPrice = basePrice;

            if (model.isDiscount()) {
                int discount = Global.calculateDiscount(basePrice,model.getProductDiscount());
                unitPrice = basePrice - discount;
            }
            int itemsTotal = unitPrice * model.getQuantity();
            sum = sum + itemsTotal;

            Toast.makeText(this, "tax:"+model.getTax(),Toast.LENGTH_SHORT).show();

            int itemTax = (unitPrice * model.getTax() * model.getQuantity()) / 100;
            totalTax  = totalTax + itemTax;

        }

        int finalAmount = sum + totalTax;
        binding.tvTotalPrice.setText("₹"+sum+".00");
        binding.tvTotalTax.setText("₹"+totalTax+".00");
        binding.tvFinalPrice.setText("₹"+finalAmount+".00");
       // Toast.makeText(this, ""+sum, Toast.LENGTH_SHORT).show();

    }





}