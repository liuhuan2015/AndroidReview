package com.liuh.tuya;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.seekbar)
    SeekBar mSeekBar;

    @BindView(R.id.imageview)
    ImageView imageView;

    private Paint mPaint;

    private Bitmap alterBitmap;//一个可以被修改的图片

    private Canvas canvas;//画布

    // new 的动作也可以放在onCreate(...)中做
    private MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {

        // 因为静态类不持有外部类的引用，所以这里建立一个对Activity的弱引用
        private WeakReference<MainActivity> activityWeakReference;

        public MyHandler(MainActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = activityWeakReference.get();

            if (activity != null) {
                // 进行相关业务逻辑处理
                if (msg.what == 1) {

                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        createImgsFolder();

        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        int screenWidth = point.x;
        int screenHeight = point.y;

        Log.e("-------", "screenWidth : " + screenWidth + " screenHeight : " + screenHeight);

        Log.e("-------", "imageView.getWidth() : " + imageView.getWidth()
                + " imageView.getHeight() : " + imageView.getHeight());

        //因为画布是正方形的，所以这里代码设置了ImageView的宽高，以期和画布宽高一致
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = screenWidth;
        layoutParams.height = screenWidth;
        imageView.setLayoutParams(layoutParams);

        //创建一个空白的Bitmap
        alterBitmap = Bitmap.createBitmap(screenWidth, screenWidth, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(alterBitmap);
        mPaint = new Paint();

        //设置画笔的颜色
        mPaint.setColor(Color.BLACK);
        canvas.drawColor(Color.WHITE);

        //设置画笔的宽度
        mPaint.setStrokeWidth(5);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                mPaint.setStrokeWidth(progress);
                Toast.makeText(MainActivity.this, "画笔宽度为：" + progress,
                        Toast.LENGTH_SHORT).show();
            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            int startX, startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("-------", "按下");
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e("-------", "移动");
                        int newX = (int) event.getX();
                        int newY = (int) event.getY();
                        canvas.drawLine(startX, startY, newX, newY, mPaint);
                        imageView.setImageBitmap(alterBitmap);
                        startX = newX;
                        startY = newY;
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e("-------", "抬起");

                        break;
                }
                return true;
            }


        });

    }

    private void createImgsFolder() {
        //创建一个保存图片的文件夹，并且文件夹中的图片可以被其他的app所访问
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/AndroidReview/Images");
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            if (mkdirs) {
                Log.e("-------", "创建文件夹成功");
            } else {
                Log.e("-------", "创建文件夹失败");
            }
        }
    }

    @OnClick({R.id.color_red, R.id.color_green, R.id.color_blue, R.id.btn_save_img})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.color_red:
                mPaint.setColor(Color.RED);
                break;
            case R.id.color_green:
                mPaint.setColor(Color.GREEN);
                break;
            case R.id.color_blue:
                mPaint.setColor(Color.BLUE);
                break;
            case R.id.btn_save_img:
                try {
                    //保存图片
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/AndroidReview/Images/" + SystemClock.currentThreadTimeMillis() + ".jpg");
                    FileOutputStream fos = new FileOutputStream(file);
                    alterBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();
                    Toast.makeText(this, "图片已保存在/AndroidView/Images目录下", Toast.LENGTH_SHORT).show();
                    //模拟一个sd卡插入广播,好像不生效
//                    Intent intent = new Intent();
//                    intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
//                    intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
//                    sendBroadcast(intent);
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
