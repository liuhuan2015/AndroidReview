package com.liuh.surfaceview_use;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

/**
 * SurfaceView
 * <p>
 * 可以在单位时间内完成界面的大量多次更新<br>
 * <p>
 * 内部维持了一个双缓冲机制
 * <p>
 * 可以在子线程中更新UI
 * <p>
 * 占用的内存和cpu开销很大，当界面完全可见时才被创建完毕，如果界面最小化就会被销毁
 */
public class UpdateUiInThreadActivity extends AppCompatActivity {

    private Button btnUpdateUi;
    private SurfaceView surfaceView;
    private Paint paint;

    private boolean isSurfaceAlive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ui_in_thread);

        btnUpdateUi = findViewById(R.id.btn_update_ui);
        surfaceView = findViewById(R.id.surfaceview);

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                System.out.println("---------surfaceCreated----SurfaceView被创建了");
                isSurfaceAlive = true;

                paint = new Paint();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();

                        for (int i = 0; i < 100; i++) {
                            if (!isSurfaceAlive) {
                                return;
                            }

                            //界面显示内容的控制器
                            SurfaceHolder holder = surfaceView.getHolder();
                            Canvas canvas = holder.lockCanvas();
                            canvas.drawColor(Color.BLACK);

                            int radius = 5 + i;
                            paint.setColor(Color.RED);
                            canvas.drawCircle(250, 250, radius, paint);
                            holder.unlockCanvasAndPost(canvas);
                            SystemClock.sleep(100);
                        }
                    }
                }.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                System.out.println("---------surfaceChanged----SurfaceView大小发生了变化");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                System.out.println("---------surfaceDestroyed----SurfaceView被销毁了");
                isSurfaceAlive = false;
            }
        });

        //子线程中更新UI
        btnUpdateUi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paint = new Paint();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        for (int i = 0; i < 100; i++) {
                            //界面显示内容的控制器
                            SurfaceHolder holder = surfaceView.getHolder();
                            Canvas canvas = holder.lockCanvas();
                            canvas.drawColor(Color.BLACK);

                            int radius = 5 + i;
                            paint.setColor(Color.RED);
                            canvas.drawCircle(250, 250, radius, paint);

                            holder.unlockCanvasAndPost(canvas);
                            SystemClock.sleep(100);
                        }
                    }
                }.start();
            }
        });

    }
}
