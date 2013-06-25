package com.thegavinkenna.blocksmasher;

import android.graphics.Bitmap;

/**
 * Created by Gavin on 25/06/13.
 */
public class Brick extends Breakable {

    private Gem gem;

    public Brick(Bitmap bitmap, int x, int y, int col,int currentHealth, int pointsToGive){

        super(currentHealth,pointsToGive,bitmap, x, y, col);
        //this.gem = new ChangePaddleSizeGem( bitmap,  x,  y,  col, currentHealth,  pointsToGive);
    }

    public void SetGem(Gem g){
        this.gem = g;
    }

    public Gem GetGem(){
        return this.gem;
    }

    public void update(){
        super.update();
        if(!this.IsAlive() && this.gem!=null){
            //bring the gem to life
            this.gem.SetAlive(true);
        }
    }




}
