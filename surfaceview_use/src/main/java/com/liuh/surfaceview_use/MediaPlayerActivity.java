package com.liuh.surfaceview_use;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class MediaPlayerActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private SurfaceView mSurfaceView;

    private MediaPlayer mMediaPlayer;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        mSurfaceView = findViewById(R.id.surfaceview);

        mSurfaceView.getHolder().addCallback(this);

        sp = getSharedPreferences("config", MODE_PRIVATE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        System.out.println("SurfaceView被创建了");
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource("/mnt/sdcard/oppo.3gp");
            mMediaPlayer.prepare();
            mMediaPlayer.setDisplay(holder);
            mMediaPlayer.start();
            mMediaPlayer.seekTo(sp.getInt("position", 0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        System.out.println("SurfaceView发生了变化");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        System.out.println("SurfaceView被销毁了");
        if (mMediaPlayer != null) {
            int position = mMediaPlayer.getCurrentPosition();
            sp.edit().putInt("position", position).commit();
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }
}
