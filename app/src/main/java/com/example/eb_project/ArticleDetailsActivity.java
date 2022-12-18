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

import com.example.eb_project.db.DbArticle;
import com.example.eb_project.entities.Article;

public class ArticleDetailsActivity extends AppCompatActivity {

    EditText etArticleDetailsId, etArticleDetailsName, etArticleDetailsMeasurement, etArticleDetailsPrice, etArticleDetailsBrand, etArticleDetailsStatus;
    Button btnArticleDetailsSave, btnArticleDetailsUpdate, btnArticleDetailsDelete;

    Article article;
    int articleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        // DETAILS VIEW
        etArticleDetailsId = findViewById(R.id.ed_article_details_id);
        etArticleDetailsName = findViewById(R.id.ed_article_details_name);
        etArticleDetailsMeasurement = findViewById(R.id.ed_article_details_measurement);
        etArticleDetailsPrice = findViewById(R.id.ed_article_details_price);
        etArticleDetailsBrand = findViewById(R.id.ed_article_details_brand);
        etArticleDetailsStatus = findViewById(R.id.et_article_details_status);

        btnArticleDetailsSave = findViewById(R.id.btn_article_details_save);

        btnArticleDetailsUpdate = findViewById(R.id.btn_article_details_update);
        btnArticleDetailsDelete = findViewById(R.id.btn_article_details_delete);

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

        if(article != null) {
            etArticleDetailsId.setText(String.valueOf(article.getId()));
            etArticleDetailsName.setText(article.getName());
            etArticleDetailsMeasurement.setText(String.valueOf(article.getMeasurement_unit()));
            etArticleDetailsPrice.setText(String.valueOf(article.getPrice()));
            etArticleDetailsBrand.setText(String.valueOf(article.getBrand()));
            etArticleDetailsStatus.setText(article.getStatus());

            btnArticleDetailsSave.setVisibility(View.INVISIBLE);
            etArticleDetailsId.setInputType(InputType.TYPE_NULL);
            etArticleDetailsName.setInputType(InputType.TYPE_NULL);
            etArticleDetailsMeasurement.setInputType(InputType.TYPE_NULL);
            etArticleDetailsPrice.setInputType(InputType.TYPE_NULL);
            etArticleDetailsBrand.setInputType(InputType.TYPE_NULL);
            etArticleDetailsStatus.setInputType(InputType.TYPE_NULL);
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


    }

    private void goToArticleActivity () {
        Intent intent = new Intent(this, ArticleActivity.class);
        startActivity(intent);
    }
}