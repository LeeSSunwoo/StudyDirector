package com.example.leeseonwoo.studydirector;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EndPlanActivity extends AppCompatActivity {

    Button send;
    DatabaseOpenHelper DBHelper;
    SQLiteDatabase db;
    String article;
    long now = System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_plan);
        final EditText editText = (EditText)findViewById(R.id.editText3);
        send = (Button)findViewById(R.id.button3);

        Date date = new Date(now);
        SimpleDateFormat D = new SimpleDateFormat("yyyy-MM-dd");
        final String curDay = D.format(date);

        final Intent intent1 = getIntent();

        DBHelper = new DatabaseOpenHelper(getApplicationContext());
        db = DBHelper.getWritableDatabase();
        int index = 1;
        final Cursor cursor = db.rawQuery("select * from MyReadRecord order by _id", null);
        while (cursor.moveToNext()){
            int __id = cursor.getInt(cursor.getColumnIndex("_id"));
            String sql1 = "update MyReadRecord set _id = "+index+" where _id = "+__id;
            db.execSQL(sql1);
            index++;
        }
        Cursor cursor1 = db.rawQuery("select * from MyFinishRecord order by _id", null);
        while (cursor1.moveToNext()){
            int __id = cursor1.getInt(cursor.getColumnIndex("_id"));
            String sql1 = "update MyReadRecord set _id = "+index+" where _id = "+__id;
            db.execSQL(sql1);
            index++;
        }
        Log.w("db","마무리 정렬됨");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                article = editText.getText().toString();
                String Bname = intent1.getExtras().getString("bname");
                int imgID = intent1.getExtras().getInt("imgid");
                int a= count();
                db.execSQL("delete from MyReadRecord where Bookname = '"+Bname+"'");
                if(a==count()){
                    db.execSQL("delete from MyReadRecord where Bookname = '" + Bname+"'");
                }

                //Toast.makeText(EndPlanActivity.this, "count : "+count(), Toast.LENGTH_SHORT).show();
                String sql2 = "insert into MyFinishRecord (Bookname, Imgid, Rdate, Record) VALUES('"+Bname+"', '"+imgID+"', '"+curDay+"', '"+article+"');";
                db.execSQL(sql2);
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private int count(){
        int cnt=0;
        Cursor cursor = db.rawQuery("select * from MyReadRecord", null);
        cnt = cursor.getCount();
        Log.w("column count",String.valueOf(cursor.getColumnCount()));
        Log.w("record count",String.valueOf(cnt));
        return cnt;
    }
}
