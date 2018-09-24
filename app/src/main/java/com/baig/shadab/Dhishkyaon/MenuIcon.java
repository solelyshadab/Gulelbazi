package com.baig.shadab.Dhishkyaon;

/**
 * Created by shadabbaig on 30/12/16.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


public class MenuIcon {

    private Rect rectangle;
    //Constants.SCREEN_WIDTH/6, Constants.SCREEN_HEIGHT/24


    public MenuIcon(int startx, int starty, int radius, int height){


        rectangle = new Rect(startx, starty, startx + radius, starty + height);

    }

    public Rect getRectangle(){
        return rectangle;
    }

    public boolean isContain(float x, float y){
        return rectangle.contains((int)x,(int)y);
    }



    public void draw(Canvas canvas, Bitmap Img) {
        Paint paint = new Paint();
        //paint.setColor(Color.WHITE);
        //canvas.drawRect(rectangle, paint);
        canvas.drawBitmap(Img, null, rectangle, paint);

    }


}


