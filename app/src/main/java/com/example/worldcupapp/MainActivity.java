package com.example.worldcupapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FloatingActionButton actionButton;
    private FirebaseFirestore db;
    private List<Match> matches = new ArrayList<>();
    private RecyclerView recyclerView;
    private MatchAdapter adapter;
    private FirebaseUser user;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        mAuth = FirebaseAuth.getInstance();
        actionButton = findViewById(R.id.floatingButtonAddNews);

        recyclerView = findViewById(R.id.recyclerViewHome);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MatchAdapter(matches, this, new MatchAdapter.onMatchListener() {
            @Override
            public void onMatchClick(Match match) {

            }
        });

        recyclerView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        initRoleModel(this);
        initFloatingButton(this);

    }

    private void initRoleModel(Context context) {

        if(user != null){
            db.collection("users").document(user.getUid()).collection(user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()){
                            role = document.getString("role");
                            if (role != null && role.equals("user")) {
                                actionButton.setVisibility(View.GONE);
                            }else if(role != null && role.equals("admin")){
                                actionButton.setVisibility(View.VISIBLE);
                            }else {
                                actionButton.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });
        }else {
            actionButton.setVisibility(View.GONE);
        }

    }

    public void initFloatingButton(Context context){

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), AddMatchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        db.collection("Match").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value != null){
                    List<Match> matchList = value.toObjects(Match.class);
                    adapter.setNews(matchList);
                }
            }
        });

    }

    public void OpenProfile(View view) {

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            Intent intent = new Intent(this, UserProfile.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }
}