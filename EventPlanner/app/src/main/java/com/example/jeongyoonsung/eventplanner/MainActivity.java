package com.example.jeongyoonsung.eventplanner;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static public Activity main_activity;

    static int event_order_bool = 0;
    static int history_order_case = 0;
    static int push_notification_bool = 0;

    AddDialog dialog;

    private SQLiteDatabase db;
    String DBname = "EventPlanner";
    String tableName = "EVENTLIST";
    String tableName2 = "HISTORY";
    String tableName3 = "SETTINGS";
    String sql, sql2, sql3, sql4;
    Cursor resultset;

    int DBcount3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Alarm(getApplicationContext()).Alarm();

        main_activity = MainActivity.this;

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        Fragment[] arrFragments = new Fragment[3];
        arrFragments[0] = new EventList();
        arrFragments[1] = new History();
        arrFragments[2] = new Settings();

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), arrFragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        ImageView add_event = (ImageView) findViewById(R.id.add);
        add_event.setOnClickListener(this);

        db = openOrCreateDatabase(DBname, MODE_PRIVATE, null);

        try {
            sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (name VARCHAR2(91), company VARCHAR2(61), prize VARCHAR2(151), link VARCHAR2(501) PRIMARY KEY, " +
                    "cat1 INTEGER, cat2 INTEGER, cat3 INTEGER, cat4 INTEGER, cat5 INTEGER, cat6 INTEGER, " +
                    "end VARCHAR2(11), notice VARCHAR2(11), not_y INTEGER, not_m INTEGER, not_d INTEGER, push_bool VARCHAR2(6));";
            db.execSQL(sql);

            sql2 = "CREATE TABLE IF NOT EXISTS " + tableName2 + "(name VARCHAR2(91), company VARCHAR2(61), prize VARCHAR2(46), notice VARCHAR2(11), get VARCHAR2(11), won INTEGER);";
            db.execSQL(sql2);

            sql3 = "CREATE TABLE IF NOT EXISTS " + tableName3 + "(event_order INTEGER, history_case INTEGER, push INTEGER, password INTEGER, " +
                    "pa1 INTEGER, pa2 INTEGER, pa3 INTEGER, pa4 INTEGER, event_count INTEGER, prize_count INTEGER, sum_won INTEGER, pure_won INTEGER, info VARCHAR2(10));";
            db.execSQL(sql3);

            sql4 = "SELECT event_order, history_case, push FROM " + tableName3 + " WHERE info='settings';";
            resultset = db.rawQuery(sql4, null);
            DBcount3 = resultset.getCount();

            if (DBcount3 == 0) {
                sql4 = "INSERT INTO " + tableName3 + " (event_order, history_case, push, password, pa1, pa2, pa3, pa4, event_count, prize_count, sum_won, pure_won, info) VALUES " +
                        "(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'settings');";
                db.execSQL(sql4);
            }
            else {
                resultset.moveToNext();
                int temp_event_order = resultset.getInt(0);
                int temp_history_case = resultset.getInt(1);
                int temp_push_notification = resultset.getInt(2);

                event_order_bool = temp_event_order;
                history_order_case = temp_history_case;
                push_notification_bool = temp_push_notification;
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class PagerAdapter extends FragmentPagerAdapter {
        private Fragment[] arrFragments;

        public PagerAdapter(FragmentManager fm, Fragment[] arrFragments) {
            super(fm);
            this.arrFragments = arrFragments;
        }

        @Override
        public Fragment getItem(int position) {
            return arrFragments[position];
        }

        @Override
        public int getCount() {
            return arrFragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "참여 이벤트";
                case 1:
                    return "당첨 기록";
                case 2:
                    return "설정";
                default:
                    return "";
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                dialog = new AddDialog(this);
                dialog.show();
                Toast.makeText(getApplicationContext(), "이벤트를 한번 추가하면 수정이 불가하오니\n정확하게 작성하여 주시기 바랍니다.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    public class Alarm {
        private Context context;

        public Alarm(Context context) {
            this.context = context;
        }

        public void Alarm() {
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(MainActivity.this, Broadcast.class);

            PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 10, 00, 0);

            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        }
    };
}