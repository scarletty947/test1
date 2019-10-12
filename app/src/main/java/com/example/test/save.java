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
import android.view.View;
import android.widget.TextView;
//汇率更改界面
public class save extends AppCompatActivity implements View.OnClickListener{
    float dollar2,euro2,won2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        Intent intent=getIntent();
        //intent获取参数
//        dollar2=intent.getFloatExtra("dollar_rate_key",0.0f);
//        euro2=intent.getFloatExtra("euro_rate_key",0.0f);
//        won2=intent.getFloatExtra("won_rate_key",0.0f);

        //myrate 获取参数
        SharedPreferences sharedPreferences=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        dollar2=sharedPreferences.getFloat("dollar_rate_key",0.0f);
        euro2=sharedPreferences.getFloat("euro_rate_key",0.0f);
        won2=sharedPreferences.getFloat("won_rate_key",0.0f);

        TextView r1=findViewById(R.id.editText2);
        r1.setHint(String.valueOf(dollar2));
        TextView r2=findViewById(R.id.editText3);
        r2.setHint(String.valueOf(euro2));
        TextView r3=findViewById(R.id.editText4);
        r3.setHint(String.valueOf(won2));
    }
    @Override
    public void onClick(View v) {
        Intent config=new Intent(this,RateActivity.class);
        TextView r1=findViewById(R.id.editText2);
        String dollarRate=r1.getText().toString();
        TextView r2=findViewById(R.id.editText3);
        String euroRate=r2.getText().toString();
        TextView r3=findViewById(R.id.editText4);
        String wonRate=r3.getText().toString();
        Bundle bdl=new Bundle();
        bdl.putFloat("dollar_rate",Float.parseFloat(dollarRate));
        bdl.putFloat("euro_rate",Float.parseFloat(euroRate));
        bdl.putFloat("won_rate",Float.parseFloat(wonRate));
        config.putExtras(bdl);
        setResult(2,config);
        finish();

    }

    public void onClick2(View v) {
        Intent search=new Intent(this,search.class);
        startActivity(search);


    }
}
