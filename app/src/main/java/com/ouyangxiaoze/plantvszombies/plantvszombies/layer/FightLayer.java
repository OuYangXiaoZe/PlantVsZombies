package com.ouyangxiaoze.plantvszombies.plantvszombies.layer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;


import org.apache.commons.lang3.StringUtils;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCCallFuncND;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.Toast;


import com.ouyangxiaoze.plantvszombies.plantvszombies.R;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.ShowPlant;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.Zombies;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.zombies.ZombiesPrimary;
import com.ouyangxiaoze.plantvszombies.plantvszombies.engine.GameController;
import com.ouyangxiaoze.plantvszombies.plantvszombies.layer.manager.BaseLayer;
import com.ouyangxiaoze.plantvszombies.plantvszombies.util.CommonUtil;


/**
 * 游戏主界面
 */
public class FightLayer extends BaseLayer {
	// 包含两部分内容：
	// ①植物选择
	// ②对战

	// 植物选择
	// 用户选择植物展示
	// 用户已经选择植物展示（限制用户选择植物个数）
	// 植物选择依据 必备植物：向日葵
	// 需要根据展示的僵尸种类进行相应植物的选择

	/**
	 * 部分一：植物选择 用户选择植物展示 用户已经选择植物展示（限制用户选择植物个数） 植物选择依据 必备植物：向日葵 需要根据展示的僵尸种类进行相应植物的选择
	 */

	private boolean isStartGame = false;// 分离植物选择和对战
	private CCTMXTiledMap gameMap;// 游戏地图
	private List<CGPoint> zombiesPoints;// 僵尸位置点集合

	private CCSprite plantChoosePool;// 植物选择容器
	private CCSprite plantChosePool;// 已经选择植物容器

	// private static Map<Integer,CCSprite> plantPool=null;// 已有植物集合
	private static int plantNum = 6;// 目前放置六种植物
	// 封装一个展示植物信息，除图片信息外需要记录起始点和终止点，处理两个容器之间植物的移动操作
	private static Map<Integer, ShowPlant> showPlants = new HashMap<Integer, ShowPlant>();
	static {
		for (int i = 1; i <= plantNum; i++) {
			showPlants.put(i, new ShowPlant(i));
		}
	}
	private static CopyOnWriteArrayList<Integer> chosePlants = new CopyOnWriteArrayList<Integer>();// 已经选择的植物

	private List<Zombies> zombiesList = new ArrayList<Zombies>();// 用于展示的僵尸集合，当游戏开始时需要将这些信息回收

	private ArrayList<CCSpriteFrame> frames;// 开始游戏前动画帧

	/**
	 * 部分二：对战 将游戏控制移交 战场分析
	 */

	/**
	 * 部分一详解： ①地图展示： 先显示对战界面 然后展示僵尸种类 ②展示已有植物和选择结果栏 植物的选择和取消
	 */

	public FightLayer() {
		transitionSceneType = 1;
		preGame();
		// 点击“一起摇滚吧”开始游戏
	}

	/**
	 * 游戏准备工作
	 */
	private void preGame() {
		engine.pauseSound();
		engine.playSound(getContext(), R.raw.day, true);
		// 加载地图
		loadMap();
		// 解析僵尸位置信息
		parserMap4ZombilesPoint();
		// 加载僵尸
		loadZombies();
		// 加载植物
		loadPlant();
		// 执行顺序:
		// 地图展示后停顿1秒
		// 移动到僵尸展示界面
		// 1秒钟后展示所有植物和植物选择框
		sequence(this, 1);
		// 选择植物处理
	}

	/**
	 * 加载地图 CCTMXTiledMap
	 */
	private void loadMap() {
		gameMap=CommonUtil.loadMap("image/fight/map_day.tmx");
//		gameMap = CCTMXTiledMap.tiledMap("image/fight/map_day.tmx");
//		gameMap.setAnchorPoint(0.5f, 0.5f);
//		CGSize contentSize = gameMap.getContentSize();
//		gameMap.setPosition(contentSize.width / 2, contentSize.height / 2);
		addChild(gameMap);
	}

