package com.example.eb_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.eb_project.db.DbBrand;
import com.example.eb_project.entities.Brand;

public class BrandDetailsActivity extends AppCompatActivity {
    EditText etBrandDetailsId, etBrandDetailsName, etBrandDetailsStatus;
    Button btnBrandDetailsSave, btnBrandDetailsUpdate, btnBrandDetailsDelete;

    Brand brand;
    int brandId;

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

        DbBrand dbBrand = new DbBrand(BrandDetailsActivity.this);
        brand = dbBrand.displayOneBrand(brandId);

        if(brand != null) {
            etBrandDetailsId.setText(String.valueOf(brand.getId()));
            etBrandDetailsName.setText(brand.getName());
            etBrandDetailsStatus.setText(brand.getStatus());

            btnBrandDetailsSave.setVisibility(View.INVISIBLE);
            etBrandDetailsId.setInputType(InputType.TYPE_NULL);
            etBrandDetailsName.setInputType(InputType.TYPE_NULL);
            etBrandDetailsStatus.setInputType(InputType.TYPE_NULL);
        }
    }
}