package com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base;

/**
 * 生产植物
 */
public abstract class ProductPlant extends Plant {

	protected ProductPlant(String filepath) {
		super(filepath);
		type = TYPE_PRODUCT;
	}
	/**
	 * 生产产品：阳光、金币
	 */
	public abstract void create();

	@Override
	public void attack(ElementAction action) {
	}
}
