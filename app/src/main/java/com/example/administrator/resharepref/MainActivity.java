package com.example.administrator.resharepref;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.library.ReSharePref;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view){
        Cat cat = ReSharePref.getInstance().create(Cat.class);
        cat.setYear("11岁");
        cat.getYear();
    }
}
