package com.zhouwei.listenlite.model;

/**
 * Created by zhouwei on 17/5/3.
 */

public class Song {
    //id
    public long id;
    //标题
    public String title;
    //歌手
    public String artist;
    // 专辑
    public String album;
    // 歌曲时长
    public long duration;
    // 音乐地址
    public String uri;
    //专辑封面ID，跟据该id 可以获取专辑图片的uri
    public long albumId;
    // 封面地址
    public String coverUri;
    //  文件名
    public String fileName;
    // 文件大小
    public long fileSize;
    //发行时间
    public String year;

}
