package com.newabel.plugin_learn;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * 通过反射调用插件里面的代码
 * <p>
 * 先把经过验证的插件APK复制到宿主APP的files目录下面，这样保证了APK的安全性;<br>
 * <p>
 * 然后通过DexClassLoader进行加载的时候，指定插件APK的路径以及解压之后的dex存放路径。
 */
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
                    Class<?> beanClass = mPluginClassLoader.loadClass("com.newabel.plugin.User");
                    Object beanObject = beanClass.newInstance();

                    Method setNameMethod = beanClass.getMethod("setName", String.class);
                    setNameMethod.setAccessible(true);
                    Method getNameMethod = beanClass.getMethod("getName");
                    getNameMethod.setAccessible(true);

                    setNameMethod.invoke(beanObject, "张无忌");
                    String name = (String) getNameMethod.invoke(beanObject);

                    Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

    }
}
