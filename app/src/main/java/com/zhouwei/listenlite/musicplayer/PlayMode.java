package com.zhouwei.listenlite.musicplayer;

/**
 * Created by zhouwei on 17/5/5.
 */

public enum PlayMode {
    SINGLE,//单曲循环
    LOOP,//列表循环
    LIST,//顺序播放
    SHUFFLE;//随机播放

    public static PlayMode getDefault() {
        return LOOP;
    }

    public static PlayMode switchNextMode(PlayMode current) {
        if (current == null) return getDefault();

        switch (current) {
            case LOOP:
                return LIST;
            case LIST:
                return SHUFFLE;
            case SHUFFLE:
                return SINGLE;
            case SINGLE:
                return LOOP;
        }
        return getDefault();
    }
}
