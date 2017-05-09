package com.zhouwei.listenlite.mvp;

/**
 * Created by zhouwei on 17/4/14.
 */

public interface BasePresenter<V extends BaseView> {

    void onAttach(V v);

    void onDetach();
}
