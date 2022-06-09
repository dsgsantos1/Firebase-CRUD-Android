package com.danhenrico.firebasecrud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.danhenrico.firebasecrud.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.textNewAccount.setOnClickListener(view -> {
            startActivity(new Intent(this, CadastroActivity.class));
        });

        binding.textForgetPWD.setOnClickListener(view -> {
            startActivity(new Intent(this, RecuperaActivity.class));
        });

        binding.btnLogin.setOnClickListener(v -> validaDados());

    }

    private void validaDados(){
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editPassword.getText().toString().trim();

        binding.progressBar.setVisibility(View.VISIBLE);

        if(!email.isEmpty()){
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.editEmail.setError("Preencha Corretamente");
                binding.editEmail.requestFocus();
            }

            if (!senha.isEmpty()){
                binding.progressBar.setVisibility(View.GONE);
                login(email, senha);
            }
            else{
                binding.editPassword.setError("Campo Obrigatório");
                binding.editPassword.requestFocus();
            }
        }
        else {
            binding.editEmail.setError("Campo Obrigatório");
            binding.editEmail.requestFocus();
        }

    }

    private void login(String email, String senha) {

        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                startActivity(new Intent(this, MainActivity.class));
            }
            else {
                Toast.makeText(this, "Erro ao logar", Toast.LENGTH_LONG).show();

            }
        });

    }

//    DialogInterface.OnClickListener listener = (dialogInterface, i) -> startActivity(new Intent(this, LoginActivity.class));

}