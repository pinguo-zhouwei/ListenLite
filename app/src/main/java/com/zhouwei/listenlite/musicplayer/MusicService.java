package com.zhouwei.listenlite.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.zhouwei.listenlite.model.Song;
import com.zhouwei.listenlite.model.PlayList;

/**
 * Created by zhouwei on 17/5/5.
 */

public class MusicService extends Service implements IPlayer,IPlayer.Callback{
    private static final String ACTION_PLAY_TOGGLE = "com.zhouwei.listenlite.ACTION.PLAY_TOGGLE";
    private static final String ACTION_PLAY_LAST = "com.zhouwei.listenlite.ACTION.PLAY_LAST";
    private static final String ACTION_PLAY_NEXT = "com.zhouwei.listenlite.ACTION.PLAY_NEXT";
    private static final String ACTION_STOP_SERVICE = "com.zhouwei.listenlite.ACTION.STOP_SERVICE";

    private static final int NOTIFICATION_ID = 1;

    private MusicPlayer mPlayer;
    private final Binder mBinder = new LocalBinder();


    public class LocalBinder extends Binder{
        public MusicService getService(){
            return MusicService.this;
        }
    }



    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = MusicPlayer.getInstance();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent !=null){
            String action = intent.getAction();
            if(ACTION_PLAY_TOGGLE.equals(action)){// 播放／暂停
                if(isPlaying()){
                    pause();
                }else{
                    play();
                }
            }else if(ACTION_PLAY_LAST.equals(action)){
                playLast();
            }else if(ACTION_PLAY_NEXT.equals(action)){
                playNext();
            }else if(ACTION_STOP_SERVICE.equals(action)){
               stopForeground(true);
               unregisterCallback(this);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public boolean stopService(Intent name) {
        stopForeground(true);
        unregisterCallback(this);
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        releasePlayer();
        super.onDestroy();
    }

    @Override
    public void setPlayList(PlayList list) {
        mPlayer.setPlayList(list);
    }

    @Override
    public boolean play() {
        return mPlayer.play();
    }

    @Override
    public boolean play(PlayList list) {
        return mPlayer.play(list);
    }

    @Override
    public boolean play(PlayList list, int startIndex) {
        return mPlayer.play(list,startIndex);
    }

    @Override
    public boolean play(Song song) {
        return mPlayer.play(song);
    }

    @Override
    public boolean playLast() {
        return mPlayer.playLast();
    }

    @Override
    public boolean playNext() {
        return mPlayer.playNext();
    }

    @Override
    public boolean pause() {
        return mPlayer.pause();
    }

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public int getProgress() {
        return mPlayer.getProgress();
    }

    @Override
    public long getDuration() {
        return mPlayer.getDuration();
    }

    @Override
    public Song getPlayingSong() {
        return mPlayer.getPlayingSong();
    }

    @Override
    public boolean seekTo(int progress) {
        return mPlayer.seekTo(progress);
    }

    @Override
    public void setPlayMode(PlayMode playMode) {
        mPlayer.setPlayMode(playMode);
    }

    @Override
    public void registerCallback(Callback callback) {
       mPlayer.registerCallback(callback);
    }

    @Override
    public void unregisterCallback(Callback callback) {
       mPlayer.unregisterCallback(callback);
    }

    @Override
    public void removeCallbacks() {

    }

    @Override
    public void releasePlayer() {

    }


    @Override
    public void onSwitchLast(@Nullable Song last) {

    }

    @Override
    public void onSwitchNext(@Nullable Song next) {

    }

    @Override
    public void onComplete(@Nullable Song next) {

    }

    @Override
    public void onStartPlay(@Nullable Song song) {

    }

    @Override
    public void onPlayStatusChanged(boolean isPlaying) {

    }
}
