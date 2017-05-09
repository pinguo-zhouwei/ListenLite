package com.zhouwei.listenlite.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhouwei.listenlite.BaseFragment;
import com.zhouwei.listenlite.R;

/**
 * Created by zhouwei on 17/5/2.
 */

public class SingerFragment extends BaseFragment {

    public static SingerFragment newInstance(){
        return new SingerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.singer_fragment_layout,null);
        return view;
    }
}
