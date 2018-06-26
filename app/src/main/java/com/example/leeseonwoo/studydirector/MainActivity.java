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
import android.widget.ImageView;
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

        ImageView CreateBtn = (ImageView) findViewById(R.id.imageButton);
        Button recordBtn = (Button)findViewById(R.id.button2);
        ListView listView = (ListView)findViewById(R.id.listview);

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);

        swipe.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        DBHelper = new DatabaseOpenHelper(getApplicationContext());
        db = DBHelper.getWritableDatabase();
        //updateDB();  데이터베이스 초기화 하시려면 이 주석을 해제하시고 실행하세요! 그 후에 다시 주석처리 하시고 다시 실행 하세요!!!

        int index = 1;
        Cursor cursor = db.rawQuery("select * from MyReadRecord order by _id", null);
        if(count()>0) {
            while (cursor.moveToNext()) {
                int __id = cursor.getInt(cursor.getColumnIndex("_id"));
                String sql1 = "update MyReadRecord set _id = '" + index + "' where _id = '" + __id+"';";
                db.execSQL(sql1);
                index++;
            }
            Log.w("db", "메인 정렬됨");
        }
        cursor.moveToFirst();

        String subject, date, page, dpage;
        boolean checked, aaa = true;

        int img_id;
        if (count() > 0) {
            //ss = "select * from MyReadRecord order by _id";

            while (cursor.moveToNext()) {
                if(aaa){
                    Log.w("aaaaaaaaaaaaaa","true");
                    cursor.moveToFirst();
                }
                img_id = cursor.getInt(cursor.getColumnIndex("Imgid"));
                subject = cursor.getString(cursor.getColumnIndex("Bookname"));
                date = cursor.getString(cursor.getColumnIndex("Date"));
                page = cursor.getString(cursor.getColumnIndex("Page"));
                dpage = cursor.getString(cursor.getColumnIndex("Dpage"));
                checked = cursor.getString(cursor.getColumnIndex("checked")).equals("True");
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                Log.w("cursor", "img : "+img_id+", sub : "+subject+", date : "+date+", page : "+page+", dpage : "+dpage+", checked : "+checked+", id : "+id);
                //Log.w("img",imgID);
                //Log.w("sub",subject);
                //Log.w("date",date);
                //Log.w("page",page);
                //Log.w("dpage",dpage);
                aaa = false;
                customAdapter.addItem(img_id, subject, date, page, dpage, checked);
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this, "position : "+i, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), EndPlanActivity.class);
                int index = 1;
                Cursor cursor = db.rawQuery("select * from MyReadRecord order by _id", null);
                if(count()>0) {
                    while (cursor.moveToNext()) {
                        int __id = cursor.getInt(cursor.getColumnIndex("_id"));
                        String sql1 = "update MyReadRecord set _id = '" + index + "' where _id = '" + __id+"';";
                        db.execSQL(sql1);
                        index++;
                    }
                    Log.w("db", "ㅁㅁㅁㅁ 정렬됨");
                }
                int a= i+1;
                String sql = "select * from MyReadRecord where _id = "+a;
                Cursor cursor1 = db.rawQuery(sql,null);
                cursor1.moveToFirst();
                String page = cursor1.getString(cursor1.getColumnIndex("Page"));
                if(Integer.parseInt(page) == 0) {
                    intent.putExtra("bname", cursor1.getString(cursor1.getColumnIndex("Bookname")));
                    intent.putExtra("imgid", cursor1.getInt(cursor1.getColumnIndex("Imgid")));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "아직 끝나지 않았습니다!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListViewItem item = (ListViewItem)adapterView.getItemAtPosition(i);
                int a=i+1;
                int b = count();
                db.execSQL("delete from MyReadRecord where _id = " + a);
                Toast.makeText(MainActivity.this, "count : "+count(), Toast.LENGTH_SHORT).show();
                if(count()>0 && a!=count()-1) {
                    Cursor cursor = db.rawQuery("select * from MyReadRecord where _id > " + a, null);

                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(cursor.getColumnIndex("_id"));
                        int aa = id - 1;
                        db.execSQL("update MyReadRecord set _id = '" + aa + "' where _id = '" + id + "'");
                    }
                }
