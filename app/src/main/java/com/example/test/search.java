package com.example.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import static android.content.ContentValues.TAG;

public class search extends AppCompatActivity implements Runnable,AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{
    Handler handler;
    private final String TAG ="mylist";
    ListView listView;
    MyAdapter myAdapter;
    List listItem;
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
                    listView=(ListView)findViewById(R.id.mylist);//使用自定义list布局  不用extend ListActivity
//                    ListAdapter adapter=new ArrayAdapter<String>(search.this,android.R.layout.simple_list_item_1,list2);
//                    listView.setAdapter(adapter);

                    String str,country,rate;
                    //自定义行布局以及动态数组对应
                    listItem=new ArrayList<HashMap<String,String>>();
//                    for(int i=0;i<list2.size();i++){
//                        HashMap<String,String> map=new HashMap<String, String>();
//                        str=list2.get(i);
//                        country=str.substring(0, str.indexOf("==>"));
//                        rate=str.substring( str.indexOf("==>")+3);
//
//                        map.put("ItemName",country);
//                        map.put("ItemValue",rate);
//                        listItem.add(map);
//                    }

                    //数据库获取方式
                    List<RateItem> list3=(List<RateItem>) msg.obj;
                    for (RateItem i:list3){
                        HashMap<String,String> map=new HashMap<String, String>();
                        country=i.getCurName();
                        rate=i.getCurRate();
                        map.put("ItemName",country);
                        map.put("ItemValue",rate);
                        listItem.add(map);
                    }
                    //listItem=null;
//                    ListAdapter listItemAdapter=new SimpleAdapter(search.this,listItem,R.layout.listitem_search,
//                            new String[]{"ItemName","ItemValue"},new int[]{R.id.ItemName,R.id.ItemValue});//使用行布局里的控件设置
//                    listView.setAdapter(listItemAdapter);
                    //自定义Adapter
                    myAdapter=new MyAdapter(search.this,R.layout.listitem_search, (ArrayList<HashMap<String, String>>) listItem);
                    listView.setAdapter(myAdapter);
                    //当列表没有数据显示设置
                    listView.setEmptyView(findViewById(R.id.nodata));

                }
                super.handleMessage(msg);
            }
        };
        //开启子线程
        Thread t=new Thread( this);
        t.start();

        //list点击事件
        listView=(ListView)findViewById(R.id.mylist);
        listView.setOnItemClickListener(this);
        //长按事件
        listView.setOnItemLongClickListener(this);
    }

    public void run() {
        Log.i(TAG, "run:run()......");
        //获取msg对象，用于返回主线程
        Message msg = handler.obtainMessage(5);

//        List<String> list1 =new ArrayList<>();
//        Document doc = null;
//        try {
//            doc = Jsoup.connect("http://www.usd-cny.com/icbc.htm").get();
//            //doc = Jsoup.parse(html);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Log.i(TAG, "run" + doc.title());
//        Elements tables = doc.getElementsByTag("table");
//        // int i = 1;
////        //查看需要的数据在第几个table
////        for (Element table : tables) {
////            Log.i(TAG, "run:table[" + i + "]" + table);
////            i++;
////        }
//        Element table = tables.get(0);
//        // 获取 TD 中的数据
//        Elements trs = table.getElementsByTag("tr ");
//        for (int k=1;k<trs.size();k++) {
//            Log.i(TAG, "run:tr=" + trs.get(k));
//            Elements tds = trs.get(k).getElementsByTag("td ");
////            i=0;
////            for (Element td : tds) {
////                Log.i(TAG, "run:td[" + i + "]" + td);
////                i++;
////            }
//            String country = tds.get(0).text();
//            Float dollar = Float.parseFloat(tds.get(4).text());
//            // 获取数据并返回 ……
//            list1.add(country+"==>"+dollar);
//        }

        //改编成数据库版本查询所有数据
        RateManager manager=new RateManager(this);
        List<RateItem> testList=manager.listALL();
        for (RateItem i:testList){
            Log.i(TAG,"onOptionItemSelected:取出数据[id="+i.getId()+"]Name="+i.getCurName()+"Rate="+i.getCurRate());
        }
        msg.obj=testList;
        handler.sendMessage(msg);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        Log.i(TAG,"onItemClick:parent"+parent);
        Log.i(TAG,"onItemClick:view"+view);
        Log.i(TAG,"onItemClick:position"+position);
        Log.i(TAG,"onItemClick:id"+id);
        //获取数据
        Object itemAtPositon=listView.getItemAtPosition(position);
        HashMap<String,String> map= (HashMap<String, String>) itemAtPositon;
        //method 1
//        String itemname=map.get("ItemName");
//        String itemvalue=map.get("ItemValue");
//        Log.i(TAG,"onItemClick:ItemName"+itemname);
//        Log.i(TAG,"onItemClick:ItemValue"+itemvalue);
        //method 2
        TextView ItemName=view.findViewById(R.id.ItemName);
        TextView ItemValue= view.findViewById(R.id.ItemValue);
        String name=String.valueOf(ItemName.getText());
        String value=String.valueOf(ItemValue.getText());
        Log.i(TAG,"onItemClick:ItemName"+name);
        Log.i(TAG,"onItemClick:ItemValue"+value);


        Intent compute=new Intent(this,newCompute.class);
        compute.putExtra("moneyType",name);
        compute.putExtra("rate",Float.parseFloat(value));
        startActivity(compute);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("请确认是否删除当前数据")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG,"onClick:对话框事件");
                        //删除数据项
                        myAdapter.remove(listView.getItemAtPosition(position));
                        //更新适配器
                        //ArryAdapter会自动调用
                    }
                }).setNegativeButton("否",null);
        builder.create().show();
        return true;
    }
}
