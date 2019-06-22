package com.example.jeongyoonsung.eventplanner;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventList extends Fragment {
    ListView list;
    SwipeRefreshLayout mRefresh;
    CustomList adapter;
    Dialog dialog;

    SQLiteDatabase db;
    String sql;
    String DBname = "EventPlanner";
    String tableName = "EVENTLIST";
    Cursor resultset;
    int DBcount;

    String[] names;
    String[] companies;
    String[] prizes;
    String[] ends;
    String[] notices;
    String[] links;
    String[] days;

    String tempName;
    String tempCompany;
    String tempPrize;
    String tempDay;
    String tempEnd;
    String tempLink;
    String today;

    int[] cat1;
    int[] cat2;
    int[] cat3;
    int[] cat4;
    int[] cat5;
    int[] cat6;

    int tempCat1;
    int tempCat2;
    int tempCat3;
    int tempCat4;
    int tempCat5;
    int tempCat6;

    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        DBcheck();

        if (DBcount != 0) {
            View view = (View) inflater.inflate(R.layout.fragment_event_list, container, false);

            adapter = new CustomList(getActivity());
            list = (ListView) view.findViewById(R.id.event_list);
            list.setAdapter(adapter);

            mRefresh = view.findViewById(R.id.refresh);
            mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(EventList.this).attach(EventList.this).commitAllowingStateLoss();

                    mRefresh.setRefreshing(false);
                }
            });

            return view;
        }
        else {
            View view = (View) inflater.inflate(R.layout.fragment_event_list_empty, container, false);

            return view;
        }
    }

    public class CustomList extends ArrayAdapter<String> {
        private final Activity context;

        public CustomList(Activity context) {
            super(context, R.layout.event_list_item, names);
            this.context = context;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.event_list_item, null, true);

            TextView name = (TextView) rowView.findViewById(R.id.event_name);
            name.setText(names[position]);
            TextView company = (TextView) rowView.findViewById(R.id.event_company);
            company.setText(companies[position]);
            TextView prize = (TextView) rowView.findViewById(R.id.event_prize);
            prize.setText(prizes[position]);
            TextView day = (TextView) rowView.findViewById(R.id.day_count);
            day.setText(days[position]);

            button = (Button) rowView.findViewById(R.id.event_check);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    dialog = new CheckDialog(getContext());

                    CheckDialog.check_name = names[position];
                    CheckDialog.check_company = companies[position];
                    CheckDialog.check_prize = prizes[position];
                    CheckDialog.check_link = links[position];
                    CheckDialog.check_end = ends[position];
                    CheckDialog.check_notice = notices[position];

                    CheckDialog.check_cat1 = cat1[position];
                    CheckDialog.check_cat2 = cat2[position];
                    CheckDialog.check_cat3 = cat3[position];
                    CheckDialog.check_cat4 = cat4[position];
                    CheckDialog.check_cat5 = cat5[position];
                    CheckDialog.check_cat6 = cat6[position];

                    dialog.show();
                }
            });

            return rowView;
        }
     }

    public void DBcheck() {
        db = getContext().openOrCreateDatabase(DBname, Context.MODE_PRIVATE, null);

        if (MainActivity.event_order_bool == 1)
            sql = "SELECT name, company, prize, notice, end, link, cat1, cat2, cat3, cat4, cat5, cat6 FROM " + tableName + ";";
        else if (MainActivity.event_order_bool == 0)
            sql = "SELECT name, company, prize, notice, end, link, cat1, cat2, cat3, cat4, cat5, cat6 FROM " + tableName + " ORDER BY notice;";

        try {
            resultset = db.rawQuery(sql, null);
            DBcount = resultset.getCount();

            if (DBcount > 0) {
                names = new String[DBcount];
                companies = new String[DBcount];
                prizes = new String[DBcount];
                ends = new String[DBcount];
                notices = new String[DBcount];
                links = new String[DBcount];
                days = new String[DBcount];

                cat1 = new int[DBcount];
                cat2 = new int[DBcount];
                cat3 = new int[DBcount];
                cat4 = new int[DBcount];
                cat5 = new int[DBcount];
                cat6 = new int[DBcount];

                long now = System.currentTimeMillis();
                Date date = new Date(now);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                today = sdf.format(date);

                for (int i=0; i<DBcount; i++) {
                    resultset.moveToNext();
                    tempName = resultset.getString(0);
                    tempCompany = resultset.getString(1);
                    tempPrize = resultset.getString(2);
                    tempDay = resultset.getString(3);
                    tempEnd = resultset.getString(4);
                    tempLink = resultset.getString(5);

                    tempCat1 = resultset.getInt(6);
                    tempCat2 = resultset.getInt(7);
                    tempCat3 = resultset.getInt(8);
                    tempCat4 = resultset.getInt(9);
                    tempCat5 = resultset.getInt(10);
                    tempCat6 = resultset.getInt(11);

                    names[i] = tempName;
                    companies[i] = tempCompany;
                    prizes[i] = tempPrize;
                    notices[i] = tempDay;
                    ends[i] = tempEnd;
                    links[i] = tempLink;

                    cat1[i] = tempCat1;
                    cat2[i] = tempCat2;
                    cat3[i] = tempCat3;
                    cat4[i] = tempCat4;
                    cat5[i] = tempCat5;
                    cat6[i] = tempCat6;

                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        Date startDate = sdf2.parse(today);
                        Date endDate = sdf2.parse(tempDay);

                        long difference = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);

                        if (difference > 0)
                            days[i] = "D-" + difference;
                        else if (difference == 0)
                            days[i] = "D-Day";
                        else
                            days[i] = "D+" + Math.abs(difference);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}