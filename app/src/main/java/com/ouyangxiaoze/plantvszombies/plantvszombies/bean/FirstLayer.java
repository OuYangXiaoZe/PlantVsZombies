package com.ouyangxiaoze.plantvszombies.plantvszombies.bean;

import android.util.Log;
import android.view.MotionEvent;

import com.ouyangxiaoze.plantvszombies.plantvszombies.R;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

/**
 * Created by suning on 2016/9/2.
 * 第一个树根
 */
public class FirstLayer extends CCLayer {
    private static final String TAG = "FirstLayer";
    private static final int TAG_X = 10;
    public FirstLayer(){
        //设置layer可以处理touch的开关
      this.setIsTouchEnabled(true);//在一个场景所有的layer中，有且仅有一个layer能够处理用户的touch

        //图片，坐标，画自己的方法
        CCSprite leaf=CCSprite.sprite("z_1_attack_01.png");//图片资源assets文件夹下
        leaf.setPosition(200,200);

        // 为什么在左下角展示：坐标原点左下角
        // 图片为什么显示了一角（1/4）：锚点
        leaf.setAnchorPoint(0, 0);

        // 图片位置确定：采用锚点结合坐标综合定位（原则：坐标计算简单）
        flipX();
        flipY();
        // addChild方法
        // addChild：按照添加的顺序决定显示的优先级（后添加覆盖先添加）
        // 如果第一个优先显示，addChild(child, z)方法,z在z轴进行排序，默认值0
        // addChild(child, z, tag)
        this.addChild(leaf);
    }
    private void flipY() {
        CCSprite sprite = CCSprite.sprite("z_1_attack_01.png");
        sprite.setFlipY(true);

        sprite.setAnchorPoint(0, 0);
        CGPoint pos = CGPoint.ccp(0, 80);
        sprite.setPosition(pos);

        this.addChild(sprite);

    }

    private void flipX() {
        CCSprite sprite = CCSprite.sprite("z_1_attack_01.png");
        sprite.setFlipX(true);

        sprite.setAnchorPoint(0, 0);
        CGPoint pos = CGPoint.ccp(100, 0);
        sprite.setPosition(pos);

        this.addChild(sprite, 0, TAG_X);
    }
    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        // 点击界面获取到x轴镜像的坐标
        CCSprite flipX = (CCSprite) this.getChildByTag(TAG_X);
        // Log.i(TAG, "x:" + event.getX() + "y:" + event.getY());
        // Log.i(TAG, "x:" + flipX.getPosition().x + "y:" +
        // flipX.getPosition().y);

        // 判断精灵的点击：
        // 不同：MotionEvent——屏幕坐标；Cocos2d处理的坐标——openGl坐标系统（原点）
        CGPoint touchPos = this.convertTouchToNodeSpace(event);
        boolean containsPoint=false;

        if(touchPos!=null&&flipX!=null) {
            containsPoint = CGRect.containsPoint(flipX.getBoundingBox(),
                    touchPos);// 参数一：矩形区域；参数二：点击点（转换后）
        }
        if (containsPoint) {
            flipX.setOpacity(100);
            flipX.setVisible(false);
            flipX.removeSelf();
        }

        return super.ccTouchesBegan(event);
    }

}
