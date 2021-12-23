package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.android.saver;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class food_list extends AppCompatActivity implements foodListAdapter.SelectItem {

    private RecyclerView recyclv;
    private foodListAdapter foodAdapter;
    private ArrayList<foodInfo> food_info;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        recyclv = findViewById(R.id.recycle_food_list);
        firestore = FirebaseFirestore.getInstance();
        foodAdapter = new foodListAdapter(this, this);
        foodAdapter.setF(saver.User.getF() != null ? saver.User.getF() : new ArrayList<>());
        recyclv.setLayoutManager(new LinearLayoutManager(this));
        recyclv.setAdapter(foodAdapter);

    }

    @Override
    public void item(int index, foodInfo foodInfo) {
        saver.index = index;
        saver.FOODINFO = foodInfo;
        startActivity(new Intent(this, aeditFood.class));
    }

    @Override
    public void deleteItem(int index) {
        saver.User.deleteFood(index);
        firestore.collection("user").document(saver.User.getId()).set(saver.User).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "deleted is ok !", Toast.LENGTH_SHORT).show();
                foodAdapter.setF(saver.User.getF());
            }

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, list_status.class));
    }
}