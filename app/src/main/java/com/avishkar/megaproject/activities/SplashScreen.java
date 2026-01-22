package com.avishkar.megaproject.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.avishkar.megaproject.MainActivity;
import com.avishkar.megaproject.R;
import com.avishkar.megaproject.constants.Global;
import com.avishkar.megaproject.constants.Utils;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView logo = findViewById(R.id.logo);

        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rootate);
        logo.startAnimation(rotation);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (checkLoggedIn()) {
                Global.navigate(SplashScreen.this, MainActivity.class);
            }
            else {
                Global.navigate(SplashScreen.this, LoginActivity.class);
            }
            finish();
        }, 3000);
    }

    private Boolean checkLoggedIn() {
        SharedPreferences preferences = getSharedPreferences(Utils.SHARED_PREF_NAME,MODE_PRIVATE);
        return preferences.getBoolean(Utils.KEY_LOGIN,false);
    }
}