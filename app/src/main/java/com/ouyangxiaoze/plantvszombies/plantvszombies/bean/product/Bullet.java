package com.ouyangxiaoze.plantvszombies.plantvszombies.bean.product;


import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.AttackPlant;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.Product;
import com.ouyangxiaoze.plantvszombies.plantvszombies.engine.DieListener;

/**
 * 子弹
 */
public abstract class Bullet extends Product {
	public static final int TYPE_COMMON = 1;
	public static final int TYPE_DOUBLE = 2;
	public static final int TYPE_ICE = 3;

	protected int type;// 类型
	protected int attack;// 攻击力
	protected int speed;// 移动速度
	protected AttackPlant attackPlant;// 生产者

	protected DieListener listener;

	protected Bullet(String filepath, AttackPlant attackPlant) {
		super(filepath);
		this.attackPlant = attackPlant;
	}

	@Override
	public void motionless() {
	}

	public int getType() {
		return type;
	}

	public int getAttack() {
		return attack;
	}

	public int getSpeed() {
		return speed;
	}

	public void setListener(DieListener listener) {
		this.listener = listener;
	}

	@Override
	public void distory() {
		if (listener != null) {
			listener.onDie(this);
		}
		super.distory();
	}

}
