package com.zhouwei.listenlite.utils;

import android.text.TextUtils;

/**
 * Created by zhouwei on 17/5/8.
 */

public class FileUtils {
    public static String wrapLocalUri(String uri){
        if(TextUtils.isEmpty(uri)){
            return uri;
        }
        return "file://"+uri;
    }
}
