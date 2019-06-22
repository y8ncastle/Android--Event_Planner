package com.example.jeongyoonsung.eventplanner;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    public static int password[];
    static int password_bool = 0;
    int pwd, pwd1, pwd2, pwd3, pwd4;

    SQLiteDatabase db;
    String sql;
    String DBname = "EventPlanner";
    String tableName = "SETTINGS";
    Cursor resultset;
    int DBcount;

    protected void onCreate(Bundle savedInstanceState) {
        password = new int[] {0, 0, 0, 0};

        db = openOrCreateDatabase(DBname, MODE_PRIVATE, null);

        try {
            sql = "SELECT password, pa1, pa2, pa3, pa4 FROM " + tableName + ";";
            resultset = db.rawQuery(sql, null);
            DBcount = resultset.getCount();

            if (DBcount != 0) {
                resultset.moveToNext();
                pwd = resultset.getInt(0);
                pwd1 = resultset.getInt(1);
                pwd2 = resultset.getInt(2);
                pwd3 = resultset.getInt(3);
                pwd4 = resultset.getInt(4);

                password_bool = pwd;
                password[0] = pwd1;
                password[1] = pwd2;
                password[2] = pwd3;
                password[3] = pwd4;
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (password_bool == 0) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(this, PasswordCheck.class);
            startActivity(intent);
            finish();
        }
    }
}