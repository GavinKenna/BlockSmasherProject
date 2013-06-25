package com.thegavinkenna.blocksmasher;

import android.graphics.Bitmap;

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
    }

    public Gem(Bitmap bitmap, int x, int y, int col,int currentHealth, int pointsToGive){

        super(1,pointsToGive,bitmap, x, y, col);
        this.timeLasts = -1;
        this.captured = false;
        this.startTime = -1;
        this.SetAlive(false);
    }

    public boolean IsAFallingGem(){
        return this.fallingGem;
    }



    public void SetFalling(boolean f){
        this.fallingGem = f;
    }

    public void update(){
        if(captured){
            Captured();
        }else if(fallingGem == true && IsAlive()){
            this.setY(this.getY()+(int)velocity); //if the gem is meant to fall, let it fall with velocity. MAKE IT RAIN GEMS!
        }else{
            Bobble(); //Gonna write this method now. Bobble, make it slightly hover, give it life I guess.
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


    public abstract void TimeUp(); //After time has expired

}
