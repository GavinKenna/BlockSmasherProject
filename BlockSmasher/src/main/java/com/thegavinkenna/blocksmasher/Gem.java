package com.thegavinkenna.blocksmasher;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.SurfaceView;

/**
 * Created by Gavin on 25/06/13.
 */
public abstract class Gem extends Breakable {

    private boolean fallingGem; //Does this gem fall when shown?
    private float velocity = 1f; //Speed of the gem falling, if it falls
    private float timeLasts; //How long does this powerup/gem last?
    private boolean captured; //Player took it?
    private long startTime; //For when the gem is touched
    //private boolean alive = false; //Is this gem alive and showing?

    public Gem(Bitmap bitmap, int x, int y, int col,int currentHealth, int pointsToGive, float time){
        super(1,pointsToGive,bitmap, x, y, col);
        this.timeLasts = time;
        this.captured = false;
        this.startTime = -1;
        this.SetAlive(false);

        velocity =  1 + (int)(Math.random() * ((10 - 1) + 1));
    }

    public Gem(Bitmap bitmap, int x, int y, int col,int currentHealth, int pointsToGive){

        super(1,pointsToGive,bitmap, x, y, col);
        this.timeLasts = -1;
        this.captured = false;
        this.startTime = -1;
        this.SetAlive(false);

        velocity =  1 + (int)(Math.random() * ((10 - 1) + 1));
    }

    public boolean IsAFallingGem(){
        return this.fallingGem;
    }



    public void SetFalling(boolean f){
        this.fallingGem = f;
    }

    public void update(SurfaceView bounds /*For bound checking, walls*/, Paddle paddle , Ball ball){
        CheckCollisionBall(ball);
        CheckCollisionPaddle(paddle);
        CheckCollisionBottom(bounds);

        if(captured){
            Captured();
        }else if(fallingGem == true && IsAlive()){
            this.setY(this.getY()+(int)velocity); //if the gem is meant to fall, let it fall with velocity. MAKE IT RAIN GEMS!
        }else{
            Bobble(); //Gonna write this method now. Bobble, make it slightly hover, give it life I guess.
        }



    }

    Rect r1;
    Rect r2;

    private void CheckCollisionPaddle(Paddle paddle) {
        r1 = new Rect(getX() - getBitmap().getWidth(), getY() - getBitmap().getHeight(),getX() + (getBitmap().getWidth()/2),getY() + (getBitmap().getHeight())/2);
        r2 = new Rect(paddle.getX() - paddle.getBitmap().getWidth(), paddle.getY() - paddle.getBitmap().getHeight(), paddle.getX() + (paddle.getBitmap().getWidth()), paddle. getY() + (paddle.getBitmap().getHeight()));

        //r1.offset(-30,-30);
        // r2.offset(-30,-30);

        if(r1.intersect(r2)){
            this.SetAlive(false);
            this.captured = true;
        }
    }

    private void CheckCollisionBottom(SurfaceView bounds) {
        // bottom wall
        if (getY() + getBitmap().getHeight() / 2 >= bounds.getHeight()) {
            this.SetAlive(false);
        }
    }
    private void CheckCollisionBall(Ball ball) {
        r1 = new Rect(getX() - getBitmap().getWidth(), getY() - getBitmap().getHeight(),getX() + (getBitmap().getWidth()/2),getY() + (getBitmap().getHeight())/2);
        r2 = new Rect(ball.getX() - ball.getBitmap().getWidth(), ball.getY() - ball.getBitmap().getHeight(), ball.getX() + (ball.getBitmap().getWidth()), ball. getY() + (ball.getBitmap().getHeight()));

        //r1.offset(-30,-30);
        // r2.offset(-30,-30);

        if(r1.intersect(r2)){
            this.SetAlive(false);
            this.captured = true;
        }

    }


    private long bobbleEnd = -1;
    private int bobbleDirection = 1;
    public void Bobble(){
        if(bobbleEnd==-1){
            bobbleEnd = System.currentTimeMillis()+200;
        }

        if(System.currentTimeMillis() >= bobbleEnd){
            bobbleDirection *= -1;
            bobbleEnd = System.currentTimeMillis()+200;
        }

        this.setY(this.getY() + bobbleDirection);


    }

    public boolean IsCaptured(){
        return this.captured;
    }

    public  void Captured(){
        if(this.timeLasts!=-1){
            if(startTime==-1){
                startTime = System.currentTimeMillis()/1000;
            }else{
                if((System.currentTimeMillis()/1000) - timeLasts <= 0){
                    //time up
                    TimeUp();
                }
            }
        }
    } //After the player hits it


    public void SetCaptured(boolean c){
        this.captured = c;
    }

    public abstract void TimeUp(); //After time has expired

}
