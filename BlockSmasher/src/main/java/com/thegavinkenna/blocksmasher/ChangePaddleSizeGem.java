package com.thegavinkenna.blocksmasher;

import android.graphics.Bitmap;

/**
 * Created by Gavin on 25/06/13.
 */
public class ChangePaddleSizeGem extends Gem {

    private boolean paddleBigger; //Does this make the paddle bigger [true], or smaller [false]

    public ChangePaddleSizeGem(Bitmap bitmap, int x, int y, int col,int currentHealth, int pointsToGive){

        super( bitmap,  x,  y,  col, currentHealth,  pointsToGive, 5f);
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

    public void Captured(){
    /*
    * Caught gem logic here
    * */
        if(!paddleBigger){
            //make paddle smaller for certain time [15 seconds]
        }else{
            //make paddle larger for certain time
        }

     }

    public void TimeUp(){

    }

}
