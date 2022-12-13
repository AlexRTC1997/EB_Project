package com.example.eb_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eb_project.db.DbStatus;
import com.example.eb_project.entities.Status;

public class StatusUpdateActivity extends AppCompatActivity {
    EditText etStatusDetailsId, etStatusDetailsDescription, etStatusDetailsStatus;
    Button btnStatusDetailsSave, btnStatusDetailsUpdate;

    Status status;
    String statusId;

    boolean ok = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_details);

        etStatusDetailsId = findViewById(R.id.ed_status_details_id);
        etStatusDetailsDescription = findViewById(R.id.et_status_details_description);
        etStatusDetailsStatus = findViewById(R.id.et_status_details_status);
        btnStatusDetailsSave = findViewById(R.id.btn_status_details_save);
        btnStatusDetailsUpdate = findViewById(R.id.btn_status_details_update);

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

        DbStatus dbStatus = new DbStatus(StatusUpdateActivity.this);
        status = dbStatus.displayOneStatus(statusId);

        if(status != null) {
            etStatusDetailsId.setText(status.getId());
            etStatusDetailsDescription.setText(status.getDescription());
            etStatusDetailsStatus.setText(status.getStatus());

            btnStatusDetailsUpdate.setVisibility(View.INVISIBLE);
        }

        btnStatusDetailsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etStatusDetailsId.getText().toString().equals("")) {
                    ok = dbStatus.updateStatus(etStatusDetailsId.getText().toString(), etStatusDetailsDescription.getText().toString(), etStatusDetailsStatus.getText().toString());
                    
                    if(ok) {
                        Toast.makeText(StatusUpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        goToStatusDetails();
                    } else {
                        Toast.makeText(StatusUpdateActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(StatusUpdateActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goToStatusDetails() {
        Intent intent = new Intent(this, StatusDetailsActivity.class);
        intent.putExtra("statusId", statusId);
        startActivity(intent);
    }
}