package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.android.saver;
import com.google.firebase.firestore.FirebaseFirestore;

public class aeditFood extends Activity {

    Spinner edit_catagory_sp;
    private FirebaseFirestore firestore;
    private EditText name, calory;
    private ImageView photo;
    private Button save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);
        edit_catagory_sp = findViewById(R.id.edit_food_catagory_sp);
        ArrayAdapter<String> catagory_adapter = new ArrayAdapter<String>(aeditFood.this, android.R.layout.simple_expandable_list_item_1, getResources().getStringArray(R.array.catagory));
        catagory_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edit_catagory_sp.setAdapter(catagory_adapter);
        name = findViewById(R.id.edit_food_foodname_et);
        calory = findViewById(R.id.edit_food_foodCal_et);
        photo = findViewById(R.id.edit_food_img);
        save = findViewById(R.id.editFood_save);
        firestore = FirebaseFirestore.getInstance();

        name.setText(saver.FOODINFO.getFoodName());
        calory.setText(saver.FOODINFO.getCal() + "");
        edit_catagory_sp.setSelection(getItem());
        save.setOnClickListener(v -> save());
    }

    private int getItem() {
        String[] list = getResources().getStringArray(R.array.catagory);
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(saver.FOODINFO.getFoodCatagory()))
                return i;
        }
        return 0;
    }

    private void save() {

        saver.FOODINFO.setFoodName(name.getText().toString());
        saver.FOODINFO.setFoodCatagory(edit_catagory_sp.getSelectedItem().toString());
        saver.FOODINFO.setCal(calory.getText().toString());
        //image
        saver.FOODINFO.setImg(uploadImage());

        saver.User.editFood(saver.index, saver.FOODINFO);
        firestore.collection("user").document(saver.User.getId()).set(saver.User).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "edit is successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, food_list.class));
                finish();
            }
        });
    }

    private String uploadImage() {
        return "";
    }
}
