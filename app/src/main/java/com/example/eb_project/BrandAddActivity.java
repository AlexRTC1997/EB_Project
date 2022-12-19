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
    EditText etBrandName, etBrandStatus;
    Button btnBrandSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_add);

        etBrandName = findViewById(R.id.et_brand_details_name);
        etBrandStatus = findViewById(R.id.et_brand_details_status);
        btnBrandSave = findViewById(R.id.btn_brand_details_save);

        // SAVE ACTION
        btnBrandSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbBrand dbBrand = new DbBrand(BrandAddActivity.this);
                long id = dbBrand.insertBrand(etBrandName.getText().toString(), etBrandStatus.getText().toString());

                if (id > 0) {
                    Toast.makeText(BrandAddActivity.this, "Saved brand", Toast.LENGTH_LONG).show();
                    cleanFields();
                    goToBrandActivity();
                } else {
                    Toast.makeText(BrandAddActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void cleanFields() {
        etBrandName.setText("");
        etBrandStatus.setText("");
    }

    private void goToBrandActivity() {
        Intent intent = new Intent(this, BrandActivity.class);
        startActivity(intent);
    }
}