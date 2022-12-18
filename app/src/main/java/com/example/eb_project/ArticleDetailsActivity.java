package com.example.eb_project;

import androidx.appcompat.app.AppCompatActivity;

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
            etArticleDetailsStatus.setInputType(InputType.TYPE_NULL);
        }
    }
}