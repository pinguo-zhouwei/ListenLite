package com.zhouwei.listenlite.home;

import android.content.Context;

import com.zhouwei.listenlite.model.LocalSongLoader;
import com.zhouwei.listenlite.model.Song;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zhouwei on 17/5/3.
 */

public class HomeSongPresenter extends HomeContract.Presenter {
    private HomeContract.HomeSongView mSongView;

    @Override
    public void onAttach(HomeContract.HomeSongView homeSongView) {
       mSongView = homeSongView;


       startScanMusic(homeSongView.returnContext());


    }


    @Override
    public void startScanMusic(Context context) {
        Subscription subscription = LocalSongLoader.getInstance().scanLocalMusic(context)
                .subscribe(new Action1<List<Song>>() {
                    @Override
                    public void call(List<Song> musicInfos) {
                        if(musicInfos!=null){
                            mSongView.setMusic(musicInfos);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                       mSongView.onError(throwable);
                    }
                });

        addSubscription(subscription);
    }


}
