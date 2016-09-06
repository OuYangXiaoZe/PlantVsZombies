package com.ouyangxiaoze.plantvszombies.plantvszombies.Action;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class SplashActivity extends AppCompatActivity {
    private GameUI gameUI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        gameUI=new GameUI(getApplicationContext());
//        setContentView(gameUI);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event!=null&&gameUI!=null)
        gameUI.handlerTouch(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onResume() {
        /**
         * 设置为横屏
         */
        if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }
    public void enter(View view){
        /*
        开始按钮点击事件
         */

    }

}
