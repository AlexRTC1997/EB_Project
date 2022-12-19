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

import com.example.eb_project.db.DbMeasurement;
import com.example.eb_project.entities.Measurement;

public class MeasurementDetailsActivity extends AppCompatActivity {

    EditText etMeasurementDetailsId, etMeasurementDetailsName, etMeasurementDetailsStatus;
    Button btnMeasurementDetailsSave, btnMeasurementDetailsUpdate, btnMeasurementDetailsDelete, btnMeasurementDetailsInactivate, btnMeasurementDetailsReactivate, btnMeasurementDetailsLDelete;

    Measurement measurement;
    int measurementId;

    boolean ok = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_details);

        // DETAILS VIEW
        etMeasurementDetailsId = findViewById(R.id.ed_measurement_details_id);
        etMeasurementDetailsName = findViewById(R.id.et_measurement_details_name);
        etMeasurementDetailsStatus = findViewById(R.id.et_measurement_details_status);
        btnMeasurementDetailsSave = findViewById(R.id.btn_measurement_details_save);

        btnMeasurementDetailsUpdate = findViewById(R.id.btn_measurement_details_update);
        btnMeasurementDetailsDelete = findViewById(R.id.btn_measurement_details_delete);
        btnMeasurementDetailsInactivate = findViewById(R.id.btn_measurement_details_inactivate);
        btnMeasurementDetailsReactivate = findViewById(R.id.btn_measurement_details_reactivate);
        btnMeasurementDetailsLDelete = findViewById(R.id.btn_measurement_details_logical_delete);

        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            if(extras == null) {
                measurementId = 0;
            } else {
                measurementId = extras.getInt("measurementId");
            }
        } else {
            measurementId = (int) savedInstanceState.getSerializable("measurementId");
        }

        DbMeasurement dbMeasurement = new DbMeasurement(MeasurementDetailsActivity.this);
        measurement = dbMeasurement.displayOneMeasurement(measurementId);

        if(measurement != null) {
            etMeasurementDetailsId.setText(String.valueOf(measurement.getId()));
            etMeasurementDetailsName.setText(measurement.getName());
            etMeasurementDetailsStatus.setText(measurement.getStatus());

            btnMeasurementDetailsSave.setVisibility(View.INVISIBLE);
            etMeasurementDetailsId.setInputType(InputType.TYPE_NULL);
            etMeasurementDetailsName.setInputType(InputType.TYPE_NULL);
            etMeasurementDetailsStatus.setInputType(InputType.TYPE_NULL);

            btnMeasurementDetailsDelete.setVisibility(View.INVISIBLE);

            if (etMeasurementDetailsStatus.getText().toString().equals("D")) {
                btnMeasurementDetailsInactivate.setVisibility(View.INVISIBLE);
            } else if (etMeasurementDetailsStatus.getText().toString().equals("A")) {
                btnMeasurementDetailsReactivate.setVisibility(View.INVISIBLE);
            } else if (etMeasurementDetailsStatus.getText().toString().equals("*")) {
                btnMeasurementDetailsLDelete.setVisibility(View.INVISIBLE);
                btnMeasurementDetailsDelete.setVisibility(View.VISIBLE);
                btnMeasurementDetailsUpdate.setVisibility(View.INVISIBLE);
                btnMeasurementDetailsReactivate.setVisibility(View.INVISIBLE);
                btnMeasurementDetailsInactivate.setVisibility(View.INVISIBLE);
            }
        }

        // UPDATE LISTENER
        btnMeasurementDetailsUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeasurementDetailsActivity.this, MeasurementUpdateActivity.class);
                intent.putExtra("measurementId", measurementId);
                startActivity(intent);
            }
        });

        // DELETE LISTENER
        btnMeasurementDetailsDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MeasurementDetailsActivity.this);
                builder.setMessage("The register will be deleted. Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(dbMeasurement.deleteMeasurement(measurementId)) {
                                    goToMeasurementActivity();
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
        btnMeasurementDetailsInactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etMeasurementDetailsStatus.getText().toString().equals("D")) {
                    ok = dbMeasurement.updateMeasurement(Integer.parseInt(etMeasurementDetailsId.getText().toString()), etMeasurementDetailsName.getText().toString(), "D");

                    if (ok) {
                        Toast.makeText(MeasurementDetailsActivity.this, "Inactivated", Toast.LENGTH_SHORT).show();
                        goToMeasurementDetailsActivity();
                    } else {
                        Toast.makeText(MeasurementDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        // REACTIVATE LISTENER
        btnMeasurementDetailsReactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etMeasurementDetailsStatus.getText().toString().equals("A")) {
                    ok = dbMeasurement.updateMeasurement(Integer.parseInt(etMeasurementDetailsId.getText().toString()), etMeasurementDetailsName.getText().toString(), "A");

                    if (ok) {
                        Toast.makeText(MeasurementDetailsActivity.this, "Reactivated", Toast.LENGTH_SHORT).show();
                        goToMeasurementDetailsActivity();
                    } else {
                        Toast.makeText(MeasurementDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // LOGICAL DELETE LISTENER
        btnMeasurementDetailsLDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etMeasurementDetailsStatus.getText().toString().equals("*")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MeasurementDetailsActivity.this);
                    builder.setMessage("The register will be deleted. Are you sure?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (dbMeasurement.updateMeasurement(Integer.parseInt(etMeasurementDetailsId.getText().toString()), etMeasurementDetailsName.getText().toString(), "*")) {
                                        goToMeasurementDetailsActivity();
                                    }
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).show();
                }
            }
        });

    }

    private void goToMeasurementActivity () {
        Intent intent = new Intent(this, MeasurementActivity.class);
        startActivity(intent);
    }

    private void goToMeasurementDetailsActivity() {
        Intent intent = new Intent(this, MeasurementDetailsActivity.class);
        intent.putExtra("measurementId", measurementId);
        startActivity(intent);
    }
}