package com.zhouwei.listenlite.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhouwei.listenlite.BaseFragment;
import com.zhouwei.listenlite.R;
import com.zhouwei.listenlite.loader.LrcLoader;
import com.zhouwei.listenlite.model.Song;
import com.zhouwei.listenlite.musicplayer.IPlayer;
import com.zhouwei.listenlite.presenter.BottomPlayContract;
import com.zhouwei.listenlite.presenter.BottomPlayPresenter;
import com.zhouwei.listenlite.utils.FileUtils;
import com.zhouwei.listenlite.utils.ImageLoaderUtils;

import java.io.File;

import rx.functions.Action1;

/**
 * Created by zhouwei on 17/5/5.
 */

public class PlayMusicBottomFragment extends BaseFragment implements BottomPlayContract.BottomPlayView,View.OnClickListener,IPlayer.Callback{
    private ImageView mAlbumImage;
    private TextView mSingerName;
    private TextView mSongName;
    private ProgressBar mProgressBar;
    private View mParentView;
    private ImageView mPlayBtn;
    private ImageView mNextBtn;


    private BottomPlayPresenter mPresenter;

    public static PlayMusicBottomFragment newInstance(){
        return new PlayMusicBottomFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.bottom_play_fragment_layout,null);
       mParentView = view;
       mAlbumImage = (ImageView) view.findViewById(R.id.album_cover_image);
       mSingerName = (TextView) view.findViewById(R.id.bottom_singer);
       mSongName = (TextView) view.findViewById(R.id.bottom_song_name);
       mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
       mPlayBtn = (ImageView) view.findViewById(R.id.bottom_play_btn);
       mNextBtn = (ImageView) view.findViewById(R.id.bottom_next_btn);
       mPlayBtn.setOnClickListener(this);
       mNextBtn.setOnClickListener(this);
       view.findViewById(R.id.bottom_fragment_layout).setOnClickListener(this);

       mPresenter = new BottomPlayPresenter();
       mPresenter.onAttach(this);
       //mPresenter.registerCallback(this);// 注册监听

       return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 更新UI
     * @param song
     */
    private void updateBottomUIEvent(Song song){
        Song info = song;
        if(info == null){
            return;
        }
        ImageLoaderUtils.loadImage(FileUtils.wrapLocalUri(info.coverUri),mAlbumImage);
        mSongName.setText(info.title);
        mSingerName.setText(info.artist);
        mPlayBtn.setImageResource(R.drawable.btn_notification_player_stop_normal);
        //  mSeekBar.setMax((int) info.duration);
        mProgressBar.setMax((int) info.duration);
        mProgressBar.setProgress(0);
        updateUI();
    }

    /**
     * 更新UI
     */
    private void updateUI(){
        if(mPresenter!=null){
            mPresenter.updateBottomUI();
        }

    }

    @Override
    public void onServiceBind() {
        mPresenter.registerCallback(this);
    }

    @Override
    public Context returnContext() {
        return getActivity();
    }

    @Override
    public void onProgressUpdate(int progress) {
       // mSeekBar.setMax((int) info.duration);
        Log.e("zhouwei","progress:"+progress);
        mProgressBar.setProgress(progress);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.bottom_play_btn:
               if(mPresenter.isPlaying()){
                 // 暂停
                 mPlayBtn.setImageResource(R.drawable.theme_color_play);
                 mPresenter.pause();
               }else{
                 // 播放
                   mPlayBtn.setImageResource(R.drawable.btn_notification_player_stop_normal);
                   mPresenter.play();
                   updateUI();
               }
               break;
           case R.id.bottom_next_btn:
               mPresenter.playNext();
               break;
           case R.id.bottom_fragment_layout:
               PlayDetailFragment detailFragment = new PlayDetailFragment();
               detailFragment.show(getFragmentManager(),"PlayDetail");
               break;
       }
    }

    @Override
    public void onSwitchLast(@Nullable Song last) {
       // updateBottomUIEvent(last);
    }

    @Override
    public void onSwitchNext(@Nullable Song next) {
       //updateBottomUIEvent(next);
    }

    @Override
    public void onComplete(@Nullable Song next) {
       updateBottomUIEvent(next);
    }

    @Override
    public void onStartPlay(@Nullable Song song) {
       updateBottomUIEvent(song);

       getLrc(song);
    }

    @Override
    public void onPlayStatusChanged(boolean isPlaying) {

    }

    /**
     * 获取歌词
     * @param song
     */
    private void getLrc(Song song){
       new LrcLoader().getLrc(song.title,song.artist,song.duration).subscribe(new Action1<File>() {
           @Override
           public void call(File file) {
               Log.e("zhouwei","getLrc:"+file.getName());
           }
       }, new Action1<Throwable>() {
           @Override
           public void call(Throwable throwable) {
               throwable.printStackTrace();
           }
       });
    }
}
