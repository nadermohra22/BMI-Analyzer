package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.android.saver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity3 extends AppCompatActivity {
    private Button b2;
    private TextView logIn, username, pass, name;
    private ProgressBar progressBar_regester;
    private FirebaseAuth fauth;
    private FirebaseFirestore fstore;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        logIn = findViewById(R.id.Login_tv_ac2);
        b2 = findViewById(R.id.b_creatacc);
        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        username = findViewById(R.id.username_regester);
        pass = findViewById(R.id.password_regester);
        name = findViewById(R.id.name_regester);
        progressBar_regester = findViewById(R.id.progressBar_regester);
//        if (fauth.getCurrentUser() != null) {
//
//            finish();
//
//        }
        logIn.setOnClickListener(v -> startActivity(new Intent(getBaseContext(), MainActivity2.class)));
        b2.setOnClickListener(v -> {
            String email = username.getText().toString();
            String password = pass.getText().toString();
            String fullname = name.getText().toString();


            if (TextUtils.isEmpty(email)) {

                username.setError("Email is Regesterd");
                return;

            }
            if (TextUtils.isEmpty(password)) {

                pass.setError("Email is Requierd");
                return;

            }

            if (password.length() < 6) {

                pass.setError("password must be at least 6 characters");
                return;
            }
            progressBar_regester.setVisibility(View.VISIBLE);
            fauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    Toast.makeText(MainActivity3.this, "user created", Toast.LENGTH_SHORT).show();

                    saver.User.setName(fullname);
                    saver.User.setPass_user(password);
                    saver.User.setUsername(email);
                    fstore.collection("user").add(saver.User)
                            .addOnSuccessListener(documentReference -> saver.User.setId(documentReference.getId()))
                            .addOnFailureListener(e -> Log.e("Regester", "onFailure: ", e));
                    startActivity(new Intent(this, MainActivity4.class));
                    finish();
                } else {
                    Toast.makeText(MainActivity3.this, "user created" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }

            });


        });

    }
}