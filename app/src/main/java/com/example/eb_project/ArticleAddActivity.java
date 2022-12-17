package com.example.eb_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eb_project.db.DbArticle;
import com.example.eb_project.db.DbBrand;

public class ArticleAddActivity extends AppCompatActivity {
    EditText etArticleName, etArticleMeasurement, etArticlePrice, etArticleBrand, etArticleStatus;
    Button btnArticleSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_add);

        etArticleName = findViewById(R.id.et_article_details_name);
        etArticleMeasurement = findViewById(R.id.et_article_details_measurement);
        etArticlePrice = findViewById(R.id.et_article_details_price);
        etArticleBrand = findViewById(R.id.et_article_details_brand);
        etArticleStatus = findViewById(R.id.et_article_details_status);

        btnArticleSave = findViewById(R.id.btn_article_details_save);

        btnArticleSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbArticle dbArticle = new DbArticle(ArticleAddActivity.this);
                long id = dbArticle.insertArticle(etArticleName.getText().toString(), Integer.parseInt(etArticleMeasurement.getText().toString()), Double.parseDouble(etArticlePrice.getText().toString()), Integer.parseInt(etArticleBrand.getText().toString()), etArticleStatus.getText().toString());

                if (id > 0) {
                    Toast.makeText(ArticleAddActivity.this, "Saved article", Toast.LENGTH_LONG).show();
                    cleanFields();
                    goToArticleActivity();
                } else {
                    Toast.makeText(ArticleAddActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void cleanFields() {
        etArticleName.setText("");
        etArticleMeasurement.setText("");
        etArticlePrice.setText("");
        etArticleBrand.setText("");
        etArticleStatus.setText("");
    }

    private void goToArticleActivity() {
        Intent intent = new Intent(this, ArticleActivity.class);
        startActivity(intent);
    }
}