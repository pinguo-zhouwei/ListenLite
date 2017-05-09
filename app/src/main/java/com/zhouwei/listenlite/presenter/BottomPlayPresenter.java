package com.zhouwei.listenlite.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;

import com.zhouwei.listenlite.event.PlayMusicEvent;
import com.zhouwei.listenlite.model.PlayList;
import com.zhouwei.listenlite.model.Song;
import com.zhouwei.listenlite.musicplayer.IPlayer;
import com.zhouwei.listenlite.musicplayer.MusicService;
import com.zhouwei.listenlite.musicplayer.PlayMode;
import com.zhouwei.listenlite.utils.RxBus;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zhouwei on 17/5/8.
 */

public class BottomPlayPresenter extends BottomPlayContract.Presenter implements IPlayer {

    private BottomPlayContract.BottomPlayView mPlayView;
    private MusicService mMusicService;
    private Context mContext;
    private Handler mHandler = new Handler();
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 服务绑定
            mMusicService = ((MusicService.LocalBinder)service).getService();
            mPlayView.onServiceBind();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 服务解除绑定
            mMusicService = null;
        }
    };
    private Runnable mUpdatePregress = new Runnable() {
        @Override
        public void run() {
           int progress = mMusicService.getProgress();
           //long duration = mMusicService.getDuration();
           mPlayView.onProgressUpdate(progress);

           if(mMusicService.isPlaying()){
               updateBottomUI();
           }

        }
    };
    @Override
    public void onAttach(BottomPlayContract.BottomPlayView bottomPlayView) {
       mPlayView = bottomPlayView;

       mContext = bottomPlayView.returnContext();

        // 绑定播放服务
        bindPlayService();

        onMusicPlayEvent();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unBindService();
    }

    @Override
    public void updateBottomUI() {
       mHandler.postDelayed(mUpdatePregress,100);
    }

    /**
     * 并定服务
     */
    private void bindPlayService(){
        Intent service = new Intent(mContext,MusicService.class);
        mContext.bindService(service,mConnection,Context.BIND_AUTO_CREATE);
    }


    /**
     * 解除服务绑定
     */
    private void unBindService(){
        mContext.unbindService(mConnection);
    }
    /**
     * 音乐播放事件
     */
    private void onMusicPlayEvent(){
        Subscription subscription = RxBus.getInstance().toObserverable(PlayMusicEvent.class)
                .subscribe(new Action1<PlayMusicEvent>() {
                    @Override
                    public void call(PlayMusicEvent playMusicEvent) {
                        play(playMusicEvent.mPlayList);
                       /* UpdatePlayUIEvent event = new UpdatePlayUIEvent();
                        event.setMusicInfo(playMusicEvent.mPlayList.mMusicInfos.get(0));
                        RxBus.getInstance().post(event);*/
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

        addSubscription(subscription);
    }

    @Override
    public boolean isPlaying() {
        return mMusicService.isPlaying();
    }

    @Override
    public int getProgress() {
        return mMusicService.getProgress();
    }

    @Override
    public long getDuration() {
        return mMusicService.getDuration();
    }

    @Override
    public Song getPlayingSong() {
        return mMusicService.getPlayingSong();
    }

    @Override
    public boolean seekTo(int progress) {
        return mMusicService.seekTo(progress);
    }

    @Override
    public void setPlayMode(PlayMode playMode) {
        mMusicService.setPlayMode(playMode);
    }

    @Override
    public void registerCallback(Callback callback) {
        mMusicService.registerCallback(callback);
    }

    @Override
    public void unregisterCallback(Callback callback) {
        mMusicService.unregisterCallback(callback);
    }

    @Override
    public void removeCallbacks() {
        mMusicService.removeCallbacks();
    }

    @Override
    public void releasePlayer() {
        mMusicService.releasePlayer();
    }

    @Override
    public boolean pause() {
        return mMusicService.pause();
    }

    @Override
    public void setPlayList(PlayList list) {
        mMusicService.setPlayList(list);
    }

    @Override
    public boolean play() {
        return mMusicService.play();
    }

    @Override
    public boolean play(PlayList list) {
        return mMusicService.play(list);
    }

    @Override
    public boolean play(PlayList list, int startIndex) {
        return mMusicService.play(list,startIndex);
    }

    @Override
    public boolean play(Song song) {
        return mMusicService.play(song);
    }

    @Override
    public boolean playLast() {
        return mMusicService.playLast();
    }

    @Override
    public boolean playNext() {
        return mMusicService.playNext();
    }
}
