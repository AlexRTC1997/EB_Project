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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eb_project.db.DbArticle;
import com.example.eb_project.db.DbBrand;
import com.example.eb_project.db.DbMeasurement;
import com.example.eb_project.entities.Article;
import com.example.eb_project.entities.Brand;

public class ArticleDetailsActivity extends AppCompatActivity {

    EditText etArticleDetailsId, etArticleDetailsName, etArticleDetailsMeasurement, etArticleDetailsPrice, etArticleDetailsBrand, etArticleDetailsStatus;
    Button btnArticleDetailsSave, btnArticleDetailsUpdate, btnArticleDetailsDelete, btnArticleDetailsInactivate, btnArticleDetailsReactivate, btnArticleDetailsLDelete;
    Spinner spArticleDetailsStatus, spArticleDetailsBrand, spArticleDetailsMeasurement;

    Article article;
    int articleId;

    boolean ok = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        // DETAILS VIEW
        etArticleDetailsId = findViewById(R.id.ed_article_details_id);
        etArticleDetailsName = findViewById(R.id.ed_article_details_name);
        etArticleDetailsMeasurement = findViewById(R.id.ed_article_details_measurement);
        spArticleDetailsMeasurement = findViewById(R.id.sp_article_details_measurement);
        etArticleDetailsPrice = findViewById(R.id.ed_article_details_price);
        etArticleDetailsBrand = findViewById(R.id.ed_article_details_brand);
        spArticleDetailsBrand = findViewById(R.id.sp_article_details_brand);
        etArticleDetailsStatus = findViewById(R.id.et_article_details_status);
        spArticleDetailsStatus = findViewById(R.id.sp_article_details_status);

