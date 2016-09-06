package com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base;

import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.product.Bullet;
import com.ouyangxiaoze.plantvszombies.plantvszombies.engine.DieListener;

import java.util.LinkedList;




/**
 * 攻击植物
 */
public abstract class AttackPlant extends Plant implements DieListener {
	protected LinkedList<Bullet> bullets = new LinkedList<Bullet>();
	protected AttackPlant(String filepath) {
		super(filepath);
		type = Plant.TYPE_ATTACK;
	}
	public LinkedList<Bullet> getBullets() {
		return bullets;
	}
	@Override
	public void onDie(BaseElement element) {
		bullets.remove(element);
	}
}
