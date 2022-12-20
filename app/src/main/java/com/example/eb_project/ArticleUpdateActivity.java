package com.example.eb_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eb_project.db.DbArticle;
import com.example.eb_project.entities.Article;
import com.example.eb_project.entities.Status;

public class ArticleUpdateActivity extends AppCompatActivity {

    EditText etArticleDetailsId, etArticleDetailsName, etArticleDetailsMeasurement, etArticleDetailsPrice, etArticleDetailsBrand, etArticleDetailsStatus;
    Button btnArticleDetailsSave, btnArticleDetailsUpdate, btnArticleDetailsDelete, btnArticleDetailsInactivate, btnArticleDetailsReactivate, btnArticleDetailsLDelete;
    Spinner spArticleDetailsStatus;


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
        etArticleDetailsPrice = findViewById(R.id.ed_article_details_price);
        etArticleDetailsBrand = findViewById(R.id.ed_article_details_brand);
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

        DbArticle dbArticle = new DbArticle(ArticleUpdateActivity.this);
        article = dbArticle.displayOneArticle(articleId);

        // SPINNER
        ArrayAdapter<Status> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, MainActivity.statusList);
        spArticleDetailsStatus.setAdapter(arrayAdapter);

        switch (article.getStatus()) {
            case "D": spArticleDetailsStatus.setSelection(1); break;
            case "*": spArticleDetailsStatus.setSelection(2); break;
            case "A":
            default: spArticleDetailsStatus.setSelection(0); break;
        }

        if(article != null) {
            etArticleDetailsId.setText(String.valueOf(article.getId()));
            etArticleDetailsName.setText(article.getName());
            etArticleDetailsMeasurement.setText(String.valueOf(article.getMeasurement_unit()));
            etArticleDetailsPrice.setText(String.valueOf(article.getPrice()));
            etArticleDetailsBrand.setText(String.valueOf(article.getBrand()));
            etArticleDetailsStatus.setText(article.getStatus());

            etArticleDetailsStatus.setVisibility(View.INVISIBLE);
            etArticleDetailsId.setInputType(InputType.TYPE_NULL);
            btnArticleDetailsUpdate.setVisibility(View.INVISIBLE);
            btnArticleDetailsDelete.setVisibility(View.INVISIBLE);
            btnArticleDetailsInactivate.setVisibility(View.INVISIBLE);
            btnArticleDetailsReactivate.setVisibility(View.INVISIBLE);
            btnArticleDetailsLDelete.setVisibility(View.INVISIBLE);
        }

        // UPDATE
        btnArticleDetailsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etArticleDetailsId.getText().toString().equals("")) {
                    ok = dbArticle.updateArticle(Integer.parseInt(etArticleDetailsId.getText().toString()), etArticleDetailsName.getText().toString(), Integer.parseInt(etArticleDetailsMeasurement.getText().toString()), Double.parseDouble(etArticleDetailsPrice.getText().toString()), Integer.parseInt(etArticleDetailsBrand.getText().toString()), ((Status) spArticleDetailsStatus.getSelectedItem()).getId());

                    if(ok) {
                        Toast.makeText(ArticleUpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        goToArticleDetails();
                    } else {
                        Toast.makeText(ArticleUpdateActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ArticleUpdateActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goToArticleDetails() {
        Intent intent = new Intent(this, ArticleDetailsActivity.class);
        intent.putExtra("articleId", articleId);
        startActivity(intent);
    }
}