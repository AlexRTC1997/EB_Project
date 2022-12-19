package com.example.eb_project.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "articles.db";

    public static final String ARTICLES_TABLE_NAME = "articles";
    public static final String MEASUREMENT_TABLE_NAME = "measurement_units";
    public static final String BRAND_TABLE_NAME = "brands";
    public static final String STATUS_TABLE_NAME = "status";

    private static final String STATUS_QUERY = "CREATE TABLE " + STATUS_TABLE_NAME + "(StaId TEXT NOT NULL, StaDes TEXT NOT NULL, StaSta TEXT NOT NULL, PRIMARY KEY(\"StaId\"))";
    private static final String BRAND_QUERY = "CREATE TABLE " + BRAND_TABLE_NAME + "(BraId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, BraNam TEXT NOT NULL, BraSta TEXT NOT NULL, FOREIGN KEY(\"BraSta\") REFERENCES \"Status\"(\"StaId\"))";
    private static final String MEASUREMENT_QUERY = "CREATE TABLE " + MEASUREMENT_TABLE_NAME + "(MeaUniId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, MeaUniNam TEXT NOT NULL, MeaUniSta TEXT NOT NULL, FOREIGN KEY(\"MeaUniSta\") REFERENCES \"Status\"(\"StaId\"))";
    private static final String ARTICLES_QUERY = "CREATE TABLE " + ARTICLES_TABLE_NAME + "(ArtId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ArtNam TEXT NOT NULL, ArtMeaUni INTEGER NOT NULL, ArtUniPri REAL NOT NULL, ArtBra INTEGER NOT NULL, ArtSta TEXT NOT NULL, FOREIGN KEY(\"ArtBra\") REFERENCES \"Brands\"(\"BraId\"), FOREIGN KEY(\"ArtSta\") REFERENCES \"Status\"(\"StaId\"), FOREIGN KEY(\"ArtMeaUni\") REFERENCES \"Measurement Units\"(\"MeaUniId\"))";

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(STATUS_QUERY);
        sqLiteDatabase.execSQL(BRAND_QUERY);
        sqLiteDatabase.execSQL(MEASUREMENT_QUERY);
        sqLiteDatabase.execSQL(ARTICLES_QUERY);

        sqLiteDatabase.execSQL("INSERT INTO " + STATUS_TABLE_NAME + " VALUES ('A', 'Activated', 'A')");
        sqLiteDatabase.execSQL("INSERT INTO " + STATUS_TABLE_NAME + " VALUES ('D', 'Disabled', 'A')");
        sqLiteDatabase.execSQL("INSERT INTO " + STATUS_TABLE_NAME + " VALUES ('*', 'Deleted', 'A')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + ARTICLES_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE " + MEASUREMENT_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE " + BRAND_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE " + STATUS_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
