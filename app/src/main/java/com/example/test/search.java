package com.example.test;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import static android.content.ContentValues.TAG;

public class search extends AppCompatActivity implements Runnable {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//        List<String> list1 =new ArrayList<>();
//        for(int i=1;i<100;i++){
//            list1.add("item"+i);
//        }
//        String[] list_data={"one","two","three","four"};
//        ListAdapter adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list_data);
//        setListAdapter(adapter);

        handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 5) {
                    List<String> list2=(List<String>) msg.obj;
                    ListView listView=(ListView)findViewById(R.id.mylist);//使用自定义list布局  不用extend ListActivity
                    ListAdapter adapter=new ArrayAdapter<String>(search.this,android.R.layout.simple_list_item_1,list2);
                    listView.setAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
        //开启子线程
        Thread t=new Thread( this);
        t.start();
    }

    public void run() {
        Log.i(TAG, "run:run()......");
        //获取msg对象，用于返回主线程
        Message msg = handler.obtainMessage(5);

        List<String> list1 =new ArrayList<>();
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.usd-cny.com/icbc.htm").get();
            //doc = Jsoup.parse(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "run" + doc.title());
        Elements tables = doc.getElementsByTag("table");
        // int i = 1;
//        //查看需要的数据在第几个table
//        for (Element table : tables) {
//            Log.i(TAG, "run:table[" + i + "]" + table);
//            i++;
//        }
        Element table = tables.get(0);
        // 获取 TD 中的数据
        Elements trs = table.getElementsByTag("tr ");
        for (int k=1;k<trs.size();k++) {
            Log.i(TAG, "run:tr=" + trs.get(k));
            Elements tds = trs.get(k).getElementsByTag("td ");
//            i=0;
//            for (Element td : tds) {
//                Log.i(TAG, "run:td[" + i + "]" + td);
//                i++;
//            }
            String country = tds.get(0).text();
            Float dollar = Float.parseFloat(tds.get(4).text());
            // 获取数据并返回 ……
            list1.add(country+"==>"+dollar);
        }
        msg.obj=list1;
        handler.sendMessage(msg);
    }

}
