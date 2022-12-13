package com.example.eb_project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbStatus extends DbHelper {

    Context context;

    public DbStatus(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertStatus(String statusId, String statusDescription, String statusStatus) {
        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("StaId", statusId);
            values.put("StaDes", statusDescription);
            values.put("StaSta", statusStatus);

            id = db.insert(STATUS_TABLE_NAME, null, values);
        } catch (Exception e) {
            e.toString();
        }

        return id;
    }
}
