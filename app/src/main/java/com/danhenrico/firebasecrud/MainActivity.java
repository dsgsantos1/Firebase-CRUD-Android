package com.danhenrico.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.danhenrico.firebasecrud.databinding.ActivityMainBinding;
import com.danhenrico.firebasecrud.model.Team;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<Team> teamList = new ArrayList<>();
    ArrayAdapter<Team> teamArrayAdapter;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeFirebase();

        showTeams();

        binding.addTeamButton.setOnClickListener(v -> addTeam());

        binding.teamListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Team team = teamList.get(i);
            teamList.clear();

            Intent intent = new Intent(MainActivity.this, UpdateTeamActivity.class);
            intent.putExtra("teamName", team.getName());
            intent.putExtra("teamId", team.getId());
            startActivity(intent);
        });
    }

    private void initializeFirebase() {
        if (firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();
        }
    }

    private void showTeams() {
        Query query;

        query = databaseReference.child("Team");

        teamList.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Team team = dataSnapshot.getValue(Team.class);
                    teamList.add(team);
                }

                teamArrayAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.simple_list_item_1, teamList);

                binding.teamListView.setAdapter(teamArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addTeam() {
        String teamName = binding.editTextAddTeam.getText().toString();

        if (!teamName.trim().isEmpty()) {
            String id = UUID.randomUUID().toString();

            Team team = new Team(teamName, id);

            databaseReference.child("Team")
                    .child(team.getId())
                    .setValue(team);

            binding.editTextAddTeam.setText("");
            teamList.clear();
        }
    }
}