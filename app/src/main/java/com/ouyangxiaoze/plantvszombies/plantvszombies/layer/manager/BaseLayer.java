package com.ouyangxiaoze.plantvszombies.plantvszombies.layer.manager;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import android.app.Activity;
import android.view.MotionEvent;

import com.ouyangxiaoze.plantvszombies.plantvszombies.R;


public abstract class BaseLayer extends CCLayer{
	protected static SoundEngine engine;
	protected CGSize winSize;//屏幕大小

	// 屏幕切换的方式
	protected int transitionSceneType = 0;

	static{
		engine=SoundEngine.sharedEngine();
		//背景音乐预加载
		engine.preloadSound(getContext(), R.raw.start);//map.put(resid,res)
		engine.preloadSound(getContext(), R.raw.day);
		engine.preloadSound(getContext(), R.raw.night);
		//按钮点击音效文件
		engine.preloadEffect(getContext(), R.raw.onclick);
	}

	protected BaseLayer()
	{
		winSize=CCDirector.sharedDirector().getWinSize();
		this.setIsTouchEnabled(true);
	}

	/**
	 * 获取上下文信息（Activity）
	 * @return
	 */
	protected static Activity getContext() {
		return CCDirector.sharedDirector().getActivity();
	}

	protected boolean isClick(MotionEvent event,CCNode sprite)
	{
		CGPoint clickPoint = this.convertTouchToNodeSpace(event);
		return CGRect.containsPoint(sprite.getBoundingBox(), clickPoint);
	}

	public int getTransitionSceneType() {
		return transitionSceneType;
	}


}
