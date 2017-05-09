package com.zhouwei.listenlite.musicplayer;

import android.media.MediaPlayer;

import com.zhouwei.listenlite.model.PlayList;
import com.zhouwei.listenlite.model.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 17/5/5.
 */

public class MusicPlayer implements IPlayer ,MediaPlayer.OnCompletionListener{
    private static final String TAG = "MusicPlayer";
    private static volatile MusicPlayer sMusicPlayer;//单例
    private MediaPlayer mPlayer;
    private PlayList mPlayList;
    // 是否是暂停状态
    private boolean isPause = false;
    // 两个Callback,一个是通知service 的，一个通知UI
    private List<Callback> mCallbacks = new ArrayList<>(2);


    private MusicPlayer(){
        mPlayer = new MediaPlayer();
        mPlayList = new PlayList();
        mPlayer.setOnCompletionListener(this);
    }

    public static MusicPlayer getInstance(){
        if(sMusicPlayer == null){
            synchronized (MusicPlayer.class){
                if(sMusicPlayer == null){
                    sMusicPlayer = new MusicPlayer();
                }
            }
        }
        return sMusicPlayer;
    }
    @Override
    public void setPlayList(PlayList list) {
       if(list == null){
           return;
       }
       mPlayList = list;
    }

    @Override
    public boolean play() {
        if(isPause){
          mPlayer.start();
          isPause = false;
          return true;
        }
        if(mPlayList.canPlay()){
            Song music = mPlayList.getCurrentPlaySong();//播放第一首
            try {
                //重置播放器
                mPlayer.reset();
                //设置播放路径
                mPlayer.setDataSource(music.uri);
                //
                mPlayer.prepare();
                mPlayer.start();
                //通知状态
                onStartPlaySong(music);

            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        }


        return false;
    }

    @Override
    public boolean play(PlayList list) {
        setPlayList(list);
        return play();
    }

    @Override
    public boolean play(PlayList list, int startIndex) {
        return false;
    }

    @Override
    public boolean play(Song song) {
        return false;
    }

    @Override
    public boolean playLast() {
        isPause = false;
        if(mPlayList.hasPrevious()){
            Song previousSong = mPlayList.previous();
            play();
            return true;
        }
        return false;
    }

    @Override
    public boolean playNext() {
        isPause = false;
        if(mPlayList.hasNext()){
            Song nextSong = mPlayList.next();
            play();
            return true;
        }
        return false;
    }

    @Override
    public boolean pause() {
        if(mPlayer.isPlaying()){
            mPlayer.pause();
            isPause = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public int getProgress() {
        return mPlayer.getCurrentPosition();
    }

    @Override
    public long getDuration() {
        return mPlayer.getDuration();
    }

    @Override
    public Song getPlayingSong() {
        return null;
    }

    @Override
    public boolean seekTo(int progress) {
        return false;
    }

    @Override
    public void setPlayMode(PlayMode playMode) {

    }

    @Override
    public void registerCallback(Callback callback) {
      mCallbacks.add(callback);
    }

    @Override
    public void unregisterCallback(Callback callback) {
      mCallbacks.remove(callback);
    }

    @Override
    public void removeCallbacks() {
      mCallbacks.clear();
    }

    @Override
    public void releasePlayer() {
        mPlayList = null;
        mPlayer.reset();
        mPlayer.release();
        mPlayer = null;
        sMusicPlayer = null;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    /**
     * 开始播放回调
     * @param song
     */
    private void onStartPlaySong(Song song){
        for(Callback callback:mCallbacks){
            callback.onStartPlay(song);
        }
    }
}
