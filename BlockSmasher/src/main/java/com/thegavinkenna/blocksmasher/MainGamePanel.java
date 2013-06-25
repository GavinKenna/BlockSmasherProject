package com.thegavinkenna.blocksmasher;

/**
 * Created by Gavin on 24/06/13.
 */




import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MainGamePanel extends SurfaceView implements
        SurfaceHolder.Callback {


    private MainGameThread thread;

    /**
     *  Game entites
     *
     */

    private Paddle paddle;
    private Ball[] balls = new Ball[1]; //Will be made into an array soon, so that there can be loads of ball on scren at once when a certain gem is hit.
    private java.util.List<Brick> bricks;

    private char[][]level = new char[][]{
        {' ',' ',' ',' ',' ',' ',' ',' ', ' ',' ',' ',' ',' ',' ',' ',' ', ' '},
        {' ','B','B','A','A','A','B','B', ' ','B','B','A','A','A','B','B', ' '},
        {' ','A','A','A','A','A','A','A', ' ','A','A','A','A','A','A','A', ' '},
        {' ','B','C','B','B','B','C','B', ' ','B','C','B','B','B','C','B', ' '},
        {' ','C','C','C','C','C','C','C', ' ','C','C','C','C','C','C','C', ' '},
        {' ','A','B','A','B','A','B','A', ' ','A','B','A','B','A','B','A', ' '},
        {' ',' ',' ',' ',' ',' ',' ',' ', ' ',' ',' ',' ',' ',' ',' ',' ', ' '},
        {' ',' ',' ',' ',' ',' ',' ',' ', ' ',' ',' ',' ',' ',' ',' ',' ', ' '},
        {' ','B','B','A','A','A','B','B', ' ','B','B','A','A','A','B','B', ' '},
        {' ','A','A','A','A','A','A','A', ' ','A','A','A','A','A','A','A', ' '},
        {' ','B','C','B','B','B','C','B', ' ','B','C','B','B','B','C','B', ' '},
        {' ','C','C','C','C','C','C','C', ' ','C','C','C','C','C','C','C', ' '},
        {' ','A','B','A','B','A','B','A', ' ','A','B','A','B','A','B','A', ' '},
        {' ',' ',' ',' ',' ',' ',' ',' ', ' ',' ',' ',' ',' ',' ',' ',' ', ' '}
    };

    public MainGamePanel(Context context) {

        super(context);

        getHolder().addCallback(this);

        thread = new MainGameThread(getHolder(),this);

        setFocusable(true);
    }

    public void Init()
    {
        bricks = new ArrayList<Brick>();
        paddle = new Paddle(BitmapFactory.decodeResource(getResources(),R.drawable.paddle),this.getWidth() / 2 /*Centre of screen*/ , this.getHeight() - 40 /*Just a little off the ground*/,Color.GREEN);

        for(int i = 0; i<balls.length; i ++)
        {
          balls[i] = new Ball(BitmapFactory.decodeResource(getResources(),R.drawable.ball),this.getWidth() / 2 /*Centre of screen*/ , this.getHeight() - 90 /*Just a little off the ground*/ ,Color.RED,i+17, i+17);
        }

        Brick tempBrick;
        Bitmap brickBit = BitmapFactory.decodeResource(getResources(),R.drawable.brick);
        int distance = 1; // Distance between each brick
        int col = 0;
        int health = 0;
        int points = 0;


        for(int i = 0 ; i < level.length ; i++){
            for(int j = 0 ; j < level[i].length ; j++){

                switch (level[i][j]){
                    case 'A':
                        col = Color.BLUE;
                        health = 1;
                        points = 1;
                        break;
                    case 'B':
                        col = Color.GREEN;
                        health = 2;
                        points = 2;
                        break;
                    case 'C':
                        col = Color.MAGENTA;
                        health = 3;
                        points = 3;
                        break;
                }

                tempBrick = new Brick(brickBit,this.getLeftPaddingOffset() + brickBit.getWidth()/2 +  (brickBit.getWidth()*distance) * j,(this.getTopPaddingOffset()+ ( brickBit.getHeight()*distance)) * i,col,health, points);
                bricks.add(tempBrick);
            }


        }






    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.SetRunningBool(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
              // thread.destroy();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //Finger touching game, do something
            //Add in update stuff here when ready
            paddle.HandleActionDown((int)event.getX(), (int)event.getY());
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (paddle.IsTouched()) {
                // paddle is picked, and now to drag it
                paddle.setX((int)event.getX());
            }
        } if (event.getAction() == MotionEvent.ACTION_UP) {
            // touch was released
            if (paddle.IsTouched()) {
                paddle.SetTouched(false);
            }
        }
        return true;
    }


    public void DrawBG(Canvas canvas){
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.bg),0,getHeight(),null);
    }

    boolean notBG = true;
    Bitmap bg = BitmapFactory.decodeResource(getResources(),R.drawable.bg);
    protected void render(Canvas canvas) {

        Rect dest = new Rect(0, 0, getWidth(), getHeight());
        canvas.drawColor(Color.BLUE);
        canvas.drawBitmap(bg, null, dest, null);
        canvas.save();

        canvas.restore();
        //Draw background once
        //Bitmap bg = BitmapFactory.decodeResource(getResources(),R.drawable.bg);
        //getHolder().lockCanvas().drawBitmap(bg,0, 0,null);

       // BitmapDrawable bd = new BitmapDrawable(bg);
       // this.setBackground(bd);
        //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.bg),0,getBottom(),null);


        this.paddle.draw(canvas);

        for(int i = 0; i < balls.length ; i++)
        {
            balls[i].draw(canvas);
        }

        for(int i = 0; i < bricks.size(); i ++){
            bricks.get(i).draw(canvas);
        }
    }

    public void update()
    {
        for(int i = 0; i < balls.length ; i++){
         balls[i].update(this, paddle, 5f, bricks);
        }

        for(int i = 0; i < bricks.size(); i ++){
            bricks.get(i).update();
            if(!bricks.get(i).IsAlive())
            {
                bricks.remove(i);
            }
        }
    }




}
