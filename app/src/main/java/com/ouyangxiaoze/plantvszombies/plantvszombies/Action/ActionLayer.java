package com.ouyangxiaoze.plantvszombies.plantvszombies.Action;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.ease.CCEaseAction;
import org.cocos2d.actions.ease.CCEaseIn;
import org.cocos2d.actions.ease.CCEaseOut;
import org.cocos2d.actions.interval.CCBezierBy;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCJumpBy;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CCBezierConfig;
import org.cocos2d.types.CGPoint;

public class ActionLayer extends CCLayer {
    public ActionLayer() {
        init();
    }

    private void init() {
        // CCAction
        // 受时间限制的动作
        // --------CCFiniteTimeAction
        // - An action with a duration of 0 seconds——瞬时动作
        // ----------------CCInstantAction
        // - An action with a duration of 35.5 seconds——延时动作
        // ----------------CCIntervalAction

        // 不受时间限制

        // --------CCFollow
        // Eg:
        // [layer runAction: [CCFollow actionWithTarget:hero]];

        // --------CCRepeatForever
        // 行走的序列帧播放

        // --------CCSpeed
        // 游戏速度控制

        // 移动：by和to的区别
        // ①by相对移动，会在现有坐标进行增加操作
        // ②by有反向的动作

        // move();
        // move1();
        // jump();

        // bezier();
        ease();
    }

    private void ease() {
        CGPoint pos = CGPoint.ccp(300, 200);
        CCMoveBy moveBy = CCMoveBy.action(2, pos);

        CCEaseIn easeIn = CCEaseIn.action(moveBy, 10);// 渐快：加速运动（加速度恒定）

        CCEaseOut easeOut = (CCEaseOut) easeIn.reverse();

        getSprite().runAction(
                CCRepeatForever.action(CCSequence.actions(easeIn,
                        CCDelayTime.action(1), easeOut)));

        // getSprite().runAction(easeIn);
    }

    private void bezier() {
        CCBezierConfig c = new CCBezierConfig();
        c.controlPoint_1 = CGPoint.ccp(0, 0);
        c.controlPoint_2 = CGPoint.ccp(150, 200);
        c.endPosition = CGPoint.ccp(300, 0);
        CCBezierBy bezierBy = CCBezierBy.action(2, c);

        CCSequence sequence = CCSequence.actions(bezierBy, bezierBy.reverse());
        CCRepeatForever forever = CCRepeatForever.action(sequence);

        getSprite().runAction(forever);

    }

    private void jump() {
        CGPoint pos = CGPoint.ccp(300, 150);
        // 跳跃：启动；时间；目标点；高度：实际跳跃的高度（最高点）；次数
        CCJumpBy jumpBy = CCJumpBy.action(2, pos, 100, 2);

        CCSprite sprite = getSprite();
        sprite.runAction(jumpBy);

    }

    private void move1() {
        CGPoint pos = CGPoint.ccp(-100, 0);
        CCMoveBy moveBy = CCMoveBy.action(0.5f, pos);

        CCSprite sprite = getSprite();
        sprite.setPosition(100, 0);
        // sprite.runAction(moveBy);

        // 按照顺序执行
        // 将动作串联起来
        CCSequence sequence = CCSequence.actions(moveBy, moveBy.reverse());

        // 反复的执行一组动作
        CCRepeatForever forever = CCRepeatForever.action(sequence);

        sprite.runAction(forever);

    }

    private void move() {
        // 移动的动作：①起点；②目标点；③时间间隔
        CGPoint pos = CGPoint.ccp(300, 150);
        CCMoveTo moveTo = CCMoveTo.action(2, pos);

        CCSprite sprite = getSprite();
        sprite.runAction(moveTo);
    }

    private CCSprite getSprite() {
        CCSprite sprite = CCSprite.sprite("z_1_attack_01.png");
        sprite.setAnchorPoint(0, 0);
        this.addChild(sprite);
        return sprite;
    }
}
