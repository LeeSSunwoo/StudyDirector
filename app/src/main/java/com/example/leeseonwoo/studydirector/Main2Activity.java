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

        Intent intent = getIntent();

        int index = 1;
        Cursor cursor = db.rawQuery("select * from MyReadRecord where Record is not null order by _id", null);
        if(count()>0) {
            while (cursor.moveToNext()) {
                int __id = cursor.getInt(cursor.getColumnIndex("_id"));
                String sql1 = "update MyReadRecord set _id = '" + index + "' where _id = '" + __id+"';";
                db.execSQL(sql1);
                index++;
            }
            Log.w("db", "메인2 정렬됨");
        }
        cursor.moveToFirst();

        String subject, rdate, record;
        boolean checked, aaa = true;

        int img_id;
        if (count() > 0) {
            //ss = "select * from MyReadRecor order by _id";

            while (cursor.moveToNext()) {
                if(aaa){
                    Log.w("aaaaaaaaaaaaaa","true");
                    cursor.moveToFirst();
                }
                img_id = cursor.getInt(cursor.getColumnIndex("Imgid"));
                subject = cursor.getString(cursor.getColumnIndex("Bookname"));
                rdate = cursor.getString(cursor.getColumnIndex("Rdate"));
                record = cursor.getString(cursor.getColumnIndex("Record"));
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                Log.w("cursor", "img : "+img_id+", sub : "+subject+", rdate : "+rdate+", id : "+id);
                //Log.w("img",imgID);
                //Log.w("sub",subject);
                //Log.w("date",date);
                //Log.w("page",page);
                //Log.w("dpage",dpage);
                aaa = false;
                customAdapter2.addItem(img_id, subject, record, rdate);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int _id = data.getExtras().getInt("id");
        Toast.makeText(this, "id : "+_id, Toast.LENGTH_SHORT).show();
        Cursor cursor = db.rawQuery("select * from MyReadRecord where _id = "+_id, null);
        int imgID = cursor.getInt(cursor.getColumnIndex("Imgid"));
        String subject = cursor.getString(cursor.getColumnIndex("Bookname"));
        String date = cursor.getString(cursor.getColumnIndex("Rdate"));
        String record = cursor.getString(cursor.getColumnIndex("Record"));
        customAdapter2.addItem(imgID, subject, record, date);
        customAdapter2.notifyDataSetChanged();

    }
    private int count(){
        int cnt=0;
        Cursor cursor = db.rawQuery("select * from MyReadRecord where Record is not null", null);
        cnt = cursor.getCount();
        Log.w("column count",String.valueOf(cursor.getColumnCount()));
        Log.w("record count",String.valueOf(cnt));
        return cnt;
    }
}
