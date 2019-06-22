package com.example.jeongyoonsung.eventplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class PasswordCheck extends AppCompatActivity {
    public int location;
    int password_d[];
    ImageView password_status;
    ImageView password_notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_check);

        location = 0;
        password_d = new int[] {0, 0, 0, 0, 0};

        password_status = (ImageView) findViewById(R.id.password_status0);
        password_notice = (ImageView) findViewById(R.id.password_notice0);

        Button num1 = (Button) findViewById(R.id.password_num10);
        num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               password_d[location] = 1;
               location++;
               checkLocation();
            }
        });

        Button num2 = (Button) findViewById(R.id.password_num20);
        num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_d[location] = 2;
                location++;
                checkLocation();
            }
        });

        Button num3 = (Button) findViewById(R.id.password_num30);
        num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_d[location] = 3;
                location++;
                checkLocation();
            }
        });

        Button num4 = (Button) findViewById(R.id.password_num40);
        num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_d[location] = 4;
                location++;
                checkLocation();
            }
        });

        Button num5 = (Button) findViewById(R.id.password_num50);
        num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_d[location] = 5;
                location++;
                checkLocation();
            }
        });

        Button num6 = (Button) findViewById(R.id.password_num60);
        num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_d[location] = 6;
                location++;
                checkLocation();
            }
        });

        Button num7 = (Button) findViewById(R.id.password_num70);
        num7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_d[location] = 7;
                location++;
                checkLocation();
            }
        });

        Button num8 = (Button) findViewById(R.id.password_num80);
        num8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_d[location] = 8;
                location++;
                checkLocation();
            }
        });

        Button num9 = (Button) findViewById(R.id.password_num90);
        num9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_d[location] = 9;
                location++;
                checkLocation();
            }
        });

        Button num0 = (Button) findViewById(R.id.password_num00);
        num0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password_d[location] = 0;
                location++;
                checkLocation();
            }
        });

        Button num_back = (Button) findViewById(R.id.password_back0);
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

            if (password_d[0] == SplashActivity.password[0] && password_d[1] == SplashActivity.password[1] && password_d[2] == SplashActivity.password[2] && password_d[3] == SplashActivity.password[3]) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                location = 0;
                password_status.setImageResource(R.drawable.new_password3);
                password_notice.setImageResource(R.drawable.password_check3);
            }
        }
        else if (location == 0)
            password_status.setImageResource(R.drawable.new_password3);
    }
}