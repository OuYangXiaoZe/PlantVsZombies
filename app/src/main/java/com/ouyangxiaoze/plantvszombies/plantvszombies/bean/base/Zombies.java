package com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base;

import com.ouyangxiaoze.plantvszombies.plantvszombies.engine.DieListener;

import org.cocos2d.types.CGPoint;



/**
 * 僵尸
 */
public abstract class Zombies extends BaseElement{
	protected DieListener listener;

	protected CGPoint startPoint;// 起始位置信息
	protected CGPoint endPoint;// 终止位置信息

	protected int attack = 0;// 攻击力
	protected int speed = 0;// 移动速度
	protected int life;// 生命值


	protected Zombies(String filepath) {
		super(filepath);
	}

	public CGPoint getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(CGPoint startPoint) {
		this.startPoint = startPoint;
	}

	public CGPoint getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(CGPoint endPoint) {
		this.endPoint = endPoint;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public void setListener(DieListener listener) {
		this.listener = listener;
	}



}
