package com.zhouwei.listenlite.utils;

import android.util.Base64;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by zhouwei on 17/5/19.
 */

public class LyricUtils {

    private static final String lrcRootPath = android.os.Environment
            .getExternalStorageDirectory().toString()
            + "/ListenLite/lyric/";

    private static String getLrcPath(String title, String artist) {
        return lrcRootPath + title + " - " + artist + ".lrc";
    }
    public static File writeLrcToFile(String title, String artist, String lrcContext) {
        FileWriter writer = null;
        try {
            File file = new File(getLrcPath(title, artist));
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            writer = new FileWriter(getLrcPath(title, artist));
            writer.write(lrcContext);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static  String decryptBASE64(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        try {
            byte[] encode = str.getBytes("UTF-8");
            // base64 解密
            return new String(Base64.decode(encode, 0, encode.length, Base64.DEFAULT), "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
