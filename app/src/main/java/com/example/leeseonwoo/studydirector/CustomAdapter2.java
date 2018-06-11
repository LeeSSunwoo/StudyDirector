//package com.example.leeseonwoo.studydirector;
//
///**
// * Created by leeseonwoo on 2018. 5. 9..
// */
//
//import android.content.Context;
//import android.provider.ContactsContract;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.CheckBox;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CustomAdapter2 extends BaseAdapter{
//    //Item 데이터를 저장할 리스트
//    List<ListViewItem2> listViewItemList = new ArrayList<>();
//
//
//
//
//
//    @Override
//    public int getCount() {
//        return listViewItemList.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return listViewItemList.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    public void addItem(int imgId, String title,String desc) {
//        ListViewItem item = new ListViewItem();
//        item.setImageID(imgId);
//        item.setTitleStr(title);
//        item.setDesc(desc);
//
//        listViewItemList.add(item);
//    }
//
//    public void removeItem (ListViewItem item){
//        listViewItemList.remove(item);
//    }
//
//    @Override //ListView에 뿌려질 item을 제공한다.
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        Context context = viewGroup.getContext();
//
//        if(view==null){
//            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            view = inflater.inflate(R.layout.listview_item, viewGroup, false);
//        }
//
//        //현재 선택된 View에 연결되는 객체 생성
//        ImageView imageView = (ImageView)view.findViewById(R.id.imageView2);
//        TextView titleTextView=(TextView)view.findViewById(R.id.textView7);
//        TextView DescTextView=(TextView)view.findViewById(R.id.textView8);
//
//        ListViewItem2 item = listViewItemList.get(i);
//
//        //현재 선택된 View에 데이터 삽입
//        imageView.setImageResource(item.getImageID());
//        titleTextView.setText(item.getTitleStr());
//        timeTextView.setText(item.getTimeStr()+"일 남았습니다.");
//        pageTextView.setText(item.getPageStr()+"페이지 남았습니다.");
//        checkBox.setText("오늘 "+item.getCheckStr()+"페이지");
//
//
//        return view;
//    }
//}