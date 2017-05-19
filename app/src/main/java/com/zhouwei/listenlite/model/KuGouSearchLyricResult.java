package com.zhouwei.listenlite.model;

import java.util.List;

/**
 * Created by zhouwei on 17/5/19.
 */

public class KuGouSearchLyricResult {
    public String info;
    public int status;
    public String proposal;
    public String keyword;
    public List<Candidates> candidates;


    public static class Candidates{
        public String nickname;

        public String accesskey;

        public int score;

        public long duration;

        public String uid;

        public String songName;

        public String id;

        public String singer;

        public String language;
    }
}
