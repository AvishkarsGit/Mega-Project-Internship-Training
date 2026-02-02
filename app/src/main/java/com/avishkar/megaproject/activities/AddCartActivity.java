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
    private void loadCartProducts() {
        cartProductList = databaseHelper.getCartProducts();
        CartAdapter adapter = new CartAdapter(this , cartProductList);
        binding.addCartRv.setAdapter(adapter);
        calculateTotal();
    }

    private void calculateTotal() {
        int sum = 0;
        for (int i = 0;i<cartProductList.size();i++){
            int productPrice = Integer.parseInt(cartProductList.get(i).getProductPrice());
            int discount = cartProductList.get(i).getProductDiscount();
            int totalDiscount = Global.calculateDiscount(productPrice,discount);
            int total = productPrice - totalDiscount;
            sum = sum + total;
        }
        binding.tvTotalPrice.setText("₹"+sum+".00");
        //Toast.makeText(this, ""+sum, Toast.LENGTH_SHORT).show();

    }





}