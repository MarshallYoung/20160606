package com.yusys.mpos.base.manager;

/**
 * 动画管理器
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @date 2016-05-10 16:03
 */
public class AnimatoinManager {

    private static AnimatoinManager instance;

    private AnimatoinManager() {
    }

    /**
     * 单例
     */
    public static AnimatoinManager getInstance() {
        if (instance == null) {
            instance = new AnimatoinManager();
        }
        return instance;
    }

}