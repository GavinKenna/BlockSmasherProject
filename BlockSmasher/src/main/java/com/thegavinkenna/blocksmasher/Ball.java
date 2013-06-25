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


    private float xV; //X velocity
    private float yV;

    private int xD = DIRECTION_RIGHT; // xD => X Direction
    private int yD = DIRECTION_DOWN;

    private boolean alive; //Is this current ball still in play, or hit the bottom?
    private boolean busy; //Is it busy breaking a brick.

    public Ball(Bitmap bitmap, int x, int y, int col, float xV, float yV){
        super( bitmap,  x,  y,  col);

        this.xV = xV;
        this.yV = yV;
        alive = true;
    }

    public Ball(Bitmap bitmap, int x, int y,  float xV, float yV){
        super( bitmap,  x,  y);

        this.xV = xV;
        this.yV = yV;
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
    }

    public void update(SurfaceView bounds /*For bound checking, walls*/, Paddle paddle /*Collision with the paddle*/ /*Add in here bricks, gens, etc*/, float xVelocityFromPaddle /*The speed of the paddle affects ball speed*/, List<Brick> bricks){

        /*
        * Wall collision testing
        * */

        if (xD == DIRECTION_RIGHT && this.getX() + this.getBitmap().getWidth() / 2 >=  bounds.getWidth())
        {
            this.InvertXDirection();
        }

        // left wall
        if (xD == DIRECTION_LEFT && this.getX() - this.getBitmap().getWidth() / 2 <= 0) {
            this.InvertXDirection();
        }

        // bottom wall
        if (yD == DIRECTION_DOWN && getY() + getBitmap().getHeight() / 2 >= bounds.getHeight()) {
            InvertYDirection();
            alive = false;
        }

        // top wall
        if (yD == DIRECTION_UP && getY() - getBitmap().getHeight() / 2 <= 0) {
           this.InvertYDirection();
        }


        //Collision with paddle
        r1 = new Rect(getX() - getBitmap().getWidth(), getY() - getBitmap().getHeight(),getX() + (getBitmap().getWidth()/2),getY() + (getBitmap().getHeight())/2);
        r2 = new Rect(paddle.getX() - paddle.getBitmap().getWidth(), paddle.getY() - paddle.getBitmap().getHeight(), paddle.getX() + (paddle.getBitmap().getWidth()), paddle. getY() + (paddle.getBitmap().getHeight()));

        //r1.offset(-30,-30);
       // r2.offset(-30,-30);

        if(r1.intersect(r2)){
            InvertYDirection();
            //InvertXDirection();
        }

        //Collision with brick
        for(int i = 0 ; i < bricks.size() ; i++){
            r1 = new Rect(getX() - getBitmap().getWidth(), getY() - getBitmap().getHeight(),getX() + (getBitmap().getWidth()/2),getY() + (getBitmap().getHeight())/2);

            r2 = new Rect(bricks.get(i).getX() -bricks.get(i).getBitmap().getWidth(), bricks.get(i).getY() - bricks.get(i).getBitmap().getHeight(), bricks.get(i).getX() + (bricks.get(i).getBitmap().getWidth()), bricks.get(i). getY() + (bricks.get(i).getBitmap().getHeight()));

            //r1.offset(-30,-30);
            // r2.offset(-30,-30);

            if(r1.intersect(r2) && !busy){
                busy = true;
                InvertYDirection();
                bricks.get(i).Hit();

                //InvertXDirection();
            }
        }





        this.update();
    }


}
