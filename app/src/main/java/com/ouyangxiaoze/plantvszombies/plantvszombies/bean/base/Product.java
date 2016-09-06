package com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base;
/**
 * 产物
 */
public abstract class Product extends BaseElement {

	protected Product(String filepath) {
		super(filepath);
	}

	@Override
	public void attack(ElementAction action) {
	}

	@Override
	public void attacked(ElementAction action) {
	}
}
