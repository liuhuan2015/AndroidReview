package com.liuh.contentprovider_provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by huan on 2018/7/14.
 * 内容提供者，提供本应用内私有数据给别的应用程序
 */

public class BankDBBackDoor extends ContentProvider {

    private static final int SUCCESS = 1;
    //对Uri进行检查，如果匹配失败，返回 UriMatcher.NO_MATCH。
    static UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI("com.liuh.contentprovider_provider", "account", SUCCESS);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int code = mUriMatcher.match(uri);
        if (SUCCESS == code) {
            Log.e("-------", "......查询数据");
            MyDBOpenHelper myDBOpenHelper = new MyDBOpenHelper(getContext());
            SQLiteDatabase db = myDBOpenHelper.getWritableDatabase();
            return db.query("account", projection, selection, selectionArgs, null, null, sortOrder);
        } else {
            throw new IllegalArgumentException("口令不对，滚一边去...");
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int code = mUriMatcher.match(uri);
        if (SUCCESS == code) {
            Log.e("-------", "......插入数据");
            MyDBOpenHelper myDBOpenHelper = new MyDBOpenHelper(getContext());
            SQLiteDatabase db = myDBOpenHelper.getWritableDatabase();
            db.insert("account", null, values);
            //利用内容提供者的解析器，通知内容观察者数据发生了变化，
            //如果有别的应用注册这个Uri的内容观察者，就会收到消息
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            throw new IllegalArgumentException("口令不对，滚一边去...");
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int code = mUriMatcher.match(uri);
        if (SUCCESS == code) {
            Log.e("-------", "......删除数据");
            MyDBOpenHelper myDBOpenHelper = new MyDBOpenHelper(getContext());
            SQLiteDatabase db = myDBOpenHelper.getWritableDatabase();

            db.delete("account", selection, selectionArgs);
            //利用内容提供者的解析器，通知内容观察者数据发生了变化
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            throw new IllegalArgumentException("口令不对，滚一边去...");
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        int code = mUriMatcher.match(uri);
        if (SUCCESS == code) {
            Log.e("-------", "......更新数据");
            MyDBOpenHelper myDBOpenHelper = new MyDBOpenHelper(getContext());
            SQLiteDatabase db = myDBOpenHelper.getWritableDatabase();
            db.update("account", values, selection, selectionArgs);
            //利用内容提供者的解析器，通知内容观察者数据发生了变化
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            throw new IllegalArgumentException("口令不对，滚一边去...");
        }
        return 0;
    }
}
