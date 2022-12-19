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

import com.example.eb_project.db.DbBrand;
import com.example.eb_project.entities.Brand;

public class BrandDetailsActivity extends AppCompatActivity {
    EditText etBrandDetailsId, etBrandDetailsName, etBrandDetailsStatus;
    Button btnBrandDetailsSave, btnBrandDetailsUpdate, btnBrandDetailsDelete, btnBrandDetailsInactivate, btnBrandDetailsReactivate, btnBrandDetailsLDelete;

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
        btnBrandDetailsInactivate = findViewById(R.id.btn_brand_details_inactivate);
        btnBrandDetailsReactivate = findViewById(R.id.btn_brand_details_reactivate);
        btnBrandDetailsLDelete = findViewById(R.id.btn_brand_details_logical_delete);

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

            btnBrandDetailsDelete.setVisibility(View.INVISIBLE);

            if (etBrandDetailsStatus.getText().toString().equals("D")) {
                btnBrandDetailsInactivate.setVisibility(View.INVISIBLE);
            } else if (etBrandDetailsStatus.getText().toString().equals("A")) {
                btnBrandDetailsReactivate.setVisibility(View.INVISIBLE);
            } else if (etBrandDetailsStatus.getText().toString().equals("*")) {
                btnBrandDetailsLDelete.setVisibility(View.INVISIBLE);
                btnBrandDetailsDelete.setVisibility(View.VISIBLE);
                btnBrandDetailsUpdate.setVisibility(View.INVISIBLE);
                btnBrandDetailsReactivate.setVisibility(View.INVISIBLE);
                btnBrandDetailsInactivate.setVisibility(View.INVISIBLE);
            }
        }

        // UPDATE LISTENER
        btnBrandDetailsUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrandDetailsActivity.this, BrandUpdateActivity.class);
                intent.putExtra("brandId", brandId);
                startActivity(intent);
            }
        });

        // DELETE LISTENER
        btnBrandDetailsDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BrandDetailsActivity.this);
                builder.setMessage("The register will be deleted. Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(dbBrand.deleteBrand(brandId)) {
                                    goToBrandActivity();
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
        btnBrandDetailsInactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etBrandDetailsStatus.getText().toString().equals("D")) {
                    ok = dbBrand.updateBrand(Integer.parseInt(etBrandDetailsId.getText().toString()), etBrandDetailsName.getText().toString(), "D");

                    if (ok) {
                        Toast.makeText(BrandDetailsActivity.this, "Inactivated", Toast.LENGTH_SHORT).show();
                        goToBrandDetailsActivity();
                    } else {
                        Toast.makeText(BrandDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        // REACTIVATE LISTENER
        btnBrandDetailsReactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etBrandDetailsStatus.getText().toString().equals("A")) {
                    ok = dbBrand.updateBrand(Integer.parseInt(etBrandDetailsId.getText().toString()), etBrandDetailsName.getText().toString(), "A");

                    if (ok) {
                        Toast.makeText(BrandDetailsActivity.this, "Reactivated", Toast.LENGTH_SHORT).show();
                        goToBrandDetailsActivity();
                    } else {
                        Toast.makeText(BrandDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // LOGICAL DELETE LISTENER
        btnBrandDetailsLDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etBrandDetailsStatus.getText().toString().equals("*")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(BrandDetailsActivity.this);
                    builder.setMessage("The register will be deleted. Are you sure?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (dbBrand.updateBrand(Integer.parseInt(etBrandDetailsId.getText().toString()), etBrandDetailsName.getText().toString(), "*")) {
                                        goToBrandDetailsActivity();
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

    private void goToBrandActivity () {
        Intent intent = new Intent(this, BrandActivity.class);
        startActivity(intent);
    }


    private void goToBrandDetailsActivity() {
        Intent intent = new Intent(this, BrandDetailsActivity.class);
        intent.putExtra("brandId", brandId);
        startActivity(intent);
    }
}