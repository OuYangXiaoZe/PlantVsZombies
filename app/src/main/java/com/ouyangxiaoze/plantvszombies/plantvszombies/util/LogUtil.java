package com.ouyangxiaoze.plantvszombies.plantvszombies.util;

import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import android.util.Log;

public class LogUtil {
	public static void info(Class clazz,CCNode node)
	{
		CGPoint position = node.getPosition();
		CGPoint anchorPoint = node.getAnchorPoint();
		CGSize contentSize = node.getContentSize();
		
		Log.i(clazz.getSimpleName(), "x:"+position.x + "y:" + position.y);
		Log.i(clazz.getSimpleName(), "ax:"+anchorPoint.x + "ay:" + anchorPoint.y);
		Log.i(clazz.getSimpleName(), "w:"+contentSize.width+"h:"+contentSize.height);
		Log.i(clazz.getSimpleName(), "order:" + node.getZOrder());
	}
}
