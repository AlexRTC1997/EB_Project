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

import com.example.eb_project.db.DbArticle;
import com.example.eb_project.db.DbBrand;
import com.example.eb_project.entities.Brand;
import com.example.eb_project.entities.Measurement;
import com.example.eb_project.entities.Status;

public class ArticleAddActivity extends AppCompatActivity {
    EditText etArticleName, etArticlePrice;
    Button btnArticleSave;
    Spinner spArticleStatus, spArticleBrand, spArticleMeasurement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_add);

        etArticleName = findViewById(R.id.et_article_details_name);
        spArticleMeasurement = findViewById(R.id.sp_article_details_measurement);
        etArticlePrice = findViewById(R.id.et_article_details_price);
        spArticleBrand = findViewById(R.id.sp_article_details_brand);
        spArticleStatus = findViewById(R.id.sp_article_details_status);

        btnArticleSave = findViewById(R.id.btn_article_details_save);

        btnArticleSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbArticle dbArticle = new DbArticle(ArticleAddActivity.this);
                long id = dbArticle.insertArticle(etArticleName.getText().toString(), ((Measurement) spArticleMeasurement.getSelectedItem()).getId(), Double.parseDouble(etArticlePrice.getText().toString()), ((Brand) spArticleBrand.getSelectedItem()).getId(), ((Status) spArticleStatus.getSelectedItem()).getId());

                if (id > 0) {
                    Toast.makeText(ArticleAddActivity.this, "Saved article", Toast.LENGTH_LONG).show();
                    cleanFields();
                    goToArticleActivity();
                } else {
                    Toast.makeText(ArticleAddActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });

        // SPINNER
        ArrayAdapter<Status> arrayStatusAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, MainActivity.statusList);
        spArticleStatus.setAdapter(arrayStatusAdapter);

        ArrayAdapter<Brand> arrayBrandAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, MainActivity.brandList);
        spArticleBrand.setAdapter(arrayBrandAdapter);

        ArrayAdapter<Measurement> arrayMeasurementAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, MainActivity.measurementList);
        spArticleMeasurement.setAdapter(arrayMeasurementAdapter);
    }

    private void cleanFields() {
        etArticleName.setText("");
        etArticlePrice.setText("");
    }

    private void goToArticleActivity() {
        Intent intent = new Intent(this, ArticleActivity.class);
        startActivity(intent);
    }
}