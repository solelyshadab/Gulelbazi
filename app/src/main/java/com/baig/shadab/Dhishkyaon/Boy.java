package com.baig.shadab.Dhishkyaon;

/**
 * Created by shadabbaig on 29/12/16.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by SHATAB on 11/7/2016.
 */

public class Boy implements GameObject {
    private int color;
    private Rect rectangle;
    //Constants.SCREEN_WIDTH/6, Constants.SCREEN_HEIGHT/24


    public Boy(int color, int startx, int starty, int radius, int height){
        this.color = color;

        rectangle = new Rect(startx, starty, startx + radius, starty + height);

    }

    public Rect getRectangle(){
        return rectangle;
    }

    public void moveRight(float x){
        rectangle.left += x;
        rectangle.right +=x;
    }

    public void moveLeft(float x){
        rectangle.left -= x;
        rectangle.right -=x;
    }

    public void moveDown(float x){
        rectangle.top += x;
        rectangle.bottom += x;
    }

    public void destroy(){
        rectangle.setEmpty();
    }

    public boolean boyCollide(Rect rectangle1){
        return Rect.intersects(rectangle, rectangle1);
    }


    @Override
    public void update() {
    }


    public void draw(Canvas canvas, Bitmap Img) {
        Paint paint = new Paint();
        canvas.drawBitmap(Img, null, rectangle, paint);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawRect(rectangle, paint);

    }
}

