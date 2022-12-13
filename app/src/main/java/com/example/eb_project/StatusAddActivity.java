package com.example.eb_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eb_project.db.DbStatus;

public class StatusAddActivity extends AppCompatActivity {
    EditText etStatusId, etStatusDescription, etStatusStatus;
    Button btnStatusSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_add);

        etStatusId = findViewById(R.id.ed_status_details_id);
        etStatusDescription = findViewById(R.id.et_status_details_description);
        etStatusStatus = findViewById(R.id.et_status_details_status);
        btnStatusSave = findViewById(R.id.btn_status_details_save);

        // SAVE ACTION
        btnStatusSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbStatus dbArticles = new DbStatus(StatusAddActivity.this);
                long id = dbArticles.insertStatus(etStatusId.getText().toString(), etStatusDescription.getText().toString(), etStatusStatus.getText().toString());

                if (id > 0) {
                    Toast.makeText(StatusAddActivity.this, "Saved status", Toast.LENGTH_LONG).show();
                    cleanFields();
                    goToStatusActivity();
                } else {
                    Toast.makeText(StatusAddActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void cleanFields() {
        etStatusId.setText("");
        etStatusDescription.setText("");
        etStatusStatus.setText("");
    }

    private void goToStatusActivity () {
        Intent intent = new Intent(this, StatusActivity.class);
        startActivity(intent);
    }
}