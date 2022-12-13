package com.example.eb_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eb_project.db.DbStatus;

public class StatusActivity extends AppCompatActivity {

    EditText etStatusId, etStatusDescription, etStatusStatus;
    Button btnStatusSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        etStatusId = findViewById(R.id.et_status_id);
        etStatusDescription = findViewById(R.id.et_status_description);
        etStatusStatus = findViewById(R.id.et_status_status);
        btnStatusSave = findViewById(R.id.btn_status_save);

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
    }

    private void cleanFields() {
        etStatusId.setText("");
        etStatusDescription.setText("");
        etStatusStatus.setText("");
    }
}