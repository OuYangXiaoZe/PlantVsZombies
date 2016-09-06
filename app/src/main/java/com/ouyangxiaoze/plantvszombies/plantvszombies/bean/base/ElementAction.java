package com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base;

/**
 * 游戏元素动作
 *
 * @author Administrator
 *
 */
public interface ElementAction {
	/**
	 * 攻击
	 *
	 * @param action
	 */
	void attack(ElementAction action);

	/**
	 * 被攻击
	 *
	 * @param action
	 */
	void attacked(ElementAction action);

	/**
	 * 移动
	 */
	void move();

	/**
	 * 固定时动画
	 */
	void motionless();
}
