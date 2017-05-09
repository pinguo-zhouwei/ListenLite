package com.zhouwei.listenlite;

import android.support.v4.app.Fragment;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhouwei on 17/5/2.
 */

public class BaseFragment extends Fragment {
   // 公共操作
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
    public void onDestroy() {
        super.onDestroy();
        // 解除订阅，如果子类重写，需要调用super
        if(mCompositeSubscription!=null){
            mCompositeSubscription.unsubscribe();
        }
    }
}
