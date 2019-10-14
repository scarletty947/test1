package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class newCompute extends AppCompatActivity {
    float rate;
    String moneyType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_compute);
        Intent intent=getIntent();
        moneyType =intent.getExtras().getString("moneyType","");
        rate=intent.getFloatExtra("rate",0.0f);

        TextView r1=findViewById(R.id.moneyType);
        r1.setText(moneyType);
        EditText rmb=findViewById(R.id.RMB);
        rmb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                TextView Result=findViewById(R.id.showR);
                if(s.length()>0){
                    float val=Float.parseFloat(s.toString());
                    Result.setText(val+"RMB="+String.valueOf(100/rate*val)+moneyType);
                }
                else {
                    Result.setText("");
                }


            }
        });


    }
}
