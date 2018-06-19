package com.example.leeseonwoo.studydirector;

/**
 * Created by leeseonwoo on 2018. 5. 9..
 */

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter2 extends BaseAdapter{
    //Item 데이터를 저장할 리스트
    List<ListViewItem2> listViewItemList2 = new ArrayList<>();





    @Override
    public int getCount() {
        return listViewItemList2.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewItemList2.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void addItem(int imgId, String title,String desc, String rdate) {
        ListViewItem2 item = new ListViewItem2();
        item.setImgID2(imgId);
        item.setTitle(title);
        item.setDesc(desc);
        item.setRdate(rdate);

        listViewItemList2.add(item);
    }

    public void removeItem (ListViewItem2 item){
        listViewItemList2.remove(item);
    }

    @Override //ListView에 뿌려질 item을 제공한다.
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();

        if(view==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.listview_item2, viewGroup, false);
        }
        Log.w("getview","getview 실행 됬다 시바리아러ㅗ미러ㅏㅗ미러ㅏㅗ미ㅓㅏ롬이ㅏㅓㅇㄹ호");

        //현재 선택된 View에 연결되는 객체 생성
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView2);
        TextView titleTextView=(TextView)view.findViewById(R.id.textView7);
        TextView DescTextView=(TextView)view.findViewById(R.id.textView8);
        TextView DateView = (TextView)view.findViewById(R.id.textView10);

        ListViewItem2 item = listViewItemList2.get(i);

        //현재 선택된 View에 데이터 삽입
        imageView.setImageResource(item.getImgID2());
        titleTextView.setText(item.getTitle());
        DescTextView.setText(item.getDesc());
        DateView.setText(item.getRdate());

        return view;

    }
 }