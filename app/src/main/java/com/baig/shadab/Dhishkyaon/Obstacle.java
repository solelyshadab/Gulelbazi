package com.baig.shadab.Dhishkyaon;

/**
 * Created by shadabbaig on 30/12/16.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


public class Obstacle implements GameObject {
    private Rect rectangle;
    private Rect rectangle2;
    private int color;
    private Bitmap rock1Img;
    private Bitmap rock2Img;

    public Rect getRectangle() {

        return rectangle;

    }

    public boolean isBroken(Rect rectangle, Rect rectangle2){

        if(rectangle.right == rectangle2.left) {
            return true;
        }
        return false;
    }


    public  Rect getRectangle2(){
        return rectangle2;
    }

    public void incrementY(float y, Bullet bullet, Rect rectangle3, Rect rectangle4, ObstacleManager obstacleManager){
        rectangle3.top += y;
        rectangle3.bottom += y;
        rectangle4.top += y;
        rectangle4.bottom += y;

        if(rectangle3.right != rectangle4.left){
            rectangle3.left -= y;
            rectangle3.right -= y;
            rectangle4.left += y;
            rectangle4.right += y;
            rectangle3.top += 2 * y;
            rectangle3.bottom += 2 * y;
            rectangle4.top += 2 * y;
            rectangle4.bottom += 2 * y;

        }

        if(bullet.bulletCollide(rectangle3, rectangle4)){
            rectangle3.left -= y;
            rectangle3.right -= y;
            rectangle4.left += y;
            rectangle4.right += y;
            rectangle3.top += 2 * y;
            rectangle3.bottom += 2 * y;
            rectangle4.top += 2 * y;
            rectangle4.bottom += 2 * y;
            obstacleManager.setScore();

        }

    }


    public Obstacle(int rectHeight, int color, int startX, int startY, int recWidth) { // constructor of class
        this.color = color;
        //l,t,r,b
        rectangle = new Rect(startX - recWidth, startY, startX, startY + rectHeight);
        rectangle2 = new Rect(startX, startY, startX + recWidth, startY + rectHeight);

        rock1Img = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.target1);
        rock2Img = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.target2);

    }

    public boolean playerCollide(RectPlayer player){   // method to check if player has collided with obstacle (i.e with rectangle and rectangle2)
        //if(rectangle.contains((int)player.getCircle().left, (int)player.getCircle().top)
        //        || rectangle.contains((int)player.getCircle().left, (int)player.getCircle().bottom)
        //       || rectangle2.contains((int)player.getCircle().right, (int)player.getCircle().top)
        //       || rectangle2.contains((int)player.getCircle().right, (int)player.getCircle().bottom))
        //   return true;
        // return false;
        if(isBroken(rectangle, rectangle2)) {
            return Rect.intersects(rectangle, player.getRectangle()) || Rect.intersects(rectangle2, player.getRectangle());
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        //paint.setColor(color);
        //canvas.drawRect(rectangle, paint);
        //canvas.drawRect(rectangle2, paint);
//        Bitmap rock1Img = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.target1);
//        Bitmap rock2Img = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.target2);
        canvas.drawBitmap(rock1Img,null, rectangle, paint);
        canvas.drawBitmap(rock2Img,null, rectangle2, paint);
    }

    @Override
    public  void update(){

    }

}

