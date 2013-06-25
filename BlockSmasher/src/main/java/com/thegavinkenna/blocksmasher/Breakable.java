package com.thegavinkenna.blocksmasher;

/**
 * Created by Gavin on 24/06/13.
 */


import android.graphics.Bitmap;

/**
 * This class will be used for objects that can be destroyed by the ball, e.g., Bricks, Gems, Powerups, etc.
 */

public  class Breakable extends Entity{

    private int currentHealth; //How many times the entity will need to be hit to die. When it hits 0, its dead.
    private int pointsToGive; //How many points to give the player when dead.
    private boolean alive = true;

    public Breakable(int currentHealth, int pointsToGive, Bitmap bitmap, int x, int y){
        super(bitmap, x, y);

        this.currentHealth = currentHealth;
        this.pointsToGive = pointsToGive;
    }

    public Breakable(int currentHealth, int pointsToGive, Bitmap bitmap, int x, int y, int col){
        super(bitmap, x, y, col);

        this.currentHealth = currentHealth;
        this.pointsToGive = pointsToGive;
    }

    public  void update(){
        if(currentHealth <= 0){
            alive = false;
        }
    }

    public boolean IsAlive(){
        return alive;
    }

    public int Points(){
        return pointsToGive;
    }

    public void Hit(){
         this.currentHealth--;
    }


}
