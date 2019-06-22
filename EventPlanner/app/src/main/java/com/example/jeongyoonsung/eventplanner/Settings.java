package com.example.jeongyoonsung.eventplanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Settings extends Fragment {
    MainActivity mainActivity = (MainActivity)MainActivity.main_activity;

    SQLiteDatabase db;
    String DBname = "EventPlanner";
    String tableName = "EVENTLIST";
    String tableName2 = "HISTORY";
    String tableName3 = "SETTINGS";
    String sql;

    int count = 0;

    AlertDialog.Builder builder;

    long timeNow;
    Date date;
    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd k:mm");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_settings, container, false);
        final ImageView event_order = (ImageView) view.findViewById(R.id.event_order);
        final ImageView history_order = (ImageView) view.findViewById(R.id.history_order);
        final ImageView push_notification = (ImageView) view.findViewById(R.id.push_notification);
        final ImageView password = (ImageView) view.findViewById(R.id.password);
        final ImageView event_default = (ImageView) view.findViewById(R.id.event_default);
        final ImageView history_default = (ImageView) view.findViewById(R.id.history_default);
        final ImageView app_history = (ImageView) view.findViewById(R.id.app_history);

        if (MainActivity.event_order_bool == 1)
            event_order.setImageResource(R.drawable.event_order_register);

        if (MainActivity.history_order_case == 1)
            history_order.setImageResource(R.drawable.history_order_register);
        else if (MainActivity.history_order_case == 2)
            history_order.setImageResource(R.drawable.history_order_korean);
        else if (MainActivity.history_order_case == 3)
            history_order.setImageResource(R.drawable.history_order_price);

        if (MainActivity.push_notification_bool == 1)
            push_notification.setImageResource(R.drawable.push_notification_on);

        if (SplashActivity.password_bool == 1)
            password.setImageResource(R.drawable.password_on);

        event_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.event_order_bool == 0) {
                    event_order.setImageResource(R.drawable.event_order_register);

                    db = getContext().openOrCreateDatabase(DBname, Context.MODE_PRIVATE, null);

                    try {
                        sql = "UPDATE " + tableName3 + " SET event_order=1 WHERE info='settings';";
                        db.execSQL(sql);
                        db.close();

                        Toast.makeText(getContext(), "이벤트 정렬이 [등록 순] 으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    MainActivity.event_order_bool = 1;
                } else if (MainActivity.event_order_bool == 1) {
                    event_order.setImageResource(R.drawable.event_order);

                    db = getContext().openOrCreateDatabase(DBname, Context.MODE_PRIVATE, null);

                    try {
                        sql = "UPDATE " + tableName3 + " SET event_order=0 WHERE info='settings';";
                        db.execSQL(sql);
                        db.close();

                        Toast.makeText(getContext(), "이벤트 정렬이 [날짜 순] 으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    MainActivity.event_order_bool = 0;
                }
            }
        });

        history_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.history_order_case == 0) {
                    history_order.setImageResource(R.drawable.history_order_register);

                    db = getContext().openOrCreateDatabase(DBname, Context.MODE_PRIVATE, null);

                    try {
                        sql = "UPDATE " + tableName3 + " SET history_case=1 WHERE info='settings';";
                        db.execSQL(sql);
                        db.close();
                        Toast.makeText(getContext(), "당첨 기록 정렬이 [등록 순] 으로 변경되었습니다.\n정렬은 앱 재시작 후에 적용됩니다.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    MainActivity.history_order_case = 1;
                } else if (MainActivity.history_order_case == 1) {
                    history_order.setImageResource(R.drawable.history_order_korean);

                    db = getContext().openOrCreateDatabase(DBname, Context.MODE_PRIVATE, null);

                    try {
                        sql = "UPDATE " + tableName3 + " SET history_case=2 WHERE info='settings';";
                        db.execSQL(sql);
                        db.close();
                        Toast.makeText(getContext(), "당첨 기록 정렬이 [가나다 순] 으로 변경되었습니다.\n정렬은 앱 재시작 후에 적용됩니다.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    MainActivity.history_order_case = 2;
                } else if (MainActivity.history_order_case == 2) {
                    history_order.setImageResource(R.drawable.history_order_price);

                    db = getContext().openOrCreateDatabase(DBname, Context.MODE_PRIVATE, null);

                    try {
                        sql = "UPDATE " + tableName3 + " SET history_case=3 WHERE info='settings';";
                        db.execSQL(sql);
                        db.close();
                        Toast.makeText(getContext(), "당첨 기록 정렬이 [금액 순] 으로 변경되었습니다.\n정렬은 앱 재시작 후에 적용됩니다.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    MainActivity.history_order_case = 3;
                } else if (MainActivity.history_order_case == 3) {
                    history_order.setImageResource(R.drawable.history_order);

                    db = getContext().openOrCreateDatabase(DBname, Context.MODE_PRIVATE, null);

                    try {
                        sql = "UPDATE " + tableName3 + " SET history_case=0 WHERE info='settings';";
                        db.execSQL(sql);
                        db.close();
                        Toast.makeText(getContext(), "당첨 기록 정렬이 [날짜 순] 으로 변경되었습니다.\n정렬은 앱 재시작 후에 적용됩니다.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    MainActivity.history_order_case = 0;
                }
            }
        });

        push_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.push_notification_bool == 0) {
                    push_notification.setImageResource(R.drawable.push_notification_on);
                    MainActivity.push_notification_bool = 1;

                    db = getContext().openOrCreateDatabase(DBname, Context.MODE_PRIVATE, null);

                    try {
                        sql = "UPDATE " + tableName3 + " SET push=1 WHERE info='settings';";
                        db.execSQL(sql);
                        db.close();
                        Toast.makeText(getContext(), getTime() + "에 푸시 알림을 설정하였습니다.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (MainActivity.push_notification_bool == 1) {
                    push_notification.setImageResource(R.drawable.push_notification_off);
                    MainActivity.push_notification_bool = 0;

                    db = getContext().openOrCreateDatabase(DBname, Context.MODE_PRIVATE, null);

                    try {
                        sql = "UPDATE " + tableName3 + " SET push=0 WHERE info='settings';";
                        db.execSQL(sql);
                        db.close();
                        Toast.makeText(getContext(), getTime() + "에 푸시 알림을 해제하였습니다.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SplashActivity.password_bool == 0) {
                    new_password();
                } else if (SplashActivity.password_bool == 1) {
                    password.setImageResource(R.drawable.password_off);

                    db = getContext().openOrCreateDatabase(DBname, Context.MODE_PRIVATE, null);

                    try {
                        sql = "UPDATE " + tableName3 + " SET password = 0 WHERE info = 'settings';";
                        db.execSQL(sql);

                        db.close();

                        SplashActivity.password_bool = 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        event_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(getContext());

                builder.setTitle("이벤트 기록 삭제 여부 확인");
                builder.setMessage("현재 등록된 이벤트 기록들을 전부 삭제합니다.\n진행하시겠습니까?");

                builder.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                    }
                });

                builder.setNegativeButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        db = getContext().openOrCreateDatabase(DBname, Context.MODE_PRIVATE, null);

                        try {
                            sql = "DROP TABLE " + tableName;
                            db.execSQL(sql);

                            db.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(getContext(), MainActivity.class);
                        mainActivity.finish();
                        getContext().startActivity(intent);

                        Toast.makeText(getContext(), "이벤트 기록이 초기화 되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });

        history_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(getContext());

                builder.setTitle("당첨 기록 삭제 여부 확인");
                builder.setMessage("현재 등록된 당첨 기록들을 전부 삭제합니다.\n단, 상단에 있는 종합 기록은 초기화되지 않습니다.\n진행하시겠습니까?");

                builder.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                    }
                });

                builder.setNegativeButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        db = getContext().openOrCreateDatabase(DBname, Context.MODE_PRIVATE, null);

                        try {
                            sql = "DROP TABLE " + tableName2;
                            db.execSQL(sql);

                            db.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(getContext(), MainActivity.class);
                        mainActivity.finish();
                        getContext().startActivity(intent);

                        Toast.makeText(getContext(), "당첨 기록이 초기화 되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });

        ImageView app_version = (ImageView) view.findViewById(R.id.app_version);
        app_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "20180326 0.0.2v Released", Toast.LENGTH_SHORT).show();

                count++;

                if (count == 10) {
                    count = 0;

                    builder = new AlertDialog.Builder(getContext());

                    builder.setTitle("[개발자 모드] 데이터 초기화");
                    builder.setMessage("현재 등록된 모든 기록들을 전부 삭제합니다.\n진행하시겠습니까?");

                    builder.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick (DialogInterface dialog, int which) {
                        }
                    });

                    builder.setNegativeButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick (DialogInterface dialog, int which) {
                            db = getContext().openOrCreateDatabase(DBname, Context.MODE_PRIVATE, null);

                            try {
                                sql = "DROP TABLE " + tableName;
                                db.execSQL(sql);

                                sql = "DROP TABLE " + tableName2;
                                db.execSQL(sql);

                                sql = "DROP TABLE " + tableName3;
                                db.execSQL(sql);
                                db.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Intent intent = new Intent(getContext(), MainActivity.class);
                            mainActivity.finish();
                            getContext().startActivity(intent);

                            Toast.makeText(getContext(), "모든 데이터가 초기화 되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.show();
                }
            }
        });

        app_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AppHistory.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private String getTime() {
        timeNow = System.currentTimeMillis();
        date = new Date(timeNow);
        return timeFormat.format(date);
    }

    public void new_password() {
        Intent intent = new Intent(getActivity(), NewPassword.class);
        startActivity(intent);
    }
}