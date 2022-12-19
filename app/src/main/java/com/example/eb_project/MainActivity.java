package com.example.eb_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.eb_project.db.DbHelper;
import com.example.eb_project.db.DbStatus;
import com.example.eb_project.entities.Status;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // STATUS - SPINNER
    public static List<Status> statusList;

    Button btnStatus, btnBrand, btnMeasurement, btnArticle;

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
        btnBrand = findViewById(R.id.btn_main_brands);
        btnMeasurement = findViewById(R.id.btn_main_measurements);
        btnArticle = findViewById(R.id.btn_main_articles);

        // Fill Status fields - SPINNER
        statusList = fillStatusSpinner();

        // MENU ACTIONS
        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToStatusActivity(view);
            }
        });

        btnBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBrandActivity(view);
            }
        });

        btnMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMeasurementActivity(view);
            }
        });

        btnArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToArticleActivity(view);
            }
        });
    }

    // Intent
    public void goToStatusActivity(View view) {
        Intent intent = new Intent(this, StatusActivity.class);
        startActivity(intent);
    }

    public void goToBrandActivity(View view) {
        Intent intent = new Intent(this, BrandActivity.class);
        startActivity(intent);
    }

    public void goToMeasurementActivity(View view) {
        Intent intent = new Intent(this, MeasurementActivity.class);
        startActivity(intent);
    }

    public void goToArticleActivity(View view) {
        Intent intent = new Intent(this, ArticleActivity.class);
        startActivity(intent);
    }

     // SPINNER [6]
    @SuppressLint("Range")
    public List<Status> fillStatusSpinner() {
        List<Status> statusList = new ArrayList<>();
        DbStatus dbStatus = new DbStatus(MainActivity.this);
        Cursor cursor = dbStatus.displayStatusInSpinner();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Status status = new Status();
                    status.setId(cursor.getString(cursor.getColumnIndex("StaId")));
                    status.setDescription(cursor.getString(cursor.getColumnIndex("StaDes")));
                    statusList.add(status);
                } while (cursor.moveToNext());
            }
        }

        dbStatus.close();

        return statusList;
    }
}