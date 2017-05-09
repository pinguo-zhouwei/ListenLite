package com.zhouwei.listenlite;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.zhouwei.listenlite.ui.PlayMusicBottomFragment;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhouwei on 17/5/2.
 */

public class BaseActivity extends AppCompatActivity {
    private PlayMusicBottomFragment mFragment;

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
    protected void onDestroy() {
        super.onDestroy();
        // 解除订阅，如果子类重写，需要调用super
        if(mCompositeSubscription!=null){
            mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * 显示或者隐藏底部Fragment
     * @param isShow
     */
    protected void showBottomPlayFragment(boolean isShow){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(isShow){
           if(mFragment == null){
               mFragment = PlayMusicBottomFragment.newInstance();
               ft.add(R.id.bottom_play_container,mFragment).commitAllowingStateLoss();
           }else{
               ft.show(mFragment).commitAllowingStateLoss();
           }
        }else{
          if(mFragment!=null){
              ft.hide(mFragment).commitAllowingStateLoss();
          }
        }
    }

}
