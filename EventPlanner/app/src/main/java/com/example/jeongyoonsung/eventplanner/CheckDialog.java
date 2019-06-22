package com.example.jeongyoonsung.eventplanner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckDialog extends Dialog {
    MainActivity mainActivity = (MainActivity)MainActivity.main_activity;

    SQLiteDatabase db;
    String DBname = "EventPlanner";
    String tableName = "EVENTLIST";
    String tableName2 = "HISTORY";
    String tableName3 = "SETTINGS";
    String sql;
    Cursor resultset;

    AlertDialog.Builder builder;
    AlertDialog.Builder builder2;
    EditText builder_prize;
    EditText builder_price;
    Boolean check;

    private static final int LAYOUT = R.layout.event_list_check;
    public static String check_name = "";
    public static String check_company = "";
    public static String check_end = "";
    public static String check_notice = "";
    public static String check_prize = "";
    public static String check_link = "";

    public static int check_cat1 = 0;
    public static int check_cat2 = 0;
    public static int check_cat3 = 0;
    public static int check_cat4 = 0;
    public static int check_cat5 = 0;
    public static int check_cat6 = 0;

    String get_prize;
    String prize_price;

    TextView ch_name;
    TextView ch_company;
    TextView ch_end;
    TextView ch_notice;
    TextView ch_prize;

    ImageView ch_yes;
    ImageView ch_no;
    ImageView ch_web;
    ImageView ch_link;
    ImageView ch_close;

    ImageView ch_cat1;
    ImageView ch_cat2;
    ImageView ch_cat3;
    ImageView ch_cat4;
    ImageView ch_cat5;
    ImageView ch_cat6;

    int prize_count;
    int sum_won;

    public CheckDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        ch_name = (TextView) findViewById(R.id.ch_name);
        ch_company = (TextView) findViewById(R.id.ch_company);
        ch_end = (TextView) findViewById(R.id.ch_end);
        ch_notice = (TextView) findViewById(R.id.ch_notice);
        ch_prize = (TextView) findViewById(R.id.ch_prize);

        ch_yes = (ImageView) findViewById(R.id.yes);
        ch_no = (ImageView) findViewById(R.id.no);
        ch_web = (ImageView) findViewById(R.id.check_web);
        ch_link = (ImageView) findViewById(R.id.check_link);
        ch_close = (ImageView) findViewById(R.id.check_close);

        ch_cat1 = (ImageView) findViewById(R.id.cat1);
        ch_cat2 = (ImageView) findViewById(R.id.cat2);
        ch_cat3 = (ImageView) findViewById(R.id.cat3);
        ch_cat4 = (ImageView) findViewById(R.id.cat4);
        ch_cat5 = (ImageView) findViewById(R.id.cat5);
        ch_cat6 = (ImageView) findViewById(R.id.cat6);

        ch_name.setText(check_name);
        ch_company.setText(check_company);
        ch_end.setText("[마감]  " + check_end + "     |     ");
        ch_notice.setText("[발표]  " + check_notice);
        ch_prize.setText(check_prize);

        if (check_cat1 == 1)
            ch_cat1.setImageResource(R.drawable.category1_on);
        if (check_cat2 == 1)
            ch_cat2.setImageResource(R.drawable.category2_on);
        if (check_cat3 == 1)
            ch_cat3.setImageResource(R.drawable.category3_on);
        if (check_cat4 == 1)
            ch_cat4.setImageResource(R.drawable.category4_on);
        if (check_cat5 == 1)
            ch_cat5.setImageResource(R.drawable.category5_on);
        if (check_cat6 == 1)
            ch_cat6.setImageResource(R.drawable.category6_on);

        ch_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(getContext());

                builder.setTitle("이벤트 당첨 여부 확인 (1/2)");
                builder.setMessage("당첨된 경품을 확인합니다. 어떤 상품에 당첨되셨나요?\n(경품 명은 15자 이하로 작성해주세요.)");
                builder_prize = new EditText(getContext());
                builder_prize.setSingleLine(true);
                builder_prize.setPadding(60, 50, 60, 50);
                builder.setView(builder_prize);

                builder.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                    }
                });

                builder.setNegativeButton("다음", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        get_prize = builder_prize.getText().toString();

                        if (get_prize.length() <= 15) {
                            if (get_prize.length() != 0 && get_prize.toString().toCharArray()[0] != ' ') {
                                builder2 = new AlertDialog.Builder(getContext());

                                builder2.setTitle("이벤트 당첨 여부 확인 (2/2)");
                                builder2.setMessage("해당 상품의 가격은 얼마입니까?\n(10억 미만의 숫자만 입력하고, \n가격을 모르는 경우 0으로 입력해주세요.)");
                                builder_price = new EditText(getContext());
                                builder_price.setSingleLine(true);
                                builder_price.setPadding(60, 50, 60, 50);
                                builder2.setView(builder_price);

                                builder2.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick (DialogInterface dialog, int which) {
                                    }
                                });

                                builder2.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick (DialogInterface dialog, int which) {
                                        prize_price = builder_price.getText().toString();

                                        if (prize_price.length() > 0 && prize_price.length() <= 9) {
                                            for (int i=0; i<prize_price.length(); i++) {
                                                if (prize_price.toString().toCharArray()[i] >= 48 && prize_price.toString().toCharArray()[i] <= 57)
                                                    check = true;
                                                else
                                                    check = false;
                                            }
                                        }
                                        else
                                            check = false;

                                        if (check == true) {
                                            int price = Integer.parseInt(prize_price);

                                            long now = System.currentTimeMillis();
                                            Date date = new Date(now);

                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                            String today = sdf.format(date);

                                            db = getContext().openOrCreateDatabase(DBname, Context.MODE_PRIVATE, null);

                                            try {
                                                sql = "INSERT INTO " + tableName2 + "(name, company, prize, notice, get, won) VALUES ('" + check_name + "', '" +
                                                        check_company + "', '" + get_prize + "', '" + check_notice + "', '" + today + "', " + price + ");";
                                                db.execSQL(sql);

                                                sql = "DELETE FROM " + tableName + " WHERE name='" + check_name + "' AND company='" + check_company + "' AND prize='" + check_prize + "'" +
                                                        " AND notice='" + check_notice + "' AND link='" + check_link + "';";
                                                db.execSQL(sql);

                                                sql = "SELECT prize_count, sum_won FROM " + tableName3 + ";";
                                                resultset = db.rawQuery(sql, null);

                                                resultset.moveToNext();

                                                prize_count = resultset.getInt(0);
                                                sum_won = resultset.getInt(1);

                                                int temp_prize_count = prize_count + 1;
                                                int temp_sum_won = sum_won + price;

                                                sql = "UPDATE " + tableName3 + " SET prize_count=" + temp_prize_count + " WHERE info='settings';";
                                                db.execSQL(sql);

                                                sql = "UPDATE " + tableName3 + " SET sum_won=" + temp_sum_won + " WHERE info='settings';";
                                                db.execSQL(sql);

                                                db.close();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            Intent intent = new Intent(getContext(), MainActivity.class);
                                            dismiss();
                                            mainActivity.finish();
                                            getContext().startActivity(intent);

                                            Toast.makeText(getContext(), "당첨 기록에 이벤트가 등록되었습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                            Toast.makeText(getContext(), "잘못된 가격 입력으로 인해 등록이 취소됩니다.", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                builder2.show();
                            }
                            else
                                Toast.makeText(getContext(), "첫 칸에 공백 없이 상품을 한 글자 이상으로\n다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getContext(), "경품 명을 15자 이내로 작성해주세요.", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });

        ch_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(getContext());

                builder.setTitle("이벤트 삭제 여부 확인");
                builder.setMessage("이벤트 미당첨으로 인해 해당 이벤트를\n기록에서 삭제합니다. 진행 하시겠습니까?");

                builder.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                    }
                });

                builder.setNegativeButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        db = getContext().openOrCreateDatabase(DBname, Context.MODE_PRIVATE, null);

                        sql = "DELETE FROM " + tableName + " WHERE name='" + check_name + "' AND company='" + check_company + "' AND prize='" + check_prize + "'" +
                                " AND notice='" + check_notice + "' AND link='" + check_link + "';";
                        db.execSQL(sql);

                        Intent intent = new Intent(getContext(), MainActivity.class);
                        dismiss();
                        mainActivity.finish();
                        getContext().startActivity(intent);

                        Toast.makeText(getContext(), "이벤트를 기록에서 삭제했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });

        ch_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Web.class);
                Web.web_url = check_link;
                getContext().startActivity(intent);
            }
        });

        ch_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("link", check_link);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getContext(), "링크가 복사되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        ch_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}