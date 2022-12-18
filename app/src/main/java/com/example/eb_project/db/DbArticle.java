package com.example.eb_project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.eb_project.entities.Article;

import java.util.ArrayList;

public class DbArticle extends DbHelper {

    Context context;

    public DbArticle(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    // INSERT
    public long insertArticle(String articleName, int articleMeasurement, Double articlePrice, int articleBrand, String articleStatus) {
        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("ArtNam", articleName);
            values.put("ArtMeaUni", articleMeasurement);
            values.put("ArtUniPri", articlePrice);
            values.put("ArtBra", articleBrand);
            values.put("ArtSta", articleStatus);

            id = db.insert(ARTICLES_TABLE_NAME, null, values);
        } catch (Exception e) {
            e.toString();
        }

        return id;
    }

    // RETURN AN ARRAYLIST WITH ARTICLE FROM DB
    public ArrayList<Article> displayArticle() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Article> articleList = new ArrayList<>();
        Article article;
        Cursor articleCursor;

        articleCursor = db.rawQuery("SELECT * FROM " + ARTICLES_TABLE_NAME, null);

        if(articleCursor.moveToFirst()) {
            do {
                article = new Article();
                article.setId(articleCursor.getInt(0));
                article.setName(articleCursor.getString(1));
                article.setStatus(articleCursor.getString(5));

                articleList.add(article);
            } while (articleCursor.moveToNext());
        }

        articleCursor.close();

        return articleList;
    }

    // RETURN AN SPECIFIC ARTICLE FROM DB
    public Article displayOneArticle(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Article article = null;
        Cursor articleCursor;

        articleCursor = db.rawQuery("SELECT * FROM " + ARTICLES_TABLE_NAME + " WHERE ArtId = \"" + id + "\" LIMIT 1", null);

        if(articleCursor.moveToFirst()) {
            article = new Article();
            article.setId(Integer.parseInt(articleCursor.getString(0)));
            article.setName(articleCursor.getString(1));
            article.setMeasurement_unit(articleCursor.getInt(2));
            article.setPrice(articleCursor.getDouble(3));
            article.setBrand(articleCursor.getInt(4));
            article.setStatus(articleCursor.getString(5));
        }

        articleCursor.close();

        return article;
    }

    // UPDATE Article in DB
    public boolean updateArticle(int articleId, String articleName, int articleMeasurement, Double articlePrice, int articleBrand, String articleStatus) {
        boolean ok = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + ARTICLES_TABLE_NAME + " SET ArtNam = '" + articleName + "', ArtMeaUni = '" + articleMeasurement + "', ArtUniPri = '" + articlePrice + "', ArtBra = '" + articleBrand + "', ArtSta = '" + articleStatus + "' WHERE ArtId = '" + articleId + "'" );
            ok = true;
        } catch (Exception e) {
            e.toString();
            ok = false;
        } finally {
            db.close();
        }

        return ok;
    }
}
