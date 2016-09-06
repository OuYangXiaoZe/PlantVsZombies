package com.ouyangxiaoze.plantvszombies.plantvszombies.bean;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by suning on 2016/9/2.
 */
public class MyButton extends Sprite {
    //点击
    //点击效果
    private Bitmap pressImg;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private OnClickListener onClickListener;


    public boolean isClick(Point touchPos) {
        //依据，用户点击点是否落在了图片所覆盖的矩形区域内
        Rect rect=new Rect(pos.x,pos.y,pos.x+img.getWidth(),pos.y+img.getHeight());
        isClick=rect.contains(touchPos.x,touchPos.y);


        return !isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    private boolean isClick=false;
    public MyButton(Bitmap img, Point pos,Bitmap pressImg) {
        super(img, pos);
        this.pressImg=pressImg;
    }

    @Override
    public void draw(Canvas canvas) {
        if(!isClick) {
            super.draw(canvas);
        }else{
            if(pressImg!=null)
                canvas.drawBitmap(pressImg,pos.x,pos.y,null);
        }
    }

    public void click() {
        if(onClickListener!=null){
            onClickListener.onClick();
        }
    }

    public interface  OnClickListener{
          void onClick();
    }

}
