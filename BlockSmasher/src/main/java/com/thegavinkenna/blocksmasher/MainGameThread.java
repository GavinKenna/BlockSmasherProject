package com.thegavinkenna.blocksmasher;

/**
 * Created by Gavin on 24/06/13.
 */

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.util.Log;

public class MainGameThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private MainGamePanel gamePanel;
    private boolean currentlyRunning;
    private static final String UNIQUE_NAME = MainGameThread.class.getSimpleName();

    public MainGameThread(SurfaceHolder sh, MainGamePanel gp)
    {
        super();
        this.surfaceHolder = sh;
        this.gamePanel = gp;
    }


    public void SetRunningBool(boolean r){
        this.currentlyRunning = r;
    }


    public void run() {
        Canvas canvas = null;
        Log.d(UNIQUE_NAME, "Starting main game loop");
        this.gamePanel.Init();

        gamePanel.updateThread.run();
        gamePanel.renderThread.run();
        while (currentlyRunning) {


            canvas = null;

            try {
                //canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    // update game state
               // gamePanel.DrawBG(canvas);
                    gamePanel.updateThread.run();
                    gamePanel.renderThread.run();
                    //this.gamePanel.update();
                    // draws the canvas on the panel
                    //this.gamePanel.render(canvas);

                }
            } finally {
                if (canvas != null) {
                    //surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }





}
