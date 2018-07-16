package com.newabel.executorslearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.sql.SQLOutput;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        newCachedThreadPool_Test();

//        newFixedThreadPool_Test();

//        newScheduledThreadPool_Test();

        newSingleThreadExecutor_Test();

    }

    private void newCachedThreadPool_Test() {
        //创建一个可缓存线程池，线程池的最大长度无限制；但是如果线程池长度超过处理需求，可灵活回收空闲线程。
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int index = i;

            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.e("-------", " currentThread: " + Thread.currentThread().getName());
                        Log.e("-------", " index: " + index);
                        Thread.sleep(index * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    private void newFixedThreadPool_Test() {
        //newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 10; i++) {
            final int index = i;

            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.e("-------", " currentThread: " + Thread.currentThread().getName());
                        Log.e("-------", "index: " + index);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void newScheduledThreadPool_Test() {
        //创建一个定长线程池，支持定时及周期性任务执行
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

        scheduledThreadPool.schedule(new Runnable() {
            @Override
            public void run() {
                Log.e("-------", " currentThread: " + Thread.currentThread().getName());
                Log.e("-------", "延时3秒");
            }
        }, 3, TimeUnit.SECONDS);
    }

    private void newSingleThreadExecutor_Test() {
        //创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序（FIFO,LIFO，优先级）执行
//        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        for (int i = 0; i < 10; i++) {
            final int index = i;
//            singleThreadExecutor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Log.e("-------", " currentThread: " + Thread.currentThread().getName());
//                        Log.e("------", "index: " + index);
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });

            scheduledExecutorService.schedule(new Runnable() {
                @Override
                public void run() {
                    Log.e("-------", " currentThread: " + Thread.currentThread().getName());
                    Log.e("------", "index: " + index);
                }
            }, 2000, TimeUnit.MILLISECONDS);
        }

        scheduledExecutorService.shutdown();
    }

}
