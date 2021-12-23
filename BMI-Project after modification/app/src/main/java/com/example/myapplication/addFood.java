package com.example.myapplication;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.android.saver;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

public class addFood extends AppCompatActivity {
    Spinner catagory_sp;
    ImageView food_img;
    EditText food_name, Food_cal;
    String catagoryItem, foodName;
    Button addFood_save, uploadImg;
    private FirebaseFirestore firestore;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private static final int requestCodePermission = 100;
    private static final int requestCodeImage = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        catagory_sp = findViewById(R.id.add_food_catagory_sp);
        ArrayAdapter<String> catagory_adapter = new ArrayAdapter<String>(addFood.this, android.R.layout.simple_expandable_list_item_1, getResources().getStringArray(R.array.catagory));
        catagory_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catagory_sp.setAdapter(catagory_adapter);
        catagoryItem = catagory_sp.getSelectedItem().toString();
        food_name = findViewById(R.id.add_food_foodname_et);
        Food_cal = findViewById(R.id.add_food_foodCal_et);
        addFood_save = findViewById(R.id.addFood_save);
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("images");
        food_img = (ImageView) findViewById(R.id.add_food_img);
        uploadImg = findViewById(R.id.addfood_upload);
        foodName = food_name.getText().toString();
       // int a = Integer.parseInt(Food_cal.getText().toString());

        uploadImg.setOnClickListener(v -> {
            getPermission();
        });


        //  int c=Integer.parseInt(Food_cal.getText().toString());
        addFood_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference food_ref = firestore.collection("user").document(saver.User.getId());
                foodInfo f = new foodInfo(food_name.getText().toString(), catagoryItem, Food_cal.getText().toString(), "img");
                saver.User.addFoodInfo(f);
                food_ref.set(saver.User).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(addFood.this, "created new record", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(addFood.this, "faild to save", Toast.LENGTH_SHORT).show();
                    }
                });
                finish();


            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                requestCode == requestCodePermission) {
            openGallery();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("requestCode :" + requestCode);
        System.out.println("resultCode : " + resultCode);
        System.out.println("data : " + data);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == requestCodeImage && data != null) {
            Uri uri = data.getData();
            storageReference.child(SystemClock.currentThreadTimeMillis() + ".jpg").putFile(uri).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    UploadTask.TaskSnapshot taskSnapshot = task.getResult();
                    if (taskSnapshot != null) {
                        System.out.println(Objects.requireNonNull(taskSnapshot.getUploadSessionUri()).toString());
                    }
                }
            });
            try {
                InputStream is = getContentResolver().openInputStream(uri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                food_img.setImageBitmap(BitmapFactory.decodeStream(is, null, options));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void getPermission() {
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE,
                                WRITE_EXTERNAL_STORAGE},
                        requestCodePermission);
            } else {
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(intent, requestCodeImage);
    }
}