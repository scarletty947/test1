package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class twogroupActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twogroup);
        //双边计分器
    }

    @Override
    public void onClick(View v) {
        TextView out=findViewById(R.id.textView5);
        out.setText("0");
        TextView out1=findViewById(R.id.textView8);
        out1.setText("0");
    }
    public void btn1(View v) {
        show1(3);
    }
    public void btn2(View v) {
        show1(2);
    }
    public void btn3(View v) {
        show1(1);
    }
    public void btn4(View v) {
        show2(3);
    }
    public void btn5(View v) {
        show2(2);
    }
    public void btn6(View v) {
        show2(1);
    }
    private void show1(int i){
        TextView out=findViewById(R.id.textView5);
        String oldscore=(String)out.getText();
        String newscore=String.valueOf(Integer.parseInt(oldscore)+i);
        out.setText(newscore);
    }
    private void show2(int i){
        TextView out=findViewById(R.id.textView8);
        String oldscore=(String)out.getText();
        String newscore=String.valueOf(Integer.parseInt(oldscore)+i);
        out.setText(newscore);
    }
}
