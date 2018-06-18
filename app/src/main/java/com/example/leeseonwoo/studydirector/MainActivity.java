package com.example.leeseonwoo.studydirector;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    DatabaseOpenHelper DBHelper;
    SQLiteDatabase db;
    int imgID;
    long now;
    TextView tv;
    String cur,after;
    SwipeRefreshLayout swipe;

    CustomAdapter customAdapter = new CustomAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        tv = (TextView)findViewById(R.id.tv);

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateYOURthing();
                            }

                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();

        ImageButton CreateBtn = (ImageButton)findViewById(R.id.imageButton);
        Button recordBtn = (Button)findViewById(R.id.button2);
        ListView listView = (ListView)findViewById(R.id.listview);

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);

        DBHelper = new DatabaseOpenHelper(getApplicationContext());
        db = DBHelper.getWritableDatabase();
/*
        db.execSQL("drop table MyReadRecord");
        db.execSQL("create table MyReadRecord (_id integer PRIMARY KEY autoincrement, Bookname text, Page text, Dpage text, Imgid integer, Date text, Rdate text, Record text, checked boolean);");
        db.execSQL("delete from MyReadRecord");
*/
        String subject, date, page, dpage, ss;

        int img_id;
        if(count()>0) {
            ss = "select * from MyReadRecord order by _id";
            Cursor cursor = db.rawQuery(ss, null);
            while (cursor.moveToNext()){
                img_id = cursor.getInt(cursor.getColumnIndex("Imgid"));
                subject = cursor.getString(cursor.getColumnIndex("Bookname"));
                date = cursor.getString(cursor.getColumnIndex("Date"));
                page = cursor.getString(cursor.getColumnIndex("Page"));
                dpage = cursor.getString(cursor.getColumnIndex("Dpage"));


                //Log.w("img",imgID);
                //Log.w("sub",subject);
                //Log.w("date",date);
                //Log.w("page",page);
                //Log.w("dpage",dpage);
                customAdapter.addItem(img_id, subject, date, page, dpage);
            }
            customAdapter.notifyDataSetChanged();
        }
        //db.execSQL("delete from MyReadRecord");

        listView.setAdapter(customAdapter);

        CreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), createPlan.class);
                startActivityForResult(intent2, 111);
            }
        });

        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(intent3);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {
            if(requestCode == 111) {

                imgID = Integer.parseInt(data.getStringExtra("imgID"));
                String subject = data.getStringExtra("Bname");
                String date = data.getStringExtra("date");
                String page = data.getStringExtra("page");
                String Dpage = data.getStringExtra("Dpage");
                customAdapter.addItem(imgID, subject, date, page, Dpage);
                customAdapter.notifyDataSetChanged();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    private int check() {
        now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat curnow = new SimpleDateFormat("dd");
        after = curnow.format(date);
        if(cur != after){
            return 1;
        }
        else {
            return 0;
        }
    }

    private void updateYOURthing() {
        now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfnow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat curnow = new SimpleDateFormat("dd");
        cur = curnow.format(date);
        String strNow = sdfnow.format(date);
        tv.setText(strNow);
    }

    private int count(){
        int cnt=0;
        Cursor cursor = db.rawQuery("select * from MyReadRecord", null);
        cnt = cursor.getCount();
        Log.w("column count",String.valueOf(cursor.getColumnCount()));
        Log.w("record count",String.valueOf(cnt));
        return cnt;
    }
    private int Rcount(){
        int cnt=0;
        Cursor cursor = db.rawQuery("select * from MyReadRecord", null);
        cnt = cursor.getColumnCount();
        Log.w("column count",String.valueOf(cursor.getColumnCount()));
        Log.w("record count",String.valueOf(cursor.getCount()));
        return cnt;
    }

    @Override
    public void onRefresh() {
        String ss = "select * from MyReadRecord order by _id";
        Cursor cursor = db.rawQuery(ss, null);
        while (cursor.moveToNext()){
            int img_id = cursor.getInt(cursor.getColumnIndex("Imgid"));
            String subject = cursor.getString(cursor.getColumnIndex("Bookname"));
            String date = cursor.getString(cursor.getColumnIndex("Date"));
            String page = cursor.getString(cursor.getColumnIndex("Page"));
            String dpage = cursor.getString(cursor.getColumnIndex("Dpage"));


            //Log.w("img",imgID);
            //Log.w("sub",subject);
            //Log.w("date",date);
            //Log.w("page",page);
            //Log.w("dpage",dpage);
            customAdapter.addItem(img_id, subject, date, page, dpage);
        }
        customAdapter.notifyDataSetChanged();
    }

    public class AlarmHATT {
        private Context context;
        public AlarmHATT(Context context) {
            this.context=context;
        }
        public void Alarm() {
            AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(MainActivity.this, BroadcastD.class);

            PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            //알람시간 calendar에 set해주기

            calendar.set(Calendar.HOUR_OF_DAY, 24);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            //알람 예약
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        }
    }
}
