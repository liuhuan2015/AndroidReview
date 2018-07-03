package com.liuh.interview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String str;

        new Thread() {
            @Override
            public void run() {
                super.run();
            }
        }.start();
    }
}
