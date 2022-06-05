package com.danhenrico.firebasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.danhenrico.firebasecrud.databinding.ActivityLoginBinding;
import com.danhenrico.firebasecrud.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

    }
}