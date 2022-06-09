package com.danhenrico.firebasecrud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.danhenrico.firebasecrud.databinding.ActivityCadastroBinding;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        iniciaToolbar();

        binding.btnNewAccount.setOnClickListener(v-> validaDados());

    }

    private void validaDados(){
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editPassword.getText().toString().trim();
        String nome = binding.editName.getText().toString().trim();

        if(!nome.isEmpty()){
            if (!email.isEmpty()){
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.editEmail.setError("Preencha Corretamente");
                    binding.editEmail.requestFocus();
                }

                if (!senha.isEmpty()){
                    binding.progressBar.setVisibility(View.VISIBLE);
                    criarConta(email, senha);

                } else {
                    binding.editPassword.setError("Informe a senha");
                    binding.editPassword.requestFocus();
                }
            } else {
                binding.editEmail.setError("Informe o email");
                binding.editEmail.requestFocus();
            }
        } else {
            binding.editName.setError("Informe o nome");
            binding.editName.requestFocus();
        }

    }

    private void criarConta(String email, String senha) {

        mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                finish(); //precisa finalizar essa tela, encerrar
                startActivity(new Intent(this, LoginActivity.class));
            }
            else{
                binding.progressBar.setVisibility(View.GONE);
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.create();
                alert.setMessage("Erro ao criar conta");
            }
        });


    }

    private void iniciaToolbar(){
        Toolbar toolbar = binding.toolbar;
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

}