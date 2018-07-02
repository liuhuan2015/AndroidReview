package com.liuh.loadbigimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 一 . 加载一张大图到内存中
 * android中加载一张图片到内存中时，占用的内存空间大小是和图片的分辨率大小有关的。<br>
 * android下的Bitmap一般都是32位的位图（argb）<br>
 * 比如一张2560*1504的jpg图片加载到内存中时，占用的内存空间大小为：<br>
 * 2560*1504*4=15400960byte=15040kb=14.6875M<br>
 * 所以在直接加载一些大图片到应用中时，一般要对图片做一些处理<br>
 * <p>
 * 使用ExifInterface可以得到图片的宽高信息，旋转角度,拍摄地点等<br>
 * 二 . 图片的一些缩放，平移，旋转，倒影。模版写法，使用的是Matrix矩阵。
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv)
    ImageView iv;

    @BindView(R.id.image_pre)
    ImageView imagePre;

    @BindView(R.id.image_after)
    ImageView imageAfter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_load_big_image, R.id.btn_process_image})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_load_big_image:
                //这是直接加载图片的做法
                //iv.setImageBitmap(BitmapFactory.decodeFile("/mnt/sdcard/very_large_photo.jpg"));

                //下面是对图片做一些处理的做法
                try {
                    //1.得到图片的宽高信息，有的时候这些信息可能是获取不到的
                    ExifInterface exif = new ExifInterface("/mnt/sdcard/very_large_photo.jpg");
                    int width = exif.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, 0);
                    int height = exif.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, 0);
                    Log.e("------------", "width : " + width + " height : " + height);

                    //2.获取屏幕的宽高
                    WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);

                    Point point = new Point();
                    wm.getDefaultDisplay().getSize(point);
                    int screenWidth = point.x;
                    int screenHeight = point.y;
                    Log.e("-----------", "screenWidth : " + screenWidth + "screenHeight : " + screenHeight);

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    //设置采样率，宽高分别是原图的1/4，总像素个数就变成了原图的1/16
                    //采样率一般是根据图片的分辨率和屏幕的分辨率进行计算或根据业务需求，进行确定的，这里为了简便就直接写了一个4.
                    options.inSampleSize = 4;
                    Bitmap bitmap = BitmapFactory.decodeFile("/mnt/sdcard/very_large_photo.jpg", options);
                    //32位的位图，argb
                    iv.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_process_image:
                //效果：绘制原图的倒影
                //原图
                Bitmap srcBitmap = BitmapFactory.decodeFile("/mnt/sdcard/girl.png");
                imagePre.setImageBitmap(srcBitmap);
                //用代码编辑图片，最好都是处理图片在内存中的拷贝，不去处理原图
                Bitmap copyedBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), srcBitmap.getConfig());
                //临摹，创建一个画板
                Canvas canvas = new Canvas(copyedBitmap);
                //创建画笔
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                //作画
                Matrix matrix = new Matrix();//按照一比一的比例作画
                //缩放
                matrix.setScale(1.5f, 1.5f);

                //倒影
                //matrix.setScale(1, -1);
                //matrix.postTranslate(0, srcBitmap.getHeight());
                canvas.drawBitmap(srcBitmap, matrix, paint);
                imageAfter.setImageBitmap(copyedBitmap);
                break;
        }
    }

}
