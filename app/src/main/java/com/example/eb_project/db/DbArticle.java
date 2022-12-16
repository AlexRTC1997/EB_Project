package com.example.eb_project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbArticle extends DbHelper {

    Context context;

    public DbArticle(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertArticle(String articleName, String articleMeasurement, Double articlePrice, String articleBrand, String articleStatus) {
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
}
