package com.example.jeongyoonsung.eventplanner;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class NewPassword extends Activity {
    SQLiteDatabase db;
    String sql, sql2, sql3, sql4, sql5;
    String DBname = "EventPlanner";
    String tableName = "SETTINGS";

    public int location;
    int password_1[];
    int password_2[];
    boolean before;
    ImageView password_status;
    ImageView password_notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_password);

        location = 0;
        before = true;
        password_1 = new int[] {0, 0, 0, 0, 0};
        password_2 = new int[] {0, 0, 0, 0, 0};

        password_status = (ImageView) findViewById(R.id.password_status);
        password_notice = (ImageView) findViewById(R.id.password_notice);

        Button num1 = (Button) findViewById(R.id.password_num1);
        num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (before == true) {
                    password_1[location] = 1;
                    location++;
                    checkLocation();
                }
                else if (before == false) {
                    password_2[location] = 1;
                    location++;
                    checkLocation();
                }
            }
        });

        Button num2 = (Button) findViewById(R.id.password_num2);
        num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (before == true) {
                    password_1[location] = 2;
                    location++;
                    checkLocation();
                }
                else if (before == false) {
                    password_2[location] = 2;
                    location++;
                    checkLocation();
                }
            }
        });

        Button num3 = (Button) findViewById(R.id.password_num3);
        num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (before == true) {
                    password_1[location] = 3;
                    location++;
                    checkLocation();
                }
                else if (before == false) {
                    password_2[location] = 3;
                    location++;
                    checkLocation();
                }
            }
        });

        Button num4 = (Button) findViewById(R.id.password_num4);
        num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (before == true) {
                    password_1[location] = 4;
                    location++;
                    checkLocation();
                }
                else if (before == false) {
                    password_2[location] = 4;
                    location++;
                    checkLocation();
                }
            }
        });

        Button num5 = (Button) findViewById(R.id.password_num5);
        num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (before == true) {
                    password_1[location] = 5;
                    location++;
                    checkLocation();
                }
                else if (before == false) {
                    password_2[location] = 5;
                    location++;
                    checkLocation();
                }
            }
        });

        Button num6 = (Button) findViewById(R.id.password_num6);
        num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (before == true) {
                    password_1[location] = 6;
                    location++;
                    checkLocation();
                }
                else if (before == false) {
                    password_2[location] = 6;
                    location++;
                    checkLocation();
                }
            }
        });

        Button num7 = (Button) findViewById(R.id.password_num7);
        num7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (before == true) {
                    password_1[location] = 7;
                    location++;
                    checkLocation();
                }
                else if (before == false) {
                    password_2[location] = 7;
                    location++;
                    checkLocation();
                }
            }
        });

        Button num8 = (Button) findViewById(R.id.password_num8);
        num8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (before == true) {
                    password_1[location] = 8;
                    location++;
                    checkLocation();
                }
                else if (before == false) {
                    password_2[location] = 8;
                    location++;
                    checkLocation();
                }
            }
        });

        Button num9 = (Button) findViewById(R.id.password_num9);
        num9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (before == true) {
                    password_1[location] = 9;
                    location++;
                    checkLocation();
                }
                else if (before == false) {
                    password_2[location] = 9;
                    location++;
                    checkLocation();
                }
            }
        });

        Button num0 = (Button) findViewById(R.id.password_num0);
        num0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (before == true) {
                    password_1[location] = 0;
                    location++;
                    checkLocation();
                }
                else if (before == false) {
                    password_2[location] = 0;
                    location++;
                    checkLocation();
                }
            }
        });

        Button num_back = (Button) findViewById(R.id.password_back);
        num_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (location >= 1 && location <= 4) {
                   location--;
                   checkLocation();
               }
               else
                   checkLocation();
            }
        });
    }

    public void checkLocation () {
        if (before == true) {
            if (location == 1)
                password_status.setImageResource(R.drawable.new_password31);
            else if (location == 2)
                password_status.setImageResource(R.drawable.new_password32);
            else if (location == 3)
                password_status.setImageResource(R.drawable.new_password33);
            else if (location == 4) {
                password_status.setImageResource(R.drawable.new_password34);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                before = false;
                location = 0;
                password_status.setImageResource(R.drawable.new_password3);
                password_notice.setImageResource(R.drawable.new_password21);
            }
            else if (location == 0)
                password_status.setImageResource(R.drawable.new_password3);
        }
        else if (before == false) {
            if (location == 1)
                password_status.setImageResource(R.drawable.new_password31);
            else if (location == 2)
                password_status.setImageResource(R.drawable.new_password32);
            else if (location == 3)
                password_status.setImageResource(R.drawable.new_password33);
            else if (location == 4) {
                password_status.setImageResource(R.drawable.new_password34);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (password_1[0] == password_2[0] && password_1[1] == password_2[1] && password_1[2] == password_2[2] && password_1[3] == password_2[3]) {
                    db = openOrCreateDatabase(DBname, MODE_PRIVATE, null);

                    sql = "UPDATE " + tableName + " SET password=1 WHERE info='settings';";
                    sql2 = "UPDATE " + tableName + " SET pa1=" + password_1[0] + " WHERE info='settings';";
                    sql3 = "UPDATE " + tableName + " SET pa2=" + password_1[1] + " WHERE info='settings';";
                    sql4 = "UPDATE " + tableName + " SET pa3=" + password_1[2] + " WHERE info='settings';";
                    sql5 = "UPDATE " + tableName + " SET pa4=" + password_1[3] + " WHERE info='settings';";

                    db.execSQL(sql);
                    db.execSQL(sql2);
                    db.execSQL(sql3);
                    db.execSQL(sql4);
                    db.execSQL(sql5);

                    SplashActivity.password_bool = 1;

                    SplashActivity.password[0] = password_1[0];
                    SplashActivity.password[1] = password_1[1];
                    SplashActivity.password[2] = password_1[2];
                    SplashActivity.password[3] = password_1[3];

                    db.close();

                    finish();
                    Toast.makeText(getApplicationContext(), "비밀번호 설정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    location = 0;
                    before = true;
                    password_status.setImageResource(R.drawable.new_password3);
                    password_notice.setImageResource(R.drawable.new_password22);
                }
            }
            else if (location == 0)
                password_status.setImageResource(R.drawable.new_password3);
        }
    }
}