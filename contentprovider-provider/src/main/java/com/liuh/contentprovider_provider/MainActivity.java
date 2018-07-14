package com.liuh.contentprovider_provider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyDBOpenHelper helper = new MyDBOpenHelper(this);
        helper.getWritableDatabase();
    }
}
