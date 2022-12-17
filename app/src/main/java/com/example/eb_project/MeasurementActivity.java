package com.example.eb_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.eb_project.adapters.MeasurementListAdapter;
import com.example.eb_project.db.DbMeasurement;
import com.example.eb_project.entities.Measurement;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MeasurementActivity extends AppCompatActivity {

    FloatingActionButton fbaMeasurementStatusAdd;

    RecyclerView rvMeasurementList;
    ArrayList<Measurement> measurementList;

    MeasurementListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);

        // STATUS FORM
        fbaMeasurementStatusAdd = findViewById(R.id.fba_measurement_add);

        // RECYCLER VIEW
        rvMeasurementList = findViewById(R.id.rv_measurement_list);
        rvMeasurementList.setLayoutManager(new LinearLayoutManager(this));

        DbMeasurement dbMeasurement = new DbMeasurement(MeasurementActivity.this);

        measurementList = new ArrayList<>();

        adapter = new MeasurementListAdapter(dbMeasurement.displayMeasurement());
        rvMeasurementList.setAdapter(adapter);


        fbaMeasurementStatusAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeasurementActivity.this, MeasurementAddActivity.class);
                startActivity(intent);
            }
        });
    }
}