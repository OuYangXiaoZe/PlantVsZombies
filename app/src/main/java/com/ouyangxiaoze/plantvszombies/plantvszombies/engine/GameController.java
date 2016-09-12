package com.ouyangxiaoze.plantvszombies.plantvszombies.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.cocos2d.actions.CCProgressTimer;
import org.cocos2d.actions.CCScheduler;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import android.util.Log;

import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.ShowPlant;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.Plant;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.plant.PlantNut;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.plant.PlantPease;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.plant.PlantPotato;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.plant.PlantSnowPease;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.plant.PlantSun;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.product.Sun;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.zombies.ZombiesPrimary;
import com.ouyangxiaoze.plantvszombies.plantvszombies.layer.FightLayer;


/**
 * 管理战场
 *
 */
public class GameController {
	// ①战场划分
	// 将正个战场分成五条战线，处理完其中一条战线，另外四条战线复制即可

	// ②用户操作
	// 控制植物的建造
	// 阳光的收集

	// 植物建造判断：
	// 阳光数量是否满足建造要求
	// 用户选择地方是否可以进行植物建造

	// 阳光管理
	// 显示当前阳光数量
	// 执行增减操作

	// ③僵尸产生
	// 进行简单的逻辑处理：每隔一个时间段增加一个僵尸

	private static GameController instance = new GameController();
	private static List<FightLine> lines = new ArrayList<FightLine>();
	static {
		for (int i = 0; i < 5; i++) {
			FightLine item = new FightLine(i);
			lines.add(item);
		}
	}

	private GameController() {
		winSize = CCDirector.sharedDirector().getWinSize();
	}

	public static GameController getInstance() {
		return instance;
	}

	public static boolean isGameStart = false;// 游戏是否开始
	private static CGSize winSize;// 屏幕信息
	private CCTMXTiledMap gameMap;// 游戏界面
	private List<CGPoint> roads;// 僵尸行走路线
	private CGPoint[][] towers = new CGPoint[5][9];// 防御塔位置信息
	private CCLabel showSun;// 阳光数
	private List<ShowPlant> plants;// 玩家已选植物
	private boolean isChosePlant = false;// 玩家是否选择了植物
	private Plant currentPlant;// 当前用户选择植物
	private ShowPlant currentShowPlant;// 当前用户选择的展示植物

	private  int GamePercentage;
	private int zombiesNum = 20;// 僵尸添加的总数
	private CCProgressTimer progressTimer;// 进度控制

	/*
	结束游戏
	 */
	public void destroyGame(){
		isGameStart=false;

	}
	/**
	 * 开启游戏
	 *
	 * @param gameMap
	 * @param plants
	 */
	public void startGame(CCTMXTiledMap gameMap, List<ShowPlant> plants) {
		isGameStart = true;
		this.gameMap = gameMap;
		this.plants = plants;
		// 初始化战线管理
		loadRoad();
		// 开启僵尸添加（按照程序设置处理僵尸的添加时间和顺序）
		CCScheduler.sharedScheduler().schedule("addZombies", this, 20, !isGameStart);
		// 开始时僵尸出来的比较慢，让用户有时间进行建造（建造需要阳光）
		Sun.TOTLE_SUN = 50;
		// 显示阳光数
		showSun();
		// 初始化植物安放位置信息
		loadTower();
		updatePlantSelect();
		progress();
	}

	private void showSun() {
		showSun = CCLabel.makeLabel(Sun.TOTLE_SUN + "", "", 12);
		showSun.setColor(ccColor3B.ccBLACK);
		showSun.setPosition(20, CCDirector.sharedDirector().getWinSize().height - 42);
		showSun.setAnchorPoint(0.5f, 0.5f);
		gameMap.getParent().addChild(showSun);
	}

	private void loadTower() {
		for (int row = 0; row < 5; row++) {
			CCTMXObjectGroup towerGroup = gameMap.objectGroupNamed(String.format("tower%02d", row + 1));
			int coloum = 0;
			for (HashMap<String, String> item : towerGroup.objects) {
				towers[row][coloum] = CGPoint.ccp(Float.parseFloat(item.get("x")), Float.parseFloat(item.get("y")) - 15);
				coloum++;
			}
		}

	}

	private void loadRoad() {
		roads = new ArrayList<CGPoint>();
		CCTMXObjectGroup roadGroup = gameMap.objectGroupNamed("road");
		for (HashMap<String, String> item : roadGroup.objects) {
			roads.add(CGPoint.ccp(Float.parseFloat(item.get("x")), Float.parseFloat(item.get("y"))));
		}

	}

	/**
	 * 执行僵尸添加动作
	 *
	 * @param f
	 */
	public void addZombies(float f) {

		if (zombiesNum > 0) {
			Random random = new Random();
			int line = random.nextInt(5);
			// int line = 2;
			ZombiesPrimary zombiesPrimary = new ZombiesPrimary(roads.get(2 * line + 1), roads.get(2 * line + 2));
			lines.get(line).addZombies(zombiesPrimary);
			gameMap.getParent().addChild(zombiesPrimary, 1);
			zombiesNum--;
			progressTimer.setPercentage((Float) progressTimer.getPercentage() + 20);
			GamePercentage=GamePercentage+20;
			if((GamePercentage==100))
				System.out.println("游戏关卡完成了");
		} else {
			// TODO 处理成功
		}
	}

	/**
	 * 处理游戏过程中的用户操作
	 *
	 * @param point
	 */

