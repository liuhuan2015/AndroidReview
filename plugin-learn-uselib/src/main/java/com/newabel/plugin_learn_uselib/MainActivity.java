package com.newabel.plugin_learn_uselib;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.newabel.plugin_library.IBean;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    private ClassLoader mPluginClassLoader;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);

        //把Assets里面的文件复制到/data/data/包名/files 目录下
        //注意：不同的手机厂商，目录可能不一样
        Util.extractAssets(newBase, "plugin-debug.apk");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //插件apk的路径
        //dexPath: /data/user/0/com.newabel.plugin_learn/files/plugin-debug.apk
        String dexPath = getFileStreamPath("plugin-debug.apk").getAbsolutePath();

        //DexClassLoader加载的时候Dex文件释放的路径
        //fileReleasePath: /data/user/0/com.newabel.plugin_learn/app_dex
        String fileReleasePath = getDir("dex", Context.MODE_PRIVATE).getAbsolutePath();

        Log.e("-------", "dexPath: " + dexPath);

        Log.e("-------", "fileReleasePath: " + fileReleasePath);

        //通过DexClassLoader加载插件apk
        mPluginClassLoader = new DexClassLoader(dexPath, fileReleasePath, null, getClassLoader());

        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Class<?> beanClass = mPluginClassLoader.loadClass("com.newabel.plugin_uselib.Bean");
                    IBean beanObject = (IBean) beanClass.newInstance();

                    beanObject.setName("哈哈哈哈");
                    Toast.makeText(MainActivity.this, beanObject.getName(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

    }
}
