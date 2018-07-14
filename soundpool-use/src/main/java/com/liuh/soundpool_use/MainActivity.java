package com.liuh.soundpool_use;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * 记录一下SoundPool的使用，因为项目中有一个地方需要播放一段短音乐，发现用SoundPool比较合适一些。<br>
 * 使用起来比较简单
 */
public class MainActivity extends AppCompatActivity {
    SoundPool soundPool;
    int soundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        soundId = soundPool.load(this, R.raw.shoot, 1);

        findViewById(R.id.btn_shoot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //priority stream priority (0 = lowest priority)
                //loop mode (0 = no loop, -1 = loop forever)
                soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);
            }
        });
    }
}