	public void onTouch(CGPoint point) {
		if (isGameStart) {

			if (CGRect.containsPoint(gameMap.getParent().getChildByTag(FightLayer.TAG_PLANT_CHOSE_POOL).getBoundingBox(), point)) {
				// 种植植物
				if (isChosePlant) {
					currentShowPlant.getPlant().setOpacity(255);
					currentPlant = null;
					currentShowPlant = null;
					isChosePlant = false;
				}

				for (ShowPlant item : plants) {
					if (CGRect.containsPoint(item.getPlant().getBoundingBox(), point)) {
						switch (item.getId()) {
							case 1:// 单个豌豆
								currentPlant = new PlantPease();
								break;
							case 2:// 向日葵
								currentPlant = new PlantSun();
								break;

							case 4:// 坚果
								currentPlant = new PlantNut();
								break;
							case 5://此处加入土豆
								currentPlant = new PlantPotato();
								break;
							case 6://此处加入寒冰射手
								currentPlant = new PlantSnowPease();
								break;
							default:
								break;
						}
						if (currentPlant != null && Sun.TOTLE_SUN >= currentPlant.getPrice()) {
							item.getPlant().setOpacity(100);
							currentShowPlant = item;
							isChosePlant = true;
						}
						break;
					}
				}
			} else {
				if (isChosePlant) {
					// 安放植物
					// 判断用户点击的地点是否可以安放植物（①是否在建造范围内②是否已经有植物建造）
					if (isBuildAble(point)) {
						addPlant(currentPlant);

					}

				} else {
					// 收集阳光
					for (Sun item : Sun.suns) {
						if (CGRect.containsPoint(item.getBoundingBox(), point)) {
							item.addSun();
							if (Sun.TOTLE_SUN <= 9999) {
								showSun.setString(Sun.TOTLE_SUN + "");
								updatePlantSelect();
							} else {
								Sun.TOTLE_SUN = 9999;
								showSun.setString(Sun.TOTLE_SUN + "");
							}
						}
					}
				}

			}
		}
	}

	/**
	 * 添加植物
	 *currentPlant2
	 * @param
	 */
	private void addPlant(Plant plant) {
		gameMap.getParent().addChild(plant);
		lines.get(plant.getLine()).addPlant(plant);
		// 改变选中状态清空当前选择植物
		currentShowPlant.getPlant().setOpacity(255);
		currentPlant = null;
		isChosePlant = false;
		// 改变玩家阳光数量
		Sun.TOTLE_SUN -= plant.getPrice();
		showSun.setString(Sun.TOTLE_SUN + "");
		updatePlantSelect();
	}

	/**
	 * 判断是否可以放置植物
	 *
	 * @param point
	 * @return
	 */
	private boolean isBuildAble(CGPoint point) {
		// 计算行列号
		// 获取用户点击坐标（转换后）
		// 计算行列号（地图）
		// x轴：使用x除以块的宽度，获得的整数商加一
		// y轴：使用屏幕高度减y坐标除以块高度，获取的整数商加一
		// 判断是否可以安放植物
		// 计算对应的安放植物的坐标（数组：计算数组的横纵index，在当前行列号基础上减二）
		// 返回安放植物的坐标
		if (Sun.TOTLE_SUN >= currentPlant.getPrice()) {
			int line = (int) ((winSize.height - point.y) / 54) - 1;
			if (line >= 0) {
				int row = (int) (point.x / 46) - 1;
				if (row >= 0 && row < 9) {
					currentPlant.setPosition(towers[line][row]);
					Log.i(this.getClass().getSimpleName(), "line:" + line + "row:" + row);
					currentPlant.setRow(row);
					currentPlant.setLine(line);
					return lines.get(line).isBuildAble(row);
				}
			}
		}
		return false;
	}

	/**
	 * 更新用户是否可选
	 */
	private void updatePlantSelect() {
		for (ShowPlant item : plants) {
			CCSprite plant = item.getPlant();
			int reference = 0;
			switch (item.getId()) {
				case 1:// 单个豌豆
					reference = 10;
					break;
				case 2:// 向日葵
					reference = 50;
					break;

				case 4:// 坚果
					reference = 50;
					break;
				case 5://土豆
					reference=25;
					break;
				case 6://寒冰射手
					reference=10;
					break;
				default:
					reference = 10000;
					break;
			}
			checkSunNum(plant, reference);
		}
	}

	private void checkSunNum(CCSprite plant, int reference) {
		if (Sun.TOTLE_SUN >= reference) {
			plant.setOpacity(255);
		} else {
			plant.setOpacity(100);
		}
	}
	/**
	 * 更新进度
	 */
	private void progress() {
		progressTimer = CCProgressTimer.progressWithFile("image/fight/progress.png");

		progressTimer.setPosition(CCDirector.sharedDirector().getWinSize().width - 80, 13);
		gameMap.getParent().addChild(progressTimer);
		progressTimer.setScale(0.6f);

		progressTimer.setPercentage(0);//每增加一个僵尸需要调整进度，增加5
		progressTimer.setType(CCProgressTimer.kCCProgressTimerTypeHorizontalBarRL);

		CCSprite sprite = CCSprite.sprite("image/fight/flagmeter.png");
		sprite.setPosition(CCDirector.sharedDirector().getWinSize().width - 80, 13);
		gameMap.getParent().addChild(sprite);
		sprite.setScale(0.6f);
		CCSprite name = CCSprite.sprite("image/fight/FlagMeterLevelProgress.png");
		name.setPosition(CCDirector.sharedDirector().getWinSize().width - 80, 5);
		gameMap.getParent().addChild(name);
		name.setScale(0.6f);
	}
}
