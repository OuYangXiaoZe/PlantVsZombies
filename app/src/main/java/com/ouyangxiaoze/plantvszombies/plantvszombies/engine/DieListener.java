package com.ouyangxiaoze.plantvszombies.plantvszombies.engine;


import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.BaseElement;

/**
 * 处理僵尸或植物死亡
 */
public interface DieListener {
	void onDie(BaseElement element);
}
