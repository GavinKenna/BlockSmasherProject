package com.thegavinkenna.blocksmasher;

import android.graphics.Bitmap;
import android.view.SurfaceView;

/**
 * Created by Gavin on 28/06/13.
 */
public class ChangeBallSpeedGem extends Gem {

    private boolean makeBallFaster; // If true then this gem will make the ball faster. If not, then slower

    public ChangeBallSpeedGem(Bitmap bitmap, int x, int y, int col,int currentHealth, int pointsToGive){

        super( bitmap,  x,  y,  col, currentHealth,  pointsToGive, 5f); //5 seconds
        int rand =  0 + (int)(Math.random() * ((1 - 0) + 1));

        if(rand!=0){
            this.SetFalling(false);
        }else{
            this.SetFalling(true);
        }

        rand =  0 + (int)(Math.random() * ((1 - 0) + 1));

        if(rand!=0){
            makeBallFaster = false;
        }else{
            makeBallFaster = true;
        }

    }

    Ball ball = null;
    public void Captured(Ball ball){
    /*
    * Caught gem logic here
    * */
        super.Captured();
        if(!makeBallFaster){
            //make ball slower for certain time [15 seconds]
            if(!ball.IsSlow() && this.IsCaptured()){
                ball.SetSlow();
            }
        }else{
            //make ball faster for certain time
            if(!ball.IsFast() && this.IsCaptured()){
                ball.SetFast();
            }
        }

        if(this.ball==null){
            this.ball = ball;
        }


    }

    public void TimeUp(){
        this.SetDead(true);
        this.SetCaptured(false);
        this.ball.SetNormal();
    }

    public void update(SurfaceView bounds /*For bound checking, walls*/, Paddle paddle , Ball ball){
        if(this.IsCaptured()){
            this.Captured(ball);
        }
        super.update(bounds,paddle,ball);


    }



}
