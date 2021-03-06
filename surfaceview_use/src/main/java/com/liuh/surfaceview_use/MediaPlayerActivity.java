package com.liuh.surfaceview_use;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.VideoView;

import java.io.IOException;

public class MediaPlayerActivity extends Activity implements SurfaceHolder.Callback, MediaPlayer.OnCompletionListener {

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

        mMediaPlayer = new MediaPlayer();

        mMediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        System.out.println("SurfaceView被创建了");
        try {
            mMediaPlayer.setDataSource("/mnt/sdcard/oppo.3gp");
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setDisplay(holder);
        mMediaPlayer.start();
        int position = sp.getInt("position", 0);
        if (position == mMediaPlayer.getDuration()) {
            mMediaPlayer.seekTo(0);
        } else {
            mMediaPlayer.seekTo(position);
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
            sp.edit().putInt("position", position).apply();
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mMediaPlayer != null) {
            sp.edit().putInt("position", 0).apply();
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }
}
