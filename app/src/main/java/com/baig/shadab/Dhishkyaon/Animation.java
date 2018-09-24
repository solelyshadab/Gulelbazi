package com.baig.shadab.Dhishkyaon;

/**
 * Created by shadabbaig on 29/12/16.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


public class Animation {
    private Bitmap[] frames;
    private int frameIndex;

    private boolean isPlaying = false;
    public boolean isPlaying(){
        return isPlaying;
    }

    public void play() {
        isPlaying = true;
        frameIndex = 0;
        lastFrame = System.currentTimeMillis();
    }

    public void stop() {
        isPlaying = false;

    }

    private float frameTime; //time in between frame

    private long lastFrame;

    public Animation(Bitmap[] frames, float animTime){ //animTime is the speed
        this.frames = frames; //assign the instance variable
        frameIndex = 0;

        frameTime = animTime/frames.length; //total animation time / total no of frames, evenly space the time between frames, it is in seconds

        lastFrame = System.currentTimeMillis();

    }

    public void draw(Canvas canvas, Rect destination){
        if(!isPlaying)
            return;

        scaleRect(destination);

        canvas.drawBitmap(frames[frameIndex], null, destination, new Paint()); //source rectangle is passed as null since we want to draw entire image

    }

    private void scaleRect(Rect rect){
        float whRatio = (float) (frames[frameIndex].getWidth())/frames[frameIndex].getHeight();
        if(rect.width() > rect.height())
            rect.left = rect.right - (int) (rect.height() * whRatio);
        else
            rect.top = rect.bottom - (int) (rect.width() * (1/whRatio));
    }

    public void update() {
        if (!isPlaying)
            return;

        if (System.currentTimeMillis() - lastFrame > frameTime * 1000) { // multiply the 1000 to convert to millisecs, IF amount of time has passed to change the frame THEN
            frameIndex++;
            frameIndex = frameIndex >= frames.length ? 0 : frameIndex;
            lastFrame = System.currentTimeMillis();
        }
    }


}
