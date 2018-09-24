package com.baig.shadab.Dhishkyaon;

/**
 * Created by shadabbaig on 30/12/16.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;

import static com.baig.shadab.Dhishkyaon.SceneManager.ACTIVE_SCENE;



public class GameplayScene implements Scene{

    private Rect r = new Rect();

    private RectPlayer player;
    //private CirclePlayer player;

    private Bullet bullet;
    private long startTime;
    private long initTime;

    private Point playerPoint;
    private ObstacleManager obstacleManager;
    private BoyManager boyManager;
    private Life life;
    private SharedPreferences mypref;
    private RectF refresh;
    private RectF close;
    private RectF pause;
    public boolean isSlingShot = false;
    private long initmtime;
    private Rect guidePanel;
    private Rect gotIt;
    private Rect nevermsg;

    private Bitmap playbgImg;
    private Bitmap refreshImg;
    private Bitmap backImg;
    private Bitmap pauseImg;
    private Bitmap pausestartImg;
    private Bitmap shootImg;
    private Bitmap guidePanelImg;
    private Bitmap gotItImg;
    private Bitmap nevermsgImg;

    private float pitch;
    private float roll;

    public boolean isGuideDisplay = false;

    private Rect playbg;

    private boolean movingPlayer = false; // this is to restrict the player movement, player should move only when drag+tap, not just touch.

    private boolean gameOver = false;
    private long gameOverTime;
    private long setLifetm =0;
    private boolean isLifedisplay = false;
    private boolean isPause = false;

    private int x=1;

    private OrientationData orientationData; //the farther i tilt, the faster the player moves... i.e speed
    private long frameTime; //we need change in time to multiply by speed and get change in distance


    public GameplayScene() {
        player = new RectPlayer(new Rect(125, 125, 200, 200), Color.rgb(255, 0, 0));

        //player = new CirclePlayer(Color.rgb(255, 0, 0), new RectF(75, 75, 200, 200));

        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4);
        player.update(playerPoint);

        obstacleManager = new ObstacleManager(550, 50, Color.BLACK, 50);
        boyManager = new BoyManager(Constants.SCREEN_WIDTH/7, Constants.SCREEN_HEIGHT/24, Color.BLUE);
        life = new Life();

        refresh = new RectF((Constants.SCREEN_WIDTH/2-Constants.SCREEN_WIDTH/20),20,(Constants.SCREEN_WIDTH/2+Constants.SCREEN_WIDTH/20),Constants.SCREEN_WIDTH/10+20);
        close = new RectF(refresh.right + 20, 20,refresh.right + Constants.SCREEN_WIDTH/10 + 20, Constants.SCREEN_WIDTH/10+20);
        pause = new RectF(refresh.left - 20 - Constants.SCREEN_WIDTH/10 ,20, refresh.left - 20, Constants.SCREEN_WIDTH/10 +20);
        refreshImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.refresh);
        backImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.close);
        pauseImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pause);
        pausestartImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pause_start);


        playbg = new Rect(0,0,Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
        playbgImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.mybg1);

        bullet = new Bullet(25, Color.BLACK, playerPoint.x, playerPoint.y);

        shootImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.shoot_pose);

        guidePanel = new Rect(50, 50, Constants.SCREEN_WIDTH-50, Constants.SCREEN_HEIGHT-50);
        gotIt = new Rect(Constants.SCREEN_WIDTH/2 - 120, guidePanel.bottom - 240, Constants.SCREEN_WIDTH/2 + 120, guidePanel.bottom -170);
        nevermsg = new Rect(Constants.SCREEN_WIDTH/2 - 120, guidePanel.bottom - 150, Constants.SCREEN_WIDTH/2 + 120, guidePanel.bottom -70);

        guidePanelImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.guidepanel1);
        gotItImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.got_it1);
        nevermsgImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.never_show_again);

        SharedPreferences settings = Constants.CURRENT_CONTEXT.getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int NeverMsg = settings.getInt("NEVER_MSG", 0);
        if(NeverMsg != 1 ) {isGuideDisplay = true;}


        startTime = Constants.INIT_TIME;

        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();


    }

    public void reset() { // method to reset the game when game restarts, set player to safe position and reset obstacle
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2);

        player.update(playerPoint);
        obstacleManager = new ObstacleManager(550, 50, Color.BLACK, 50);
        boyManager = new BoyManager(Constants.SCREEN_WIDTH/7, Constants.SCREEN_HEIGHT/24, Color.BLUE);
        life = new Life();

        refresh = new RectF((Constants.SCREEN_WIDTH/2-Constants.SCREEN_WIDTH/20),20,(Constants.SCREEN_WIDTH/2+Constants.SCREEN_WIDTH/20),Constants.SCREEN_WIDTH/10+20);
        close = new RectF(refresh.right + 20, 20,refresh.right + Constants.SCREEN_WIDTH/10 + 20, Constants.SCREEN_WIDTH/10+20);
        pause = new RectF(refresh.left - 20 - Constants.SCREEN_WIDTH/10 , 20, refresh.left - 20, Constants.SCREEN_WIDTH/10 + 20);
        refreshImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.refresh);
        backImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.close);
        pauseImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pause);
        pausestartImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pause_start);

        playbg = new Rect(0,0,Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
        playbgImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.mybg1);
        bullet = new Bullet(20, Color.BLACK, playerPoint.x, playerPoint.y);
        shootImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.shoot_pose);

        movingPlayer = false;

        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();

    }


    @Override
    public void terminate(){
        ACTIVE_SCENE = 0;

    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: //touch screen
                if(!pause.contains((int) event.getX(), (int) event.getY())){
                    bullet = new Bullet(20, Color.BLACK, playerPoint.x, playerPoint.y);
                    initmtime = System.currentTimeMillis();
                    isSlingShot = true;
                }

                if (!gameOver && player.getRectangle().contains((int) event.getX(), (int) event.getY()))//pass the point we touch and check if the rectangle lies in that point
                    movingPlayer = true;

                if (gameOver && System.currentTimeMillis() - gameOverTime >= 2000) { // after game is over allow restart of game after 2 secs
                    reset();
//                    ACTIVE_SCENE = 0;
                    gameOver = false;
                    orientationData.newGame();
                }
                if(refresh.contains((int) event.getX(), (int) event.getY())){
                    reset();

                }
                if(close.contains((int) event.getX(), (int) event.getY())){
                    reset();
                    terminate();

                }
                if(pause.contains((int) event.getX(), (int) event.getY())){
                    if(isPause){isPause = false;}
                    else isPause = true;

                }
                if(gotIt.contains((int) event.getX(), (int) event.getY())){isGuideDisplay = false;}

                if(nevermsg.contains((int) event.getX(), (int) event.getY())){isGuideDisplay = false;
                    SharedPreferences settings = Constants.CURRENT_CONTEXT.getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
                    int NeverMsg = settings.getInt("NEVER_MSG", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("NEVER_MSG", 1);
                    editor.commit();
                }

                break;
            case MotionEvent.ACTION_MOVE: // move player
                if (!gameOver && movingPlayer)
                    playerPoint.set((int) event.getX(), (int) event.getY()); // when screen is touched, centre point of rectangle is set to that point
                break;
            case MotionEvent.ACTION_UP: // when we lift our finger up
                movingPlayer = false; // when finger is lifted moving player is false otherwise when we tap for first time it will become true forever
                break;
        }

    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawColor(Color.WHITE); //fills the canvas to white
        Paint paint = new Paint();
        //Bitmap playbgImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.mybg);
        canvas.drawBitmap(playbgImg, null, playbg , paint);



        //player.draw(canvas);
        paint.setColor(Color.WHITE);
        canvas.drawOval(refresh,paint);
        canvas.drawOval(close, paint);
        canvas.drawOval(pause, paint);
        //Bitmap refreshImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.refresh);
        canvas.drawBitmap(refreshImg, null, refresh, paint);
        //Bitmap backImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.close);
        canvas.drawBitmap(backImg, null, close, paint);
        canvas.drawBitmap(pauseImg, null, pause, paint);


        obstacleManager.draw(canvas);
        boyManager.draw(canvas);
        bullet.draw(canvas);
        life.draw(canvas);

        canvas.drawText("Pitch: ", 10, Constants.SCREEN_HEIGHT-50, paint);
        canvas.drawText("Roll: " + roll, 10, Constants.SCREEN_HEIGHT, paint);

        if(isPause){
            canvas.drawBitmap(pausestartImg,null, pause, paint);

        }else {
            canvas.drawBitmap(pauseImg,null, pause, paint);
        }

        if(isSlingShot){
            player.draw(canvas, shootImg);

        }else {
            player.draw(canvas);
        }

        if(isGuideDisplay){
            BitmapFactory bf = new BitmapFactory();
            //Bitmap guidePanelImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.guidepanel1);
            canvas.drawBitmap(guidePanelImg, null, guidePanel, paint);
            //Bitmap gotItImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.got_it1);
            canvas.drawBitmap(gotItImg, null, gotIt, paint);
            //Bitmap nevermsgImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.never_show_again);
            canvas.drawBitmap(nevermsgImg, null, nevermsg, paint);


        }


        if (gameOver) {
            //Paint paint2 = new Paint();
            paint.setTextSize(50);
            paint.setColor(Color.rgb(178,34,34));
            paint.setFakeBoldText(true);
            paint.setTypeface(Typeface.create("cursive",Typeface.BOLD));
            //paint.setShadowLayer(200,50,50, android.R.color.holo_red_dark);
            if(!isGuideDisplay) {
                drawCentreText(canvas, paint, "Game Over! Your Score: " + obstacleManager.getScore());
            }
        }
        else if(isLifedisplay||obstacleManager.isLifeDisplayOM){
            paint.setTextSize(x * 10);
            paint.setColor(Color.rgb(178,34,34));
            paint.setFakeBoldText(true);
            //paint.setTypeface(Typeface.create("cursive",Typeface.BOLD_ITALIC));
            drawCentreText(canvas, paint, "LIFE -1");
            if(x<10) {x +=1;}
            else x -=1;
        }

    }

    @Override
    public void update() {
        if (!isPause) {
            if (!gameOver) {
                if (frameTime < Constants.INIT_TIME)
                    frameTime = Constants.INIT_TIME;
                int elapsedTime = (int) (System.currentTimeMillis() - frameTime);
                frameTime = System.currentTimeMillis();
                if (orientationData.getOrientation() != null && orientationData.getStartOrientation() != null) {
//                    pitch = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1]; // pitch is player movement in y direction, pitch value can go from pie to negative pie
//                    roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2]; // roll is player movement in x direction, roll value can go from pie/2 to negative pie/2

                    pitch = orientationData.getOrientation()[1] + 0.5f;
                    roll = orientationData.getOrientation()[2];
//                    Log.d("X0: ", Float.toString(orientationData.getStartOrientation()[1]) );
//                    Log.d("Y0: ", Float.toString(orientationData.getStartOrientation()[2]) );
//                    Log.d("X: ", Float.toString(orientationData.getOrientation()[1]) );
//                    Log.d("Y: ", Float.toString(orientationData.getOrientation()[2]) );


                    float xSpeed = 2 * roll * Constants.SCREEN_WIDTH / 2000f; //xspeed = 2 * pie/2 * screen width/ 1sec
                    float ySpeed = pitch * Constants.SCREEN_HEIGHT / 2000f; //ySpeed = pie * screen height / 1sec

                    playerPoint.x += Math.abs(xSpeed * elapsedTime) > 5 ? xSpeed * elapsedTime : 0; //update x position of player only if player moves by 5 pixels
                    playerPoint.y -= Math.abs(ySpeed * elapsedTime) > 5 ? ySpeed * elapsedTime : 0; //update y position of player only if player moves by 5 pixels


//                    playerPoint.x += Math.pow(orientationData.getOrientation()[1], 4);
//                    playerPoint.y -= Math.pow(orientationData.getOrientation()[2], 4);
                }

                if (playerPoint.x < 0)
                    playerPoint.x = 0;
                else if (playerPoint.x > Constants.SCREEN_WIDTH)
                    playerPoint.x = Constants.SCREEN_WIDTH;

                if (playerPoint.y < Constants.SCREEN_HEIGHT / 3)
                    playerPoint.y = Constants.SCREEN_HEIGHT / 3;
                else if (playerPoint.y > (9 * Constants.SCREEN_HEIGHT / 10))
                    playerPoint.y = (9 * Constants.SCREEN_HEIGHT / 10);


                player.update(playerPoint); //updating the player position
                obstacleManager.update(bullet, obstacleManager, life); // updating the obstacles position
                boyManager.update(obstacleManager, player, life);
                //life.update(obstacleManager);


                bullet.update();

                if (initmtime + 500 <= System.currentTimeMillis()) {
                    isSlingShot = false;
                }
                //killerManager.update();
                //killer2Manager.update(killer2Manager, obstacleManager);

                if (obstacleManager.playerCollide(player) || boyManager.BoyCollide(player.getRectangle())) {
                    if (System.currentTimeMillis() > setLifetm + 2000) {
                        obstacleManager.decrementLife(life);
                        setLifetm = System.currentTimeMillis();
                        isLifedisplay = true;
                        x=1;
                        //life.getLives().remove(life.getLives().size()-1);
                        //obstacleManager.setLife();

                    }
                }

                if (System.currentTimeMillis() >= setLifetm + 1000) {
                    isLifedisplay = false;
                }

                if (obstacleManager.lifeOver()) {
                    gameOver = true;
                    gameOverTime = System.currentTimeMillis();


                    SharedPreferences settings = Constants.CURRENT_CONTEXT.getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
                    int highScore = settings.getInt("HIGH_SCORE", 0);

                    if (obstacleManager.getScore() > highScore) {
                        obstacleManager.setTopScore(obstacleManager.getScore());

                        //save
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("HIGH_SCORE", obstacleManager.getScore());
                        editor.commit();
                    } else obstacleManager.setTopScore(highScore);
                }


            }


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