        btnArticleDetailsSave = findViewById(R.id.btn_article_details_save);
        btnArticleDetailsUpdate = findViewById(R.id.btn_article_details_update);
        btnArticleDetailsDelete = findViewById(R.id.btn_article_details_delete);
        btnArticleDetailsInactivate = findViewById(R.id.btn_article_details_inactivate);
        btnArticleDetailsReactivate = findViewById(R.id.btn_article_details_reactivate);
        btnArticleDetailsLDelete = findViewById(R.id.btn_article_details_logical_delete);

        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            if(extras == null) {
                articleId = 0;
            } else {
                articleId = extras.getInt("articleId");
            }
        } else {
            articleId = (int) savedInstanceState.getSerializable("articleId");
        }

        DbArticle dbArticle = new DbArticle(ArticleDetailsActivity.this);
        article = dbArticle.displayOneArticle(articleId);

        DbBrand dbBrand = new DbBrand(ArticleDetailsActivity.this);
        DbMeasurement dbMeasurement = new DbMeasurement(ArticleDetailsActivity.this);

        if(article != null) {
            etArticleDetailsId.setText(String.valueOf(article.getId()));
            etArticleDetailsName.setText(article.getName());
//            etArticleDetailsMeasurement.setText(String.valueOf(article.getMeasurement_unit()));
            etArticleDetailsMeasurement.setText(String.valueOf(dbMeasurement.displayOneMeasurement(article.getMeasurement_unit()).getName()));
            etArticleDetailsPrice.setText(String.valueOf(article.getPrice()));
//            etArticleDetailsBrand.setText(String.valueOf(article.getBrand()));
            etArticleDetailsBrand.setText(String.valueOf(dbBrand.displayOneBrand(article.getBrand()).getName()));
            etArticleDetailsStatus.setText(article.getStatus());

            spArticleDetailsStatus.setVisibility(View.INVISIBLE);
            spArticleDetailsBrand.setVisibility(View.INVISIBLE);
            spArticleDetailsMeasurement.setVisibility(View.INVISIBLE);
            btnArticleDetailsSave.setVisibility(View.INVISIBLE);
            etArticleDetailsId.setInputType(InputType.TYPE_NULL);
            etArticleDetailsName.setInputType(InputType.TYPE_NULL);
            etArticleDetailsMeasurement.setInputType(InputType.TYPE_NULL);
            etArticleDetailsPrice.setInputType(InputType.TYPE_NULL);
            etArticleDetailsBrand.setInputType(InputType.TYPE_NULL);
            etArticleDetailsStatus.setInputType(InputType.TYPE_NULL);

            btnArticleDetailsDelete.setVisibility(View.INVISIBLE);

            if (etArticleDetailsStatus.getText().toString().equals("D")) {
                btnArticleDetailsInactivate.setVisibility(View.INVISIBLE);
            } else if (etArticleDetailsStatus.getText().toString().equals("A")) {
                btnArticleDetailsReactivate.setVisibility(View.INVISIBLE);
            } else if (etArticleDetailsStatus.getText().toString().equals("*")) {
                btnArticleDetailsLDelete.setVisibility(View.INVISIBLE);
                btnArticleDetailsDelete.setVisibility(View.VISIBLE);
                btnArticleDetailsUpdate.setVisibility(View.INVISIBLE);
                btnArticleDetailsReactivate.setVisibility(View.INVISIBLE);
                btnArticleDetailsInactivate.setVisibility(View.INVISIBLE);
            }
        }

        // UPDATE LISTENER
        btnArticleDetailsUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArticleDetailsActivity.this, ArticleUpdateActivity.class);
                intent.putExtra("articleId", articleId);
                startActivity(intent);
            }
        });

        // DELETE LISTENER
        btnArticleDetailsDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ArticleDetailsActivity.this);
                builder.setMessage("The register will be deleted. Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(dbArticle.deleteArticle(articleId)) {
                                    goToArticleActivity();
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
        btnArticleDetailsInactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etArticleDetailsStatus.getText().toString().equals("D")) {
//                    ok = dbArticle.updateArticle(Integer.parseInt(etArticleDetailsId.getText().toString()), etArticleDetailsName.getText().toString(), Integer.parseInt(etArticleDetailsMeasurement.getText().toString()), Double.parseDouble(etArticleDetailsPrice.getText().toString()), Integer.parseInt(etArticleDetailsBrand.getText().toString()), "D");
                    ok = dbArticle.updateArticle(Integer.parseInt(etArticleDetailsId.getText().toString()), etArticleDetailsName.getText().toString(), article.getMeasurement_unit(), Double.parseDouble(etArticleDetailsPrice.getText().toString()), article.getBrand(), "D");

                    if (ok) {
                        Toast.makeText(ArticleDetailsActivity.this, "Inactivated", Toast.LENGTH_SHORT).show();
                        goToArticleDetailsActivity();
                    } else {
                        Toast.makeText(ArticleDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        // REACTIVATE LISTENER
        btnArticleDetailsReactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etArticleDetailsStatus.getText().toString().equals("A")) {
                    ok = dbArticle.updateArticle(Integer.parseInt(etArticleDetailsId.getText().toString()), etArticleDetailsName.getText().toString(), article.getMeasurement_unit(), Double.parseDouble(etArticleDetailsPrice.getText().toString()), article.getBrand(), "A");

                    if (ok) {
                        Toast.makeText(ArticleDetailsActivity.this, "Reactivated", Toast.LENGTH_SHORT).show();
                        goToArticleDetailsActivity();
                    } else {
                        Toast.makeText(ArticleDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        // LOGICAL DELETE LISTENER
        btnArticleDetailsLDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etArticleDetailsStatus.getText().toString().equals("*")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(ArticleDetailsActivity.this);
                    builder.setMessage("The register will be deleted. Are you sure?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (dbArticle.updateArticle(Integer.parseInt(etArticleDetailsId.getText().toString()), etArticleDetailsName.getText().toString(), article.getMeasurement_unit(), Double.parseDouble(etArticleDetailsPrice.getText().toString()), article.getBrand(), "*")) {
                                        goToArticleDetailsActivity();
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

    private void goToArticleActivity () {
        Intent intent = new Intent(this, ArticleActivity.class);
        startActivity(intent);
    }

    private void goToArticleDetailsActivity() {
        Intent intent = new Intent(this, ArticleDetailsActivity.class);
        intent.putExtra("articleId", articleId);
        startActivity(intent);
    }
}