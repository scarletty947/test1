package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    TextView out;
    int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        out=findViewById(R.id.textView7);


    }

    @Override
    public void onClick(View v) {
        String s=out.getText().toString();
        score=Integer.parseInt(s);
        out.setText(String.valueOf(score+3));
    }
    public void onClick2(View v) {
        String s=out.getText().toString();
        score=Integer.parseInt(s);
        out.setText(String.valueOf(score+2));
    }
    public void onClick3(View v) {
        String s=out.getText().toString();
        score=Integer.parseInt(s);
        out.setText(String.valueOf(score+1));
    }
    public void onClick4(View v) {
        out.setText("0");
    }
}
