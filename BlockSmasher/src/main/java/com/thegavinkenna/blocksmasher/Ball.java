package com.thegavinkenna.blocksmasher;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

/**
 * Created by Gavin on 24/06/13.
 */
public class Ball extends Entity {


    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_LEFT = -1;
    public static final int DIRECTION_UP = -1;
    public static final int DIRECTION_DOWN = 1;

    private boolean touched; //Is the ball being touched?

    private float xV; //X velocity
    private float yV;
    private float defaultVelocity; //For when restarting velocity
    private final float velocityMult = 2; //The multiplier for changing the speed of the ball, from the ball speed gem
    private boolean fastBall;
    private boolean slowBall;

    private int xD = DIRECTION_RIGHT; // xD => X Direction
    private int yD = DIRECTION_DOWN;

    private boolean alive; //Is this current ball still in play, or hit the bottom?
    private boolean busy; //Is it busy breaking a brick.

    public Ball(Bitmap bitmap, int x, int y, int col, float xV, float yV){
        super( bitmap,  x,  y,  col);

        this.xV = xV;
        this.yV = yV;
        this.defaultVelocity = xV;
        alive = true;
    }

    public Ball(Bitmap bitmap, int x, int y,  float xV, float yV){
        super( bitmap,  x,  y);

        this.xV = xV;
        this.yV = yV;
        this.defaultVelocity = xV;
        alive = true;
    }

    public void InvertXDirection(){
        this.xD = this.xD * -1;
    }

    public void InvertYDirection(){
        this.yD = this.yD * -1;
    }

    public boolean IsAlive(){
        return alive;
    }

    public void update(){

       this.setX((int)(this.getX() + (xV * xD)));
       this.setY((int)(this.getY() + (yV * yD)));
       busy = false;
    }

    Rect r1;
    Rect r2;

    public void draw(Canvas canvas){
        canvas.drawBitmap(getBitmap(),getX() - (getBitmap().getWidth() / 2), getY() - (getBitmap().getHeight()/2),getPaint());
          /*Paint p = new Paint();
         p.setColor(Color.GREEN);
       canvas.drawRect(r1, p);*/ //Debugging purposes
    }

    public boolean IsBusy(){
        return this.busy;
    }

    public boolean HadCollision(SurfaceView bounds /*For bound checking, walls*/, Paddle paddle /*Collision with the paddle*/ /*Add in here bricks, gens, etc*/, float xVelocityFromPaddle /*The speed of the paddle affects ball speed*/, List<Brick> bricks){
        if(!busy){
            if (xD == DIRECTION_RIGHT && this.getX() + this.getBitmap().getWidth() / 2 >=  bounds.getWidth())
            {
                busy = true;
                this.InvertXDirection();
                return true;
            }

            // left wall
            if (xD == DIRECTION_LEFT && this.getX() - this.getBitmap().getWidth() / 2 <= 0) {
                this.InvertXDirection();
                busy = true;
                return true;
            }

            // bottom wall
            if (yD == DIRECTION_DOWN && getY() + getBitmap().getHeight() / 2 >= bounds.getHeight()) {
                InvertYDirection();
                alive = false;
                busy = true;
                return true;
            }

            // top wall
            if (yD == DIRECTION_UP && getY() - getBitmap().getHeight() / 2 <= 0) {
                this.InvertYDirection();
                busy = true;
                return true;
            }


            //Collision with paddle
            r1 = new Rect(getX() - (getBitmap().getWidth()/2), getY() - (getBitmap().getHeight()/2),getX() + (getBitmap().getWidth()/2),getY() + (getBitmap().getHeight()/2));
            r2 = new Rect(paddle.getX() - (paddle.getBitmap().getWidth()/2), paddle.getY() - (paddle.getBitmap().getHeight()/2), paddle.getX() + (paddle.getBitmap().getWidth()/2), paddle.getY() + (paddle.getBitmap().getHeight()/2));
            //r1.offset(-30,-30);
            // r2.offset(-30,-30);

            if(r1.intersect(r2)){
                InvertYDirection();
                busy = true;
                return true;
                //InvertXDirection();
            }

            //Collision with brick
            for(int i = 0 ; i < bricks.size() ; i++){
                r1 = new Rect(getX() - (getBitmap().getWidth()/2), getY() - (getBitmap().getHeight()/2),getX() + (getBitmap().getWidth()/2),getY() + (getBitmap().getHeight()/2));

                r2 = new Rect(bricks.get(i).getX() - (bricks.get(i).getBitmap().getWidth()/2) , bricks.get(i).getY() -(bricks.get(i).getBitmap().getHeight()/2) , bricks.get(i).getX() + (bricks.get(i).getBitmap().getWidth()/2), bricks.get(i).getY() + (bricks.get(i).getBitmap().getHeight()/2));

                //r1.offset(-30,-30);
                // r2.offset(-30,-30);

                if(r1.intersect(r2) && !busy){
                    busy = true;
                    InvertYDirection();
                    bricks.get(i).Hit();
                    return true;

                    //InvertXDirection();
                }
            }
            busy = false;
        }
        return false;
    }