	/**
	 * 解析僵尸位置信息
	 */
	private void parserMap4ZombilesPoint() {
		zombiesPoints=CommonUtil.loadPoint(gameMap, "zombies");

//		CCTMXObjectGroup zombiesGroup = gameMap.objectGroupNamed("zombies");
//		// 获取僵尸位置信息
//		ArrayList<HashMap<String, String>> zombies = zombiesGroup.objects;
//		// 分别以x和y为键，获取坐标值信息---->封装到点集合中
//		zombiesPoints = new ArrayList<CGPoint>();
//		for (HashMap<String, String> item : zombies) {
//			float x = Float.parseFloat(item.get("x"));
//			float y = Float.parseFloat(item.get("y"));
//			zombiesPoints.add(ccp(x, y));
//		}
	}

	/**
	 * 加载僵尸
	 */
	private void loadZombies() {
		// 在僵尸位置中任选6个点，添加僵尸
		for (int i = 4; i < 10; i++) {
			ZombiesPrimary primary = new ZombiesPrimary();
			primary.setPosition(zombiesPoints.get(i));
			zombiesList.add(primary);
			gameMap.addChild(primary);
		}
	}

	/**
	 * 加载植物
	 */
	private void loadPlant() {
		plantChoosePool = CCSprite.sprite("image/fight/chose/fight_choose.png");
		plantChosePool = CCSprite.sprite("image/fight/chose/fight_chose.png");

		// 向植物容器中添加植物
		for (int i = 0; i < plantNum; i++) {
			ShowPlant item = showPlants.get(i + 1);
			item.setAnchorPoint(0, 0);
			item.setPosition(16 + (i % 4) * 56f, 175 - (i / 4) * 59f);
			plantChoosePool.addChild(item.getBack());
			plantChoosePool.addChild(item.getPlant());
		}
		// 添加开始按钮
		CGSize contentSize = plantChoosePool.getContentSize();
		CCSprite start = CCSprite.sprite("image/fight/chose/fight_start.png");
		start.setPosition(ccp(contentSize.width / 2, 20));
		plantChoosePool.addChild(start, TAG_START_GAME, TAG_START_GAME);
	}

	/**
	 * 执行顺序
	 */
	public void sequence(Object node, Object data) {
		// 执行顺序:
		// 地图展示后停顿1秒
		// 移动到僵尸展示界面（展示僵尸）
		// 1秒钟后展示所有植物和植物选择框
		if (data != null && StringUtils.isNumeric(data.toString())) {
			int step = Integer.parseInt(data.toString());
			CCSequence sequence = null;
			switch (step) {
				case 1:
					CGSize mapSize = gameMap.getContentSize();
					CCMoveTo ccMoveTo = CCMoveTo.action(1, ccp(mapSize.width / 2 - (mapSize.width - winSize.width),
							mapSize.height / 2));
					sequence = CCSequence.actions(CCDelayTime.action(1), ccMoveTo, CCDelayTime.action(1), CCCallFuncND.action(
							this, "sequence", 2));
					gameMap.runAction(sequence);
					break;
				case 2:
					// 显示植物相关的两个容器
					plantChosePool.setAnchorPoint(0, 1);
					plantChosePool.setPosition(0, winSize.height);
					addChild(plantChosePool, 0, TAG_PLANT_CHOSE_POOL);
					// addChild(plantChosePool,TAG_PLANT_CHOSE_POOL,TAG_PLANT_CHOSE_POOL);
					plantChosePool.runAction(CCMoveTo.action(0.5f, ccp(0, winSize.height)));

					plantChoosePool.setAnchorPoint(0, 1);
					plantChoosePool.setPosition(0, 0);
					addChild(plantChoosePool);
					plantChoosePool.runAction(CCMoveTo.action(0.5f, ccp(0, plantChoosePool.getContentSize().height)));
					break;
			}
		}
	}

	private void startGame() {
		isStartGame = true;
		// 移除用于展示的僵尸
		for (Zombies item : zombiesList) {
			gameMap.removeChild(item, true);
		}
		// 移除植物容器
		plantChoosePool.removeSelf();
		// 添加新的已选植物，缩小已选植物容器
		plantChosePool.setScale(0.65);
		for (Integer item : chosePlants) {
			ShowPlant showPlant = showPlants.get(item);
			CCSprite plant = showPlant.getPlant();
			plant.setPosition(showPlant.getEnd().x * 0.65f, showPlant.getEnd().y + (winSize.height - showPlant.getEnd().y)
					* 0.65f);
			plant.setScale(0.65);
			plant.setAnchorPoint(0, 0.5f);
			this.addChild(plant);
		}

		// 移动地图，显示开始游戏前动画
		CGSize contentSize = gameMap.getContentSize();
		CCMoveTo action = CCMoveTo.action(0.2f, ccp(contentSize.width / 2, contentSize.height / 2));
		CCSequence sequence = CCSequence.actions(action, CCCallFunc.action(this, "ready"));
		gameMap.runAction(sequence);
	}

