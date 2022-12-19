package com.example.eb_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eb_project.db.DbBrand;
import com.example.eb_project.entities.Status;

import java.util.List;

public class BrandAddActivity extends AppCompatActivity {
    EditText etBrandName;
    Button btnBrandSave;
    Spinner spBrandStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_add);

        etBrandName = findViewById(R.id.et_brand_details_name);
        spBrandStatus = findViewById(R.id.sp_brand_details_status);
        btnBrandSave = findViewById(R.id.btn_brand_details_save);

        // SAVE ACTION
        btnBrandSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbBrand dbBrand = new DbBrand(BrandAddActivity.this);
                long id = dbBrand.insertBrand(etBrandName.getText().toString(), ((Status) spBrandStatus.getSelectedItem()).getId());

                if (id > 0) {
                    Toast.makeText(BrandAddActivity.this, "Saved brand", Toast.LENGTH_LONG).show();
                    cleanFields();
                    goToBrandActivity();
                } else {
                    Toast.makeText(BrandAddActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });

        // SPINNER
        ArrayAdapter<Status> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, MainActivity.statusList);
        spBrandStatus.setAdapter(arrayAdapter);
    }

    private void cleanFields() {
        etBrandName.setText("");
    }

    private void goToBrandActivity() {
        Intent intent = new Intent(this, BrandActivity.class);
        startActivity(intent);
    }
}