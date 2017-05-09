package com.zhouwei.listenlite.event;

import com.zhouwei.listenlite.model.Song;

/**
 * Created by zhouwei on 17/5/8.
 */

public class UpdatePlayUIEvent {
    public Song mMusicInfo;

    public void setMusicInfo(Song musicInfo) {
        mMusicInfo = musicInfo;
    }
}
