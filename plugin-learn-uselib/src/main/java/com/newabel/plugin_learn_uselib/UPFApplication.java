package com.newabel.plugin_learn_uselib;

import android.app.Application;
import android.content.Context;

/**
 * Date: 2018/6/26 09:38
 * Description:
 */

public class UPFApplication extends Application {

    private static Context mContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mContext = base;
    }

    public static Context getContext() {
        return mContext;
    }
}
