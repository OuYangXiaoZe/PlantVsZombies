package com.ouyangxiaoze.plantvszombies.plantvszombies.layer;

import java.util.ArrayList;
import java.util.List;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;

import com.ouyangxiaoze.plantvszombies.plantvszombies.R;
import com.ouyangxiaoze.plantvszombies.plantvszombies.layer.manager.BaseLayer;
import com.ouyangxiaoze.plantvszombies.plantvszombies.layer.manager.LayerManager;
import com.ouyangxiaoze.plantvszombies.plantvszombies.util.CommonUtil;


/**
 * 资源加载界面
 *
 * @author Administrator
 *
 */
public class Loading1 extends BaseLayer {
	protected static final String TAG = "Loading";

	private CCSprite start;

	private CCAnimation loadingAnimation;
	private CCAnimate loadingAnimate;

	public Loading1() {
		transitionSceneType = 1;
		init();
	}

	private ArrayList<CCSpriteFrame> loadFreams = new ArrayList<CCSpriteFrame>();

	private void init() {
		loadBGM();
		setLoadingAnimation();
		loadImage();
		timeConsuming();
	}

	private void setLoadingAnimation() {
		for (int i = 1; i <= 9; i++) {
			loadFreams.add(CCSprite.sprite(String.format("image/loading/loading_%02d.png", i)).displayedFrame());
		}
		loadingAnimation = CCAnimation.animation("", 0.2f, loadFreams);
		loadingAnimate = CCAnimate.action(loadingAnimation, false);
	}

	/**
	 * 耗时工作：主要工作检查版本信息
	 */
	private void timeConsuming() {
		new AsyncTask<Void, Integer, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				publishProgress(1);
				SystemClock.sleep(1000);
				publishProgress(2);
				// 进行到第一个状态
				// TODO 获取网络最新版本信息
				SystemClock.sleep(5000);
				if (CommonUtil.isUpdate(getContext(), 1)) {
					// TODO 下载最新应用
				}
				return false;
			}

			@Override
			protected void onProgressUpdate(Integer... values) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(values);
			}

			protected void onPostExecute(Boolean result) {
				start.setVisible(true);
				if (progressLoad != null)
					progressLoad.setVisible(false);
				isClick = true;
				if (result) {
					// TODO 提示用户是否进行版本更新
				}
			};

		}.execute();

	}

	/**
	 * 加载图片
	 */
	private void loadImage() {
		// logo信息展示
		// 需要显示一段时间，隐藏后加载背景图片
		CCSprite logo = CCSprite.sprite("image/popcap_logo.png");
		logo.setPosition(winSize.width / 2, winSize.height / 2);
		this.addChild(logo);

		start = CCSprite.sprite("image/loading/loading_start.png");
		start.setScale(0.6);
		start.setPosition(winSize.width / 2, 30);
		start.setVisible(false);
		addChild(start, TAG_START, TAG_START);

		// 延时
		CCDelayTime delayTime = CCDelayTime.action(1);
		// 隐藏
		CCHide hide = CCHide.action();
		// 加载背景图片
		// 瞬时动作，调用一个方法
		// 调用目标对象中的方法（当前的Layer中加载背景图片）
		CCCallFunc func = CCCallFunc.action(this, "loadBG");

		CCSequence sequence = CCSequence.actions(delayTime, hide, func);
		logo.runAction(sequence);

	}

	private CCSprite progressLoad;

	/**
	 * 背景显示
	 *
	 */
	public void loadBG() {
		// 背景显示
		CCSprite sprite = CCSprite.sprite("image/cover.jpg");
		sprite.setAnchorPoint(0, 0);
		sprite.setScale(0.6);
		this.addChild(sprite);
		// 加载进度图片
		progressLoad = CCSprite.sprite("image/loading/loading_01.png");
		progressLoad.setScale(0.6);
		progressLoad.setPosition(winSize.width / 2, 30);
		addChild(progressLoad);

		progressLoad.runAction(loadingAnimate);
	}

	/**
	 * 加载背景音乐
	 */
	private void loadBGM() {
		engine.playSound(getContext(), R.raw.start, true);
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		if (isClick && isClick(event, this.getChildByTag(TAG_START)))
			engine.playEffect(getContext(), R.raw.onclick);
		return super.ccTouchesBegan(event);
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		// 判断是否点击到按钮
		if (isClick && isClick(event, this.getChildByTag(TAG_START))) {
			// 界面跳转
			Log.i(TAG, "click");
			LayerManager.getInstance().changeLayerByTransitionSceneType(MainMenuLayer.class);

		}

		return super.ccTouchesEnded(event);
	}

	private static final int TAG_START = 1;// 进入游戏
	private boolean isClick = false;// 进入游戏按钮是否可以点击

}
