package com.example.eb_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eb_project.db.DbBrand;
import com.example.eb_project.entities.Brand;

public class BrandUpdateActivity extends AppCompatActivity {
    EditText etBrandDetailsId, etBrandDetailsName, etBrandDetailsStatus;
    Button btnBrandDetailsSave, btnBrandDetailsUpdate, btnBrandDetailsDelete;

    Brand brand;
    int brandId;

    boolean ok = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_details);

        // DETAILS VIEW
        etBrandDetailsId = findViewById(R.id.ed_brand_details_id);
        etBrandDetailsName = findViewById(R.id.et_brand_details_name);
        etBrandDetailsStatus = findViewById(R.id.et_brand_details_status);
        btnBrandDetailsSave = findViewById(R.id.btn_brand_details_save);

        btnBrandDetailsUpdate = findViewById(R.id.btn_brand_details_update);
        btnBrandDetailsDelete = findViewById(R.id.btn_brand_details_delete);

        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            if(extras == null) {
                brandId = 0;
            } else {
                brandId = extras.getInt("brandId");
            }
        } else {
            brandId = (int) savedInstanceState.getSerializable("brandId");
        }

        DbBrand dbBrand = new DbBrand(BrandUpdateActivity.this);
        brand = dbBrand.displayOneBrand(brandId);

        if(brand != null) {
            etBrandDetailsId.setText(String.valueOf(brand.getId()));
            etBrandDetailsName.setText(brand.getName());
            etBrandDetailsStatus.setText(brand.getStatus());

            etBrandDetailsId.setInputType(InputType.TYPE_NULL);
            btnBrandDetailsUpdate.setVisibility(View.INVISIBLE);
            btnBrandDetailsDelete.setVisibility(View.INVISIBLE);
        }

        // UPDATE
        btnBrandDetailsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etBrandDetailsId.getText().toString().equals("")) {
                    ok = dbBrand.updateBrand(Integer.parseInt(etBrandDetailsId.getText().toString()), etBrandDetailsName.getText().toString(), etBrandDetailsStatus.getText().toString());

                    if(ok) {
                        Toast.makeText(BrandUpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        goToBrandDetails();
                    } else {
                        Toast.makeText(BrandUpdateActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BrandUpdateActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goToBrandDetails() {
        Intent intent = new Intent(this, BrandDetailsActivity.class);
        intent.putExtra("brandId", brandId);
        startActivity(intent);
    }
}