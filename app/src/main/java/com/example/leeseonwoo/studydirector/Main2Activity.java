package com.example.leeseonwoo.studydirector;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    DatabaseOpenHelper DBHelper;
    SQLiteDatabase db;

    CustomAdapter2 customAdapter2 = new CustomAdapter2();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_activity);

        ListView listView = (ListView)findViewById(R.id.listView2);

        DBHelper = new DatabaseOpenHelper(getApplicationContext());
        db = DBHelper.getWritableDatabase();

        if(count()==0){
            customAdapter2.clear();
        }

        int index = 1;

        Cursor cursor1 = db.rawQuery("select * from MyFinishRecord", null);
        while (cursor1.moveToNext()){
            int __id = cursor1.getInt(cursor1.getColumnIndex("_id"));
            String sql1 = "update MyFinishRecord set _id = "+index+" where _id = "+__id;
            db.execSQL(sql1);
            index++;
        }
        Log.w("db", "메인2 정렬됨");
        String subject, rdate, record;
        boolean aaa = true;
        cursor1.moveToFirst();
        int img_id;
        if (count() > 0) {
            //ss = "select * from MyReadRecor order by _id";

            while (true) {
                if(aaa){
                    Log.w("aaaaaaaaaaaaaa","true");
                    cursor1.moveToFirst();
                }

                img_id = cursor1.getInt(cursor1.getColumnIndex("Imgid"));
                subject = cursor1.getString(cursor1.getColumnIndex("Bookname"));
                rdate = cursor1.getString(cursor1.getColumnIndex("Rdate"));
                record = cursor1.getString(cursor1.getColumnIndex("Record"));
                int id = cursor1.getInt(cursor1.getColumnIndex("_id"));
                Log.w("cursor", "img : "+img_id+", sub : "+subject+", rdate : "+rdate+", id : "+id);
                //Log.w("img",imgID);
                //Log.w("sub",subject);
                //Log.w("date",date);
                //Log.w("page",page);
                //Log.w("dpage",dpage);
                aaa = false;
                customAdapter2.addItem(img_id, subject, record, rdate);
                if(!cursor1.moveToNext()){
                    break;
                }
            }
            customAdapter2.notifyDataSetChanged();
        }

        listView.setAdapter(customAdapter2);

        Button backBtn = findViewById(R.id.button_1);
        //Toast.makeText(this,"아직 아무 기록도 없습니다. 공부나 하세요.",Toast.LENGTH_SHORT).show();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }
    private int count(){
        int cnt=0;
        Cursor cursor = db.rawQuery("select * from MyFinishRecord", null);
        cnt = cursor.getCount();
        Log.w("column count",String.valueOf(cursor.getColumnCount()));
        Log.w("record count",String.valueOf(cnt));
        return cnt;
    }
}
