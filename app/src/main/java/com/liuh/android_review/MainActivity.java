package com.liuh.android_review;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnMaopao;
    Button btnBinarySearch;
    Button btnFibonacci;

    private int[] args = {1, 55, 4, 6, 7, 88, 101, 113, 55, 77};

    private int[] args2 = {1, 3, 7, 10, 33, 44, 55, 99, 444};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMaopao = findViewById(R.id.btn_maopao);
        btnMaopao.setOnClickListener(this);

        btnBinarySearch = findViewById(R.id.btn_binary_search);
        btnBinarySearch.setOnClickListener(this);

        btnFibonacci = findViewById(R.id.btn_Fibonacci);
        btnFibonacci.setOnClickListener(this);

        Map<String, String> hashMap = new HashMap<>();

        Map<String, String> hashTable = new Hashtable<>();

        SparseArray<String> sparseArray = new SparseArray<>();

    }

    // 从小到大排序
    public void maopao(int[] args) {
        for (int i = 0; i < args.length; i++) {
            for (int j = 0; j < args.length - i - 1; j++) {

                if (args[j] > args[j + 1]) {
                    // 互换，将最大的数值冒泡在数组的最后位置
                    int temp = args[j];
                    args[j] = args[j + 1];
                    args[j + 1] = temp;
                }
            }
        }
        Log.e("-----", Arrays.toString(args));
    }

    // 二分查找
    public int binarySearch(int[] args, int target) {
        int left = 0;
        int right = args.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (args[mid] == target) {
                return mid;
            }

            if (args[mid] < target) {
                left = mid + 1;
            }

            if (args[mid] > target) {
                right = mid - 1;
            }

        }
        return -1;
    }

    // 斐波那契数列
    public int fibonacci(int n) {
        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_maopao:
                maopao(args);
                break;
            case R.id.btn_binary_search:
                int index = binarySearch(args2, 44);
                Log.e("-----", "44 的索引位置是：" + index);
                break;
            case R.id.btn_Fibonacci:
                Log.e("-----", "fibonacci(8): " + fibonacci(8));
                break;
        }

    }
}
