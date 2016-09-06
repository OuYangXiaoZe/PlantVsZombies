package com.ouyangxiaoze.plantvszombies.plantvszombies.bean.zombies;

import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.ElementAction;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.Plant;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.base.Zombies;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.product.Bullet;

import java.util.ArrayList;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGPoint;


/**
 * 普通级别僵尸
 */
public class ZombiesPrimary extends Zombies {

	private static final String resPath = "image/zombies/zombies_1/walk/z_1_01.png";// 资源

	private static String walkRes = "image/zombies/zombies_1/walk/z_1_%02d.png";// 移动
	private static String attackRes = "image/zombies/zombies_1/attack/z_1_attack_%02d.png";// 攻击

	private static String dieRes = "image/zombies/zombies_1/die/z_1_die_%02d.png";// 死亡
	private static String headRes = "image/zombies/zombies_1/head/z_1_head_%02d.png";// 掉脑袋

	// 移动帧
	private static ArrayList<CCSpriteFrame> moveFrames;// 移动帧集合
	private CCAnimation moveAnimation;// 移动的帧
	private CCAnimate moveAnimate;// 移动动作
	// 静止帧
	protected static ArrayList<CCSpriteFrame> motionlessFrames;// 静止帧集合
	protected CCAnimation motionlessAnimation;// 静止的帧
	protected CCAnimate motionlessAnimate;// 静止动作

	// 攻击帧
	protected static ArrayList<CCSpriteFrame> attackFrames;// 攻击帧集合
	protected CCAnimation attackAnimation;// 攻击
	protected static CCAnimate attackAnimate;// 攻击动作
	// 死亡帧
	protected static ArrayList<CCSpriteFrame> dieFrames;// 死亡帧集合
	protected CCAnimation dieAnimation;// 死亡的帧
	protected CCAnimate dieAnimate;// 死亡动作
	// 僵尸掉脑袋
	protected static ArrayList<CCSpriteFrame> lostHeadFrames;
	protected CCAnimation lostHeadAnimation;
	protected CCAnimate lostHeadAnimate;

	private boolean isAttack = false;// 标示是否处于攻击状态
	private boolean isDie = false;// 僵尸是否存活
	private Plant target;// 攻击的目标

	public ZombiesPrimary() {
		super(resPath);
		initInfo();
		motionless();
	}

	public ZombiesPrimary(CGPoint start, CGPoint end) {
		super(resPath);
		initInfo();
		startPoint = start;
		endPoint = ccp(end.x, end.y - 15);
		setPosition(startPoint.x, startPoint.y - 15);
		speed = 20;
		move();
	}

	private void initInfo() {
		setAnchorPoint(0.5f, 0);
		setScale(0.5);
		life = 100;
		attack = 10;
	}

	@Override
	public void attack(ElementAction action) {
		if (isAttack)
			return;
		// 停止所有动作
		this.stopAllActions();
		// 播放攻击帧动作
		if (attackFrames == null) {
			attackFrames = new ArrayList<CCSpriteFrame>();
			for (int i = 1; i <= 10; i++) {
				attackFrames.add(CCSprite.sprite(String.format(attackRes, i)).displayedFrame());
			}
		}
		attackAnimation = CCAnimation.animation("", 0.2f, attackFrames);
		attackAnimate = CCAnimate.action(attackAnimation);
		this.runAction(CCRepeatForever.action(attackAnimate));
		isAttack = true;
		// 攻击传入的action：植物
		target = (Plant) action;
		CCScheduler.sharedScheduler().schedule("attackPlant", this, 0.5f, isDie);

	}

	public void attackPlant(float f) {
		if (target.getLife() > 0) {
			target.attacked(this);
		} else {
			isAttack = false;
			attackAnimate.stop();
			move();
			CCScheduler.sharedScheduler().unschedule("attackPlant", this);
		}
	}

	@Override
	public void attacked(ElementAction action) {
		if (action instanceof Bullet) {
			int attack = ((Bullet) action).getAttack();
			this.life -= attack;
			if (life <= 0) {
				CCScheduler.sharedScheduler().unschedule("attackPlant", this);
				// 播放动画
				if (listener != null)
					listener.onDie(this);
				stopAllActions();
				// 僵尸脑袋掉下的序列帧
				if (lostHeadFrames == null) {
					lostHeadFrames = new ArrayList<CCSpriteFrame>();
					for (int i = 1; i <= 6; i++) {
						lostHeadFrames.add(CCSprite.sprite(String.format(headRes, i)).displayedFrame());
					}
				}

				lostHeadAnimation = CCAnimation.animation("", 0.1f, lostHeadFrames);
				lostHeadAnimate = CCAnimate.action(lostHeadAnimation);

				// 僵尸倒下的序列帧
				if (dieFrames == null) {
					dieFrames = new ArrayList<CCSpriteFrame>();
					for (int i = 1; i <= 6; i++) {
						dieFrames.add(CCSprite.sprite(String.format(dieRes, i)).displayedFrame());
					}
				}

				dieAnimation = CCAnimation.animation("", 0.3f, dieFrames);
				dieAnimate = CCAnimate.action(dieAnimation);

				CCSequence sequence = CCSequence.actions(lostHeadAnimate, dieAnimate, CCCallFunc.action(this, "distory"));
				runAction(sequence);

			}
		}

	}

	@Override
	public void motionless() {
		if (motionlessFrames == null) {
			// 移动帧处理
			motionlessFrames = new ArrayList<CCSpriteFrame>();
			for (int i = 1; i < 3; i++) {
				motionlessFrames.add(CCSprite.sprite(String.format(walkRes, i)).displayedFrame());
			}
		}

		motionlessAnimation = CCAnimation.animation("", 0.2f, motionlessFrames);
		motionlessAnimate = CCAnimate.action(motionlessAnimation);
		this.runAction(CCRepeatForever.action(motionlessAnimate));
	}

	@Override
	public void move() {
		if (startPoint != null && endPoint != null) {
			float time = (getPosition().x - endPoint.x) / speed;

			CCMoveTo ccMoveTo = CCMoveTo.action(time, endPoint);
			CCSequence sequence = CCSequence.actions(ccMoveTo, CCCallFunc.action(this, "gameOver"));
			runAction(sequence);

			if (moveFrames == null) {
				// 移动帧处理
				moveFrames = new ArrayList<CCSpriteFrame>();
				moveFrames.addAll(motionlessFrames);
				for (int i = 3; i < 8; i++) {
					moveFrames.add(CCSprite.sprite(String.format(walkRes, i)).displayedFrame());
				}
			}

			moveAnimation = CCAnimation.animation("", 0.2f, moveFrames);
			moveAnimate = CCAnimate.action(moveAnimation);
			this.runAction(CCRepeatForever.action(moveAnimate));
		}

	}

	public void gameOver() {
		distory();
	}

	@Override
	public void distory() {
		isDie = true;
		if (listener != null)
			listener.onDie(this);
		super.distory();
	}

}
