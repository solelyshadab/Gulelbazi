package com.baig.shadab.Dhishkyaon;

/**
 * Created by shadabbaig on 30/12/16.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import java.util.ArrayList;


public class ObstacleManager { //create obstacles
    //higher index = lower on screen = higher y value
    private ArrayList<Obstacle> obstacles;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;
    private long startTime;
    private long initTime; // time when we initialize this class
    private int score = 0;
    private int recWidth;
    private  int bonus = 0;
    private int playerlife = 3;
    private Rect r = new Rect();
    private boolean isBonusDisplay = false;
    private long initmTime;
    private long x=0;
    public boolean isLifeDisplayOM = false;
    private long setLifetmOM;
    private int xOM=1;



    SharedPreferences settings = Constants.CURRENT_CONTEXT.getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
    int highScore = settings.getInt("HIGH_SCORE", 0);
    public int topScore= highScore;


    public ArrayList getObstacles(){
        return obstacles;
    }

    public int getScore(){
        return score;
    }
    public void setScore() { score +=10;}
    public void setTopScore(int tpscore) { topScore = tpscore;}
    public int getTopScore(){ return topScore; }

    public void setBonus() {
        if(score < 100){x = 10;}
        else if(score>=100 && score<500){x=20;}
        else if(score>=500 && score<1000) {x=30;}
        else if(score>=1000 && score<2000) {x=50;}
        else if(score>=2000 && score<5000) {x=50;}
        else if(score>=5000 && score<10000) {x=100;}
        else if(score>10000) {x=200;}
        bonus +=x;
        score +=x;
        initmTime = System.currentTimeMillis();
        isBonusDisplay = true;
    }

    public boolean lifeOver(){
        if(playerlife <= 0)
            return true;
        return false;
    }

    public int getLife(){return playerlife;}


    public void decrementLife(Life life){
        playerlife -=1;
        life.getLives().clear();
        life.populateLives(playerlife);
        life.getLives().trimToSize();
        //life.decreaselife();


    }

    public void incrementLife(Life life){
        if(playerlife < 3) {
            playerlife += 1;
            life.populateLives(playerlife);
            life.getLives().trimToSize();
        }
    }

    public ObstacleManager(int obstacleGap, int obstacleHeight, int color, int recWidth){

        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;
        this.recWidth = recWidth;


        startTime = initTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();


        populateObstacles();
        //populateLives();


    }


    public boolean playerCollide(RectPlayer player){ // loop all the obstacles and check if player collides with any of the obstacles
        for(Obstacle ob : obstacles){
            if(ob.playerCollide(player))
                return true;
        }
        return false;
    }

    private void populateObstacles() {
        int currY = - 5 * Constants.SCREEN_HEIGHT/4; // 5/4 of screen height
        while (currY < 0 ) { //keep creating obstacles till we dont reach bottom of screen
            int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH)); //xstart will be used to generate gap in random width
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, recWidth)); // add a new rectangle (obstacle)
            currY += obstacleHeight + obstacleGap;

        }
    }

    public void update( Bullet bullet, ObstacleManager obstacleManager, Life life) {
        if(startTime < Constants.INIT_TIME)
            startTime = Constants.INIT_TIME;
        int elapsedTime = (int) (System.currentTimeMillis()- startTime);
        startTime = System.currentTimeMillis();
        float speed = (float)((Math.sqrt(1+(startTime - initTime)/50000.0))* Constants.SCREEN_HEIGHT/10000.0f);
        for(Obstacle ob : obstacles){
            ob.incrementY(speed * elapsedTime, bullet, ob.getRectangle(), ob.getRectangle2(), obstacleManager);
            if(ob.getRectangle().top>=Constants.SCREEN_HEIGHT && ob.getRectangle().right == ob.getRectangle2().left){
//                playerlife -=1;
//                life.getLives().remove(life.getLives().size()-1);
                decrementLife(life);
                setLifetmOM = System.currentTimeMillis();
                isLifeDisplayOM = true;
                xOM=1;
            }

        }
        if (System.currentTimeMillis() >= setLifetmOM + 1000) {
            isLifeDisplayOM = false;
        }

        if(initmTime + 500 <= System.currentTimeMillis()){isBonusDisplay=false;}



        if (obstacles.get(obstacles.size()-1).getRectangle().top >= Constants.SCREEN_HEIGHT){
            int xStart = (int) (Math.random()*(Constants.SCREEN_WIDTH));
            obstacles.add(0, new Obstacle(obstacleHeight, color, xStart, obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap, recWidth));
            obstacles.remove(obstacles.size() - 1);

            //score += 5; //increases the score by 1 everytime the obstacle passes the screen from top to bottom i.e player dodges an obstacle
        }


    }


    public void draw(Canvas canvas){
        for(Obstacle ob : obstacles)
            ob.draw(canvas);
        Paint paint = new Paint();
        paint.setTypeface(Typeface.create("cursive",Typeface.BOLD_ITALIC));
        paint.setFakeBoldText(true);
        //paint.setUnderlineText(true);
        paint.setTextSize(40);
        paint.setColor(Color.rgb(105,105,105));
        canvas.drawText("Score: " + score, 10, 20 + paint.descent() - paint.ascent(), paint);
        //paint.ascent is baseline of text, paint descent is top of the text
        paint.setTextSize(40);
        paint.setColor(Color.rgb(105,105,105));
        canvas.drawText("Bonus: " + bonus, 10, 80 + paint.descent() - paint.ascent(), paint);
        paint.setTextSize(40);
        paint.setColor(Color.rgb(105,105,105));
        canvas.drawText("Lives: " + playerlife, 4 * Constants.SCREEN_WIDTH/5, 20 + paint.descent() - paint.ascent(), paint);
        paint.setTextSize(40);
        paint.setColor(Color.rgb(105,105,105));
        paint.setStrokeWidth(3);
        canvas.drawText("Top Score: " + topScore, 10, 140 + paint.descent() - paint.ascent(), paint);
        if(isBonusDisplay) {
            Paint paint1 = new Paint();
            paint1.setTextSize(100);
            paint1.setColor(Color.rgb(178, 34, 34));
            paint1.setFakeBoldText(true);
            paint1.setTypeface(Typeface.create("cursive", Typeface.BOLD_ITALIC));
            //paint.setShadowLayer(200,50,50, android.R.color.holo_red_dark);
            drawCentreText(canvas, paint1, "Bonus "+x);
        }

    }

    private void drawCentreText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth =  r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);

    }

}

