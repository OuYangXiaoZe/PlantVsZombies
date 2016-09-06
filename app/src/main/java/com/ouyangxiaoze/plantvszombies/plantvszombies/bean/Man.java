package com.ouyangxiaoze.plantvszombies.plantvszombies.bean;

import android.graphics.Bitmap;
import android.graphics.Point;

/**
 * Created by suning on 2016/9/1.
 */
public class Man extends Sprite {
    protected  static final int DOWN=0;
    protected  static final int UP=1;
    protected  static final int LEFT=2;
    protected  static final int RIGHT=3;
    private  int speed=10;
    public Man(Bitmap img, Point pos) {
        super(img, pos);
    }
    //产生一个笑脸
    public Face createFace(Bitmap faceImg,Point touchPos){
        //坐标
        Point facePos=new Point(pos.x+50,pos.y+45);

        Face face=new Face(faceImg,facePos,touchPos);
        return face;

    }
    public void move(int dicate){
      switch (dicate){
          case DOWN:
              pos.y+=speed;
              break;
          case UP:
              pos.y-=speed;
              break;
          case LEFT:
              pos.x-=speed;
              break;
          case RIGHT:
              pos.x+=speed;
              break;
      }
    }
}
