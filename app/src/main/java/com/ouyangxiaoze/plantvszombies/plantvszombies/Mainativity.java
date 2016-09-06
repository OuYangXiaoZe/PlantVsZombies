package com.ouyangxiaoze.plantvszombies.plantvszombies;

import android.app.Activity;
import android.os.Bundle;

import com.ouyangxiaoze.plantvszombies.plantvszombies.layer.WelcomeLayer;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

/**
 * Created by suning on 2016/9/3.
 */
public class Mainativity extends Activity {
    private CCDirector director;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        director=CCDirector.sharedDirector();
        CCGLSurfaceView surfaceView=new CCGLSurfaceView(this);
        setContentView(surfaceView);

        director.attachInView(surfaceView);

        // 屏幕方向的设置
        director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);

        director.setScreenSize(480,320);
        director.setDisplayFPS(true);

        CCScene scene=CCScene.node();
        scene.addChild(new WelcomeLayer());


       director.runWithScene(scene);
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
