package com.example.eb_project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.eb_project.entities.Measurement;

import java.util.ArrayList;

public class DbMeasurement extends DbHelper {

    Context context;

    public DbMeasurement(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    // INSERT
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

    // RETURN AN ARRAYLIST WITH MEASUREMENT FROM DB
    public ArrayList<Measurement> displayMeasurement() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Measurement> measurementList = new ArrayList<>();
        Measurement measurement;
        Cursor measurementCursor;

        measurementCursor = db.rawQuery("SELECT * FROM " + MEASUREMENT_TABLE_NAME, null);

        if(measurementCursor.moveToFirst()) {
            do {
                measurement = new Measurement();
                measurement.setId(measurementCursor.getInt(0));
                measurement.setName(measurementCursor.getString(1));
                measurement.setStatus(measurementCursor.getString(2));

                measurementList.add(measurement);
            } while (measurementCursor.moveToNext());
        }

        measurementCursor.close();

        return measurementList;
    }

    // RETURN AN SPECIFIC MEASUREMENT FROM DB
    public Measurement displayOneMeasurement(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Measurement measurement = null;
        Cursor measurementCursor;

        measurementCursor = db.rawQuery("SELECT * FROM " + MEASUREMENT_TABLE_NAME + " WHERE MeaUniId = \"" + id + "\" LIMIT 1", null);

        if(measurementCursor.moveToFirst()) {
            measurement = new Measurement();
            measurement.setId(Integer.parseInt(measurementCursor.getString(0)));
            measurement.setName(measurementCursor.getString(1));
            measurement.setStatus(measurementCursor.getString(2));
        }

        measurementCursor.close();

        return measurement;
    }

    // UPDATE Measurement in DB
    public boolean updateMeasurement(int measurementId, String measurementName, String measurementStatus) {
        boolean ok = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + MEASUREMENT_TABLE_NAME + " SET MeaUniNam = '" + measurementName + "', MeaUniSta = '" + measurementStatus + "' WHERE MeaUniId = '" + measurementId + "'" );
            ok = true;
        } catch (Exception e) {
            e.toString();
            ok = false;
        } finally {
            db.close();
        }

        return ok;
    }

    // DELETE Measurement in DB
    public boolean deleteMeasurement(int measurementId) {
        boolean ok = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + MEASUREMENT_TABLE_NAME + " WHERE MeaUniId = '" + measurementId + "'");
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
