package com.example.leeseonwoo.studydirector;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseOpenHelper DBHelper;
    SQLiteDatabase db;

    CustomAdapter customAdapter = new CustomAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent1 = new Intent(this, SplashActivity.class);
        startActivity(intent1);

        ImageButton CreateBtn = (ImageButton)findViewById(R.id.imageButton);
        Button recordBtn = (Button)findViewById(R.id.button2);
        ListView listView = (ListView)findViewById(R.id.listView);
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
                Toast.makeText(MainActivity.this,"아직 남은 페이지가 있습니다. 더 공부하세요.",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 111) {
            int imgID = Integer.parseInt(data.getStringExtra("imgID"));
            String subject = data.getStringExtra("Bname");
            String date = data.getStringExtra("date");
            String page = data.getStringExtra("page");
            String Dpage = data.getStringExtra("Dpage");
            customAdapter.addItem(imgID, subject, date, page, Dpage);
            customAdapter.notifyDataSetChanged();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

    }

}
