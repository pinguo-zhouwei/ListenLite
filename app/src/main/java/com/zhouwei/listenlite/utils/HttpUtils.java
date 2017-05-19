package com.zhouwei.listenlite.utils;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhouwei on 17/5/12.
 */

public class HttpUtils {
    public static String getResponseString(String action1) {
        try {
            OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(1000, TimeUnit.MINUTES)
                    .readTimeout(1000, TimeUnit.MINUTES)
                    .build();
            Request request = new Request.Builder()
                    .url(action1)
                    .build();
            Response response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String c = response.body().string();
                Log.e("LRC", c);
                return c;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
