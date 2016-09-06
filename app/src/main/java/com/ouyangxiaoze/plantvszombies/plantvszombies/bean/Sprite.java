package com.ouyangxiaoze.plantvszombies.plantvszombies.bean;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by suning on 2016/9/1.
 */
public abstract class Sprite {
    //图片
    protected Bitmap img;

    public Sprite(Bitmap img, Point pos) {
        this.img = img;
        this.pos = pos;

        if(this.pos==null){
            this.pos=new Point(0,0);
        }
    }

    public Point getPos() {
        return pos;
    }

    //位置
    protected Point pos;
    //绘制自己



    public void draw(Canvas canvas){
        if(img!=null)
        canvas.drawBitmap(img,pos.x,pos.y,null);
    }


}
