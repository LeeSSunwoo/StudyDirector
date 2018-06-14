package com.example.leeseonwoo.studydirector;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class createPlan extends AppCompatActivity {
    Intent intent = new Intent();
    int imgID;
    String str;
    DatabaseOpenHelper DBHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);

        Button btn = (Button)findViewById(R.id.button3);
        final EditText titleText = (EditText)findViewById(R.id.editText);
        final EditText pageText = (EditText)findViewById(R.id.editText2);
        Spinner subject = (Spinner)findViewById(R.id.spinner);
        final EditText dateText = (EditText)findViewById(R.id.dateT);

        ArrayAdapter subAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.subject));
        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subject.setAdapter(subAdapter);

        subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String page = pageText.getText().toString();
                String date = dateText.getText().toString();
                String Bname = titleText.getText().toString();
                switch (str)
                {
                    case "Node.js": imgID = R.drawable.nodejsimg;
                        break;
                    case "C++": imgID = R.drawable.cppimg;
                        break;
                    case "JAVA": imgID = R.drawable.javaimg;
                        break;
                    case "C#" : imgID = R.drawable.csh;
                        break;
                    case "기타 도서" : imgID = R.drawable.book;
                        break;
                }
                intent.putExtra("subject",str);
                intent.putExtra("imgID", String.valueOf(imgID));
                intent.putExtra("Bname",Bname);
                intent.putExtra("page",page);
                intent.putExtra("date",date);
                int Dpage = Integer.parseInt(page)/Integer.parseInt(date);
                intent.putExtra("Dpage", String.valueOf(Dpage));
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
