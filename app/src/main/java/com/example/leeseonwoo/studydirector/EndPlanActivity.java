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

public class EndPlanActivity extends AppCompatActivity {

    int id;
    Button send;
    DatabaseOpenHelper DBHelper;
    SQLiteDatabase db;
    String article;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_plan);
        final EditText editText = (EditText)findViewById(R.id.editText3);
        send = (Button)findViewById(R.id.button3);

        final Intent intent1 = getIntent();

        DBHelper = new DatabaseOpenHelper(getApplicationContext());
        db = DBHelper.getWritableDatabase();
        int index = 1;
        Cursor cursor = db.rawQuery("select * from MyReadRecord order by _id", null);
        while (cursor.moveToNext()){
            int __id = cursor.getInt(cursor.getColumnIndex("_id"));
            String sql1 = "update MyReadRecord set _id = "+index+" where _id = "+__id;
            db.execSQL(sql1);
            index++;
        }
        Log.w("db","마무리 정렬됨");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                article = editText.getText().toString();
                id = intent1.getExtras().getInt("id");
                db.execSQL("update MyReadRecord set Record = '"+article+"' where _id = '"+id+"';");

                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                finish();
            }
        });
    }
}
