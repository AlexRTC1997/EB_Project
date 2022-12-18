package com.example.eb_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.eb_project.db.DbMeasurement;
import com.example.eb_project.entities.Measurement;

public class MeasurementDetailsActivity extends AppCompatActivity {

    EditText etMeasurementDetailsId, etMeasurementDetailsName, etMeasurementDetailsStatus;
    Button btnMeasurementDetailsSave, btnMeasurementDetailsUpdate, btnMeasurementDetailsDelete;

    Measurement measurement;
    int measurementId;

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
        }
    }
}