package com.example.leeseonwoo.studydirector;

/**
 * Created by leeseonwoo on 2018. 6. 19..
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by GHKwon on 2016-02-17.
 */
public class BroadcastD extends BroadcastReceiver {
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;
    DatabaseOpenHelper DBHelper;
    SQLiteDatabase db;
    @Override
    public void onReceive(Context context, Intent intent) {//알람 시간이 되었을때 onReceive를 호출함
        DBHelper = new DatabaseOpenHelper(context);
        db = DBHelper.getWritableDatabase();
        boolean a = true;
        Cursor cursor = db.rawQuery("select * from MyReadRecord where checked = "+String.valueOf(a), null);
        while(cursor.moveToNext()) {
            int page = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Page")));
            int dpage = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Dpage")));
            int date = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Date")));
            Toast.makeText(context, "page : " + page + " dpage : " + dpage, Toast.LENGTH_SHORT).show();
            page = page - dpage;
            date--;
            db.execSQL("update MyReadRecord set Page = '" + String.valueOf(page) + "', Date = '" + String.valueOf(date) + "' where checked = '" + String.valueOf(a) + "';");
        }
    }
}

