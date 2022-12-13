package com.example.eb_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.eb_project.db.DbHelper;

public class MainActivity extends AppCompatActivity {

    Button btnStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create DB
        DbHelper dbHelper = new DbHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        /* CREATE DATABASE MESSAGE
        if(db != null) {
            Toast.makeText(MainActivity.this, "Database created", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
        */

        btnStatus = findViewById(R.id.btn_main_status);

        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goStatusActivity(view);
            }
        });


    }

    // Intent
    public void goStatusActivity(View view) {
        Intent statusIntent = new Intent(this, StatusActivity.class);
        startActivity(statusIntent);
    }

}