package com.thegavinkenna.blocksmasher;

/**
 * Created by Gavin on 24/06/13.
 */




import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MainGamePanel extends SurfaceView implements
        SurfaceHolder.Callback {


    private MainGameThread thread;

    private int fps = 0 ; //For calculating the fps.
    private int elapsedTime = 0;
    private int totalFrames = 0;
    private long startTimeFPS = 0;
    private long endTimeFPS = 0;

    /**
     *  Game entites
     *
     */

    private Paddle paddle;
    private Ball[] balls = new Ball[1]; //Will be made into an array soon, so that there can be loads of ball on scren at once when a certain gem is hit.
    private java.util.List<Brick> bricks;
    private java.util.List<Gem> gems;

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
        bkgrnd = new Canvas();


        bricks = new ArrayList<Brick>();
        gems = new ArrayList<Gem>();
        paddle = new Paddle(BitmapFactory.decodeResource(getResources(),R.drawable.paddle),BitmapFactory.decodeResource(getResources(),R.drawable.paddlesmall),BitmapFactory.decodeResource(getResources(),R.drawable.paddlelong),this.getWidth() / 2 /*Centre of screen*/ , this.getHeight() - 40 /*Just a little off the ground*/,Color.GREEN);

        for(int i = 0; i<balls.length; i ++)
        {
          balls[i] = new Ball(BitmapFactory.decodeResource(getResources(),R.drawable.ball),this.getWidth() / 2 /*Centre of screen*/ , this.getHeight() - 90 /*Just a little off the ground*/ ,Color.RED,20, 20);
        }


        Boolean emptyBrick = false;
        Brick tempBrick;
        Bitmap brickBit = BitmapFactory.decodeResource(getResources(),R.drawable.brick);
        int distance = 1; // Distance between each brick
        int col = 0;
        int health = 0;
        int points = 0;

        Gem tempGem;
        Bitmap padGemBit = BitmapFactory.decodeResource(getResources(),R.drawable.gem);
        Bitmap ballGemBit = BitmapFactory.decodeResource(getResources(),R.drawable.speedgem);

        for(int i = 0 ; i < level.length ; i++){
            for(int j = 0 ; j < level[i].length ; j++){

                switch (level[i][j]){
                    case 'A':
                        col = Color.BLUE;
                        health = 1;
                        points = 1;
                        emptyBrick = false;
                        break;
                    case 'B':
                        col = Color.GREEN;
                        health = 2;
                        points = 2;
                        emptyBrick = false;
                        break;
                    case 'C':
                        col = Color.MAGENTA;
                        health = 3;
                        points = 3;
                        emptyBrick = false;
                        break;
                    case ' ':
                        emptyBrick = true;
                        break;
                }

                if(!emptyBrick){
                    tempBrick = new Brick(brickBit,this.getLeftPaddingOffset() + brickBit.getWidth()/2 +  (brickBit.getWidth()*distance) * j,(this.getTopPaddingOffset()+ ( brickBit.getHeight()*distance)) * i,col,health, points);
                    bricks.add(tempBrick);

                    //Gen gems
                    if(Math.random() < 0.08){ //if true, add a gem to this brick
                        tempGem = null;
                        int rand =  0 + (int)(Math.random() * ((1 - 0) + 1));

                        switch (rand){
                            case 0:
                                tempGem = new ChangePaddleSizeGem(padGemBit,  this.getLeftPaddingOffset() + brickBit.getWidth()/2 +  (brickBit.getWidth()*distance) * j,(this.getTopPaddingOffset()+
                                        ( brickBit.getHeight()*distance)) * i , col, 1,  1);
                                break;
                            case 1:
                                tempGem = new ChangeBallSpeedGem(ballGemBit,  this.getLeftPaddingOffset() + brickBit.getWidth()/2 +  (brickBit.getWidth()*distance) * j,(this.getTopPaddingOffset()+
                                        ( brickBit.getHeight()*distance)) * i , col, 1,  1);
                                break;
                        }



                        gems.add(tempGem);
                }else{
                        tempGem = null;
                    }

                    tempBrick.SetGem(tempGem);
                }

            }


        }


        /**
         * The following code is to compress the background image, as it takes a bit of CPU power drawing it on the screen.
         */
        bg = BitmapFactory.decodeResource(getResources(),R.drawable.bbg);
        Bitmap s = bg.createScaledBitmap(bg, getWidth(), getHeight(), true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        s.compress(Bitmap.CompressFormat.JPEG, 90, baos);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inJustDecodeBounds = false;
        options.inSampleSize = 1;
        options.mCancel = false;
        options.outHeight = getHeight();
        options.outWidth = getWidth();

        options.inPreferredConfig = Bitmap.Config.RGB_565;

       // scaled = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size());
        scaled = BitmapFactory.decodeResource(getResources(), R.drawable.ndbg, options);
        scaled = Bitmap.createScaledBitmap(scaled, getWidth(), getHeight(), false);

        /**
         * End of compression
         */

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

    public Paddle GetPaddle(){
        return this.paddle;
    }



    public void onDraw(Canvas c){
        super.onDraw(c);
        c.drawColor(Color.BLACK);
        //Drawable d = getResources().getDrawable(R.drawable.bbg);
        //d.setBounds(0,0,getWidth(),getHeight());
       // setBackgroundResource(0);
        //c.drawBitmap(scaled,0,0,null);
    }

    Bitmap bg ;//= BitmapFactory.decodeResource(getResources(),R.drawable.bbg);
    Bitmap scaled;// = bg.createScaledBitmap(bg, getWidth(), getHeight(), true);
    Canvas bkgrnd;
    boolean firstTime=true;
    Thread renderThread = new Thread(){
        public void run(){
            totalFrames++;

            Canvas canvas = getHolder().lockCanvas();

            //canvas.drawBitmap(scaled,0,0,null);
           // getHolder().unlockCanvasAndPost(canvas);

            //canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.BLUE);
            canvas.restore();
            canvas.drawBitmap(scaled,0,0,null);
//            bkgrnd.restore();
            paddle.draw(canvas);

            for(int i = 0; i < balls.length ; i++)
            {
                balls[i].draw(canvas);
            }

            for(int i = 0; i < bricks.size(); i ++){
                bricks.get(i).draw(canvas);
            }

            for(int i = 0; i < gems.size(); i ++){
                if(gems.get(i).IsAlive()){
                 gems.get(i).draw(canvas);
                }
            }

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(15);
            canvas.drawText(String.valueOf(fps),10,10,paint );

           // canvas.restore();
            getHolder().unlockCanvasAndPost(canvas);

        }
    };


    SurfaceView sv = this;
    Thread updateThread = new Thread(){
        public void run(){
            endTimeFPS = System.currentTimeMillis();

            if(startTimeFPS == 0){
                startTimeFPS = System.currentTimeMillis();
            }

            elapsedTime+=(endTimeFPS/1000-startTimeFPS/1000);

            if(elapsedTime>=1){
                fps = totalFrames;
                totalFrames = 0;
                elapsedTime = 0;
                startTimeFPS = 0;
            }

            for(int i = 0; i < balls.length ; i++){
                balls[i].update(sv, paddle, 5f, bricks);
            }

            for(int i = 0; i < bricks.size(); i ++){
                bricks.get(i).update();
                if(!bricks.get(i).IsAlive())
                {
                    if(bricks.get(i).GetGem()!=null){
                        bricks.get(i).GetGem().SetAlive(true);
                    }
                    bricks.remove(i);
                }
            }

            for(int i = 0; i < gems.size(); i ++){
                if(gems.get(i).IsAlive() || gems.get(i).IsCaptured() ){
                  gems.get(i).update(sv, paddle, balls[0]); //change to list of balls
                }
                if(gems.get(i).IsDead())
                {
                    gems.remove(i);
                }
            }
        }

    };





}
