package com.example.eb_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BrandActivity extends AppCompatActivity {

    FloatingActionButton fbaBrandStatusAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);

        fbaBrandStatusAdd = findViewById(R.id.fba_brand_add);

        fbaBrandStatusAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrandActivity.this, BrandAddActivity.class);
                startActivity(intent);
            }
        });
    }
}