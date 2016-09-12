package com.ouyangxiaoze.plantvszombies.plantvszombies.bean.product;

import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.AttackPlant;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;

import java.util.ArrayList;


/**
 * 普通子弹
 */
public class BulletCommon extends Bullet {

	private static final String filepath = "image/fight/bullet.png";


	public BulletCommon(AttackPlant plant) {
		super(filepath, plant);

		speed=60;
		type = TYPE_COMMON;
		attack = 10;
		setScale(0.5);
		setPosition(plant.getPosition().x + 20, plant.getPosition().y + 35);
		attackPlant.getParent().addChild(this);
		move();
	}

	@Override
	public void move() {
		float time = (CCDirector.sharedDirector().getWinSize().width - getPosition().x) / speed;
		CCMoveTo moveTo = CCMoveTo.action(time, ccp(CCDirector.sharedDirector().getWinSize().width, getPosition().y));
		CCSequence sequence = CCSequence.actions(moveTo, CCCallFunc.action(this, "distory"));
		runAction(sequence);
	}
//	protected static ArrayList<CCSpriteFrame> motionlessFrames;// 静止帧集合
//	protected CCAnimation motionlessAnimation;// 静止的帧
//	protected CCAnimate motionlessAnimate;// 静止动作
//	static {
//		motionlessFrames = new ArrayList<CCSpriteFrame>();
//
//		motionlessFrames.add(CCSprite.sprite("image/fight/Pea_particles.png").displayedFrame());
//		motionlessFrames.add(CCSprite.sprite("image/fight/pea_splats.png").displayedFrame());
//
//	}
	@Override
	public void distory() {
		/*
		播放击中的动画
		 */

//		motionlessAnimation = CCAnimation.animation("", 0.3f, motionlessFrames);
//		motionlessAnimate = CCAnimate.action(motionlessAnimation);
//
//		runAction(motionlessAnimate);
		System.out.println("击中了僵尸");
		super.distory();
	}

}
