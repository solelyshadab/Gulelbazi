package com.baig.shadab.Dhishkyaon;

/**
 * Created by shadabbaig on 29/12/16.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;



public class Bullet implements GameObject {

    private Rect rectangle;
    private int color;
    private Bitmap bulletImg;
//    private long startTime;
//    private long initTime;



    public Rect getBullet() {

        return rectangle;

    }

    public Bullet(int r, int color, int startxB, int startyB) { // constructor of class
        this.color = color;
        //l,t,r,b

        rectangle = new Rect(startxB, startyB - 2* r, startxB + r, startyB );

        bulletImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.black_stone);

        // startTime = initTime = System.currentTimeMillis();

    }

    public boolean bulletCollide(Rect rectangle1, Rect rectangle2){   // method to check if player has collided with obstacle (i.e with rectangle and rectangle2)
        //if(rectangle.contains((int)player.getCircle().left, (int)player.getCircle().top)
        //        || rectangle.contains((int)player.getCircle().left, (int)player.getCircle().bottom)
        //       || rectangle2.contains((int)player.getCircle().right, (int)player.getCircle().top)
        //       || rectangle2.contains((int)player.getCircle().right, (int)player.getCircle().bottom))
        //   return true;
        // return false;
        if(rectangle.bottom > 0) {
            return(rectangle.intersects(rectangle1.left, rectangle1.top-200, rectangle1.right, rectangle1.bottom)||rectangle.intersects(rectangle2.left, rectangle2.top-200, rectangle2.right, rectangle2.bottom));
            //return Rect.intersects(rectangle, rectangle1) || Rect.intersects(rectangle, rectangle2);
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        //Bitmap bulletImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.black_stone);
        canvas.drawBitmap(bulletImg, null, rectangle, paint);

    }



    @Override
    public  void update(){

        //if(startTime < Constants.INIT_TIME)
        //   startTime = Constants.INIT_TIME;
        //int elapsedTime = (int) (System.currentTimeMillis()- startTime);
        //startTime = System.currentTimeMillis();
        //float speedB = (float)Constants.SCREEN_HEIGHT/700.0f;
        getBullet().top -= (Constants.SCREEN_HEIGHT/20.0f);
        getBullet().bottom -= (Constants.SCREEN_HEIGHT/20.0f);

    }
}