    public void update(SurfaceView bounds /*For bound checking, walls*/, Paddle paddle /*Collision with the paddle*/ /*Add in here bricks, gens, etc*/, float xVelocityFromPaddle /*The speed of the paddle affects ball speed*/, List<Brick> bricks){

        /*
        * Wall collision testing
        * */


        HadCollision( bounds,  paddle ,  xVelocityFromPaddle ,bricks);

        this.update();
    }

    public boolean IsFast(){
        return this.fastBall;
    }

    public boolean IsSlow(){
        return slowBall;
    }

    public boolean IsNormalSpeed(){
        return !(slowBall||fastBall);
    }

    public void SetFast(){
        if(this.IsSlow()){
            this.xV *= (this.velocityMult*2);
            this.yV *= (this.velocityMult*2);
        }

        if(this.IsNormalSpeed()){
            this.xV *= this.velocityMult;
            this.yV *= this.velocityMult;
        }
        this.fastBall = true;
        this.slowBall = false;
    }

    public void SetSlow(){
        if(this.IsFast()){
            this.xV /= (this.velocityMult*2);
            this.yV /= (this.velocityMult*2);
        }

        if(this.IsNormalSpeed()){
            this.xV /= this.velocityMult;
            this.yV /= this.velocityMult;
        }
        this.fastBall = false;
        this.slowBall = true;
    }

    public void SetNormal(){
        if(this.IsFast()){
            this.xV /= this.velocityMult;
            this.yV /= this.velocityMult;
        }

        if(this.IsSlow()){
            this.xV *= this.velocityMult;
            this.yV *= this.velocityMult;
        }
        this.fastBall = false;
        this.slowBall = false;
    }

    public boolean IsTouched(){
        return touched;
    }

    public void SetTouched(boolean t){
        touched = t;
    }

    public void SetXV(float xv){
        this.xV = xv;
    }

    public void SetYV(float yv){
        this.yV = yv;
    }

    public float GetXV(){
        return this.xV;
    }

    public float GetYV(){
        return this.yV;
    }


    private int startTouchX, startTouchY; //This is used to calculate the fling of the finger
    public void HandleActionDown(int eventX, int eventY)
    {
        SetTouched(true);
        this.startTouchX  =eventX;
        this.startTouchY = eventY;
    }

    public void HandleDirection(int eventX, int eventY){

        if(eventX <= startTouchX){
            this.xD = DIRECTION_LEFT;
        }else if(eventX > startTouchX){
            this.xD = DIRECTION_RIGHT;
        }

        if(eventY <= startTouchY){
            this.yD = DIRECTION_UP;
        }else if(eventY > startTouchY){
            this.yD = DIRECTION_DOWN;
        }

        float xN = Math.abs(startTouchX-eventX);
        float yN = Math.abs(startTouchY - eventY);

        if(xN > yN){
            SetXV(1 * defaultVelocity);
            SetYV(yN/xN * defaultVelocity);
        }else if(xN < yN){
            SetXV( xN/yN * defaultVelocity);
            SetYV(1 * defaultVelocity);
        }else{
            SetYV(1 * defaultVelocity);
            SetXV(1 * defaultVelocity);
        }

        //reset startTouches
        startTouchY = 0;
        startTouchX = 0;

    }

    public void SetAlive(boolean a){
        this.alive = a;
    }


}
