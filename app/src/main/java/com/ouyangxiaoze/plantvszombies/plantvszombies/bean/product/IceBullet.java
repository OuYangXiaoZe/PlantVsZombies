package com.ouyangxiaoze.plantvszombies.plantvszombies.bean.product;

/**
 * Created by suning on 2016/9/10.
 */

import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.AttackPlant;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;

/**
 * 普通子弹
 */
public class IceBullet extends Bullet {

    private static final String filepath = "image/fight/icebullet.png";


    public IceBullet(AttackPlant plant) {
        super(filepath, plant);

        speed=60;
        type = TYPE_COMMON;
        attack = 10;
        setScale(0.3);
        setPosition(plant.getPosition().x + 20, plant.getPosition().y + 35);
        attackPlant.getParent().addChild(this);
        move();
        motionless();
    }

    @Override
    public void move() {
        float time = (CCDirector.sharedDirector().getWinSize().width - getPosition().x) / speed;
        CCMoveTo moveTo = CCMoveTo.action(time, ccp(CCDirector.sharedDirector().getWinSize().width, getPosition().y));
        CCSequence sequence = CCSequence.actions(moveTo, CCCallFunc.action(this, "distory"));
        runAction(sequence);
    }
    @Override
    public void motionless() {
        runAction(CCRepeatForever.action(CCRotateBy.action(1, 720)));
    }

    @Override
    public void distory() {
        super.distory();
    }

}
