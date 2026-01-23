package com.avishkar.megaproject.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.avishkar.megaproject.MainActivity;
import com.avishkar.megaproject.R;
import com.avishkar.megaproject.constants.Global;
import com.avishkar.megaproject.constants.Utils;
import com.avishkar.megaproject.databinding.ActivityLoginBinding;
import com.avishkar.megaproject.helper.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding ;

    private String email , password ;

    private DatabaseHelper helper;
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

        //initialize
        init();

        binding.btnLogin.setOnClickListener(v -> {
            if(validate()){
                boolean isLoggedIn = helper.login(email, password);
                if (isLoggedIn) {
                    Toast.makeText(this , "Login Successfully " , Toast.LENGTH_SHORT).show();
                    login();
                }else {
                    Toast.makeText(this, "Invalid credentials...", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.tvSignUp.setOnClickListener(v ->{
            Global.navigate(LoginActivity.this, SignupActivity.class);
        });

        binding.tvForgotPassword.setOnClickListener(v ->{
            Toast.makeText(this, "Forgot password screen coming soon!", Toast.LENGTH_SHORT).show();
        });
    }

    private Boolean validate(){

        email = binding.edtLoginEmail.getText().toString().trim();
        password = binding.edtLoginPassword.getText().toString().trim();

        if(email.isEmpty()){
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

    private void login() {
        SharedPreferences preferences = getSharedPreferences(Utils.SHARED_PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Utils.KEY_LOGIN,true); //i have wrote
        editor.apply(); //save data
        Global.navigate(LoginActivity.this, MainActivity.class);
        finish();
    }


    private void init() {
        helper = new DatabaseHelper(LoginActivity.this);
    }



}
