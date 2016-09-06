package com.ouyangxiaoze.plantvszombies.plantvszombies.bean.product;

import java.util.concurrent.CopyOnWriteArrayList;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;

import android.util.Log;

import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.Product;

/**
 * 阳光
 */
public class Sun extends Product {

	public static final CopyOnWriteArrayList<Sun> suns = new CopyOnWriteArrayList<Sun>();// 放置生产出来的阳光
	public static int TOTLE_SUN = 50;

	public static final int TYPE_BIG = 1;
	public static final int TYPE_SMALL = 2;

	private int type;
	private CGPoint start;
	private CGPoint end;

	private int speed = 20;

	private static final String resPath = "image/product/sun.png";

	public Sun(CCNode parent, int type, CGPoint start, CGPoint end) {
		super(resPath);
		this.type = type;
		this.start = start;
		this.end = end;

		if (type == TYPE_BIG) {
			setScale(0.3);
		} else {

		}
		setPosition(start);
		parent.addChild(this);
		suns.add(this);
		move();
		motionless();
	}

	@Override
	public void motionless() {
		runAction(CCRepeatForever.action(CCRotateBy.action(1, 180)));
	}

	@Override
	public void move() {
		float t = (start.y - end.y) / speed;
		CCMoveTo moveTo = CCMoveTo.action(t, end);
		CCCallFunc callFunc = CCCallFunc.action(this, "distory");

		CCSequence sequence = CCSequence.actions(moveTo, CCDelayTime.action(5), callFunc);
		runAction(sequence);
	}

	@Override
	public void distory() {
		suns.remove(this);
		this.removeSelf();
	}

	public void addSun() {
		if (type == TYPE_BIG) {
			TOTLE_SUN += 25;
		} else {
			TOTLE_SUN += 15;
		}
		Log.i(this.getClass().getSimpleName(),"sun:"+TOTLE_SUN);
		float t = (CCDirector.sharedDirector().getWinSize().getHeight() - getPosition().y - 20) / 200;
		CCMoveTo action = CCMoveTo.action(t, CGPoint.ccp(20, CCDirector.sharedDirector().getWinSize().getHeight() - 20));
		runAction(CCSequence.actions(action, CCCallFunc.action(this, "distory")));
	}

}
