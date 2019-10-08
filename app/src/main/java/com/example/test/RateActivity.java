package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//汇率转换界面，目前的主要界面，主要程序
//实现参数传值，myrate.xml文件存储和修改提供参数传值，线程，获取网络数据，解析网络数据
public class RateActivity extends AppCompatActivity implements View.OnClickListener{
    EditText rmb;
    TextView mes;
    String s;
    float dollar1=7.07f,euro1=7.89f,won1=0.0059f;
    float dollar2=0f,euro2=0f,won2=0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        rmb=findViewById(R.id.editText);
        mes=findViewById(R.id.textView6);

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

    @Override
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
}
