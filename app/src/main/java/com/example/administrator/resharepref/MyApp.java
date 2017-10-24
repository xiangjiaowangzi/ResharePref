package com.example.administrator.resharepref;

import android.app.Application;

import com.example.library.ReSharePref;

/**
 * Created by Lbin on 2017/10/24.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ReSharePref.getInstance().init(this);
    }
}
