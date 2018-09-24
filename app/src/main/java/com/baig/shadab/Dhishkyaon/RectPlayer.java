package com.baig.shadab.Dhishkyaon;

/**
 * Created by shadabbaig on 30/12/16.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;


public class RectPlayer implements GameObject {

    private Rect rectangle;
    private int color;

    private Animation idle;
    private Animation walkRight;
    private Animation walkLeft;
    private AnimationManager animManager;


    public Rect getRectangle(){
        return rectangle;
    }

    public RectPlayer(Rect rectangle, int color) { // constructor of class
        this.rectangle = rectangle;
        this.color = color;

        BitmapFactory bf = new BitmapFactory(); //producing, decoding, modifying various bitmaps
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.idle_pose);
        Bitmap walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.walk1);
        Bitmap walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.walk2);

        idle = new Animation(new Bitmap[]{idleImg} , 2); //set this image for 2 secs
        walkRight = new Animation(new Bitmap[]{walk1, walk2}, 1.0f); //set for 0.5 secs float

        Matrix m = new Matrix();
        m.preScale(-1,1);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m, false); // create a mirror image
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), m, false); // create a mirror image


        walkLeft = new Animation(new Bitmap[]{walk1, walk2}, 1.0f); //re assigned the new mirrored images to walk left.

        animManager = new AnimationManager(new Animation[] {idle, walkRight, walkLeft}); //pass animation array 0, 1 and 2 values respectively


    }


    @Override
    public void draw(Canvas canvas){
//        Paint paint = new Paint();
//        paint.setColor(color);
//        //canvas.drawRect(rectangle, paint);
//        canvas.drawBitmap(Img, null, rectangle, paint);
        animManager.draw(canvas, rectangle);
    }

    public void draw(Canvas canvas, Bitmap Img){
        Paint paint = new Paint();
        //canvas.drawRect(rectangle, paint);
        canvas.drawBitmap(Img, null, rectangle, paint);

    }



    @Override
    public  void update(){

        animManager.update();
    }

    public void update(Point point) {   //point will be the centre of the rectangle
        float oldLeft = rectangle.left; // left x-cordinate of rectangle before we modify it

        //left, top, right, bottom
        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height()/2, point.x + rectangle.width()/2, point.y + rectangle.height()/2);

        int state = 0; // 0 is idle, 1 is walking to right, 2 is walking to left
        if(rectangle.left - oldLeft > 5)  // if rectangle moves by 5 pixels then true
            state = 1;
        else if(rectangle.left - oldLeft < -5)
            state = 2;

        animManager.playAnim(state);
        animManager.update();
    }

}

