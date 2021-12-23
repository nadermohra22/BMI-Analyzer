package com.example.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.android.saver;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewRecodr extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.O)
   /* public double bmi(int kg, int cm, int age) {
        if (age>=2 && age<=10)
            return (kg / Math.pow((cm / 100), 2)) * 0.7;

        else
        if (age>10 && age<=20)
            return (kg / Math.pow((cm / 100), 2)) * 0.9;

        else
        if (age>=20)
            return (kg / Math.pow((cm / 100), 2)) * 1;


        return   (kg / Math.pow((cm / 100), 2)) * 1;

    }*/
    public  static double d;
    private FirebaseFirestore firestore;
    Button b;
    static final String newRecord = "newRecord";
    EditText date_text, time_text;
    String date = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
    String time;
    TextView cm_plus, cm_minus, kg_plus, kg_minus, kg_tv, cm_tv;
    int kg = 60, cm = 100;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recodr);
        firestore = FirebaseFirestore.getInstance();
        b = findViewById(R.id.saveDate_new_record);
        cm_plus = findViewById(R.id.new_record_cm_plus_tv);
        cm_minus = findViewById(R.id.new_record_cm_minus_tv);
        kg_plus = findViewById(R.id.new_record_kg_plus_tv);
        kg_minus = findViewById(R.id.new_record_kg_minus_tv);
        kg_tv = findViewById(R.id.new_record_kg_tv);
        cm_tv = findViewById(R.id.new_record_cm_tv);
        date_text = findViewById(R.id.new_record_date_et);
        time_text = findViewById(R.id.new_record_time_et);
        date = date_text.getText().toString();
        kg_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (kg == 0) {

                    kg = 0;
                } else {
                    kg = kg - 1;
                    kg_tv.setText(kg + "");
                }
            }
        });
        kg_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kg = kg + 1;
                kg_tv.setText(kg + "");
            }
        });
        cm_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cm == 0) {

                    cm = 0;
                } else {
                    cm = cm - 1;
                    cm_tv.setText(cm + "");
                }
            }
        });
        cm_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cm = cm + 1;
                cm_tv.setText(cm + "");
            }
        });

        
        b.setOnClickListener(v -> {
            DocumentReference state_ref = firestore.collection("user").document(saver.User.getId());
            Status s = new Status(date_text.getText().toString(), kg, "normal", cm);
            double bmi_value = MainActivity4.bmi_calc(kg, cm, saver.User.getAge());
            double bmi_oldValue = saver.User.getBmi();
            if (saver.User.getS() != null) {
                bmi_oldValue = saver.User.getBmi();
            }
            d = bmi_oldValue - bmi_value ;
            saver.User.setBmi(bmi_oldValue);
            saver.User.addStatus(s);


            state_ref.set(saver.User).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    Toast.makeText(NewRecodr.this, "created new record", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(NewRecodr.this, "faild to save", Toast.LENGTH_SHORT).show();
                }
            });
            finish();
        });


    }
}