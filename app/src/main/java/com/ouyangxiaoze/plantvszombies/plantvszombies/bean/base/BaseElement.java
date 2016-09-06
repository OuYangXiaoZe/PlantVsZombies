package com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base;

import org.cocos2d.nodes.CCSprite;

/**
 * 游戏界面元素
 */
public abstract class BaseElement extends CCSprite implements ElementAction {
	// 行列信息
	// 通过构造（指定资源路径）
	// 消亡
	protected Integer line;// 行
	protected Integer row;// 列
	/**
	 * 元素的创建
	 * @param filepath
	 */
	protected BaseElement(String filepath) {
		super(filepath);
	}

	/**
	 * 元素的消亡
	 */
	public void distory() {
		this.removeSelf();
	}

	public Integer getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
}
