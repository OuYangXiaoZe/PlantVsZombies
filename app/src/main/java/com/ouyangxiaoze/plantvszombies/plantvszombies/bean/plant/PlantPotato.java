package com.ouyangxiaoze.plantvszombies.plantvszombies.bean.plant;

import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.AttackPlant;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.ElementAction;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;

import java.util.ArrayList;

/**
 * Created by suning on 2016/9/8.
 */
public class PlantPotato extends AttackPlant {
    private static final String resPath = "image/plant/potato/potatoP%02d.png";
    // 静止帧
    protected static ArrayList<CCSpriteFrame> motionlessFrames;// 静止帧集合
    protected CCAnimation motionlessAnimation;// 静止的帧
    protected CCAnimate motionlessAnimate;// 静止动作


    static {
        motionlessFrames = new ArrayList<CCSpriteFrame>();

        for (int i = 1; i <10; i++) {
               System.out.println(resPath);


                motionlessFrames.add( CCSprite.sprite(String.format(resPath, i)).displayedFrame());

        }
//        motionlessFrames.add(CCSprite.sprite("image/plant/potato/potatoP01.png").displayedFrame());
//        motionlessFrames.add(CCSprite.sprite("image/plant/potato/potatoP02.png").displayedFrame());
//        motionlessFrames.add(CCSprite.sprite("image/plant/potato/potatoP03.png").displayedFrame());
//        motionlessFrames.add(CCSprite.sprite("image/plant/potato/potatoP04.png").displayedFrame());
//        motionlessFrames.add(CCSprite.sprite("image/plant/potato/potatoP05.png").displayedFrame());
//        motionlessFrames.add(CCSprite.sprite("image/plant/potato/potatoP06.png").displayedFrame());
//        motionlessFrames.add(CCSprite.sprite("image/plant/potato/potatoP07.png").displayedFrame());
//        motionlessFrames.add(CCSprite.sprite("image/plant/potato/potatoP08.png").displayedFrame());
//        motionlessFrames.add(CCSprite.sprite("image/plant/potato/potatoP09.png").displayedFrame());


    }



    public PlantPotato() {
        super("image/plant/potato/potatoP1.png");
        life = 100;
        price = 25;
        motionless();
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
        motionlessAnimation = CCAnimation.animation("", 0.3f, motionlessFrames);

        motionlessAnimate = CCAnimate.action(motionlessAnimation);

        runAction(CCRepeatForever.action(motionlessAnimate));
    }

    @Override
    public void distory() {
        /*
        播放爆炸动画
         */
        super.distory();
    }
}
