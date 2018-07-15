package com.liuh.system_sms_access;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * 访问手机系统的短信数据库，实现对其的增加和删除<br>
 * 小米手机MIUI系统使用这种方式好像不起作用
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_add_sms).setOnClickListener(this);
        findViewById(R.id.btn_delete_sms).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_sms:
                addSms();
                break;
            case R.id.btn_delete_sms:
                deleteSms();
                break;
        }
    }

    private void addSms() {
        Log.e("-------", "添加一条短信");
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://sms/");

        ContentValues contentValues = new ContentValues();
        contentValues.put("address", "10086");
        contentValues.put("type", "1");
        contentValues.put("date", System.currentTimeMillis());
        contentValues.put("body", "恭喜你，你中了一个大奖，快来领取~~~");

        Uri uriLast = contentResolver.insert(uri, contentValues);
        Log.e("-------", "uriLast: " + uriLast.toString());
    }

    private void deleteSms() {
        Uri uri = Uri.parse("content://sms/");
        ContentResolver contentResolver = getContentResolver();
        Log.e("-------", "delete: " + contentResolver.delete(uri, "address=?", new String[]{"10086"}));
    }
}
