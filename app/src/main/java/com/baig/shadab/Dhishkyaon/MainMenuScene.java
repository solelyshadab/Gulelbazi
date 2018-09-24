package com.baig.shadab.Dhishkyaon;

/**
 * Created by shadabbaig on 29/12/16.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;

import static com.baig.shadab.Dhishkyaon.SceneManager.ACTIVE_SCENE;



public class MainMenuScene implements Scene {

    private RectF read;
    private MenuIcon startbutton;
    private MenuIcon tutorial;
    private MenuIcon close;
    private MenuIcon topper;
    private MenuIcon title;
    private MenuIcon bg;
    private long t1;
    private MenuIcon checkbox1;
    private MenuIcon checkbox2;
    private boolean isNormalChecked = true;
    private MenuIcon phonePosText;

    private boolean display=false;
    private boolean displayread = false;

    private long startTime;
    private long initTime;


    private long frameTime; //we need change in time to multiply by speed and get change in distance


    public MainMenuScene() {

        startbutton = new MenuIcon(Constants.SCREEN_WIDTH/4, Constants.SCREEN_HEIGHT/3, Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT/4);
        //tutorial = new MenuIcon(Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT - Constants.SCREEN_HEIGHT/8, Constants.SCREEN_WIDTH/2-Constants.SCREEN_WIDTH/5,Constants.SCREEN_HEIGHT/8);
        topper = new MenuIcon(0, Constants.SCREEN_HEIGHT - Constants.SCREEN_HEIGHT/8, Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT/8);
        close = new MenuIcon(Constants.SCREEN_WIDTH - Constants.SCREEN_WIDTH/5, Constants.SCREEN_HEIGHT - Constants.SCREEN_HEIGHT/8, Constants.SCREEN_WIDTH/5,Constants.SCREEN_HEIGHT/8);
        //title = new MenuIcon(0, 0, Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT/8);
        bg = new MenuIcon(0,0,Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
//        phonePosText = new MenuIcon(Constants.SCREEN_WIDTH/2-(Constants.SCREEN_WIDTH/5 + 50), (int)( startbutton.getRectangle().bottom + 10), Constants.SCREEN_WIDTH/2 + 50,Constants.SCREEN_HEIGHT/15 );
//        checkbox1 = new MenuIcon(Constants.SCREEN_WIDTH/2-(Constants.SCREEN_WIDTH/5 + 50), (int)( phonePosText.getRectangle().bottom + 10),Constants.SCREEN_WIDTH/5,Constants.SCREEN_HEIGHT/15 );
//        checkbox2 = new MenuIcon(Constants.SCREEN_WIDTH/2 + 50, (int)( startbutton.getRectangle().bottom + 100),Constants.SCREEN_WIDTH/5,Constants.SCREEN_HEIGHT/15 );
//
//        read = new RectF(10, Constants.SCREEN_HEIGHT/2, Constants.SCREEN_WIDTH-10, (int)tutorial.getRectangle().top);

        startTime = Constants.INIT_TIME;

        frameTime = System.currentTimeMillis();
    }

    public void reset() { // method to reset the game when game restarts, set player to safe position and reset obstacle

        ACTIVE_SCENE = 0;

    }


    @Override
    public void terminate(){
        ACTIVE_SCENE = 0;

    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: //touch screen
//                if(button1.contains((int)event.getX(),(int)event.getY())) {
//
//                    ACTIVE_SCENE = 1;
//                }
                if(startbutton.isContain((int)event.getX(),(int)event.getY())) {

                    ACTIVE_SCENE = 1;
                }
                if(close.isContain((int)event.getX(),(int)event.getY())){
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
                if(topper.isContain((int)event.getX(), (int)event.getY())) {

                    display=true;
                }

                if(isDisplayTopScore()&&!topper.isContain((int)event.getX(), (int)event.getY())&&!startbutton.isContain((int)event.getX(),(int)event.getY())&&!close.isContain((int)event.getX(),(int)event.getY())) {
                    display = false;
                }

//                if(tutorial.isContain((int)event.getX(), (int)event.getY())) {
//                  displayread = true;
//                }
//
//                if(isDisplayRead()&& !tutorial.isContain((int)event.getX(), (int)event.getY())&& !topper.isContain((int)event.getX(),(int)event.getY())&&!startbutton.isContain((int)event.getX(),(int)event.getY())&&!close.isContain((int)event.getX(),(int)event.getY())) {
//                    displayread = false;
//                }
//                if(checkbox1.isContain((int)event.getX(), (int)event.getY())) {
//
//                    isNormalChecked = true;
//                }
//                if(checkbox2.isContain((int)event.getX(), (int)event.getY())) {
//
//                    isNormalChecked = false;
//                }



                break;
        }



    }

    @Override
    public void draw(Canvas canvas) {

//        canvas.drawColor(Color.LTGRAY); //fills the canvas to white
        Bitmap bgImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.background1);
        bg.draw(canvas, bgImg);
        Bitmap startImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.start2);
        startbutton.draw(canvas, startImg);
        Bitmap closeImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.close);
        close.draw(canvas, closeImg);
        Bitmap topperImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.top_score);
        topper.draw(canvas, topperImg);
        //Bitmap tutorialImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.how_to_play);
        //tutorial.draw(canvas, tutorialImg);


        //Bitmap phonePosTextImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.phonepostext3);
        //phonePosText.draw(canvas, phonePosTextImg);


        //Bitmap titleImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.name);
//        Paint paint = new Paint();
//        drawTitle(canvas, paint);



        if(isDisplayTopScore()) {
            SharedPreferences settings = Constants.CURRENT_CONTEXT.getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
            int highScore = settings.getInt("HIGH_SCORE", 0);
            Paint paint1 = new Paint();
            drawTopScore(canvas, paint1, "Top Score: " + highScore);
        }



//        if(isNormalChecked){
//
//            Bitmap checkbox1Img = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.normal_check1);
//            checkbox1.draw(canvas, checkbox1Img);
//            Bitmap checkbox2Img = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.sleep_uncheck1);
//            checkbox2.draw(canvas, checkbox2Img);
//        }
//        else{
//            Bitmap checkbox1Img = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.normal_uncheck1);
//            checkbox1.draw(canvas, checkbox1Img);
//            Bitmap checkbox2Img = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.sleep_check1);
//            checkbox2.draw(canvas, checkbox2Img);
//        }


    }

    @Override
    public void update() {

    }

    public void drawTitle(Canvas canvas, Paint paint){
        paint = new Paint();
        paint.setColor(Color.RED);
        int fontSize = 110;
        paint.setTextSize(fontSize);
        Typeface tf = Typeface.create("cursive", Typeface.BOLD);
        paint.setTypeface(tf);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(" GULELBAZI!", 0 , 20+paint.getTextSize(), paint);

    }

    public void drawTopScore(Canvas canvas, Paint paint, String text){
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(80);
        paint.setTypeface(Typeface.create("cursive", Typeface.BOLD));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text, 0  , topper.getRectangle().top , paint);

    }

    public void drawRead(Canvas canvas, Paint paint, String text){
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        paint.setTypeface(Typeface.create("cursive", Typeface.BOLD));
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text, 0  , topper.getRectangle().top , paint);

    }




    private boolean isDisplayTopScore(){
        return display;
    }

    private boolean isDisplayRead(){
        return displayread;
    }

}

