package com.example.eb_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.eb_project.adapters.BrandListAdapter;
import com.example.eb_project.db.DbBrand;
import com.example.eb_project.entities.Brand;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class BrandActivity extends AppCompatActivity {

    FloatingActionButton fbaBrandStatusAdd;

    RecyclerView rvBrandList;
    ArrayList<Brand> brandList;

    BrandListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);

        // STATUS FORM
        fbaBrandStatusAdd = findViewById(R.id.fba_brand_add);

        // RECYCLER VIEW
        rvBrandList = findViewById(R.id.rv_brand_list);
        rvBrandList.setLayoutManager(new LinearLayoutManager(this));

        DbBrand dbBrand = new DbBrand(BrandActivity.this);

        brandList = new ArrayList<>();

        adapter = new BrandListAdapter(dbBrand.displayBrand());
        rvBrandList.setAdapter(adapter);


        fbaBrandStatusAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrandActivity.this, BrandAddActivity.class);
                startActivity(intent);
            }
        });
    }
}