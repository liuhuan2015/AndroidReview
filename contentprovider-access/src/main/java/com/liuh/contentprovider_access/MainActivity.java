package com.liuh.contentprovider_access;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);

        tvResult = findViewById(R.id.tv_result);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                add();
                break;
            case R.id.btn_delete:
                delete();
                break;
            case R.id.btn_update:
                update();
                break;
            case R.id.btn_query:
                query();
                break;
        }
    }

    private void add() {
        ContentResolver contentResolver = getContentResolver();
//        Uri uri = Uri.parse("content://com.itheima.db/account");
        Uri uri = Uri.parse("content://com.liuh.contentprovider_provider/account");
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "zhangsan");
        contentValues.put("money", 10000);
        contentResolver.insert(uri, contentValues);
    }

    private void delete() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.liuh.contentprovider_provider/account");
        contentResolver.delete(uri, "name=?", new String[]{"zhangsan"});
    }

    private void update() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.liuh.contentprovider_provider/account");
        ContentValues contentValues = new ContentValues();
        contentValues.put("money", 20000);
        contentResolver.update(uri, contentValues, "name=?", new String[]{"zhangsan"});
    }

    private void query() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.liuh.contentprovider_provider/account");
        Cursor cursor = contentResolver.query(uri, new String[]{"name", "money"}, null, null, null);
        StringBuilder resultStr = new StringBuilder();
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            float money = cursor.getFloat(1);
            Log.e("-------", "name: " + name + " money: " + money);
            resultStr.append("name: " + name + " money: " + money);
        }
        tvResult.setText(resultStr.toString());
        cursor.close();
    }
}
