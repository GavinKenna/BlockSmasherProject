package com.thegavinkenna.blocksmasher;

import android.gesture.GestureUtils;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Gavin on 24/06/13.
 */
public class Paddle extends Entity {

    private int score; //Players score
    private int livesLeft = 3;

    private boolean touched; //Is the paddle being touched?
    private boolean large; //Is it large?
    private boolean small;//Is it small?

    private Bitmap bitSmall,bitNormal,bitLarge; // Different bitmaps

    public Paddle(Bitmap bN, Bitmap bS, Bitmap bL, int x, int y){
        super(bN,  x,  y);
        this.bitLarge = bL;
        this.bitNormal = bN;
        this.bitSmall = bS;
        this.score = 0;
        this.touched = false;
    }

    public Paddle(Bitmap bN, Bitmap bS, Bitmap bL, int x, int y, int col){
        super(bN,  x,  y);
        this.bitLarge = bL;
        this.bitNormal = bN;
        this.bitSmall = bS;
        this.setColour(col);
        this.score = 0;
        this.touched = false;
    }

    public int GetLivesLeft(){
        return this.livesLeft;
    }

    public void RemoveLife(){
        this.livesLeft--;
    }

    public int GetScore(){
        return this.score;
    }

    public void IncreaseScore(int s){
        this.score+=s;
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

    public boolean IsLarge(){
        return large;
    }

    public void SetLarge(boolean l){
        this.large = l;
        this.small = !l;
        this.setBitmap(bitLarge);
    }

    public boolean IsSmall(){
        return small;
    }

    public void SetSmall(boolean s){
        this.large = !s;
        this.small = s;
        this.setBitmap(bitSmall);
    }

    public boolean IsNormalSize(){
        return !(large||small);
    }

    public void SetNormal(){
        small = false;
        large = false;
        this.setBitmap(bitNormal);
    }

    public void draw(Canvas canvas){

        super.draw(canvas);
        //canvas.drawBitmap(bitmap,x - (bitmap.getWidth() / 2), y - (bitmap.getHeight()/2),paint);
       /* Rect r2 = new Rect(this.getX() -(this.getBitmap().getWidth()/2) , this.getY() -  (this.getBitmap().getHeight()/2) , this.getX() + (this.getBitmap().getWidth()/2),this.getY()+(this.getBitmap().getHeight())/2);
        Paint p = new Paint();
        p.setColor(Color.RED);
        canvas.drawRect(r2, p);*/ //For testing purposes

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
