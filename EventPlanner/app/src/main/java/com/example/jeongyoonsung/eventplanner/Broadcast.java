package com.example.jeongyoonsung.eventplanner;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Broadcast extends BroadcastReceiver {
    SQLiteDatabase db;
    String DBname = "EventPlanner";
    String tableName = "EVENTLIST";
    String tableName2 = "SETTINGS";
    String sql, sql2, temp, temp2, temp3;
    Cursor resultset;
    int DBcount, push_count, temp_push;

    @Override
    public void onReceive (Context context, Intent intent) {
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(date);

        db = context.openOrCreateDatabase(DBname, Context.MODE_PRIVATE, null);

        try {
            sql = "SELECT push FROM " + tableName2 + " WHERE info='settings';";
            resultset = db.rawQuery(sql, null);
            DBcount = resultset.getCount();

            if (DBcount != 0) {
                resultset.moveToNext();
                temp_push = resultset.getInt(0);

                if (temp_push == 1) {
                    sql = "SELECT name, notice, push_bool FROM " + tableName + ";";
                    resultset = db.rawQuery(sql, null);
                    DBcount = resultset.getCount();
                    push_count = 0;

                    if (DBcount != 0) {
                        for (int i = 0; i < DBcount; i++) {
                            resultset.moveToNext();
                            temp = resultset.getString(0);
                            temp2 = resultset.getString(1);
                            temp3 = resultset.getString(2);

                            if ((temp2.equals(today) == true) && (temp3.equals("false") == true)) {
                                push_count++;

                                sql2 = "UPDATE " + tableName + " SET push_bool='true' WHERE name= '" + temp + "';";
                                db.execSQL(sql2);
                            }
                        }

                        if (push_count != 0) {
                            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

                            Notification.Builder builder = new Notification.Builder(context)
                                    .setContentTitle("이벤트 당첨자 발표 예고 안내")
                                    .setContentText("오늘 " + push_count + "개의 이벤트 발표 계획이 있습니다.")
                                    .setSmallIcon(R.drawable.notification_icon)
                                    .setContentIntent(pendingIntent)
                                    .setWhen(System.currentTimeMillis())
                                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                                    .setAutoCancel(true);

                            notificationManager.notify(1, builder.build());
                        }
                    }
                }
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
