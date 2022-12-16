package com.example.eb_project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbBrand extends DbHelper {

    Context context;

    public DbBrand(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertBrand(String brandName, String brandStatus) {
        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("BraNam", brandName);
            values.put("BraSta", brandStatus);

            id = db.insert(BRAND_TABLE_NAME, null, values);
        } catch (Exception e) {
            e.toString();
        }

        return id;
    }
}
