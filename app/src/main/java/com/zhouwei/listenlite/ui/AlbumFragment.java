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

public class AlbumFragment extends BaseFragment {

    public static AlbumFragment newInstance(){
        return new AlbumFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.album_fragment_layout,null);
        return view;
    }
}
