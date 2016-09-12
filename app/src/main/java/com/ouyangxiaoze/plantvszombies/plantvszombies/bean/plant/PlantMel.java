package com.ouyangxiaoze.plantvszombies.plantvszombies.bean.plant;

import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.AttackPlant;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.ElementAction;

/**
 * Created by suning on 2016/9/6.
 */
public class PlantMel extends AttackPlant {

    private static final String resPath = "image/plant/nut/p_3_%02d.png";

    protected PlantMel(String filepath) {
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
