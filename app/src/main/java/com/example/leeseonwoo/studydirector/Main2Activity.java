package com.example.leeseonwoo.studydirector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_activity);

        Button backBtn = findViewById(R.id.button_1);
        Toast.makeText(this,"아직 아무 기록도 없습니다. 공부나 하세요.",Toast.LENGTH_SHORT).show();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }
}
