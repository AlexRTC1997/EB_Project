package com.example.eb_project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.eb_project.entities.Status;

import java.util.ArrayList;

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

    // RETURN AN ARRAYLIST WITH STATUS FROM DB
    public ArrayList<Status> displayStatus() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Status> statusList = new ArrayList<>();
        Status status;
        Cursor statusCursor;

        statusCursor = db.rawQuery("SELECT * FROM " + STATUS_TABLE_NAME, null);

        if(statusCursor.moveToFirst()) {
            do {
                status = new Status();
                status.setId(statusCursor.getString(0));
                status.setDescription(statusCursor.getString(1));
                status.setStatus(statusCursor.getString(2));

                statusList.add(status);
            } while (statusCursor.moveToNext());
        }

        statusCursor.close();

        return statusList;
    }

    // RETURN AN SPECIFIC STATUS FROM DB
    public Status displayOneStatus(String id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Status status = null;
        Cursor statusCursor;

        statusCursor = db.rawQuery("SELECT * FROM " + STATUS_TABLE_NAME + " WHERE StaId = \"" + id + "\" LIMIT 1", null);

        if(statusCursor.moveToFirst()) {
                status = new Status();
                status.setId(statusCursor.getString(0));
                status.setDescription(statusCursor.getString(1));
                status.setStatus(statusCursor.getString(2));
        }

        statusCursor.close();

        return status;
    }

    // UPDATE Status in DB
    public boolean updateStatus(String statusId, String statusDescription, String statusStatus) {
        boolean ok = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + STATUS_TABLE_NAME + " SET StaId = '" + statusId + "', StaDes = '" + statusDescription + "', StaSta = '" + statusStatus + "' WHERE StaId = '" + statusId + "'" );
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
