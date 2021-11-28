package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity4 extends AppCompatActivity {
    Button b2_kginc, b1_kgdec ;
    Button b2_cminc, b1_cmdec ;
    Button save_data;
    TextView tv_kg, tv_cm;
    int kg,cm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        b1_kgdec=findViewById(R.id.b_kgdec);
        b2_kginc=findViewById(R.id.b2_kginc);
        b1_cmdec =findViewById(R.id.b1_cmdec);
        b2_cminc=findViewById(R.id.b2_cminc);
        tv_kg=findViewById(R.id.tv_kg);
        tv_cm=findViewById(R.id.tv_cm);
        save_data=findViewById(R.id.save_data);

        b1_kgdec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kg=kg-1;
                tv_kg.setText(kg+"");
            }
        });
        b2_kginc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kg=kg+1;
                tv_kg.setText(kg+"");
            }
        });
        b1_cmdec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cm=cm-1;
                tv_cm.setText(cm+"");
            }
        });
        b2_cminc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cm=cm+1;
                tv_cm.setText(cm+"");
            }
        });
        save_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),NewRecodr.class);
                startActivity(intent);

            }
        });
    }
}