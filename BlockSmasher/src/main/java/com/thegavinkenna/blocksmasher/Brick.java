package com.thegavinkenna.blocksmasher;

import android.graphics.Bitmap;

/**
 * Created by Gavin on 25/06/13.
 */
public class Brick extends Breakable {

    public Brick(Bitmap bitmap, int x, int y, int col,int currentHealth, int pointsToGive){

        super(currentHealth,pointsToGive,bitmap, x, y, col);

    }

   // public void update(){}




}
