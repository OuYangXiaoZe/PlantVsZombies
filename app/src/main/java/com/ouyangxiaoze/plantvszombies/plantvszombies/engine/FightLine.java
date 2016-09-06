package com.ouyangxiaoze.plantvszombies.plantvszombies.engine;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.cocos2d.actions.CCScheduler;

import android.util.Log;

import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.AttackPlant;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.BaseElement;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.Plant;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.Zombies;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.product.Bullet;


/**
 * 行战场
 *
 * @author Administrator
 *
 */
public class FightLine implements DieListener {

	private int lineNum = 0;// 行战场编号
	private CopyOnWriteArrayList<Zombies> zombiesList = new CopyOnWriteArrayList<Zombies>();// 当前行战场中僵尸的集合
	// 植物
	private Map<Integer, Plant> plants = new HashMap<Integer, Plant>();// key为植物所处的列信息
	// 攻击植物
	private CopyOnWriteArrayList<AttackPlant> attackPlants = new CopyOnWriteArrayList<AttackPlant>();

	public FightLine(int lineNum) {
		super();
		this.lineNum = lineNum;
		// 开启僵尸攻击
		// 每个一个时间段判断当前的列中是否含有植物，如果有植物就对这个植物进行攻击
		CCScheduler.sharedScheduler().schedule("attackPlant", this, 0.5f, false);
		// 开启植物攻击
		// 每个一个时间段判断当前列中是否含有僵尸，如果有僵尸
		// 判断是否含有攻击植物，如果含有攻击植物,发射豌豆
		CCScheduler.sharedScheduler().schedule("attackZombies", this, 0.5f, false);
		// 检查豌豆
		CCScheduler.sharedScheduler().schedule("checkBullet", this, 0.1f, false);
	}

	/**
	 * 僵尸攻击植物
	 *
	 * @param f
	 */
	public void attackPlant(float f) {
		if (plants.size() > 0) {
			for (Zombies item : zombiesList) {
				int x = (int) (item.getPosition().x - 15);
				Integer row = (int) (x / 46) - 1;
				if (row >= 0 && row < 9) {
					if (plants.get(row) != null) {
						item.attack(plants.get(row));
					}
				}
			}
		}
	}

	/**
	 * 攻击僵尸
	 */
	public void attackZombies(float f) {
		if (zombiesList.size() > 0) {
			for (AttackPlant item : attackPlants) {
				Zombies slowestZombies = getSlowestZombies();
				if (slowestZombies != null) {
					if (slowestZombies.getPosition().x > item.getPosition().x)
						item.attack(null);
				}
			}
		}
	}

	/**
	 * 检查植物发射的豌豆是否击中僵尸
	 *
	 * @param f
	 */
	public void checkBullet(float f) {
		for (AttackPlant item : attackPlants) {
			LinkedList<Bullet> bullets = item.getBullets();
			if (bullets.size() > 0) {
				Bullet first = bullets.getFirst();
				for (Zombies zombies : zombiesList) {
					float x = first.getPosition().x;
					if (x >= zombies.getPosition().x - 30 && x <= zombies.getPosition().x + 30) {
						zombies.attacked(first);
						first.distory();
					}
				}
			}
		}
	}

	/**
	 * 获取跑的最慢的僵尸
	 */
	private Zombies getSlowestZombies() {
		float max = 0;
		int index = -1;
		for (int i = zombiesList.size() - 1; i >= 0; i--) {
			if (zombiesList.get(i).getPosition().x > max) {
				max = zombiesList.get(i).getPosition().x;
				index = i;
			}
		}
		if (index != -1) {
			return zombiesList.get(index);
		}
		return null;
	}

	public int getLineNum() {
		return lineNum;
	}

	/**
	 * 添加僵尸
	 *
	 * @param zombies
	 */
	public void addZombies(Zombies zombies) {
		zombies.setListener(this);
		zombiesList.add(zombies);
	}

	/**
	 * 添加植物
	 *
	 * @param plant
	 */
	public void addPlant(Plant plant) {
		plant.setListener(this);
		if (plant.getType() == Plant.TYPE_ATTACK) {
			attackPlants.add((AttackPlant) plant);
		}
		plants.put(plant.getRow(), plant);
	}

	/**
	 * 判断当前行战场的某个列是否可以放置植物
	 *
	 * @param row
	 * @return
	 */
	public boolean isBuildAble(Integer row) {
		Log.i(this.getClass().getSimpleName(), "line:" + lineNum + "row:" + row + "hasPlant:" + (plants.get(row) == null));
		return plants.get(row) == null;
	}

	@Override
	public void onDie(BaseElement element) {
		if (element instanceof Plant) {
			plants.remove(element.getRow());
			int type = ((Plant) element).getType();
			if (type == Plant.TYPE_ATTACK) {
				attackPlants.remove(element);
			}
		} else if (element instanceof Zombies) {
			zombiesList.remove(element);
		}

	}
}
