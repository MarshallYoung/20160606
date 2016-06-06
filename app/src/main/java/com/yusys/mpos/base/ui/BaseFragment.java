package com.yusys.mpos.base.ui;

import android.app.Fragment;

import butterknife.ButterKnife;

/**
 * Fragment基类
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-04-07 10:03
 */
public class BaseFragment extends Fragment {

    /**
     * 每次显示在前台会执行的方法
     */
    public void onReveal() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}