package com.ouyangxiaoze.plantvszombies.plantvszombies.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.MotionEvent;

/**
 * 通用工具
 *
 * @author Administrator
 *
 */
public class CommonUtil {
	/**
	 * 版本信息获取
	 *
	 * @return
	 */
	public static String getVersion(Context context) {
		PackageManager manager = context.getPackageManager();
		try {
			PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 服务器是否有版本更新
	 *
	 * @return
	 */
	@Deprecated
	public static boolean isUpdate(Context context, String newVersionName) {
		PackageManager manager = context.getPackageManager();
		try {
			PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
			String oldVersionName = packageInfo.versionName;
			if (oldVersionName.compareTo(newVersionName) < 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 检查版本更新
	 *
	 * @param context
	 * @param newVersionCode
	 * @return
	 */
	public static boolean isUpdate(Context context, int newVersionCode) {
		PackageManager manager = context.getPackageManager();
		try {
			PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
			int oldVersionCode = packageInfo.versionCode;
			if (oldVersionCode < newVersionCode) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 加载游戏地图
	 * @param tmxFile
	 * @return
	 */
	public static CCTMXTiledMap loadMap(String tmxFile)
	{
		CCTMXTiledMap gameMap = CCTMXTiledMap.tiledMap(tmxFile);

		//便于手动平移地图
		gameMap.setAnchorPoint(0.5f, 0.5f);
		CGSize contentSize = gameMap.getContentSize();
		gameMap.setPosition(contentSize.width / 2, contentSize.height / 2);

		return gameMap;
	}
	/**
	 * 从地图中加载指定名称的点集合
	 * @param map
	 * @param name
	 * @return
	 */
	public static List<CGPoint> loadPoint(CCTMXTiledMap map,String name)
	{
		CCTMXObjectGroup zombiesGroup = map.objectGroupNamed(name);
		// 获取僵尸位置信息
		ArrayList<HashMap<String, String>> zombies = zombiesGroup.objects;
		// 分别以x和y为键，获取坐标值信息---->封装到点集合中
		List<CGPoint> points = new ArrayList<CGPoint>();
		for (HashMap<String, String> item : zombies) {
			float x = Float.parseFloat(item.get("x"));
			float y = Float.parseFloat(item.get("y"));
			points.add(CGPoint.ccp(x, y));
		}
		return points;
	}

}
