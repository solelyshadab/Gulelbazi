package com.baig.shadab.Dhishkyaon;

/**
 * Created by shadabbaig on 29/12/16.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.List;


public class BoyManager {
    private int radius;
    private int height;
    private int color;
    private long startTime;
    private long initTime;
    private Boy boy1;
    private Boy boy2;
    private Bitmap Boy1Img;
    private Bitmap Boy2Img;
    private Boy star;
    private Bitmap star1Img;
    private Bitmap star2Img;
    private int n;
    private boolean isStarShine;
    private boolean isLifedisplayBM = false;
    private long setLifetm;

    private Rect r = new Rect();


    public BoyManager(int radius, int height, int color) {
        this.color = color;
        this.height = height;
        this.radius = radius;

        Boy1Img = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.red_ant1);
        Boy2Img = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.red_ant2);
        star1Img = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.star1);
        star2Img = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.star2);

        startTime = initTime = System.currentTimeMillis();

        createBoy();

    }

    public boolean BoyCollide(Rect rectangle) {

        return (boy1.boyCollide(rectangle) || boy2.boyCollide(rectangle));

    }

    private void createBoy() {

        int starty1 = (int) (Constants.SCREEN_HEIGHT / 2 + (Math.random() * Constants.SCREEN_HEIGHT / 3));
        boy1 = new Boy(color, -radius, starty1, radius, height);
        int starty2 = (int) (Constants.SCREEN_HEIGHT / 3 + (Math.random() * Constants.SCREEN_HEIGHT / 3));
        boy2 = new Boy(color, Constants.SCREEN_WIDTH, starty2, radius, height);
        int startx = (int) (Math.random() * Constants.SCREEN_WIDTH);
        star = new Boy(color, startx, 0, Constants.SCREEN_WIDTH / 10, Constants.SCREEN_WIDTH / 10);
        n = 1;

    }

    public void update(ObstacleManager obstacleManager, RectPlayer player, Life life) {
        if (startTime < Constants.INIT_TIME)
            startTime = Constants.INIT_TIME;
//        int elapsedTime = (int) (System.currentTimeMillis()- startTime);
//        startTime = System.currentTimeMillis();
//        float speed = (float)((Math.sqrt(1+(startTime - initTime)/50000.0))* Constants.SCREEN_HEIGHT/10000.0f);

        boy1.moveRight(Constants.SCREEN_WIDTH / 225);
        boy2.moveLeft(Constants.SCREEN_WIDTH / 225);
        star.moveDown(Constants.SCREEN_WIDTH / 225);


        if (boy1.getRectangle().left >= Constants.SCREEN_WIDTH) {
            int starty = (int) (Constants.SCREEN_HEIGHT / 2 + (Math.random() * Constants.SCREEN_HEIGHT / 3));
            boy1 = new Boy(color, -radius, starty, radius, height);
        }
        if (boy2.getRectangle().right <= 0) {
            int starty2 = (int) (Constants.SCREEN_HEIGHT / 2 + (Math.random() * Constants.SCREEN_HEIGHT / 3));
            boy2 = new Boy(color, Constants.SCREEN_WIDTH, starty2, radius, height);
        }

        if (star.getRectangle().top >= Constants.SCREEN_HEIGHT) {
            if (System.currentTimeMillis() >= startTime + (n * 20000)) {

                int startx = (int) (Math.random() * Constants.SCREEN_WIDTH);
                star = new Boy(color, startx, 0, Constants.SCREEN_WIDTH / 10, Constants.SCREEN_WIDTH / 10);
                n += 1;
            }
        }

        if ((int) ((star.getRectangle().top / Math.pow(10, 0)) % 10) == 5) {
            isStarShine = false;
        }
        if ((int) ((star.getRectangle().top / Math.pow(10, 0)) % 10) == 0) {
            isStarShine = true;
        }


        if (Rect.intersects(player.getRectangle(), star.getRectangle())) {

            if(obstacleManager.getLife()<3 && obstacleManager.getLife()>0){
                obstacleManager.incrementLife(life);
                setLifetm = System.currentTimeMillis();
                isLifedisplayBM = true;
                star.destroy();
            }
            else star.destroy();
        }

        if (System.currentTimeMillis() >= setLifetm + 1000) {
            isLifedisplayBM = false;
        }


        List<Obstacle> obstacle = obstacleManager.getObstacles();
        for (Obstacle ob : obstacle) {
            Rect rec = ob.getRectangle();
            Rect rec2 = ob.getRectangle2();
            if (rec.right != rec2.left) {
                if (Rect.intersects(rec, boy1.getRectangle()) || Rect.intersects(rec2, boy1.getRectangle())) {
                    boy1.getRectangle().set(Constants.SCREEN_WIDTH, 0, Constants.SCREEN_WIDTH, 0);
                    obstacleManager.setBonus();
                } else if (Rect.intersects(rec2, boy2.getRectangle()) || Rect.intersects(rec, boy2.getRectangle())) {
                    boy2.destroy();
                    obstacleManager.setBonus();
                }
            }
        }
    }

    public void draw(Canvas canvas) {
//        Bitmap Boy1Img = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.red_ant1);
//        Bitmap Boy2Img = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.red_ant2);
        boy1.draw(canvas, Boy1Img);
        boy2.draw(canvas, Boy2Img);
        if (!(n == 1)) {
            if (isStarShine) {
                star.draw(canvas, star1Img);
            } else star.draw(canvas, star2Img);
        }
        //star.draw(canvas);

        if(isLifedisplayBM){
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.rgb(178,34,34));
            paint.setFakeBoldText(true);
            //paint.setTypeface(Typeface.create("cursive",Typeface.BOLD_ITALIC));
            drawCentreText(canvas, paint, "LIFE +1");

        }

    }

    private void drawCentreText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);

    }
}