package com.example.eb_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eb_project.adapters.StatusListAdapter;
import com.example.eb_project.db.DbStatus;
import com.example.eb_project.entities.Status;

import java.util.ArrayList;

public class StatusActivity extends AppCompatActivity {

    EditText etStatusId, etStatusDescription, etStatusStatus;
    Button btnStatusSave;

    RecyclerView rvStatusList;
    ArrayList<Status> statusList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        // STATUS FORM
        etStatusId = findViewById(R.id.ed_status_details_id);
        etStatusDescription = findViewById(R.id.et_status_details_description);
        etStatusStatus = findViewById(R.id.et_status_details_status);
        btnStatusSave = findViewById(R.id.btn_status_details_save);

        btnStatusSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbStatus dbArticles = new DbStatus(StatusActivity.this);
                long id = dbArticles.insertStatus(etStatusId.getText().toString(), etStatusDescription.getText().toString(), etStatusStatus.getText().toString());

                if (id > 0) {
                    Toast.makeText(StatusActivity.this, "Saved status", Toast.LENGTH_LONG).show();
                    cleanFields();
                } else {
                    Toast.makeText(StatusActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });

        // RECYCLER VIEW
        rvStatusList = findViewById(R.id.rv_status_list);
        rvStatusList.setLayoutManager(new LinearLayoutManager(this));

        DbStatus dbStatus = new DbStatus(StatusActivity.this);

        statusList = new ArrayList<>();

        StatusListAdapter adapter = new StatusListAdapter(dbStatus.displayStatus());
        rvStatusList.setAdapter(adapter);
    }

    private void cleanFields() {
        etStatusId.setText("");
        etStatusDescription.setText("");
        etStatusStatus.setText("");
    }
}