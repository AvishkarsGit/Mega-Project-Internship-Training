package com.avishkar.megaproject.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

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
import com.avishkar.megaproject.helper.DatabaseHelper;
import com.avishkar.megaproject.models.ProductsModel;

import java.util.ArrayList;

public class AddCartActivity extends AppCompatActivity {

   ImageButton button ;

   private RecyclerView addCartRv ;
   private ArrayList<ProductsModel> cartProductList;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addCartRv = findViewById(R.id.addCartRv);
        databaseHelper = new DatabaseHelper(this);
        addCartRv.setLayoutManager(new LinearLayoutManager(this));




        button = findViewById(R.id.CartbackIb);
        button.setOnClickListener(v -> {
            finish();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadCartProducts();
    }

    private void loadCartProducts() {

        cartProductList = databaseHelper.getCartProducts();

        CartAdapter adapter = new CartAdapter(this , cartProductList);
        addCartRv.setAdapter(adapter);
    }


}