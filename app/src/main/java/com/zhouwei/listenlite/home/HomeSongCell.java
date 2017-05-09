package com.zhouwei.listenlite.home;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhouwei.listenlite.R;
import com.zhouwei.listenlite.event.PlayMusicEvent;
import com.zhouwei.listenlite.model.LocalSongLoader;
import com.zhouwei.listenlite.model.Song;
import com.zhouwei.listenlite.model.PlayList;
import com.zhouwei.listenlite.utils.FileUtils;
import com.zhouwei.listenlite.utils.RxBus;
import com.zhouwei.rvadapterlib.base.RVBaseCell;
import com.zhouwei.rvadapterlib.base.RVBaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by zhouwei on 17/5/3.
 */

public class HomeSongCell extends RVBaseCell<Song> implements View.OnClickListener{
    private List<Song> mSongs;
    public HomeSongCell(Song musicInfo) {
        super(musicInfo);
    }

    public void setSongs(List<Song> songs) {
        mSongs = songs;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new RVBaseViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_song_cell_layout,null));
    }

    @Override
    public void onBindViewHolder(final RVBaseViewHolder holder, int i) {
       holder.setText(R.id.song_name,mData.title);
       holder.setText(R.id.song_singer,mData.artist);
       holder.setText(R.id.song_album,mData.album);

       if(mData.coverUri != null){
           Log.e("zhouwei","cell cover uri = "+mData.coverUri);
           ImageLoader.getInstance().displayImage(FileUtils.wrapLocalUri(mData.coverUri),holder.getImageView(R.id.song_cover_image));
           //ImageLoaderUtils.loadImage("content://"+mData.coverUri,R.drawable.icon_singer_default,holder.getImageView(R.id.song_cover_image));
       }else{
           //TODO: Subscription取消订阅
           LocalSongLoader.getInstance().getAlbumBitmap(holder.getItemView().getContext(),mData.uri,mData.id,mData.albumId)
                   .subscribe(new Action1<Bitmap>() {
                       @Override
                       public void call(Bitmap bitmap) {
                           holder.getImageView(R.id.song_cover_image).setImageBitmap(bitmap);
                       }
                   }, new Action1<Throwable>() {
                       @Override
                       public void call(Throwable throwable) {
                           throwable.printStackTrace();
                       }
                   });
       }


       holder.getItemView().setOnClickListener(this);
    }

    @Override
    public void releaseResource() {
        super.releaseResource();
    }

    @Override
    public void onClick(View v) {
        PlayList playList = new PlayList();
        if(mSongs == null){
            mSongs = new ArrayList<>();
            mSongs.add(mData);
        }
        int playIndex = mSongs.indexOf(mData);
        playList.setSongs(mSongs);
        playList.setCurrentPlayingIndex(playIndex);
        PlayMusicEvent playMusicEvent = new PlayMusicEvent();
        playMusicEvent.setPlayList(playList);
        RxBus.getInstance().post(playMusicEvent);
    }
}
