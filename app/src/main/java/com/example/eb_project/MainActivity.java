package com.example.eb_project;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.eb_project.db.DbHelper;

public class MainActivity extends AppCompatActivity {
    // [1]
    Button tempButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // [1]
        tempButton = findViewById(R.id.temp_button);

        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper dbHelper = new DbHelper(MainActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                if(db != null) {
                    Toast.makeText(MainActivity.this, "Database created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}