package com.example.eb_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eb_project.adapters.StatusListAdapter;
import com.example.eb_project.db.DbStatus;
import com.example.eb_project.entities.Status;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class StatusActivity extends AppCompatActivity {


    FloatingActionButton fbaStatusAdd;

    RecyclerView rvStatusList;
    ArrayList<Status> statusList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        // STATUS FORM
        fbaStatusAdd = findViewById(R.id.fba_status_add);


        // RECYCLER VIEW
        rvStatusList = findViewById(R.id.rv_status_list);
        rvStatusList.setLayoutManager(new LinearLayoutManager(this));

        DbStatus dbStatus = new DbStatus(StatusActivity.this);

        statusList = new ArrayList<>();

        StatusListAdapter adapter = new StatusListAdapter(dbStatus.displayStatus());
        rvStatusList.setAdapter(adapter);

        // ADD ACTION
        fbaStatusAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatusActivity.this, StatusAddActivity.class);
                startActivity(intent);
            }
        });
    }


}