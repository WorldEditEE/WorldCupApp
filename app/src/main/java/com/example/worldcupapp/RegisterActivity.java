package com.example.worldcupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressBar progressBar;
    private FirebaseFirestore db;
    private TextView textViewLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.editTextUserLoginReg);
        editTextPassword = findViewById(R.id.editTextPasswordReg);
        textViewLog = findViewById(R.id.textViewLoginFromReg);

        progressBar = findViewById(R.id.progressBarReg);
        db = FirebaseFirestore.getInstance();

        textViewLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public void RegisterAcc(View view) {


        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Введите ваш Email");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Некорректный Email !");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Введите пароль");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Пароль должен быть больше 5 символов !");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(email,"user");
                            db.collection("users").document(mAuth.getUid()).collection(email).add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if(task.isSuccessful()){
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(RegisterActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
                                        Intent intentSuccessReg = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intentSuccessReg);

                                    }else {
                                        Toast.makeText(RegisterActivity.this, "Ошибка при регистрации ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    }
                });


    }

}
