package com.example.eb_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eb_project.db.DbMeasurement;
import com.example.eb_project.entities.Measurement;
import com.example.eb_project.entities.Status;

public class MeasurementUpdateActivity extends AppCompatActivity {

    EditText etMeasurementDetailsId, etMeasurementDetailsName, etMeasurementDetailsStatus;
    Button btnMeasurementDetailsSave, btnMeasurementDetailsUpdate, btnMeasurementDetailsDelete, btnMeasurementDetailsInactivate, btnMeasurementDetailsReactivate, btnMeasurementDetailsLDelete;
    Spinner spMeasurementDetailsStatus;

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
        spMeasurementDetailsStatus = findViewById(R.id.sp_measurement_details_status);

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

        DbMeasurement dbMeasurement = new DbMeasurement(MeasurementUpdateActivity.this);
        measurement = dbMeasurement.displayOneMeasurement(measurementId);

        // SPINNER
        ArrayAdapter<Status> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, MainActivity.statusList);
        spMeasurementDetailsStatus.setAdapter(arrayAdapter);

        String sId = measurement.getStatus();
        for(int i=0; i < MainActivity.statusList.size(); ++i) {
            String item = MainActivity.statusList.get(i).getId();

            if(sId.equals(item)) {
                spMeasurementDetailsStatus.setSelection(i);
                break;
            }
        }

        if(measurement != null) {
            etMeasurementDetailsId.setText(String.valueOf(measurement.getId()));
            etMeasurementDetailsName.setText(measurement.getName());
            etMeasurementDetailsStatus.setText(measurement.getStatus());

            etMeasurementDetailsStatus.setVisibility(View.INVISIBLE);
            etMeasurementDetailsId.setInputType(InputType.TYPE_NULL);
            btnMeasurementDetailsUpdate.setVisibility(View.INVISIBLE);
            btnMeasurementDetailsDelete.setVisibility(View.INVISIBLE);
            btnMeasurementDetailsInactivate.setVisibility(View.INVISIBLE);
            btnMeasurementDetailsReactivate.setVisibility(View.INVISIBLE);
            btnMeasurementDetailsLDelete.setVisibility(View.INVISIBLE);
        }

        // UPDATE
        btnMeasurementDetailsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etMeasurementDetailsId.getText().toString().equals("")) {
                    ok = dbMeasurement.updateMeasurement(Integer.parseInt(etMeasurementDetailsId.getText().toString()), etMeasurementDetailsName.getText().toString(), ((Status) spMeasurementDetailsStatus.getSelectedItem()).getId());

                    if(ok) {
                        Toast.makeText(MeasurementUpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        goToMeasurementDetails();
                    } else {
                        Toast.makeText(MeasurementUpdateActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MeasurementUpdateActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goToMeasurementDetails() {
        Intent intent = new Intent(this, MeasurementDetailsActivity.class);
        intent.putExtra("measurementId", measurementId);
        startActivity(intent);
    }
}