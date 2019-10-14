package com.example.test;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.test.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;

public class MyAdapter extends ArrayAdapter {
    private static final String TAG="MyAdapter";
    public MyAdapter(@NonNull Context context, int resource,  ArrayList<HashMap<String,String>> list) {
        super(context, resource,list);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View itemView=convertView;
        if(itemView==null){
            itemView= LayoutInflater.from(getContext()).inflate(R.layout.listitem_search,parent,false);
        }
        HashMap<String,String> map=(HashMap<String, String>)getItem(position);
        TextView ItemName=itemView.findViewById(R.id.ItemName);
        TextView ItemValue=itemView.findViewById(R.id.ItemValue);

        ItemName.setText(map.get("ItemName"));
        ItemValue.setText(map.get("ItemValue"));

        return  itemView;
    }
}
