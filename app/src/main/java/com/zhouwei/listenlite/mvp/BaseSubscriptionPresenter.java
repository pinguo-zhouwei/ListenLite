package com.zhouwei.listenlite.mvp;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 *
 * 1，抽象类，统一处理Subscription ,在 onDetach() 方法中取消订阅
 * 2，子类Presenter 如果处理了Rxjava 相关请求，继承类{@link BaseSubscriptionPresenter}
 * Created by zhouwei on 17/5/3.
 */

public abstract class BaseSubscriptionPresenter<V extends BaseView> implements BasePresenter<V>{
    protected CompositeSubscription mCompositeSubscription;

    /**
     * 添加Subscription
     * @param subscription
     */
    public void addSubscription(Subscription subscription){
        if(subscription == null){
            return;
        }

        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void onDetach() {
        // 解除订阅，如果子类重写，需要调用super
        if(mCompositeSubscription!=null){
            mCompositeSubscription.unsubscribe();
        }
    }
}
