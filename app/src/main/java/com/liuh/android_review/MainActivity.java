package com.liuh.android_review;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<String, String> hashMap = new HashMap<>();

        Map<String, String> hashTable = new Hashtable<>();

        SparseArray<String> sparseArray = new SparseArray<>();


    }
}
