package com.zhouwei.listenlite.presenter;

import com.zhouwei.listenlite.mvp.BaseSubscriptionPresenter;
import com.zhouwei.listenlite.mvp.BaseView;

/**
 * Created by zhouwei on 17/5/8.
 */

public class BottomPlayContract {
    public interface BottomPlayView extends BaseView{
        void onProgressUpdate(int progress);

        public abstract void onServiceBind();
    }

    public static abstract class Presenter extends BaseSubscriptionPresenter<BottomPlayView>{
        public  abstract void updateBottomUI();

    }
}
