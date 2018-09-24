package com.baig.shadab.Dhishkyaon;

/**
 * Created by shadabbaig on 29/12/16.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    private SceneManager manager;

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);

        Constants.CURRENT_CONTEXT = context;

        thread = new MainThread(getHolder(), this);

        manager = new SceneManager();

        setFocusable(true);
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this); // Restart a new thread

        Constants.INIT_TIME = System.currentTimeMillis();

        thread.setRunning(true); //later will make our game loop setrunning
        thread.start(); // method inside a thread class which our main class will extend (class in the java packages)
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join(); // will finish the thread but will terminate it
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        manager.recieveTouch(event);

        return true;
        //return super.onTouchEvent(event);
    }

    public void update() {
        manager.update();

    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas); // take a canvas and draw it on our game
        manager.draw(canvas);
    }



}