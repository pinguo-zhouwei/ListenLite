package com.zhouwei.listenlite.model;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.zhouwei.listenlite.utils.AlnumUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 扫描本地音乐文件
 * Created by zhouwei on 17/5/3.
 */

public class LocalSongLoader {
    private static LocalSongLoader sLocalSongLoader;
    private LocalSongLoader(){

    }

    public static LocalSongLoader getInstance(){
        if(sLocalSongLoader == null){
            synchronized (LocalSongLoader.class){
                if(sLocalSongLoader == null){
                    sLocalSongLoader = new LocalSongLoader();
                }
            }
        }
        return sLocalSongLoader;
    }

    /**
     * 获取专辑封面图
     * @param context
     * @param songId
     * @param albumId
     * @return
     */
    public Observable<Bitmap> getAlbumBitmap(final Context context, String songPath,final long songId, final long albumId){
        return Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                try {

                  Bitmap bitmap = AlnumUtils.getArtwork(context,songId,albumId,true,false);
                  subscriber.onNext(bitmap);
                  subscriber.onCompleted();
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io())
          .unsubscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     *  扫描本地音乐文件，返回一个音乐列表
     * @param context
     * @return
     */
    public Observable<List<Song>> scanLocalMusic(final Context context){
        return Observable.create(new Observable.OnSubscribe<List<Song>>() {
            @Override
            public void call(Subscriber<? super List<Song>> subscriber) {
                try {
                    subscriber.onNext(scanMusic(context));
                    subscriber.onCompleted();
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread());
    }

    private static List<Song> scanMusic(Context context){
        List<Song> musicInfos = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor == null) {
            return null;
        }
        while (cursor.moveToNext()) {
            Song info = new Song();
            // 是否为音乐
            int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
            if (isMusic == 0) {
                continue;
            }
            info.id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            // 标题
            info.title = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            // 艺术家
            info.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            // 专辑
            info.album = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
            // 音乐时长
            info.duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            // 音乐uri
            info.uri = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            // 专辑封面id，根据该id可以获得专辑图片uri
            info.albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
           /* Uri  albumUri = getAlbumArtUri(info.albumId);
            if(albumUri!=null){
                info.coverUri = albumUri.getPath();
            }*/
            info.coverUri = getAlbumArt(context,info.albumId);
            Log.e("zhouwei","album_uri = "+info.coverUri);
            // 音乐文件名
            info.fileName = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
            // 音乐文件大小
            info.fileSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            // 发行时间
            info.year = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.YEAR)));

            musicInfos.add(info);
        }
        // close
        cursor.close();

        return musicInfos;
    }

    /**
     * 查询专辑封面图片uri
     */
    private static String getCoverUri(Context context, long albumId) {
        String uri = null;
        Cursor cursor = context.getContentResolver().query(
                Uri.parse("content://media/external/audio/albums/" + albumId),
                new String[]{"album_art"}, null, null, null);
        if (cursor != null) {
            cursor.moveToNext();
            uri = cursor.getString(0);
            cursor.close();

        }
        return uri;
    }

    public static Uri getAlbumArtUri(long paramInt) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), paramInt);
    }

    private static String getAlbumArt(Context context,long album_id) {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[] { "album_art" };
        Cursor cur = context.getContentResolver().query(
                Uri.parse(mUriAlbums + "/" + Long.toString(album_id)),
                projection, null, null, null);
        String album_art = null;
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        cur = null;
        return album_art;
    }



}
