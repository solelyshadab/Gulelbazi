package com.baig.shadab.Dhishkyaon;

/**
 * Created by shadabbaig on 29/12/16.
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;



public class Life {
    private ArrayList<RectF> lives;
    private long startTime;
    private long initTime;
    //private int playerlife = 3;
    //private Rect life;

    public Life(){

        startTime = initTime = System.currentTimeMillis();

        lives = new ArrayList<>();
        populateLives(3);
    }

    public ArrayList getLives(){ return lives;}



    public void populateLives(int x) {
        int lifegap = Constants.SCREEN_WIDTH/72;
        int startpoint = Constants.SCREEN_WIDTH-lifegap;
        int r = Constants.SCREEN_WIDTH/18;
        int lifehtgap = 90;
        //life = new Rect(startpoint - r,lifehtgap, startpoint, lifehtgap + r);

        int i = 1;
        while(i<= x){

            lives.add(new RectF(startpoint - r,lifehtgap, startpoint, lifehtgap + r));
            startpoint += -lifegap - r;
            i+=1 ;
        }

    }

    public void update(ObstacleManager obstacleManager){



    }



    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        for(RectF life: lives)
            canvas.drawRoundRect(life,5,5, paint);
        //canvas.drawRect(life, paint);

    }


}

