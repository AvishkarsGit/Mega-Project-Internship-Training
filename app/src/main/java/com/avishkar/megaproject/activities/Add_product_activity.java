package com.avishkar.megaproject.activities;

import android.os.Bundle;
import android.renderscript.ScriptGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.avishkar.megaproject.MainActivity;
import com.avishkar.megaproject.R;
import com.avishkar.megaproject.constants.Global;
import com.avishkar.megaproject.databinding.ActivityAddProduct2Binding;
import com.avishkar.megaproject.databinding.ActivityAddProductBinding;

public class Add_product_activity extends AppCompatActivity {

    ActivityAddProduct2Binding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddProduct2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btnBackProduct.setOnClickListener(v -> {

            Global.navigate(this, MainActivity.class);
            finish();
        });


    }
}