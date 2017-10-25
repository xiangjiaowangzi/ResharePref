package com.example.administrator.resharepref;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.library.ReSharePref;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
    }

    public void click(View view) {
        Cat cat = ReSharePref.getInstance().create(Cat.class);
        cat.setYear(12);
        cat.setName("小猫");
        textView.setText(cat.getName() + " : " + cat.getYear() + "岁");
    }

    public void click1(View view) {
        Cat cat = ReSharePref.getInstance().create(Cat.class);
        cat.setYear(20);
        cat.setName("小狗");
        textView.setText(cat.getName() + " : " + cat.getYear() + "岁");
    }
}
