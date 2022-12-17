package com.example.eb_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.eb_project.adapters.ArticleListAdapter;
import com.example.eb_project.db.DbArticle;
import com.example.eb_project.entities.Article;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ArticleActivity extends AppCompatActivity {

    FloatingActionButton fbaArticleStatusAdd;

    RecyclerView rvArticleList;
    ArrayList<Article> articleList;

    ArticleListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        // STATUS FORM
        fbaArticleStatusAdd = findViewById(R.id.fba_article_add);

        // RECYCLER VIEW
        rvArticleList = findViewById(R.id.rv_article_list);
        rvArticleList.setLayoutManager(new LinearLayoutManager(this));

        DbArticle dbArticle = new DbArticle(ArticleActivity.this);

        articleList = new ArrayList<>();

        adapter = new ArticleListAdapter(dbArticle.displayArticle());
        rvArticleList.setAdapter(adapter);



        fbaArticleStatusAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArticleActivity.this, ArticleAddActivity.class);
                startActivity(intent);
            }
        });
    }
}