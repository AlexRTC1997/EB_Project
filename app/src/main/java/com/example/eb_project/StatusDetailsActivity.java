package com.example.eb_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.eb_project.db.DbStatus;
import com.example.eb_project.entities.Status;

public class StatusDetailsActivity extends AppCompatActivity {
    EditText etStatusDetailsId, etStatusDetailsDescription, etStatusDetailsStatus;
    Button btnStatusDetailsSave;

    Status status;
    String statusId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_details);

        etStatusDetailsId = findViewById(R.id.ed_status_details_id);
        etStatusDetailsDescription = findViewById(R.id.et_status_details_description);
        etStatusDetailsStatus = findViewById(R.id.et_status_details_status);
        btnStatusDetailsSave = findViewById(R.id.btn_status_details_save);

        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            if(extras == null) {
                statusId = "";
            } else {
                statusId = extras.getString("statusId");
            }
        } else {
            statusId = (String) savedInstanceState.getSerializable("statusId");
        }

        DbStatus dbStatus = new DbStatus(StatusDetailsActivity.this);
        status = dbStatus.displayOneStatus(statusId);

        if(status != null) {
            etStatusDetailsId.setText(status.getId());
            etStatusDetailsDescription.setText(status.getDescription());
            etStatusDetailsStatus.setText(status.getStatus());

            btnStatusDetailsSave.setVisibility(View.INVISIBLE);
            etStatusDetailsId.setInputType(InputType.TYPE_NULL);
            etStatusDetailsDescription.setInputType(InputType.TYPE_NULL);
            etStatusDetailsStatus.setInputType(InputType.TYPE_NULL);
        }
    }
}