	/**
	 * 准备、安放、植物(声音版)
	 */
	public void ready() {
		// 准备、安放、植物
		CCSprite ready = CCSprite.sprite("image/fight/StartReady.png");
		frames = new ArrayList<CCSpriteFrame>();

		frames.add(CCSprite.sprite("image/fight/StartReady.png").displayedFrame());
		frames.add(CCSprite.sprite("image/fight/StartSet.png").displayedFrame());
		frames.add(CCSprite.sprite("image/fight/StartPlant.png").displayedFrame());

		ready.setPosition(winSize.width / 2, winSize.height / 2);
		addChild(ready, TAG_FIGHT_READY, TAG_FIGHT_READY);
		schedule("displayImgAndSound", 1f);
	}

	private int index = 0;// 播放的frames下标

	public void displayImgAndSound(float f) {
		CCSprite ready = (CCSprite) getChildByTag(TAG_FIGHT_READY);
		if (index < frames.size()) {
			ready.setDisplayFrame(frames.get(index));
			engine.playEffect(getContext(), R.raw.onclick);
			index++;
		} else {
			ready.removeSelf();
			// 游戏开始
			List<ShowPlant> plants = new ArrayList<ShowPlant>();
			for (Integer key : chosePlants) {
				plants.add(showPlants.get(key));
			}
			GameController.getInstance().startGame(gameMap, plants);
			unschedule("displayImgAndSound");
		}
	}


	@Override
	public boolean ccTouchesEnded(MotionEvent event) {

		if (isStartGame) {
			// 游戏控制

			GameController.getInstance().onTouch(convertTouchToNodeSpace(event));

		} else {
			if (CGRect.containsPoint(plantChosePool.getBoundingBox(), convertTouchToNodeSpace(event))) {
				// 处理删除已选植物
				boolean isDeleteKey = false;
				CGPoint lastPoint = null;
				for (Integer item : chosePlants) {
					ShowPlant showPlant = showPlants.get(item);

					if (isDeleteKey) {
						// 进行后续的移动工作
						CCMoveTo moveTo = CCMoveTo.action(0, lastPoint);
						showPlant.getPlant().runAction(moveTo);
						CGPoint temp = lastPoint;
						lastPoint = showPlant.getEnd();
						showPlant.setEnd(temp);

					} else {
						if (isClick(event, showPlant.getPlant())) {
							// 取消已选植物
							CCMoveTo moveTo = CCMoveTo.action(0.3f, showPlant.getStart());
							showPlant.getPlant().runAction(moveTo);
							lastPoint = showPlant.getEnd();
							chosePlants.remove(item);
							isDeleteKey = true;
						}
					}
				}
			} else {
				if (isClick(event, plantChoosePool.getChildByTag(TAG_START_GAME))) {
					// 点击开启游戏
					if (chosePlants.size() <= 5)
						startGame();
				} else {
					// 处理选择植物

					for (int i = 0; i < plantNum; i++) {
						ShowPlant showPlant = showPlants.get(i + 1);
						if (isClick(event, showPlant.getPlant())) {
							// 用户选择了一个植物
							if(chosePlants.size() < 5) {
								CCMoveTo moveTo = CCMoveTo.action(0.3f, ccp(75 + chosePlants.size() * 53f, winSize.height - 65));
								showPlant.getPlant().runAction(moveTo);
								showPlant.setEnd(ccp(75 + chosePlants.size() * 53f, winSize.height - 65));
								chosePlants.add(i + 1);
							}else{
//								Toast.makeText(getContext(),"你所选的植物已经超过五种了，无法在选择其他植物", Toast.LENGTH_SHORT).show();
								System.out.println(chosePlants.size());
								System.out.println("你所选的植物已经超过五种了，无法在选择其他植物");
							}
						}
					}
				}
			}
		}

		return super.ccTouchesEnded(event);
	}

	private static final int TAG_START_GAME = 10000;
	private static final int TAG_FIGHT_READY = 10005;
	public static final int TAG_PLANT_CHOSE_POOL = 10010;

}

