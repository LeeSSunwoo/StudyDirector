package com.example.leeseonwoo.studydirector;

/**
 * Created by leeseonwoo on 2018. 5. 8..
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CustomAdapter extends BaseAdapter{
    //Item 데이터를 저장할 리스트
    List<ListViewItem> listViewItemList = new ArrayList<>();
    DatabaseOpenHelper DBHelper;
    SQLiteDatabase db;



    int a=0;



    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void addItem(int imgId, String title, String time, String page, String chpage, boolean checked) {
        ListViewItem item = new ListViewItem();
        item.setImageID(imgId);
        item.setTitleStr(title);
        item.setTimeStr(time);
        item.setPageStr(page);
        item.setCheckStr(chpage);
        item.setChecked(checked);

        listViewItemList.add(item);
    }



    public void removeItem (ListViewItem item){
        listViewItemList.remove(item);
    }

    @Override //ListView에 뿌려질 item을 제공한다.
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        if(view==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.listview_item, viewGroup, false);
        }

        //현재 선택된 View에 연결되는 객체 생성
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        TextView titleTextView=(TextView)view.findViewById(R.id.textView);
        TextView timeTextView=(TextView)view.findViewById(R.id.textView2);
        TextView pageTextView=(TextView)view.findViewById(R.id.textView3);
        TextView checkBox=(CheckBox)view.findViewById(R.id.checkBox);
        CheckBox cb = (CheckBox)view.findViewById(R.id.checkBox);

        ListViewItem item = listViewItemList.get(i);
        DBHelper = new DatabaseOpenHelper(context);
        db = DBHelper.getWritableDatabase();
        /*int index = 1;
        Cursor cursor = db.rawQuery("select * from MyReadRecord order by _id", null);
        while (cursor.moveToNext()){
            int __id = cursor.getInt(cursor.getColumnIndex("_id"));
            String sql1 = "update MyReadRecord set _id = "+index+" where _id = "+__id;
            db.execSQL(sql1);
            index++;
        }
        Log.w("db","커스텀 정렬됨");*/
        cb.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int index = 1;
                Cursor cursor = db.rawQuery("select * from MyReadRecord order by _id", null);
                while (cursor.moveToNext()){
                    int __id = cursor.getInt(cursor.getColumnIndex("_id"));
                    String sql1 = "update MyReadRecord set _id = "+index+" where _id = "+__id;
                    db.execSQL(sql1);
                    index++;
                }
                Log.w("db","커스텀 정렬됨1");
                Toast.makeText(context, "flag: "+b+", position : "+i, Toast.LENGTH_SHORT).show();
                int a = i+1;
                cursor = db.rawQuery("select * from MyReadRecord where _id = "+a, null);
                cursor.moveToFirst();
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                Toast.makeText(context, "id : "+id, Toast.LENGTH_SHORT).show();
                String s = "update MyReadRecord set checked = '"+String.valueOf(b)+"' where _id = "+a+";";
                db.execSQL(s);
            }
        });

        //현재 선택된 View에 데이터 삽입
        imageView.setImageResource(item.getImageID());
        titleTextView.setText(item.getTitleStr());
        timeTextView.setText(item.getTimeStr()+"일 남았습니다.");
        pageTextView.setText(item.getPageStr()+"페이지 남았습니다.");

        Cursor cursor1 = db.rawQuery("select * from MyReadRecord where _id = "+a, null);
        cursor1.moveToFirst();
        if(cursor1.getCount()>0) {

            String checked = cursor1.getString(cursor1.getColumnIndex("checked"));
            if (!Boolean.valueOf(checked)) {
                checkBox.setText("오늘 " + item.getCheckStr() + "페이지");
            } else {
                checkBox.setText("하루 목표 완료");
            }
        } else {
            checkBox.setText("오늘 " + item.getCheckStr() + "페이지");
        }

        return view;

    }
    public void clear(){
        listViewItemList.clear();
    }


}
