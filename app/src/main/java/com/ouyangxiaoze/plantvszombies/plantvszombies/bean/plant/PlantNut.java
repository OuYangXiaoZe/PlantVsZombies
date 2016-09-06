package com.ouyangxiaoze.plantvszombies.plantvszombies.bean.plant;

import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.DefencePlant;

import java.util.ArrayList;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;



/**
 * 坚果
 *
 */
public class PlantNut extends DefencePlant {
	private static final String resPath = "image/plant/nut/p_3_%02d.png";
	// 静止帧
	protected static ArrayList<CCSpriteFrame> motionlessFrames;// 静止帧集合
	protected CCAnimation motionlessAnimation;// 静止的帧
	protected CCAnimate motionlessAnimate;// 静止动作

//	static {
//		motionlessFrames = new ArrayList<CCSpriteFrame>();
//		for (int i = 1; i < 12; i++) {
//			motionlessFrames.add(CCSprite.sprite(String.format(resPath, i)).displayedFrame());
//		}
//	}

	public PlantNut() {
		super("image/plant/nut/p_3_01.png");
		price = 50;
		life = 200;
		motionless();
	}

	@Override
	public void motionless() {
		if(motionlessFrames==null)
		{
			motionlessFrames = new ArrayList<CCSpriteFrame>();
			for (int i = 1; i < 12; i++) {
//				if(i==1){
//					motionlessFrames.add(CCSprite.sprite("image/plant/sunflower/feilao.png").displayedFrame());
//				}else {
//					motionlessFrames.add(CCSprite.sprite(String.format(resPath, i)).displayedFrame());
//				}
				motionlessFrames.add(CCSprite.sprite(String.format(resPath, i)).displayedFrame());
			}
		}

		motionlessAnimation = CCAnimation.animation("", 0.3f, motionlessFrames);
		motionlessAnimate = CCAnimate.action(motionlessAnimation);
		this.runAction(CCRepeatForever.action(motionlessAnimate));
	}

//	@Override
//	public void attacked(ElementAction action) {
//		Zombies zombies = (Zombies) action;
//
//		life -= zombies.getAttack();
//		if (life <= 0) {
//			// TODO 在死亡时通知当前的行战场，进行清理工作
//			// 方案一：在GameContorller中获取到当前行战场管理对象FightLine，调用对象的清除
//			// 方案二：循环集合，判断当前的精灵是否已经被回收了,被回收的精灵父类为null
//			// 方案三：事件监听
//			distory();
//		}
//	}
}
