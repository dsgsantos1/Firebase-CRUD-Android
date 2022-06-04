package com.danhenrico.firebasecrud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.danhenrico.firebasecrud.databinding.ActivityRecuperaBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperaActivity extends AppCompatActivity {

    private ActivityRecuperaBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecuperaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        mAuth = FirebaseAuth.getInstance();
        
        binding.btnResetAccount.setOnClickListener(view -> validaDados());
        
    }

    private void validaDados() {
        String email = binding.editEmail.getText().toString().trim();

        if(!email.isEmpty()){
            binding.progressBar2.setVisibility(View.VISIBLE);
            recuperaSenha(email);

        }
        else {
            binding.editEmail.setError("Informe o email");
            binding.editEmail.requestFocus();
        }
    }

    private void recuperaSenha(String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                binding.progressBar2.setVisibility(View.GONE);
                AlertDialog.Builder window = new AlertDialog.Builder(this);
                window.setTitle("Email");
                window.setMessage("Email de recuperação enviado");
                window.setPositiveButton("Ok", listener);
                window.show();
            }
            else{
                binding.progressBar2.setVisibility(View.GONE);

            }
        });

    }

    DialogInterface.OnClickListener listener = (dialogInterface, i) ->
            startActivity(new Intent(this, LoginActivity.class));
}