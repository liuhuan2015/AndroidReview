package com.liuh.surfaceview_use;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnUpdateUi, btnMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnUpdateUi = findViewById(R.id.btn_update_ui);
        btnMediaPlayer = findViewById(R.id.btn_mediaplayer);
        btnUpdateUi.setOnClickListener(this);
        btnMediaPlayer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_mediaplayer:
                intent.setClass(this, UpdateUiInThreadActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_update_ui:
                intent.setClass(this, MediaPlayerActivity.class);
                startActivity(intent);
                break;
        }
    }
}
