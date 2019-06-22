package com.example.jeongyoonsung.eventplanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class History extends Fragment {
    MainActivity mainActivity = (MainActivity)MainActivity.main_activity;

    AlertDialog.Builder builder;
    ListView list;
    CustomList adapter;

    SQLiteDatabase db;
    String DBname = "EventPlanner";
    String tableName = "HISTORY";
    String tableName2 = "SETTINGS";
    String sql;
    Cursor resultset;
    int DBcount;

    EditText builder_pure;

    TextView t_event_count;
    TextView t_prize_count;
    TextView t_sum_won;
    TextView t_pure_won;

    String[] names;
    String[] companies;
    String[] prizes;
    String[] notices;
    String[] gets;
    String[] wons;

    String tempName;
    String tempCompany;
    String tempPrize;
    String tempNotice;
    String tempGet;
    String temp_pure;

    int tempWon;
    int event_count;
    int prize_count;
    int sum_won;
    int pure_won;

    Boolean check;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DBcheck();

        if (DBcount != 0) {
            View view = (View) inflater.inflate(R.layout.fragment_history, container, false);

            db = getContext().openOrCreateDatabase(DBname, Context.MODE_PRIVATE, null);

            try {
                sql = "SELECT event_count, prize_count, sum_won, pure_won FROM " + tableName2 + ";";
                resultset = db.rawQuery(sql, null);

                resultset.moveToNext();
                event_count = resultset.getInt(0);
                prize_count = resultset.getInt(1);
                sum_won = resultset.getInt(2);
                pure_won = resultset.getInt(3);

                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            adapter = new History.CustomList(getActivity());
            list = (ListView) view.findViewById(R.id.history_list);
            list.setAdapter(adapter);

            t_event_count = view.findViewById(R.id.event_count);
            String count = Integer.toString(event_count);
            t_event_count.setText(count);

            t_prize_count = view.findViewById(R.id.prize_count);
            String prize = Integer.toString(prize_count);
            t_prize_count.setText(prize);

            t_sum_won = view.findViewById(R.id.sum_won);

            if (Integer.toString(sum_won).length() == 7)
                t_sum_won.setTextSize(15);
            else if (Integer.toString(sum_won).length() >= 8)
                t_sum_won.setTextSize(13);

            String won = String.format("%,d", sum_won);
            t_sum_won.setText(won);

            t_pure_won = view.findViewById(R.id.pure_won);

            if (Integer.toString(pure_won).length() == 7) {
                t_pure_won.setTextSize(15);
                t_pure_won.setPadding(0, 10, 0, 0);
            }
            else if (Integer.toString(pure_won).length() >= 8) {
                t_pure_won.setTextSize(15);
                t_pure_won.setPadding(0, 10, 0, 0);
            }

            String won2 = String.format("%,d", pure_won);
            t_pure_won.setText(won2);

            t_pure_won.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder = new AlertDialog.Builder(getContext());

                    builder.setTitle("경품 순 이익 금액 변동");
                    builder.setMessage("판매한 경품이 있을 경우 순 이익 금액을\n수정할 수 있습니다.");
                    builder_pure = new EditText(getContext());
                    builder_pure.setSingleLine(true);
                    builder_pure.setPadding(60, 50, 60, 50);
                    builder.setView(builder_pure);

                    builder.setPositiveButton("감소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick (DialogInterface dialog, int which) {
                            temp_pure = builder_pure.getText().toString();

                            if (temp_pure.length() > 0 && temp_pure.length() <= 9) {
                                for (int i=0; i<temp_pure.length(); i++) {
                                    if (temp_pure.toString().toCharArray()[i] >= 48 && temp_pure.toString().toCharArray()[i] <= 57)
                                        check = true;
                                    else
                                        check = false;
                                }
                            }
                            else
                                check = false;

                            if (check == true) {
                                int price = Integer.parseInt(temp_pure);

                                db = getContext().openOrCreateDatabase(DBname, Context.MODE_PRIVATE, null);

                                try {
                                    sql = "SELECT pure_won FROM " + tableName2 + ";";
                                    resultset = db.rawQuery(sql, null);

                                    resultset.moveToNext();
                                    int pure_sum = resultset.getInt(0);
                                    pure_sum -= price;

                                    if (pure_sum >= 0 && pure_sum <= 999999999) {
                                        sql = "UPDATE " + tableName2 + " SET pure_won=" + pure_sum + " WHERE info='settings';";

                                        try {
                                            db.execSQL(sql);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        Intent intent = new Intent(getContext(), MainActivity.class);
                                        mainActivity.finish();
                                        getContext().startActivity(intent);

                                        Toast.makeText(getContext(), "값이 정상적으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                        Toast.makeText(getContext(), "계산된 값은 0보다 크고 10억보다 작아야 합니다.", Toast.LENGTH_SHORT).show();

                                    db.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else
                                Toast.makeText(getContext(), "잘못된 가격 입력으로 인해 변경이 취소됩니다.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick (DialogInterface dialog, int which) {
                        }
                    });

                    builder.setNegativeButton("증가", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick (DialogInterface dialog, int which) {
                            temp_pure = builder_pure.getText().toString();

                            if (temp_pure.length() > 0 && temp_pure.length() <= 9) {
                                for (int i=0; i<temp_pure.length(); i++) {
                                    if (temp_pure.toString().toCharArray()[i] >= 48 && temp_pure.toString().toCharArray()[i] <= 57)
                                        check = true;
                                    else
                                        check = false;
                                }
                            }
                            else
                                check = false;

                            if (check == true) {
                                int price = Integer.parseInt(temp_pure);

                                db = getContext().openOrCreateDatabase(DBname, Context.MODE_PRIVATE, null);

                                try {
                                    sql = "SELECT pure_won FROM " + tableName2 + ";";
                                    resultset = db.rawQuery(sql, null);

                                    resultset.moveToNext();
                                    int pure_sum = resultset.getInt(0);
                                    pure_sum += price;

                                    if (pure_sum >= 0 && pure_sum <= 999999999) {
                                        sql = "UPDATE " + tableName2 + " SET pure_won=" + pure_sum + " WHERE info='settings';";

                                        try {
                                            db.execSQL(sql);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        Intent intent = new Intent(getContext(), MainActivity.class);
                                        mainActivity.finish();
                                        getContext().startActivity(intent);

                                        Toast.makeText(getContext(), "값이 정상적으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                        Toast.makeText(getContext(), "계산된 값은 0보다 크고 10억보다 작아야 합니다.", Toast.LENGTH_SHORT).show();

                                    db.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else
                                Toast.makeText(getContext(), "잘못된 가격 입력으로 인해 변경이 취소됩니다.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.show();
                }
            });

            return view;
        }
        else {
            View view = (View) inflater.inflate(R.layout.fragment_history_empty, container, false);

            return view;
        }
    }

    public class CustomList extends ArrayAdapter<String> {
        private final Activity context;

        public CustomList(Activity context) {
            super(context, R.layout. history_list_item, names);
            this.context = context;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.history_list_item, null, true);

            TextView company = (TextView) rowView.findViewById(R.id.hi_company);
            company.setText("|  " + companies[position] + "  |");
            TextView name = (TextView) rowView.findViewById(R.id.hi_name);
            name.setText(names[position]);
            TextView notice = (TextView) rowView.findViewById(R.id.hi_notice);
            notice.setText(notices[position]);
            TextView get = (TextView) rowView.findViewById(R.id.hi_get);
            get.setText(gets[position]);
            TextView prize = (TextView) rowView.findViewById(R.id.hi_prize);
            prize.setText(prizes[position]);
            TextView won = (TextView) rowView.findViewById(R.id.hi_won);
            won.setText(wons[position] + "원");

            return rowView;
        }
    }

    public void DBcheck() {
        db = getContext().openOrCreateDatabase(DBname, Context.MODE_PRIVATE, null);

        if (MainActivity.history_order_case == 1)
            sql = "SELECT name, company, prize, notice, get, won FROM " + tableName + ";";
        else if (MainActivity.history_order_case == 2)
            sql = "SELECT name, company, prize, notice, get, won FROM " + tableName + " ORDER BY name;";
        else if (MainActivity.history_order_case == 3)
            sql = "SELECT name, company, prize, notice, get, won FROM " + tableName + " ORDER BY won DESC;";
        else if (MainActivity.history_order_case == 0)
            sql = "SELECT name, company, prize, notice, get, won FROM " + tableName + " ORDER BY notice DESC;";

        try {
            resultset = db.rawQuery(sql, null);
            DBcount = resultset.getCount();

            if (DBcount > 0) {
                names = new String[DBcount];
                companies = new String[DBcount];
                prizes = new String[DBcount];
                notices = new String[DBcount];
                gets = new String[DBcount];
                wons = new String[DBcount];

                for (int i=0; i<DBcount; i++) {
                    resultset.moveToNext();
                    tempName = resultset.getString(0);
                    tempCompany = resultset.getString(1);
                    tempPrize = resultset.getString(2);
                    tempNotice = resultset.getString(3);
                    tempGet = resultset.getString(4);

                    tempWon = resultset.getInt(5);

                    names[i] = tempName;
                    companies[i] = tempCompany;
                    prizes[i] = tempPrize;
                    notices[i] = tempNotice;
                    gets[i] = tempGet;

                    String won = String.format("%,d", tempWon);

                    wons[i] = won;
                }
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}