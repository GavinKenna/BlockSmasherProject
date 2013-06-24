package com.thegavinkenna.blocksmasher;

/**
 * Created by Gavin on 24/06/13.
 */




import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;
import android.app.Activity;
import android.graphics.Color;


public class MainGamePanel extends SurfaceView implements
        SurfaceHolder.Callback {


    private MainGameThread thread;

    /**
     *  Game entites
     *
     */

    private Paddle paddle;
    private Ball ball; //Will be made into an array soon, so that there can be loads of ball on scren at once when a certain gem is hit.

    public MainGamePanel(Context context) {

        super(context);

        getHolder().addCallback(this);

        thread = new MainGameThread(getHolder(),this);

        setFocusable(true);
    }

    public void Init()
    {
        paddle = new Paddle(BitmapFactory.decodeResource(getResources(),R.drawable.paddle),this.getWidth() / 2 /*Centre of screen*/ , this.getHeight() - 40 /*Just a little off the ground*/,Color.GREEN);
        ball = new Ball(BitmapFactory.decodeResource(getResources(),R.drawable.ball),this.getWidth() / 2 /*Centre of screen*/ , this.getHeight() - 90 /*Just a little off the ground*/ ,Color.RED,9, 9);
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


    protected void render(Canvas canvas) {
        // canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.droid_1),10,10,null);
        canvas.drawColor(Color.BLUE);
        this.paddle.draw(canvas);
        this.ball.draw(canvas);
    }

    public void update()
    {
        ball.update(this, paddle, 5f);
    }




}
