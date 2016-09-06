package com.ouyangxiaoze.plantvszombies.plantvszombies.Action;

import android.app.Activity;
import android.os.Bundle;

import com.ouyangxiaoze.plantvszombies.plantvszombies.Action.ActionLayer;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.FirstLayer;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

/**
 * Created by suning on 2016/9/2.
 */
public class Cocos2dTestActivity extends Activity {
    private CCDirector director;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("欧阳小泽");
        CCGLSurfaceView surfaceView=new CCGLSurfaceView(this);
        setContentView(surfaceView);

        director=CCDirector.sharedDirector();
        //核心方法一
        director.attachInView(surfaceView);//开启绘制线程在surfaceView完成绘制操作

        // 屏幕方向的设置
        director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
        // 设置屏幕的大小
        director.setScreenSize(480, 320);
        director.setDisplayFPS(true);

        CCScene scene=createScene();
        //核心方法二
        director.runWithScene(scene);
    }

    private CCScene createScene() {
        CCScene root=CCScene.node();

//        FirstLayer layer=new FirstLayer();
        ActionLayer layer=new ActionLayer();
        root.addChild(layer);
        return root;
    }


    @Override
    protected void onResume() {
        director.onResume();
        super.onResume();
    }


    @Override
    protected void onPause() {
        director.onPause();
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        director.end();
        super.onDestroy();
    }
}
