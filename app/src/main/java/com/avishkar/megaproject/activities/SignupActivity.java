package com.avishkar.megaproject.activities;

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
import com.avishkar.megaproject.databinding.ActivitySignupBinding;
import com.avishkar.megaproject.helper.DatabaseHelper;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;

    private String name, email, phoneNumber, password;

    private DatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //initialize comps
        init();

        //handle click on signup button
        binding.btnSignUp.setOnClickListener(v->{
            //validate the data
            if (validate()) {
                //database handling logic
                boolean isRegistered = helper.register(name, email, password, phoneNumber);
                if (isRegistered){
                    Toast.makeText(this, "Account created successfully...", Toast.LENGTH_SHORT).show();
                    setLogin();
                }
                else {
                    Toast.makeText(this, "Failed to create account!...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.backIb.setOnClickListener(v ->{
            finish();
        });

    }

    private Boolean validate() {
        name = binding.edtName.getText().toString().trim();
        email = binding.edtEmail.getText().toString().trim();
        phoneNumber = binding.edtPhone.getText().toString().trim();
        password = binding.edtPassword.getText().toString().trim();

        if (name.isEmpty()){
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (email.isEmpty()) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (phoneNumber.isEmpty()) {
            Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (password.isEmpty()) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (password.length() < 8 && password.length() > 20) {
            Toast.makeText(this, "Password must be between 8-20 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    private void setLogin() {
        SharedPreferences preferences = getSharedPreferences(Utils.SHARED_PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Utils.KEY_LOGIN,true); //i have wrote
        editor.apply(); //save data
        Global.navigate(SignupActivity.this, MainActivity.class);
        finish();
    }

    private void init() {
        helper = new DatabaseHelper(SignupActivity.this);
    }

}