package com.thegavinkenna.blocksmasher;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by Gavin on 24/06/13.
 */
public class Paddle extends Entity {

    private int score;
    private boolean touched; //Is the paddle being touched?


    public Paddle(Bitmap bitmap, int x, int y){
        super( bitmap,  x,  y);

        this.score = 0;
        this.touched = false;
    }

    public Paddle(Bitmap bitmap, int x, int y, int col){
        super( bitmap,  x,  y, col);

        this.score = 0;
        this.touched = false;
    }

    public boolean IsTouched(){
        return touched;
    }

    public void SetTouched(boolean t){
        touched = t;
    }

    public void update(){
        //Update logic here
    }


    public void HandleActionDown(int eventX, int eventY)
    {
        if (eventX >= (this.getX() - this.getBitmap().getWidth() / 2) && (eventX <= (this.getX() + this.getBitmap().getWidth()/2)))
        {
            if (eventY >= (this.getY() - this.getBitmap().getHeight() / 2) && (this.getY() <= (this.getY() + this.getBitmap().getHeight() / 2)))
            {
                //its touched
                SetTouched(true);
            }
            else
            {
                SetTouched(false);
            }
        }
        else{
            SetTouched(false);
        }
    }
}
