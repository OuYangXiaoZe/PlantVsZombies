package com.ouyangxiaoze.plantvszombies.plantvszombies.layer;

import com.ouyangxiaoze.plantvszombies.plantvszombies.layer.manager.BaseLayer;
import com.ouyangxiaoze.plantvszombies.plantvszombies.layer.manager.LayerManager;
import com.ouyangxiaoze.plantvszombies.plantvszombies.util.LogUtil;

import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCSprite;


/**
 * 游戏模式选择
 */
public class MainMenuLayer extends BaseLayer {
	private static final String resPath = "image/menu/";

	public MainMenuLayer() {
		transitionSceneType=1;
		init();
	}

	private void init() {
		loadBG();
		addMenu();
	}

	/**
	 * 添加模式选择按钮
	 */
	private void addMenu() {
		//最后的参数指定点击事件
		CCMenuItem item = CCMenuItemSprite.item(CCSprite.sprite(resPath + "start_adventure_default.png"), CCSprite.sprite(resPath
				+ "start_adventure_press.png"), this, "onClick");
		CCMenu menu = CCMenu.menu();
		menu.addChild(item);
		menu.setScale(0.5f);
		menu.setPosition(winSize.width / 2-25, winSize.height / 2 - 110);
		menu.setRotation(4.5f);


		addChild(menu);
		LogUtil.info(getClass(), menu);
	}

	private void loadBG() {
		CCSprite bg = CCSprite.sprite(resPath + "main_menu_bg.jpg");
		bg.setAnchorPoint(0, 0);
		this.addChild(bg);
	}

	public void onClick(Object object) {

		LayerManager.getInstance().changeLayerByTransitionSceneType(FightLayer.class);
	}

}
