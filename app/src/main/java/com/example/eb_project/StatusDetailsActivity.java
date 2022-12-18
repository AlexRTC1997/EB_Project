package com.example.eb_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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
    Button btnStatusDetailsUpdate, btnStatusDetailsDelete;

    Status status;
    String statusId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_details);

        // DETAILS VIEW
        etStatusDetailsId = findViewById(R.id.ed_status_details_id);
        etStatusDetailsDescription = findViewById(R.id.et_status_details_description);
        etStatusDetailsStatus = findViewById(R.id.et_status_details_status);
        btnStatusDetailsSave = findViewById(R.id.btn_status_details_save);

        btnStatusDetailsUpdate = findViewById(R.id.btn_status_details_update);
        btnStatusDetailsDelete = findViewById(R.id.btn_status_details_delete);

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

        // UPDATE LISTENER
        btnStatusDetailsUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatusDetailsActivity.this, StatusUpdateActivity.class);
                intent.putExtra("statusId", statusId);
                startActivity(intent);
            }
        });

        // DELETE LISTENER
        btnStatusDetailsDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StatusDetailsActivity.this);
                builder.setMessage("The register will be deleted. Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(dbStatus.deleteStatus(statusId)) {
                                    goToStatusActivity();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });
    }

    private void goToStatusActivity () {
        Intent intent = new Intent(this, StatusActivity.class);
        startActivity(intent);
    }
}