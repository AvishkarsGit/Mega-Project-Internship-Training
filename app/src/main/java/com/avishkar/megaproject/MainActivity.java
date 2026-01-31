package com.avishkar.megaproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.avishkar.megaproject.activities.AddCartActivity;
import com.avishkar.megaproject.activities.AddProductActivity;
import com.avishkar.megaproject.activities.LoginActivity;
import com.avishkar.megaproject.adapters.CartAdapter;
import com.avishkar.megaproject.adapters.ProductsAdapter;
import com.avishkar.megaproject.constants.Global;
import com.avishkar.megaproject.constants.Utils;
import com.avishkar.megaproject.databinding.ActivityMainBinding;
import com.avishkar.megaproject.helper.DatabaseHelper;
import com.avishkar.megaproject.models.ProductsModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DatabaseHelper helper;

    private ProductsAdapter adapter ;

    private CartAdapter cartAdapter ;

    private String image;
    private ArrayList<ProductsModel> productsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //initialize
        init();

        binding.btnLogout.setOnClickListener(v-> {
            logout();
            Global.navigate(MainActivity.this, LoginActivity.class,true);
        });

        binding.btnNext.setOnClickListener(v -> {
            Global.navigate(MainActivity.this, AddProductActivity.class,false);
        });

        binding.btnCart.setOnClickListener(v -> {
            Global.navigate(MainActivity.this, AddCartActivity.class,false);
        });

        binding.searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                 ArrayList<ProductsModel> filterList =helper.searchProduct( s.toString());
                 adapter.updateList(filterList);

            }
        });
    }

    private void init() {
        helper = new DatabaseHelper(MainActivity.this);
        productsList = new ArrayList<>();
        binding.productsRv.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
    }

    private void logout() {
        SharedPreferences preferences = getSharedPreferences(Utils.SHARED_PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Utils.KEY_LOGIN,false);
        editor.apply();
    }

    private void fetchAllData() {
        productsList =  helper.getAllProducts();
        adapter= new ProductsAdapter(MainActivity.this,productsList);
        binding.productsRv.setAdapter(adapter);
        cartAdapter = new CartAdapter(MainActivity.this,productsList);
    }

    @Override
    protected void onStart() {
        super.onStart();

        fetchAllData();

    }
}