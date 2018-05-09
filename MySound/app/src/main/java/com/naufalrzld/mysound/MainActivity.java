package com.naufalrzld.mysound;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnSound;
    private Button btnMedia;
    private Button btnMediaStop;

    private SoundPool sp ;
    private int wav;
    private boolean spLoaded = false;

    Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSound = (Button)findViewById(R.id.btn_soundpool);
        btnSound.setOnClickListener(this);

        btnMedia = (Button)findViewById(R.id.btn_mediaplayer);
        btnMediaStop = (Button)findViewById(R.id.btn_mediaplayer_stop);
        btnMedia.setOnClickListener(this);
        btnMediaStop.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sp = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .build();
        } else {
            sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
        }

        sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            public void onLoadComplete(SoundPool soundPool, int sampleId,int status) {
                spLoaded = true;
            }
        });

        wav = sp.load(this, R.raw.beep, 1);

        it = new Intent(this, MediaService.class);
        it.setAction(MediaService.ACTION_CREATE);
        it.setPackage(MediaService.ACTION_PACKAGE);
        startService(it);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_soundpool:
                if (spLoaded){
                    sp.play(wav, 1, 1, 0, 0, 1);
                }
                break;
            case R.id.btn_mediaplayer:
                it.setAction(MediaService.ACTION_PLAY);
                it.setPackage(MediaService.ACTION_PACKAGE);
                startService(it);
                break;
            case R.id.btn_mediaplayer_stop:
                it.setAction(MediaService.ACTION_STOP);
                it.setPackage(MediaService.ACTION_PACKAGE);
                startService(it);
                break;
            default:
                break;
        }
    }
}
