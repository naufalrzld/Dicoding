package com.naufalrzld.mysound;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

public class MediaService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    public static final String ACTION_PACKAGE = "com.example.action";
    public static final String ACTION_PLAY = "com.example.action.PLAY";
    public static final String ACTION_STOP = "com.example.action.STOP";
    public static final String ACTION_CREATE = "com.example.action.CREATE";
    MediaPlayer mMediaPlayer = null;

    int serviceId = 777;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void init(){
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        AssetFileDescriptor afd = getApplicationContext().getResources().openRawResourceFd(R.raw.guitar);

        try {
            mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMediaPlayer.setOnPreparedListener(this);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        switch(action) {
            case ACTION_CREATE:
                init();
                break;
            case ACTION_PLAY:
                if (!mMediaPlayer.isPlaying()) {
                    mMediaPlayer.prepareAsync();
                }
                break;
            case ACTION_STOP:
                mMediaPlayer.stop();
                break;
            default:
                break;
        }
        return flags;
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mMediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        if (mMediaPlayer != null) mMediaPlayer.release();
    }
}
