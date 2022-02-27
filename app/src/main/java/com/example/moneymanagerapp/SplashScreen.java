package com.example.moneymanagerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH = 3000;
    Animation animation;
    private ImageView imageView;
    private TextView appName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_splashscreen);


        animation = AnimationUtils.loadAnimation(this, R.anim.animation);
        ColorDrawable colorDrawable=new ColorDrawable(Color.parseColor("#F5BD1F"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        imageView = findViewById(R.id.imageView);
        appName = findViewById(R.id.appName);

        imageView.setAnimation(animation);
        appName.setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH);
    }
}