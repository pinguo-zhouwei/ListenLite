package com.zhouwei.listenlite.event;

import com.zhouwei.listenlite.model.PlayList;

/**
 * Created by zhouwei on 17/5/5.
 */

public class PlayMusicEvent {
    public PlayList mPlayList;

    public void setPlayList(PlayList playList) {
        mPlayList = playList;
    }
}
