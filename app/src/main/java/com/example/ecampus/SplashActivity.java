package com.example.ecampus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    int time = 3500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        GifImageView gifImageView = findViewById(R.id.img_gif);
        gifImageView.setGifImageResource(R.drawable.netflix);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_APP", MODE_PRIVATE);

        String strEmail = sharedPreferences.getString("Email", "");
        String strPass = sharedPreferences.getString("Password", "");



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (strEmail.equals("") && strPass.equals("")){
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Intent intent = new Intent(SplashActivity.this, DashBoardActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, time);
    }
}