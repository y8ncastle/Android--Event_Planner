package com.example.jeongyoonsung.eventplanner;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddDialog extends Dialog {
    MainActivity mainActivity = (MainActivity)MainActivity.main_activity;
    private static final int LAYOUT = R.layout.event_list_add;

    SQLiteDatabase db;
    String tableName = "EVENTLIST";
    String tableName2 = "SETTINGS";
    String DBname = "EventPlanner";
    String sql;
    String basic_bool = "false";
    Cursor resultset;

    EditText ed_name, ed_company, ed_prize, ed_link, ed_endDate, ed_noticeDate;
    CheckBox cb1, cb2, cb3, cb4, cb5, cb6;
    ImageView register, close;

    String name, company, prize, link, endDate, noticeDate;
    String year1 = "", month1 = "", day1 = "";
    String year2 = "", month2 = "", day2 = "";

    char compare1[], compare2[];

    int cat1, cat2, cat3, cat4, cat5, cat6;
    int p_year1, p_month1, p_day1;
    int p_year2, p_month2, p_day2;
    int event_count;

    public AddDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        setup();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();

                if (nameToChar() == false)
                    Toast.makeText(getContext(), "이벤트 이름을 작성하지 않았거나\n맨 앞에 공백이 있습니다.", Toast.LENGTH_SHORT).show();
                else if (companyToChar() == false)
                    Toast.makeText(getContext(), "이벤트 주최기관을 작성하지 않았거나\n맨 앞에 공백이 있습니다.", Toast.LENGTH_SHORT).show();
                else if (endDateCheck() == false)
                    Toast.makeText(getContext(), "이벤트 마감일을 양식에 맞게 입력해주세요.", Toast.LENGTH_SHORT).show();
                else if (noticeDateCheck() == false)
                    Toast.makeText(getContext(), "이벤트 발표일을 양식에 맞게 입력해주세요.", Toast.LENGTH_SHORT).show();
                else if (dataValidationCheck() == false)
                    Toast.makeText(getContext(), "이벤트 마감일이 발표일보다 빠르거나\n날짜 양식에 맞지 않는 부분이 있습니다.", Toast.LENGTH_SHORT).show();
                else if (boxCheck() == false)
                    Toast.makeText(getContext(), "이벤트 분류를 한 개 이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                else if (prize.length() > 50)
                    Toast.makeText(getContext(), "이벤트 경품을 50자 이내로 입력해주세요.", Toast.LENGTH_SHORT).show();
                else if (prizeToChar() == false)
                    Toast.makeText(getContext(), "이벤트 경품을 작성하지 않았거나\n맨 앞에 공백이 있습니다.", Toast.LENGTH_SHORT).show();
                else if (link.length() > 500)
                    Toast.makeText(getContext(), "이벤트 링크를 500자 이내로 입력해주세요.", Toast.LENGTH_SHORT).show();
                else if (linkToChar() == false)
                    Toast.makeText(getContext(), "이벤트 링크를 작성하지 않았거나\n맨 앞에 공백이 있습니다.", Toast.LENGTH_SHORT).show();
                else {
                    ed_name.setText("");
                    ed_company.setText("");
                    ed_endDate.setText("");
                    ed_noticeDate.setText("");
                    ed_prize.setText("");
                    ed_link.setText("");
                    cb1.setChecked(false);
                    cb2.setChecked(false);
                    cb3.setChecked(false);
                    cb4.setChecked(false);
                    cb5.setChecked(false);
                    cb6.setChecked(false);

                    db = getContext().openOrCreateDatabase(DBname, Context.MODE_PRIVATE, null);

                    try {
                        sql = "INSERT INTO " + tableName + "(name, company, prize, link, cat1, cat2, cat3, cat4, cat5, cat6, end, notice, not_y, not_m, not_d, push_bool) " +
                                "VALUES ('" + name + "', '" + company + "', '" + prize + "', '" + link + "', " +
                                cat1 + ", " + cat2 + ", " + cat3 + ", " + cat4 + ", " + cat5 + ", " + cat6 + ", '" +
                                endDate + "', '" + noticeDate + "', " + p_year2 + ", " + p_month2 + ", " + p_day2 + ", '" + basic_bool + "');";
                        db.execSQL(sql);

                        sql = "SELECT event_count FROM " + tableName2 + ";";
                        resultset = db.rawQuery(sql, null);

                        resultset.moveToNext();

                        event_count = resultset.getInt(0);

                        int temp_event_count = event_count + 1;

                        sql = "UPDATE " + tableName2 + " SET event_count=" + temp_event_count + " WHERE info='settings';";
                        db.execSQL(sql);
                        db.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getContext(), "이벤트를 등록하였습니다. 제대로 출력되지 않는 경우\n중복되는 내용일 수 있습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                dismiss();
                mainActivity.finish();
                getContext().startActivity(intent);
            }
        });
    }

    public void setup() {
        ed_name = (EditText) findViewById(R.id.addDialogName);
        ed_company = (EditText) findViewById(R.id.addDialogCompany);
        ed_prize = (EditText) findViewById(R.id.addDialogPrize);
        ed_link = (EditText) findViewById(R.id.addDialogLink);
        ed_endDate = (EditText) findViewById(R.id.addDialogEndDate);
        ed_noticeDate = (EditText) findViewById(R.id.addDialogNoticeDate);

        cb1 = (CheckBox) findViewById(R.id.add_CheckBox1);
        cb2 = (CheckBox) findViewById(R.id.add_CheckBox2);
        cb3 = (CheckBox) findViewById(R.id.add_CheckBox3);
        cb4 = (CheckBox) findViewById(R.id.add_CheckBox4);
        cb5 = (CheckBox) findViewById(R.id.add_CheckBox5);
        cb6 = (CheckBox) findViewById(R.id.add_CheckBox6);

        register = (ImageView) findViewById(R.id.add_register);
        close = (ImageView) findViewById(R.id.add_cancel);
    }

    public boolean boxCheck() {
        if (cb1.isChecked() == true || cb2.isChecked() == true || cb3.isChecked() == true || cb4.isChecked() == true || cb5.isChecked() == true || cb6.isChecked() == true)
            return true;
        else
            return false;
    }

    public boolean endDateCheck() {
        if (endDate.length() != 10)
            endDate = "0000000000";

        compare1 = endDate.toCharArray();

        year1 = (Character.toString(compare1[0]) + Character.toString(compare1[1]) + Character.toString(compare1[2]) + Character.toString(compare1[3]));
        p_year1 = Integer.parseInt(year1);

        if (compare1[5] != 0) {
            month1 = (Character.toString(compare1[5]) + Character.toString(compare1[6]));
            p_month1 = Integer.parseInt(month1);
        } else if (compare1[5] == 0) {
            month1 = Character.toString(compare1[6]);
            p_month1 = Integer.parseInt(month1);
        }

        if (compare1[8] != 0) {
            day1 = (Character.toString(compare1[8]) + Character.toString(compare1[9]));
            p_day1 = Integer.parseInt(day1);
        } else if (compare1[8] == 0) {
            day1 = Character.toString(compare1[9]);
            p_day1 = Integer.parseInt(day1);
        }

        if ((compare1[0] == 50) && (compare1[1] == 48) && (compare1[2] >= 48 && compare1[2] <= 57) &&
                (compare1[3] >= 48 && compare1[3] <= 57) && (compare1[4] == '-') && (compare1[5] >= 48 && compare1[5] <= 49) &&
                (compare1[6] >= 48 && compare1[6] <= 57) && (compare1[7] == '-') && (compare1[8] >= 48 && compare1[8] <= 51) && (compare1[9] >= 48 && compare1[9] <= 57))
            return true;
        else
            return false;
    }

    public boolean noticeDateCheck() {
        if (noticeDate.length() != 10)
            noticeDate = "0000000000";

        compare2 = noticeDate.toCharArray();

        year2 = (Character.toString(compare2[0]) + Character.toString(compare2[1]) + Character.toString(compare2[2]) + Character.toString(compare2[3]));
        p_year2 = Integer.parseInt(year2);

        if (compare2[5] != 0) {
            month2 = (Character.toString(compare2[5]) + Character.toString(compare2[6]));
            p_month2 = Integer.parseInt(month2);
        } else if (compare2[5] == 0) {
            month2 = (Character.toString(compare2[6]));
            p_month2 = Integer.parseInt(month2);
        }

        if (compare2[8] != 0) {
            day2 = (Character.toString(compare2[8]) + Character.toString(compare2[9]));
            p_day2 = Integer.parseInt(day2);
        } else if (compare2[8] == 0) {
            day2 = Character.toString(compare2[9]);
            p_day2 = Integer.parseInt(day2);
        }

        if ((compare2[0] == 50) && (compare2[1] == 48) && (compare2[2] >= 48 && compare2[2] <= 57) &&
                (compare2[3] >= 48 && compare2[3] <= 57) && (compare2[4] == '-') && (compare2[5] >= 48 && compare2[5] <= 49) &&
                (compare2[6] >= 48 && compare2[6] <= 57) && (compare2[7] == '-') && (compare2[8] >= 48 && compare2[8] <= 51) && (compare2[9] >= 48 && compare2[9] <= 57))
            return true;
        else
            return false;
    }

    public boolean dataValidationCheck() {
        if (p_year1 > p_year2)
            return false;
        else if ((p_year1 == p_year2) && (p_month1 > p_month2))
            return false;
        else if ((p_year1 == p_year2) && (p_month1 == p_month2) && (p_day1 > p_day2))
            return false;
        else if (p_month1 == 0 || p_month2 == 0 || p_month1 > 12 || p_month2 > 12)
            return false;
        else if (p_day1 == 0 || p_day2 == 0 || (p_month1 == 1 && p_day1 > 31) || (p_month2 == 1 && p_day2 > 31) ||
                (p_month1 == 2 && p_day1 > 29) || (p_month2 == 2 && p_day2 > 29) || (p_month1 == 3 && p_day1 > 31) || (p_month2 == 3 && p_day2 > 31) ||
                (p_month1 == 4 && p_day1 > 30) || (p_month2 == 4 && p_day2 > 30) || (p_month1 == 5 && p_day1 > 31) || (p_month2 == 5 && p_day2 > 31) ||
                (p_month1 == 6 && p_day1 > 30) || (p_month2 == 6 && p_day2 > 30) || (p_month1 == 7 && p_day1 > 31) || (p_month2 == 7 && p_day2 > 31) ||
                (p_month1 == 8 && p_day1 > 31) || (p_month2 == 8 && p_day2 > 31) || (p_month1 == 9 && p_day1 > 30) || (p_month2 == 9 && p_day2 > 30) ||
                (p_month1 == 10 && p_day1 > 31) || (p_month2 == 10 && p_day2 > 31) || (p_month1 == 11 && p_day1 > 30) || (p_month2 == 11 && p_day2 > 30) ||
                (p_month1 == 12 && p_day1 > 31) || (p_month2 == 12 && p_day2 > 31))
            return false;
        else
            return true;
    }

    public void setData() {
        name = ed_name.getText().toString();
        company = ed_company.getText().toString();
        prize = ed_prize.getText().toString();
        link = ed_link.getText().toString();
        endDate = ed_endDate.getText().toString();
        noticeDate = ed_noticeDate.getText().toString();

        if (cb1.isChecked())
            cat1 = 1;
        else cat1 = 0;

        if (cb2.isChecked())
            cat2 = 1;
        else cat2 = 0;

        if (cb3.isChecked())
            cat3 = 1;
        else cat3 = 0;

        if (cb4.isChecked())
            cat4 = 1;
        else cat4 = 0;

        if (cb5.isChecked())
            cat5 = 1;
        else cat5 = 0;

        if (cb6.isChecked())
            cat6 = 1;
        else cat6 = 0;
    }

    public boolean nameToChar() {
        char compare[];

        compare = ed_name.getText().toString().toCharArray();

        if (name.length() == 0 || compare[0] == ' ')
            return false;
        else
            return true;
    }

    public boolean companyToChar() {
        char compare[];

        compare = ed_company.getText().toString().toCharArray();

        if (company.length() == 0 || compare[0] == ' ')
            return false;
        else
            return true;
    }

    public boolean prizeToChar() {
        char compare[];

        compare = ed_prize.getText().toString().toCharArray();

        if (prize.length() == 0 || compare[0] == ' ')
            return false;
        else
            return true;
    }

    public boolean linkToChar() {
        char compare[];

        compare = ed_link.getText().toString().toCharArray();

        if (link.length() == 0 || compare[0] == ' ')
            return false;
        else
            return true;
    }
}