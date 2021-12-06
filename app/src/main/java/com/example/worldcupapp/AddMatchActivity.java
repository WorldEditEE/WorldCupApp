package com.example.worldcupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddMatchActivity extends AppCompatActivity {

    private EditText editTextFirstMatch;
    private EditText editTextSecondMatch;
    private EditText editTextURLToImage;
    private EditText editTextTime;
    private EditText editTextStadium;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match);

        editTextFirstMatch = findViewById(R.id.editTextFirstTeam);
        editTextSecondMatch = findViewById(R.id.editTextSecondTeam);
        editTextURLToImage = findViewById(R.id.editTextUrlImage);
        editTextTime = findViewById(R.id.editTextTime);
        editTextStadium = findViewById(R.id.editTextStadium);

        db = FirebaseFirestore.getInstance();

    }

    public void addMatch(View view) {

        String firstTeam = editTextFirstMatch.getText().toString().trim();
        String secondTeam = editTextSecondMatch.getText().toString().trim();
        String urlToImage = editTextURLToImage.getText().toString().trim();
        String time = editTextTime.getText().toString().trim();
        String stadium = editTextStadium.getText().toString().trim();

        Match match = new Match(firstTeam,secondTeam,time,stadium,urlToImage);
        db.collection("Match").add(match);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}