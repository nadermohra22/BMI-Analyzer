package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.android.saver;
import com.example.myapplication.android.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity2 extends AppCompatActivity {
    Button b1;
    TextView signUp;
    EditText email, password;
    ProgressBar p;
    FirebaseAuth fauth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        b1 = findViewById(R.id.login_b1);
        signUp = findViewById(R.id.siignUp_tv);
        p = findViewById(R.id.p);
        fauth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);
        firestore = FirebaseFirestore.getInstance();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity3.class);
                startActivity(intent);
                finish();
            }
        });
        b1.setOnClickListener(v -> {
            String em = email.getText().toString();
            String pass = password.getText().toString();

            if (TextUtils.isEmpty(em)) {
                email.setError("Email is Regesterd");
                return;

            }
            if (TextUtils.isEmpty(pass)) {

                password.setError("Email is Requierd");
                return;

            }

            if (pass.length() < 6) {

                password.setError("password must be at least 6 characters");
                return;
            }
            p.setVisibility(View.VISIBLE);

            fauth.signInWithEmailAndPassword(em, pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity2.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    saver.User = new user();
                    saver.User.setUsername(em);
                    startActivity(new Intent(getBaseContext(), list_status.class));
                } else {

                    Toast.makeText(MainActivity2.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        });
    }
}