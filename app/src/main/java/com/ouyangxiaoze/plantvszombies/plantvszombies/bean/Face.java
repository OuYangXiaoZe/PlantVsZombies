package com.ouyangxiaoze.plantvszombies.plantvszombies.bean;

import android.graphics.Bitmap;
import android.graphics.Point;

/**
 * Created by suning on 2016/9/1.
 */
public class Face extends Sprite {
    private  int speed=3;
    public   int dx=0;
    public  int dy=0;
    public Face(Bitmap img, Point pos,Point touchPos) {
        super(img, pos);
        System.out.println("欧阳泽鹏："+touchPos.x+touchPos.y+pos.x+pos.y);
        //坐标的增量
        int x=touchPos.x-pos.x;
        int y=touchPos.y-pos.y;
        //移动的距离
        int d= (int) Math.sqrt(x*x+y*y);

        dx=x*speed/d;
        dy=y*speed/d;

    }
    /*
    移动(1子线程控制，每绘制一次，move一次)
     */
    public void move(){
        //改变笑脸的坐标

        pos.x+=dx ;
        pos.y+=dy;
    }
}