/*
                int c = Rcount();
                db.execSQL("delete from MyFinishRecord where _id = "+a);
                if(c==Rcount()) {
                    db.execSQL("delete from MyFinishRecord where _id = " + a);
                }
                if(Rcount()>0 && a!=Rcount()-1) {
                    Cursor cursor1 = db.rawQuery("select * from MyFinishRecord where _id > " + a, null);

                    while (cursor1.moveToNext()) {
                        int id_ = cursor1.getInt(cursor1.getColumnIndex("_id"));
                        int ab = id_ - 1;
                        db.execSQL("update MyFinishRecord set _id = '" + ab + "' where _id = '" + id_ + "'");
                    }
                }*/
                customAdapter.removeItem(item);
                customAdapter.notifyDataSetChanged();
                return false;
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
                boolean checked = data.getStringExtra("checked").equals("True");
                customAdapter.addItem(imgID, subject, date, page, Dpage, checked);
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
        SimpleDateFormat curnow = new SimpleDateFormat("HH");
        SimpleDateFormat min = new SimpleDateFormat("mm");
        SimpleDateFormat sec = new SimpleDateFormat("ss");
        cur = curnow.format(date);
        String m = min.format(date);
        String s = sec.format(date);
        String strNow = sdfnow.format(date);
        tv.setText(strNow);
        //Toast.makeText(this, "hh : "+cur+" m : "+m+" s : "+s, Toast.LENGTH_SHORT).show();
        if(cur.equals("00") && m.equals("00") && s.equals("00")) {
            boolean a = true;
            Cursor cursor = db.rawQuery("select * from MyReadRecord", null);
            while(cursor.moveToNext()) {
                int page = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Page")));
                int dpage = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Dpage")));
                int date1 = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Date")));
                String check = cursor.getString(cursor.getColumnIndex("checked"));
                int id = cursor.getInt(cursor.getColumnIndex("_id"));

                date1--;
                if(check.equals("true")){
                    page = page - dpage;

                }
                db.execSQL("update MyReadRecord set Page = '" + String.valueOf(page) + "', Date = '" + String.valueOf(date1) + "', checked = 'false' where _id = " + id + ";");

                if(page==0){
                    Toast.makeText(this, "계획 하나를 완료했습니다!!!! 소감을 알려주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(date1==0){
                    db.execSQL("delete from MyReadRecord where _id = '"+id+"'");
                    Toast.makeText(this, "당신의 계획중 하나를 실패 하셨네요...", Toast.LENGTH_SHORT).show();
                }
            }
            Toast.makeText(this, "하루가 지났습니다. 새로고침을 하고 체크를 풀어주세요.", Toast.LENGTH_SHORT).show();
        }
        else {
            //Toast.makeText(this, "dkssfasdfasf", Toast.LENGTH_SHORT).show();
        }
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
        Cursor cursor = db.rawQuery("select * from MyFinishRecord", null);
        cnt = cursor.getCount();
        Log.w("column count",String.valueOf(cursor.getColumnCount()));
        Log.w("record count",String.valueOf(cursor.getCount()));
        return cnt;
    }

    @Override
    public void onRefresh() {
        customAdapter.clear();
        int index = 1;
        Cursor cursor4 = db.rawQuery("select * from MyReadRecord order by _id", null);
        if(count()>0) {
            while (cursor4.moveToNext()) {
                int __id = cursor4.getInt(cursor4.getColumnIndex("_id"));
                String sql1 = "update MyReadRecord set _id = '" + index + "' where _id = '" + __id+"';";
                db.execSQL(sql1);
                index++;
            }
            Log.w("db", "메인 정렬됨");
        }
        count();
        String ss = "select * from MyReadRecord order by _id";
        Cursor cursor = db.rawQuery(ss, null);
        while (cursor.moveToNext()){
            int img_id = cursor.getInt(cursor.getColumnIndex("Imgid"));
            String subject = cursor.getString(cursor.getColumnIndex("Bookname"));
            String date = cursor.getString(cursor.getColumnIndex("Date"));
            String page = cursor.getString(cursor.getColumnIndex("Page"));
            String dpage = cursor.getString(cursor.getColumnIndex("Dpage"));
            String checked = cursor.getString(cursor.getColumnIndex("checked"));
            Log.w("cursor", "img : "+img_id+", sub : "+subject+", date : "+date+", page : "+page+", dpage : "+dpage+", checked : "+checked);

            //Log.w("img",imgID);
            //Log.w("sub",subject);
            //Log.w("date",date);
            //Log.w("page",page);
            //Log.w("dpage",dpage);
            customAdapter.addItem(img_id, subject, date, page, dpage, Boolean.valueOf(checked));
        }
        customAdapter.notifyDataSetChanged();

        swipe.setRefreshing(false);
    }
    public void updateDB(){
        db.execSQL("drop table MyReadRecord");
        db.execSQL("drop table MyFinishRecord");
        db.execSQL("create table MyReadRecord (_id integer PRIMARY KEY autoincrement, Bookname text, Page text, Dpage text, Imgid integer, Date text, checked text);");
        db.execSQL("create table MyFinishRecord (_id integer PRIMARY KEY autoincrement, Bookname text, Imgid integer, Rdate text, Record text);");
        db.execSQL("delete from MyReadRecord");
        db.execSQL("delete from MyFinishRecord");
    }
}
