package com.liuh.takeoff_game;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * 撕衣服的游戏
 * 原理：两张图片叠放在一起，手指在最上面一张图片上滑动时，让滑动经过的地方的色值变为透明<br>
 *
 * 使用了一个中间Bitmap alterBitmap,这张图片的参数和背景图一致，手指滑动的过程中，修改这张中间Bitmap的手指经过区域的色值，
 * 然后调用ivPre.setImageBitmap(alterBitmap)将其设置给ImageView。
 */
public class MainActivity extends AppCompatActivity {

    private ImageView ivPre;
    private Bitmap alterBitmap;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivPre = findViewById(R.id.iv_pre);

        //原图
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pre);
        alterBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

        Canvas canvas = new Canvas(alterBitmap);
        Paint paint = new Paint();
        canvas.drawBitmap(bitmap, new Matrix(), paint);

        ivPre.setOnTouchListener(new View.OnTouchListener() {
            int x, y;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = (int) event.getX();
                        y = (int) event.getY();

                        for (int i = -10; i < 11; i++) {
                            for (int j = -10; j < 11; j++) {
                                if (Math.sqrt(i * i + j * j) <= 10) {
                                    alterBitmap.setPixel(x + i, y + j, Color.TRANSPARENT);
                                }
                            }
                        }
                        ivPre.setImageBitmap(alterBitmap);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x = (int) event.getX();
                        y = (int) event.getY();

                        for (int i = -10; i < 11; i++) {
                            for (int j = -10; j < 11; j++) {
                                if (Math.sqrt(i * i + j * j) <= 10) {
                                    alterBitmap.setPixel(x + i, y + j, Color.TRANSPARENT);
                                }
                            }
                        }
                        ivPre.setImageBitmap(alterBitmap);
                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                }
                return true;//事件结束，被消费掉了
            }
        });

    }
}
