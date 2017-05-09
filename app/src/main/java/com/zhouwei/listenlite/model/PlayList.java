package com.zhouwei.listenlite.model;

import com.zhouwei.listenlite.musicplayer.PlayMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zhouwei on 17/5/5.
 */

public class PlayList {
    public static final int NO_POSITION = -1;
    public int id;
    /**
     * 当前播放音乐的位置
     */
    private int mCurrentPlayingIndex = NO_POSITION;

    private PlayMode mPlayMode = PlayMode.LOOP;// 默认列表循环播放

    public List<Song> mSongs = new ArrayList<>();

    public void setMusicInfos(List<Song> songs) {
        mSongs = songs;
    }

    public void addMusic(Song musicInfo){
        mSongs.clear();
        mSongs.add(musicInfo);
    }

    public List<Song> getSongs() {
        return mSongs;
    }

    public void setSongs(List<Song> songs) {
        if(songs == null){
            songs = new ArrayList<>();
        }
        mSongs = songs;
    }

    public void setPlayMode(PlayMode playMode) {
        mPlayMode = playMode;
    }

    public PlayMode getPlayMode() {
        return mPlayMode;
    }

    public void setCurrentPlayingIndex(int currentPlayingIndex) {
        mCurrentPlayingIndex = currentPlayingIndex;
    }

    public int getCurrentPlayingIndex() {
        return mCurrentPlayingIndex;
    }

    /**
     * 判断是否有可播放的音乐
     * @return
     */
    public boolean canPlay(){
        if(mSongs == null || mSongs.size() == 0){
            return false;
        }
        if(mCurrentPlayingIndex == NO_POSITION){
            mCurrentPlayingIndex = 0;
        }
        return true;
    }

    /**
     * 获取当前播放的歌曲
     * @return
     */
    public Song getCurrentPlaySong(){
        if(mCurrentPlayingIndex != NO_POSITION){
            return mSongs.get(mCurrentPlayingIndex);
        }
        return null;
    }

    /**
     * 是否有下一首歌
     * <p>
     * 歌曲数量大于1，才能下一首切换
     * </p>
     * @return
     */
    public boolean hasNext(){
        return mSongs!=null && mSongs.size()>1;
    }

    /**
     *
     * 是否有上一首歌
     * <p>
     *   歌曲数量大于1，才能上一首切换
     * </p>
     *
     *
     * @return
     */
    public boolean hasPrevious(){
        return mSongs!=null && mSongs.size()>1;
    }

    /**
     * 获取下一首播放的歌曲
     * @return
     */
    public Song next(){
        switch (mPlayMode){
            case LOOP:
            case LIST:
            case SINGLE:
                int newIndex = mCurrentPlayingIndex + 1;
                if(newIndex > mSongs.size()){
                    newIndex = 0;
                }
                mCurrentPlayingIndex = newIndex;
                break;
            case SHUFFLE://随机播放模式
                mCurrentPlayingIndex = generateRandomPlayIndex();
                break;
        }
        return mSongs.get(mCurrentPlayingIndex);
    }

    /**
     * 获取上一首播放的歌曲
     * @return
     */
    public Song previous(){
        switch (mPlayMode){
            case LOOP:
            case LIST:
            case SINGLE:
                int newIndex = mCurrentPlayingIndex - 1;
                if(newIndex < 0){
                    newIndex = mSongs.size() -1;
                }
                mCurrentPlayingIndex = newIndex;
                break;
            case SHUFFLE://随机播放模式
                mCurrentPlayingIndex = generateRandomPlayIndex();
                break;
        }
        return mSongs.get(mCurrentPlayingIndex);
    }

    /**
     * 生成随机播放模式的要播放的歌曲位置
     * @return
     */
    private int generateRandomPlayIndex(){

        int randomIndex = new Random().nextInt(mSongs.size());
        // 确保在歌曲数量大于2首的情况下，重复播放同一首歌2次
        if(mSongs.size() > 1 && randomIndex == mCurrentPlayingIndex){
            generateRandomPlayIndex();
        }
        return randomIndex;
    }

}
