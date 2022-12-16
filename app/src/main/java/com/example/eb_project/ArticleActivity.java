package com.example.eb_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ArticleActivity extends AppCompatActivity {

    FloatingActionButton fbaArticleStatusAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        fbaArticleStatusAdd = findViewById(R.id.fba_article_add);

        fbaArticleStatusAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArticleActivity.this, ArticleAddActivity.class);
                startActivity(intent);
            }
        });
    }
}