package com.example.eb_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eb_project.db.DbStatus;
import com.example.eb_project.entities.Status;

public class StatusAddActivity extends AppCompatActivity {
    EditText etStatusId, etStatusDescription;
    Button btnStatusSave;
    Spinner spStatusStatus; // [3]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_add);

        etStatusId = findViewById(R.id.ed_status_details_id);
        etStatusDescription = findViewById(R.id.et_status_details_description);
        btnStatusSave = findViewById(R.id.btn_status_details_save);

        spStatusStatus = findViewById(R.id.sp_status_details_status);

        // SAVE ACTION
        btnStatusSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbStatus dbArticles = new DbStatus(StatusAddActivity.this);
//                long id = dbArticles.insertStatus(etStatusId.getText().toString(), etStatusDescription.getText().toString(), etStatusStatus.getText().toString());
                // SPINNER [4]
                long id = dbArticles.insertStatus(etStatusId.getText().toString(), etStatusDescription.getText().toString(), ((Status) spStatusStatus.getSelectedItem()).getId());

                if (id > 0) {
                    Toast.makeText(StatusAddActivity.this, "Saved status", Toast.LENGTH_LONG).show();
                    cleanFields();
                    goToStatusActivity();
                } else {
                    Toast.makeText(StatusAddActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });

        // SPINNER [5]
        ArrayAdapter<Status> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, MainActivity.statusList);
        spStatusStatus.setAdapter(arrayAdapter);

        spStatusStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String statusId = ((Status) adapterView.getSelectedItem()).getId();
                String statusDescription = ((Status) adapterView.getSelectedItem()).getDescription();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void cleanFields() {
        etStatusId.setText("");
        etStatusDescription.setText("");
    }

    private void goToStatusActivity() {
        Intent intent = new Intent(this, StatusActivity.class);
        startActivity(intent);
    }
}