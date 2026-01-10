package com.avishkar.megaproject.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.avishkar.megaproject.R;
import com.avishkar.megaproject.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;

    private String name, username, phoneNumber, password;
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

        //handle click on signup button
        binding.btnSignup.setOnClickListener(v->{
            //validate the data
            if (validate()) {
                //database handling logic
                Toast.makeText(this, "Account created successfully...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Boolean validate() {
        name = binding.edtName.getText().toString().trim();
        username = binding.edtUsername.getText().toString().trim();
        phoneNumber = binding.edtPhoneNo.getText().toString().trim();
        password = binding.edtPassword.getText().toString().trim();

        if (name.isEmpty()){
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (username.isEmpty()) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
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


}