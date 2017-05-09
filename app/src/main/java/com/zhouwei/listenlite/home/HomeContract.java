package com.zhouwei.listenlite.home;

import android.content.Context;

import com.zhouwei.listenlite.model.Song;
import com.zhouwei.listenlite.mvp.BaseSubscriptionPresenter;
import com.zhouwei.listenlite.mvp.BaseView;

import java.util.List;

/**
 * Created by zhouwei on 17/5/3.
 */

public class HomeContract {
    public interface HomeSongView extends BaseView{
         void setMusic(List<Song> music);

         void onError(Throwable e);
    }

    public static abstract class Presenter extends BaseSubscriptionPresenter<HomeSongView> {
        // 扫描音乐
        abstract void startScanMusic(Context context);
    }
}
