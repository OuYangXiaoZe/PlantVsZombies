package com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base;


import com.ouyangxiaoze.plantvszombies.plantvszombies.engine.DieListener;

/**
 * 植物
 * @author Administrator
 *
 */
public abstract class Plant extends BaseElement {
	protected DieListener listener;
	/**
	 * 攻击类型
	 */
	public static final int TYPE_ATTACK = 0;
	/**
	 * 防御类型
	 */
	public static final int TYPE_DEFENSE = 1;
	/**
	 * 生产类型
	 */
	public static final int TYPE_PRODUCT = 2;

	protected int type;// 植物类型
	protected int freeze = 0; // 冻结时间
	protected int price = 0;// 生成该植物需要的阳光数
	protected int life;//生命值

	protected Plant(String filepath) {
		super(filepath);
		setScale(0.65);
		setAnchorPoint(0.5f, 0);
	}

	@Override
	public void move() {
	}

	public int getType() {
		return type;
	}

	public int getFreeze() {
		return freeze;
	}

	public int getPrice() {
		return price;
	}

	public int getLife() {
		return life;
	}

	public void setListener(DieListener listener) {
		this.listener = listener;
	}



	@Override
	public void attacked(ElementAction action) {
		Zombies zombies=(Zombies) action;
		int attack = zombies.getAttack();
		life -= attack;
		if (life <= 0) {
			distory();
		}
	}

	@Override
	public void distory() {
		if (listener != null) {
			listener.onDie(this);
		}
		super.distory();
	}



}
