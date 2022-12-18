package com.example.eb_project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.eb_project.entities.Brand;

import java.util.ArrayList;

public class DbBrand extends DbHelper {

    Context context;

    public DbBrand(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    // INSERT
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

    // RETURN AN ARRAYLIST WITH BRAND FROM DB
    public ArrayList<Brand> displayBrand() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Brand> brandList = new ArrayList<>();
        Brand brand;
        Cursor brandCursor;

        brandCursor = db.rawQuery("SELECT * FROM " + BRAND_TABLE_NAME, null);

        if(brandCursor.moveToFirst()) {
            do {
                brand = new Brand();
                brand.setId(brandCursor.getInt(0));
                brand.setName(brandCursor.getString(1));
                brand.setStatus(brandCursor.getString(2));

                brandList.add(brand);
            } while (brandCursor.moveToNext());
        }

        brandCursor.close();

        return brandList;
    }

    // RETURN AN SPECIFIC BRAND FROM DB
    public Brand displayOneBrand(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Brand brand = null;
        Cursor brandCursor;

        brandCursor = db.rawQuery("SELECT * FROM " + BRAND_TABLE_NAME + " WHERE BraId = \"" + id + "\" LIMIT 1", null);

        if(brandCursor.moveToFirst()) {
            brand = new Brand();
            brand.setId(Integer.parseInt(brandCursor.getString(0)));
            brand.setName(brandCursor.getString(1));
            brand.setStatus(brandCursor.getString(2));
        }

        brandCursor.close();

        return brand;
    }

    // UPDATE Brand in DB
    public boolean updateBrand(int brandId, String brandName, String brandStatus) {
        boolean ok = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + BRAND_TABLE_NAME + " SET BraNam = '" + brandName + "', BraSta = '" + brandStatus + "' WHERE BraId = '" + brandId + "'" );
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
