package com.danhenrico.firebasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.danhenrico.firebasecrud.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.textNewAccount.setOnClickListener(view -> {
            startActivity(new Intent(this, CadastroActivity.class));
        });

        binding.textForgetPWD.setOnClickListener(view -> {
            startActivity(new Intent(this, RecuperaActivity.class));
        });

        binding.btnLogin.setOnClickListener(v-> validaDados());

    }

    private void validaDados(){
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editPassword.getText().toString().trim();

        binding.progressBar.setVisibility();

        if(!email.isEmpty()){
            if (!senha.isEmpty()){

            }
            else{

            }
        }

    }

}