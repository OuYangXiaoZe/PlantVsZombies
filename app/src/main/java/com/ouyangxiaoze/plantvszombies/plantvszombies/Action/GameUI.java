package com.ouyangxiaoze.plantvszombies.plantvszombies.Action;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.MotionEvent;

import com.ouyangxiaoze.plantvszombies.plantvszombies.R;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.Face;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.Man;
import com.ouyangxiaoze.plantvszombies.plantvszombies.bean.MyButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by suning on 2016/9/1.
 */
public class GameUI extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private RenderThread renderThread;
    private Face face;



    /**************************************************************************************/
    private Man man;
    private List<Face> faces;
    private MyButton mybutton;
     /*************************************************************************************/
    private boolean isRender=true;//是否进行绘制

    public GameUI(Context context) {
        super(context);
        holder=this.getHolder();
        holder.addCallback(this);
        renderThread=new RenderThread();
    }

    /**
     * This is called immediately after the surface is first created.
     * Implementations of this should start up whatever rendering code
     * they desire.  Note that only one thread can ever draw into
     * a {@link }, so you should not draw into the Surface here
     * if your normal rendering will be in another thread.
     *
     * @param holder The SurfaceHolder whose surface is being created.
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        man=new Man(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_boy),null);
//        face= man.createFace(BitmapFactory.decodeResource(getResources(),R.drawable.rating_small));
        faces=new CopyOnWriteArrayList<Face>();
        Point pos=new Point(50,80);
        mybutton=new MyButton(BitmapFactory.decodeResource(getResources(),R.drawable.bottom_default),pos,BitmapFactory.decodeResource(getResources()
        ,R.drawable.bottom_press));
        mybutton.setOnClickListener(new MyButton.OnClickListener() {
                                        @Override
                                        public void onClick() {
                                            man.move(3);
                                        }
                                    }
           );
          renderThread.start();
    }

    /**
     * This is called immediately after any structural changes (format or
     * size) have been made to the surface.  You should at this point update
     * the imagery in the surface.  This method is always called at least
     * once, after {@link #surfaceCreated}.
     *
     * @param holder The SurfaceHolder whose surface has changed.
     * @param format The new PixelFormat of the surface.
     * @param width  The new width of the surface.
     * @param height The new height of the surface.
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * This is called immediately before a surface is being destroyed. After
     * returning from this call, you should no longer try to access this
     * surface.  If you have a rendering thread that directly accesses
     * the surface, you must ensure that thread is no longer touching the
     * Surface before returning from this function.
     *
     * @param holder The SurfaceHolder whose surface is being destroyed.
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRender=false;
    }

    public void handlerTouch(MotionEvent event) {
        Point touchPos=new Point((int)event.getX(),(int) event.getY());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                if(mybutton.isClick(touchPos)) {
//                    mybutton.setClick(true);
                    mybutton.click();
                }else{
                    face= man.createFace(BitmapFactory.decodeResource(getResources(),R.drawable.rating_small),touchPos);
                    faces.add(face);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                face= man.createFace(BitmapFactory.decodeResource(getResources(),R.drawable.rating_small),touchPos);
                faces.add(face);
                break;
            case MotionEvent.ACTION_UP:
                mybutton.setClick(false);
                break;
        }
    }

    private class RenderThread extends Thread{
        @Override
        public void run() {
            while(isRender){
//                游戏循环绘制
                //Surface操作
                /*
                draw into a surface
                public Canvas进行界面的绘制
              在用Canvas进行界面的绘制
              解锁，更新界面
                 */
                long startTime= System.currentTimeMillis();

                Canvas lockcanvas=null;
                try{
                    lockcanvas=holder.lockCanvas();
                    drawUI(lockcanvas);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (lockcanvas != null) {

                    holder.unlockCanvasAndPost(lockcanvas);
                }

                }



                long endTime=System.currentTimeMillis();

                //性能评定的参数
    //           每秒界面绘制的次数

                //1000/(endTime-startTime) FPS，帧率
                //如何连贯的界面：FPS>=30
            }
        }
    }

    private void drawUI(Canvas lockcanvas) {
       Paint paint=new Paint();
        paint.setColor(Color.RED);
        lockcanvas.drawRect(0,0,getWidth(),getHeight(),paint);
        man.draw(lockcanvas);
       drawFace(lockcanvas);
        mybutton.draw(lockcanvas);
    }

    private void drawFace(Canvas lockcanvas) {
//        if(face!=null){
//            face.draw(lockcanvas);
//            face.move();
//        }
        for(Face item:faces){
            item.draw(lockcanvas);
           item.move();

            if(item.getPos().x>=getWidth()||item.getPos().y>=getHeight()){
                faces.remove(item);
            }
        }
    }

}
