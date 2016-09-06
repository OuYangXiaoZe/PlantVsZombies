package com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base;


/**
 * 防御型植物
 *
 * @author Administrator
 *
 */
public abstract class DefencePlant extends Plant {

	public DefencePlant(String filepath) {
		super(filepath);
		type = Plant.TYPE_DEFENSE;
	}

	@Override
	public void attack(ElementAction action) {
	}

}
