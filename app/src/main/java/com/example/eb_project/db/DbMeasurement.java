package com.example.eb_project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbMeasurement extends DbHelper {

    Context context;

    public DbMeasurement(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertMeasurement(String measurementName, String measurementStatus) {
        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("MeaUniNam", measurementName);
            values.put("MeaUniSta", measurementStatus);

            id = db.insert(MEASUREMENT_TABLE_NAME, null, values);
        } catch (Exception e) {
            e.toString();
        }

        return id;
    }
}
