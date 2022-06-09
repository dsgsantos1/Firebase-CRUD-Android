package com.danhenrico.firebasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Manda pra outra tela depois de x milisegundos
        new Handler(getMainLooper()).postDelayed(() -> {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }, 1000);
    }
}