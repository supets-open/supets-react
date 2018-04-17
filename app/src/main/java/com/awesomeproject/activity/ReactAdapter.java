//package com.awesomeproject.activity;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.awesomeproject.R;
//
//import java.util.ArrayList;
//
//public class ReactAdapter extends BaseAdapter {
//
//
//    public ArrayList<ReactData> getData() {
//        return data;
//    }
//
//    private ArrayList<ReactData> data = new ArrayList<>();
//
//    @Override
//    public int getCount() {
//        return data.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return data.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        TextView textView;
//        if (convertView == null) {
//            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
//        }
//        textView = (TextView) convertView.findViewById(R.id.name);
//        textView.setText(data.get(position).moduleName);
//        return convertView;
//    }
//
//}
