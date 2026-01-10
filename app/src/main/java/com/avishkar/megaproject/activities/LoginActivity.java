package com.avishkar.megaproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.avishkar.megaproject.R;
import com.avishkar.megaproject.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding ;

    private String username , password ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        binding.btnLogin.setOnClickListener(v -> {
            if(validte()){

                Toast.makeText(this , "Login Successfully " , Toast.LENGTH_SHORT).show();

            }
        });

        binding.tvSignUp.setOnClickListener(v ->{
            Intent i = new Intent(LoginActivity.this , SignupActivity.class);
            startActivity(i);
        });

        binding.tvForgotPassword.setOnClickListener(v ->{
            Toast.makeText(this, "Forgot password screen coming soon!", Toast.LENGTH_SHORT).show();
        });
    }

    private Boolean validte(){

        username = binding.edtLoginUsername.getText().toString().trim();
        password = binding.edtLoginPassword.getText().toString().trim();

        if(username.isEmpty()){
            Toast.makeText(this, "Please Enter your Username " , Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty()){
            Toast.makeText(this, "Please enter your Password" , Toast.LENGTH_SHORT).show();
            return false ;
        }

        else {
            return true;
        }

    }

}
