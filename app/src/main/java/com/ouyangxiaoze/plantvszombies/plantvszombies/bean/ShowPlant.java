package com.ouyangxiaoze.plantvszombies.plantvszombies.bean;

import java.util.HashMap;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

/**
 * 封装用于展示的植物
 * 除图片信息外需要记录起始点和终止点，处理两个容器之间植物的移动操作
 * @author Administrator
 *
 */
public class ShowPlant {
	// 配置文件信息：记录每个植物及其id
	private static HashMap<Integer, String> plants=new HashMap<Integer, String>();
	static
	{
		//模拟6种植物
		for(int i=1;i<=6;i++)
		{
			plants.put(i, String.format("image/fight/chose/choose_default%02d.png", i));
		}
	}

	private int id;

	private CCSprite plant;
	private CCSprite back;
	private CGPoint start;
	private CGPoint end;
	public ShowPlant(int id) {
		super();
		this.id=id;
		if(plants.containsKey(id)){
			plant = CCSprite.sprite(plants.get(id));
			back = CCSprite.sprite("image/fight/chose/chose_01.png");
		}
		else{
			//抛出异常
			throw new IllegalArgumentException(id+"not find from config.");
		}
	}
	/**
	 * 设置植物及背景位置信息
	 * @param x
	 * @param y
	 */
	public void setPosition(float x,float y)
	{
		plant.setPosition(x, y);
		back.setPosition(x, y);
		start = CGPoint.ccp(x, y);
	}
	/**
	 * 设置植物及背景锚点信息
	 * @param x
	 * @param y
	 */
	public void setAnchorPoint(float x,float y)
	{
		plant.setAnchorPoint(x, y);
		back.setAnchorPoint(x, y);
	}

	public CGPoint getStart() {
		return start;
	}
	public CGPoint getEnd() {
		return end;
	}
	public void setEnd(CGPoint end) {
		this.end = end;
	}
	public CCSprite getPlant() {
		return plant;
	}
	public CCSprite getBack() {
		return back;
	}
	public int getId() {
		return id;
	}
}
