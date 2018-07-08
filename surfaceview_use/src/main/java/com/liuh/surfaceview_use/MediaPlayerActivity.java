package com.liuh.surfaceview_use;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MediaPlayerActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private SurfaceView mSurfaceView;

    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        mSurfaceView = findViewById(R.id.surfaceview);

        mSurfaceView.getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        System.out.println("SurfaceView被创建了");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        System.out.println("SurfaceView发生了变化");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        System.out.println("SurfaceView被销毁了");
    }
}
