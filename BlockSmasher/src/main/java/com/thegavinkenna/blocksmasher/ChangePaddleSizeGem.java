package com.thegavinkenna.blocksmasher;

import android.graphics.Bitmap;
import android.view.SurfaceView;

/**
 * Created by Gavin on 25/06/13.
 */
public class ChangePaddleSizeGem extends Gem {

    private boolean paddleBigger; //Does this make the paddle bigger [true], or smaller [false]

    public ChangePaddleSizeGem(Bitmap bitmap, int x, int y, int col,int currentHealth, int pointsToGive){

        super( bitmap,  x,  y,  col, currentHealth,  pointsToGive, 5f); //5 seconds
        int rand =  0 + (int)(Math.random() * ((1 - 0) + 1));

        if(rand!=0){
        this.SetFalling(false);
        }else{
            this.SetFalling(true);
        }

        rand =  0 + (int)(Math.random() * ((1 - 0) + 1));

        if(rand!=0){
            paddleBigger = false;
        }else{
            paddleBigger = true;
        }

    }

    private Paddle paddle = null; //Local paddle to keep track of it, for setting it back to normal
    public void Captured(Paddle paddle){
    /*
    * Caught gem logic here
    * */
        super.Captured();
         if(!paddleBigger){
            //make paddle smaller for certain time [15 seconds]
             if(!paddle.IsSmall() && IsCaptured()){
                 paddle.SetSmall(true);
             }
        }else{
            //make paddle larger for certain time
             if(!paddle.IsLarge() && IsCaptured()){
                 paddle.SetLarge(true);
             }
        }
        if(this.paddle==null){
            this.paddle = paddle;
        }

     }

    public void TimeUp(){
        this.SetDead(true);
        this.SetCaptured(false);
        this.paddle.SetNormal();
    }

    public void update(SurfaceView bounds /*For bound checking, walls*/, Paddle paddle , Ball ball){
        if(this.IsCaptured()){
            this.Captured(paddle);
        }
        super.update(bounds,paddle,ball);


    }

}
