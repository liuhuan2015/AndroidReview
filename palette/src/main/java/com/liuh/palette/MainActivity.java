package com.liuh.palette;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

/**
 * 一个调色板的演示demo.
 * <p>
 * 主要使用到一个颜色矩阵 ColorMatrix，其内部有一个四行五列的矩阵用于表示颜色（r、g、b、a）
 * <p>
 * 颜色对应关系：青-------红，紫-------绿，黄-------蓝
 * <p>
 * <pre>
 *   ColorMatrix cm = new ColorMatrix();
 *   cm.set(new float[]{
 *      1 * result, 0, 0, 0, 0,//red
 *      0, 1, 0, 0, 0,//green
 *      0, 0, 1, 0, 0,//blue
 *      0, 0, 0, 1, 0,//alpha
 *    });
 *   paint.setColorFilter(new ColorMatrixColorFilter(cm));
 *   canvas.drawBitmap(srcBitmap, new Matrix(), paint);
 *   imageView.setImageBitmap(copyedBitmap);
 * </pre>
 */
public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private SeekBar seekBar;
    private Paint paint;
    private Canvas canvas;
    private Bitmap copyedBitmap;
    private Bitmap srcBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageview);

        seekBar = findViewById(R.id.seekbar);

        srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pre);
        copyedBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), srcBitmap.getConfig());
        canvas = new Canvas(copyedBitmap);
        paint = new Paint();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int progress_changeed = seekBar.getProgress();
                float result = progress_changeed / 50.0f;
                System.out.println("变化的百分比为： " + result);

                //颜色矩阵，四行五列
                ColorMatrix cm = new ColorMatrix();
                cm.set(new float[]{
                        1 * result, 0, 0, 0, 0,//red
                        0, 1, 0, 0, 0,//green
                        0, 0, 1, 0, 0,//blue
                        0, 0, 0, 1, 0,//alpha
                });
                paint.setColorFilter(new ColorMatrixColorFilter(cm));
                canvas.drawBitmap(srcBitmap, new Matrix(), paint);
                imageView.setImageBitmap(copyedBitmap);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress_stoptrack = seekBar.getProgress();
                float result = progress_stoptrack / 50.0f;
                System.out.println("变化的百分比为： " + result);

                //颜色矩阵，四行五列
                ColorMatrix cm = new ColorMatrix();
                cm.set(new float[]{
                        1 * result, 0, 0, 0, 0,//red
                        0, 1, 0, 0, 0,//green
                        0, 0, 1, 0, 0,//blue
                        0, 0, 0, 1, 0,//alpha
                });
                paint.setColorFilter(new ColorMatrixColorFilter(cm));
                canvas.drawBitmap(srcBitmap, new Matrix(), paint);
                imageView.setImageBitmap(copyedBitmap);
            }
        });

    }
}
