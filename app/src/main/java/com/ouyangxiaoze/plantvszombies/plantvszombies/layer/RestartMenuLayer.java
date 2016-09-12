package com.ouyangxiaoze.plantvszombies.plantvszombies.layer;

import com.ouyangxiaoze.plantvszombies.plantvszombies.layer.manager.BaseLayer;

/**
 * 重新开始游戏选择
 */
public class RestartMenuLayer extends BaseLayer {
    public RestartMenuLayer() {
        transitionSceneType=1;
        init();
    }

    private void init() {
        loadBG();
        addMenu();
    }

    private void addMenu() {

    }

    private void loadBG() {
     /*
     加载
      */
    }

}
