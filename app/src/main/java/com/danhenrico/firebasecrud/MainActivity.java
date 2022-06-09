package com.danhenrico.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.danhenrico.firebasecrud.databinding.ActivityLoginBinding;
import com.danhenrico.firebasecrud.databinding.ActivityMainBinding;
import com.danhenrico.firebasecrud.model.Team;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

        binding.teamListView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Team removedTeam = teamList.get(i);

            teamList.clear();
            
            databaseReference.child("Team").child(removedTeam.getName()).removeValue();

            return false;
        });
    }

    private void initializeFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    private void showTeams() {
        Query query;

        query = databaseReference.child("Team").orderByChild("name");

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

        if (!teamName.isEmpty()) {
            Team team = new Team(teamName);

            databaseReference.child("Team").
                    child(team.getName()).
                    setValue(team);

            binding.editTextAddTeam.setText("");
            teamList.clear();
        }
    }
}