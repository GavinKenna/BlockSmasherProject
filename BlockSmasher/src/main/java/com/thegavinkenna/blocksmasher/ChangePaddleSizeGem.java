package com.thegavinkenna.blocksmasher;

import android.graphics.Bitmap;

/**
 * Created by Gavin on 25/06/13.
 */
public class ChangePaddleSizeGem extends Gem {

    private boolean paddleBigger; //Does this make the paddle bigger [true], or smaller [false]

    public ChangePaddleSizeGem(Bitmap bitmap, int x, int y, int col,int currentHealth, int pointsToGive){

        super( bitmap,  x,  y,  col, currentHealth,  pointsToGive, 50000f);
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

    public void Captured(Paddle paddle){
    /*
    * Caught gem logic here
    * */
        super.Captured(paddle);
         if(!paddleBigger){
            //make paddle smaller for certain time [15 seconds]
             if(!paddle.IsSmall()){
                 paddle.SetSmall(true);
             }
        }else{
            //make paddle larger for certain time
             if(!paddle.IsLarge()){
                 paddle.SetLarge(true);
             }
        }

     }

    public void TimeUp(){
        this.SetDead(true);
    }

}
