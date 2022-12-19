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
import android.widget.Toast;

import com.example.eb_project.db.DbStatus;
import com.example.eb_project.entities.Status;

public class StatusDetailsActivity extends AppCompatActivity {
    EditText etStatusDetailsId, etStatusDetailsDescription, etStatusDetailsStatus;
    Button btnStatusDetailsSave, btnStatusDetailsUpdate, btnStatusDetailsDelete, btnStatusDetailsInactivate, btnStatusDetailsReactivate, btnStatusDetailsLDelete;

    Status status;
    String statusId;

    boolean ok = false;

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
        btnStatusDetailsInactivate = findViewById(R.id.btn_status_details_inactivate);
        btnStatusDetailsReactivate = findViewById(R.id.btn_status_details_reactivate);
        btnStatusDetailsLDelete = findViewById(R.id.btn_status_details_logical_delete);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            if (extras == null) {
                statusId = "";
            } else {
                statusId = extras.getString("statusId");
            }
        } else {
            statusId = (String) savedInstanceState.getSerializable("statusId");
        }

        DbStatus dbStatus = new DbStatus(StatusDetailsActivity.this);
        status = dbStatus.displayOneStatus(statusId);

        if (status != null) {
            etStatusDetailsId.setText(status.getId());
            etStatusDetailsDescription.setText(status.getDescription());
            etStatusDetailsStatus.setText(status.getStatus());

            btnStatusDetailsSave.setVisibility(View.INVISIBLE);
            etStatusDetailsId.setInputType(InputType.TYPE_NULL);
            etStatusDetailsDescription.setInputType(InputType.TYPE_NULL);
            etStatusDetailsStatus.setInputType(InputType.TYPE_NULL);

            btnStatusDetailsDelete.setVisibility(View.INVISIBLE);

            if (etStatusDetailsStatus.getText().toString().equals("D")) {
                btnStatusDetailsInactivate.setVisibility(View.INVISIBLE);
            } else if (etStatusDetailsStatus.getText().toString().equals("A")) {
                btnStatusDetailsReactivate.setVisibility(View.INVISIBLE);
            } else if (etStatusDetailsStatus.getText().toString().equals("*")) {
                btnStatusDetailsLDelete.setVisibility(View.INVISIBLE);
                btnStatusDetailsDelete.setVisibility(View.VISIBLE);
                btnStatusDetailsUpdate.setVisibility(View.INVISIBLE);
                btnStatusDetailsReactivate.setVisibility(View.INVISIBLE);
                btnStatusDetailsInactivate.setVisibility(View.INVISIBLE);
            }
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
                                if (dbStatus.deleteStatus(statusId)) {
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

        // INACTIVATE LISTENER
        btnStatusDetailsInactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etStatusDetailsStatus.getText().toString().equals("D")) {
                    ok = dbStatus.updateStatus(etStatusDetailsId.getText().toString(), etStatusDetailsDescription.getText().toString(), "D");

                    if (ok) {
                        Toast.makeText(StatusDetailsActivity.this, "Inactivated", Toast.LENGTH_SHORT).show();
                        goToStatusDetailsActivity();
                    } else {
                        Toast.makeText(StatusDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        // REACTIVATE LISTENER
        btnStatusDetailsReactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etStatusDetailsStatus.getText().toString().equals("A")) {
                    ok = dbStatus.updateStatus(etStatusDetailsId.getText().toString(), etStatusDetailsDescription.getText().toString(), "A");

                    if (ok) {
                        Toast.makeText(StatusDetailsActivity.this, "Reactivated", Toast.LENGTH_SHORT).show();
                        goToStatusDetailsActivity();
                    } else {
                        Toast.makeText(StatusDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // LOGICAL DELETE LISTENER
        btnStatusDetailsLDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etStatusDetailsStatus.getText().toString().equals("*")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(StatusDetailsActivity.this);
                    builder.setMessage("The register will be deleted. Are you sure?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (dbStatus.updateStatus(etStatusDetailsId.getText().toString(), etStatusDetailsDescription.getText().toString(), "*")) {
                                        goToStatusDetailsActivity();
                                    }
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).show();

//                    ok = dbStatus.updateStatus(etStatusDetailsId.getText().toString(), etStatusDetailsDescription.getText().toString(), "*");
//
//                    if (ok) {
//                        Toast.makeText(StatusDetailsActivity.this, "Logically deleted", Toast.LENGTH_SHORT).show();
//                        goToStatusDetailsActivity();
//                    } else {
//                        Toast.makeText(StatusDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });
    }

    private void goToStatusActivity() {
        Intent intent = new Intent(this, StatusActivity.class);
        startActivity(intent);
    }

    private void goToStatusDetailsActivity() {
        Intent intent = new Intent(this, StatusDetailsActivity.class);
        intent.putExtra("statusId", statusId);
        startActivity(intent);
    }
}