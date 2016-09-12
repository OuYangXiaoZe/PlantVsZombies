package com.ouyangxiaoze.plantvszombies.plantvszombies.bean.plant;

import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.AttackPlant;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.ElementAction;

/**
 * Created by suning on 2016/9/8.
 */
public class PlantChomper extends AttackPlant {
    protected PlantChomper(String filepath) {
        super(filepath);
    }

    /**
     * 攻击
     *
     * @param action
     */
    @Override
    public void attack(ElementAction action) {

    }

    /**
     * 固定时动画
     */
    @Override
    public void motionless() {

    }
}
