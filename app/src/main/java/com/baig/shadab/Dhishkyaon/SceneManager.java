package com.baig.shadab.Dhishkyaon;

/**
 * Created by shadabbaig on 30/12/16.
 */

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;


public class SceneManager {
    private ArrayList<Scene> scenes = new ArrayList<>();
    public static int ACTIVE_SCENE;

    public SceneManager(){
        ACTIVE_SCENE = 0;
        scenes.add(new MainMenuScene());
        scenes.add(new GameplayScene());
    }

    public void recieveTouch(MotionEvent event){
        scenes.get(ACTIVE_SCENE).recieveTouch(event); //get active scene and pass the information to active scene


    }

    public void update(){
        scenes.get(ACTIVE_SCENE).update();

    }

    public void draw(Canvas canvas) {
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }

}

