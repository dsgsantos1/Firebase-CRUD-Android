package com.danhenrico.firebasecrud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

import com.danhenrico.firebasecrud.databinding.ActivityMainBinding;
import com.danhenrico.firebasecrud.databinding.ActivityUpdateTeamBinding;
import com.danhenrico.firebasecrud.model.Team;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateTeamActivity extends AppCompatActivity {

    private ActivityUpdateTeamBinding binding;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_team);

        initializeFirebase();

        binding = ActivityUpdateTeamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeToolbar();

        Intent thisIntent = getIntent();
        team = new Team(thisIntent.getStringExtra("teamName"), thisIntent.getStringExtra("teamId"));

        binding.editNewTeamName.setText(team.getName());

        binding.btnUpdate.setOnClickListener(view -> updateTeam());
        binding.btnDelete.setOnClickListener(view -> deleteTeam());
    }

    private void initializeFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void updateTeam() {
        String newTeamName = binding.editNewTeamName.getText().toString();

        if (!newTeamName.equals(team.getName()) && !newTeamName.trim().isEmpty()) {
            Team newTeam = new Team(newTeamName, team.getId());

            databaseReference.child("Team")
                    .child(team.getId())
                    .setValue(newTeam)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(this, MainActivity.class));
                        }
                    });
        }
    }

    private void deleteTeam() {
        databaseReference.child("Team").child(team.getId()).removeValue()
            .addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(this, MainActivity.class));
                }
            });
    }

    private void initializeToolbar() {
        Toolbar toolbar = binding.toolbarUpdate;
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

}