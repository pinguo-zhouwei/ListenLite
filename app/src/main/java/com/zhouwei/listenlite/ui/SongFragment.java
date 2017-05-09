package com.zhouwei.listenlite.ui;

import android.content.Context;
import android.util.Log;

import com.zhouwei.listenlite.SwipAbsBaseFragment;
import com.zhouwei.listenlite.home.HomeContract;
import com.zhouwei.listenlite.home.HomeSongCell;
import com.zhouwei.listenlite.home.HomeSongPresenter;
import com.zhouwei.listenlite.model.Song;
import com.zhouwei.rvadapterlib.base.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 17/5/2.
 */

public class SongFragment extends SwipAbsBaseFragment<Song> implements HomeContract.HomeSongView {
    private HomeSongPresenter mPresenter;
    public static SongFragment newInstance(){
        return new SongFragment();
    }

    @Override
    public void onRecyclerViewInitialized() {
        // 初始化完成后
        mPresenter = new HomeSongPresenter();
        mPresenter.onAttach(this);
    }

    @Override
    public void onPullRefresh() {
       mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMore() {
       hideLoadMore();
    }

    @Override
    protected List<Cell> getCells(List<Song> list) {
        List<Cell> cells = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            HomeSongCell cell = new HomeSongCell(list.get(i));
            cell.setSongs(list);
            cells.add(cell);
        }
        return cells;
    }


    @Override
    public void setMusic(List<Song> music) {
       if(music == null){
           return;
       }
       mBaseAdapter.setData(getCells(music));
       mBaseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(Throwable e) {
        Log.e("zhouwei",e.getMessage());
    }

    @Override
    public Context returnContext() {
        return getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.onDetach();
        }
    }
}
