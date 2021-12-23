package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.android.saver;
import com.example.myapplication.android.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class list_status extends AppCompatActivity {
    ListView status_list;
    TextView rate_status_tv, logout, tv_hi;
    Status sta;
    int age;
    int a, b;
    double bmi_val;
    private FirebaseFirestore firestore;
    final String LC="Little Change",SG="Still Good", GA="Go Ahead",BC ="Be Carful",SB = "So Bad";
    public String userts(char sta){

        if (sta =='U')
            return "Underweight";
        else if (sta =='H')
            return "Healthy Weight";
        else if (sta =='O')
            return "Overweight";
        else
            return "Obesity";

    }

    /* static double bmi(int  len,int wei,int age){

          double bmi_calculate ;
          int a,b;
          a=len*len;
          b=a/100;
          bmi_calculate=(wei/b)*age;
          return bmi_calculate;
      }*/

    final int REQ_COD_NEW_RECORD = 1;
    Button add_record_bt, add_food_bt, viewFood_bt;
    statusAdapter sAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_status);
        status_list = findViewById(R.id.status_list);
        add_record_bt = findViewById(R.id.add_record_bt);
        rate_status_tv = findViewById(R.id.statusRate_tv);
        viewFood_bt = findViewById(R.id.viewFood_bt);
        add_food_bt = findViewById(R.id.status__list_add_food_bt);
        logout = findViewById(R.id.logout);
        tv_hi = findViewById(R.id.tv_hi);
        firestore = FirebaseFirestore.getInstance();
        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            saver.User = new user();
            startActivity(new Intent(getBaseContext(), MainActivity2.class));
            finish();
        });


       char st= MainActivity4.userState(saver.User.getBmi());
        saver.User.setState(userts(st));
        sAdapter = new statusAdapter(this, R.layout.custom_status_layout);
        firestore.collection("user").whereEqualTo("username", saver.User.getUsername()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    saver.User = querySnapshot.toObjects(user.class).get(0);
                    sAdapter.setS(saver.User.getS());
                    tv_hi.setText("Hi, " + saver.User.getName());
                    double delta=NewRecodr.d;
                    switch (st){
                        case 'U':
                            if (delta<-1)
                                rate_status_tv.setText(SB);
                            else
                            if (-1<=delta && delta <-0.6)
                                rate_status_tv.setText(SB);
                            else
                            if (-0.6<delta && delta <-0.3)
                                rate_status_tv.setText(SB);
                            else
                            if (-0.3<=delta && delta <-0)
                                rate_status_tv.setText(LC);
                            else
                            if (0<=delta && delta <0.3)
                                rate_status_tv.setText(LC);
                            else
                            if (0.3<=delta && delta <-0.6)
                                rate_status_tv.setText(SG);
                            else
                            if (0.6<=delta && delta <1)
                                rate_status_tv.setText(GA);
                            else
                            if (delta>=1)
                                rate_status_tv.setText(GA);

                                break;
                        case 'H':

                            if (delta<-1)
                                rate_status_tv.setText(SB);
                            else
                            if (-1<=delta && delta<-0.6)
                                rate_status_tv.setText(BC);
                            else
                            if (-0.6<=delta && delta <-0.3)
                                rate_status_tv.setText(BC);
                            else
                            if (-0.3<=delta && delta<0)
                                rate_status_tv.setText(LC);
                            else
                            if (0<=delta && delta <0.3)
                                rate_status_tv.setText(LC);
                            else
                            if (0.3<=delta && delta <-0.6)
                                rate_status_tv.setText(BC);
                            else
                            if (0.6<=delta && delta <1)
                                rate_status_tv.setText(BC);
                            else
                            if (delta>=1)
                                rate_status_tv.setText(BC);

                            break;

                        case 'O':

                            if (delta<-1)
                                rate_status_tv.setText(BC);
                            else
                            if (-1<=delta && delta <-0.6)
                                rate_status_tv.setText(GA);
                            else
                            if (-0.6<=delta&& delta<-0.3)
                                rate_status_tv.setText(SG);
                            else
                            if (-0.3<=delta&& delta <-0)
                                rate_status_tv.setText(LC);
                            else
                            if (0<=delta&& delta <0.3)
                                rate_status_tv.setText(LC);
                            else
                            if (0.3<=delta && delta <-0.6)
                                rate_status_tv.setText(BC);
                            else
                            if (0.6<= delta && delta <1)
                                rate_status_tv.setText(SB);
                            else
                            if (delta>=1)
                                rate_status_tv.setText(SB);

                            break;
                        case 'b':
                        if (delta<-1)
                            rate_status_tv.setText(GA);
                        else
                        if (-1<delta && delta <-0.6)
                            rate_status_tv.setText(GA);
                        else
                        if (-0.6<delta && delta<-0.3)
                            rate_status_tv.setText(LC);
                        else
                        if (-0.3<=delta && delta <-0)
                            rate_status_tv.setText(LC);
                        else
                        if (0<=delta && delta <0.3)
                            rate_status_tv.setText(BC);
                        else
                        if (0.3<=delta && delta<-0.6)
                            rate_status_tv.setText(SB);
                        else
                        if (0.6<=delta && delta <1)
                            rate_status_tv.setText(SB);
                        else
                        if (delta>=1)
                            rate_status_tv.setText(SB);
                            break;


                    }

                }
            }
        });
        // bmi_c= sta.bmi(sta.getLength(),sta.getWeight(),age);
        status_list.setAdapter(sAdapter);
        //    bmi_c=bmi(a,b,age);


        viewFood_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), food_list.class);
                startActivity(intent);
            }
        });
        add_record_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NewRecodr.class);
                startActivity(intent);
            }
        });
        add_food_bt.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), addFood.class);
            startActivity(intent);
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
        sAdapter.setS(saver.User.getS() != null ? saver.User.getS() : new ArrayList<>());
        if (saver.User.getName() != null) {
            tv_hi.setText("Hi, " + saver.User.getName());
           // rate_status_tv.setText(saver.User.getBmi() + "");
        }
    }

}