package com.ouyangxiaoze.plantvszombies.plantvszombies.bean.plant;

/**
 * Created by suning on 2016/9/10.
 */

import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.AttackPlant;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.ElementAction;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.product.Bullet;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.product.BulletCommon;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.product.IceBullet;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;

import java.util.ArrayList;

/**
 * 豌豆
 *
 * @author Administrator
 *
 */
public class PlantSnowPease extends AttackPlant {
    private static final String resPath = "image/plant/snowpease/snowpeaseP%02d.png";
    // 静止帧
    protected static ArrayList<CCSpriteFrame> motionlessFrames;// 静止帧集合
    protected CCAnimation motionlessAnimation;// 静止的帧
    protected CCAnimate motionlessAnimate;// 静止动作

    static {
        motionlessFrames = new ArrayList<CCSpriteFrame>();

        for (int i = 1; i < 18; i++) {
               System.out.println("第"+i+"张图片");
                motionlessFrames.add(CCSprite.sprite(String.format(resPath, i)).displayedFrame());

        }
    }

    public PlantSnowPease() {
        super("image/plant/snowpease/snowpeaseP01.png");
        life = 100;
        price = 10;
        motionless();
    }

    /**
     * 植物的攻击
     */
    @Override
    public void attack(ElementAction action) {
        if (bullets.size() < 1) {
            IceBullet common = new IceBullet(this);
            common.setListener(this);
            bullets.addLast(common);
        }
    }

    @Override
    public void motionless() {
        motionlessAnimation = CCAnimation.animation("", 0.3f, motionlessFrames);
        motionlessAnimate = CCAnimate.action(motionlessAnimation);
        runAction(CCRepeatForever.action(motionlessAnimate));
    }

    @Override
    public void distory() {
        for (Bullet item : bullets) {
            item.removeSelf();
        }
        super.distory();
    }

}
