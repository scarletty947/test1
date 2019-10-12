package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//汇率转换界面，目前的主要界面，主要程序
//实现参数传值，myrate.xml文件存储和修改提供参数传值，线程，获取网络数据，解析网络数据
public class RateActivity extends AppCompatActivity implements Runnable{
    EditText rmb;
    TextView mes;
    String s;
    float dollar1=7.07f,euro1=7.89f,won1=0.0059f;
    float dollar2=0f,euro2=0f,won2=0f;
    private final String TAG ="Rate";
    Handler handler;
    String update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        rmb=findViewById(R.id.editText);
        mes=findViewById(R.id.textView6);

        handler=new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==5){
                    String str=(String)msg.obj;
                    Log.i(TAG,"handleMessage:getMessage msg="+str);
                    mes.setText(str);
                }
                super.handleMessage(msg);
            }
        };

        //每天更新
        //获取当前系统时间
        Date today= Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String todayStr=sdf.format(today);

        //获取上次更新日期
        SharedPreferences sharedPreferences=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        update=sharedPreferences.getString("update_data","");

        //判断时间
        if(!todayStr.equals(update)){
            Log.i(TAG,todayStr+"需要更新"+update);
            Toast.makeText(this, "已更新", Toast.LENGTH_SHORT).show();
            //开启子线程
            Thread t=new Thread(this);
            t.start();
            //保存更新的日期
            SharedPreferences sp=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("update_data",todayStr);
            editor.apply();
        }
        else{
            Log.i(TAG,"不需要更新");
        }
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){

            if(requestCode==1&&resultCode==2){
                Bundle bundle=data.getExtras();
                dollar1=bundle.getFloat("dollar_rate",0.0f);
                euro1=bundle.getFloat("euro_rate",0.0f);
                won1=bundle.getFloat("won_rate",0.0f);
            }
        super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.new_friends){
            //...
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        Intent config=new Intent(this,save.class);
        //intent传参
//        config.putExtra("dollar_rate_key",dollar1);
//        config.putExtra("euro_rate_key",euro1);
//        config.putExtra("won_rate_key",won1);

        //myrate.xml
        SharedPreferences sp=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putFloat("dollar_rate_key",dollar1);
        editor.putFloat("euro_rate_key",euro1);
        editor.putFloat("won_rate_key",won1);
        editor.apply();

        startActivityForResult(config,1);



    }
    public void onClick2(View v) {
        s=rmb.getText().toString();
        if(s.equals(""))
            show();
        else {
            float r = Float.parseFloat(s);
            float dollar = r *dollar1;
            mes.setText("$"+String.valueOf(dollar));
        }
    }
    public void onClick3(View v) {
        s=rmb.getText().toString();
        if(s.equals(""))
            show();
        else {
            float r = Float.parseFloat(s);
            float euro = r * euro1;
            mes.setText("&"+String.valueOf(euro));
        }
    }
    public void onClick4(View v){
        s=rmb.getText().toString();
        if(s.equals(""))
            show();
        else{
            float r=Float.parseFloat(s);
            float won=r*won1;
            mes.setText("w"+String.valueOf(won));
        }
    }
    private void show(){
        mes.setText("please enter a number");
        Toast.makeText(this, "please enter a number", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void run() {
        Log.i(TAG, "run:run()......");
//        for(int i=1;i<6;i++){
//            Log.i(TAG,"run:i="+i);
//            try{
//                Thread.sleep(2000);
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }
//        }
        //获取msg对象，用于返回主线程
        Message msg = handler.obtainMessage(5);
        //msg.what=5;
        msg.obj = "Hello from run()";
        handler.sendMessage(msg);

        //获取网络数据
//        URL url=null;
//        try{
//            url=new URL("http://www.usd-cny.com/icbc.htm");
//            HttpURLConnection http=(HttpURLConnection) url.openConnection();
//            InputStream in =http.getInputStream();
//
//            String html=InputStream2String(in);
//            Log.i(TAG,"run:html="+html);
//            System.out.println(html);
//        }catch (MalformedURLException e){
//            e.printStackTrace();
//        }catch (IOException e){
//            e.printStackTrace();
//        }

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
            switch (country) {
                case "美元":
                    dollar1 = Float.parseFloat(tds.get(4).text());
                    break;
                case "欧元":
                    euro1 = Float.parseFloat(tds.get(4).text());
                    break;
                case "韩币":
                    won1 = Float.parseFloat(tds.get(4).text());
                    break;
                default:
                    break;
            }
            // 获取数据并返回 ……

        }
    }
    public static String InputStream2String(InputStream in) {
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(in, "gb2312");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader br = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        String line = "";
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
