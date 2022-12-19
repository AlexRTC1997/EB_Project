package com.example.eb_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eb_project.db.DbMeasurement;
import com.example.eb_project.entities.Status;

public class MeasurementAddActivity extends AppCompatActivity {

    EditText etMeasurementName;
    Button btnMeasurementSave;
    Spinner spMeasurementStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_add);

        etMeasurementName = findViewById(R.id.et_measurement_details_name);
        spMeasurementStatus = findViewById(R.id.sp_measurement_details_status);
        btnMeasurementSave = findViewById(R.id.btn_measurement_details_save);

        btnMeasurementSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbMeasurement dbMeasurement = new DbMeasurement(MeasurementAddActivity.this);
//                long id = dbMeasurement.insertMeasurement(etMeasurementName.getText().toString(), etMeasurementStatus.getText().toString());
                long id = dbMeasurement.insertMeasurement(etMeasurementName.getText().toString(), ((Status) spMeasurementStatus.getSelectedItem()).getId());

                if (id > 0) {
                    Toast.makeText(MeasurementAddActivity.this, "Saved measurement", Toast.LENGTH_LONG).show();
                    cleanFields();
                    goToMeasurementActivity();
                } else {
                    Toast.makeText(MeasurementAddActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });

        // SPINNER
        ArrayAdapter<Status> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, MainActivity.statusList);
        spMeasurementStatus.setAdapter(arrayAdapter);
    }

    private void cleanFields() {
        etMeasurementName.setText("");
    }

    private void goToMeasurementActivity() {
        Intent intent = new Intent(this, MeasurementActivity.class);
        startActivity(intent);
    }
}