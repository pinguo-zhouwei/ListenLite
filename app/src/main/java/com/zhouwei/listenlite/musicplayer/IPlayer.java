package com.zhouwei.listenlite.musicplayer;

import android.support.annotation.Nullable;

import com.zhouwei.listenlite.model.Song;
import com.zhouwei.listenlite.model.PlayList;

/**
 * Created by zhouwei on 17/5/5.
 */

public interface IPlayer {
    void setPlayList(PlayList list);

    boolean play();

    boolean play(PlayList list);

    boolean play(PlayList list, int startIndex);

    boolean play(Song song);

    boolean playLast();

    boolean playNext();

    boolean pause();

    boolean isPlaying();

    int getProgress();

    long getDuration();

    Song getPlayingSong();

    boolean seekTo(int progress);

    void setPlayMode(PlayMode playMode);

    void registerCallback(Callback callback);

    void unregisterCallback(Callback callback);

    void removeCallbacks();

    void releasePlayer();

    interface Callback {

        void onSwitchLast(@Nullable Song last);

        void onSwitchNext(@Nullable Song next);

        void onComplete(@Nullable Song next);

        void onStartPlay(@Nullable Song song);

        void onPlayStatusChanged(boolean isPlaying);
    }
}
