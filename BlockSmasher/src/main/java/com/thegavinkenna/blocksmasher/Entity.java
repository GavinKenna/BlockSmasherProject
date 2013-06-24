package com.thegavinkenna.blocksmasher;

/**
 * Created by Gavin on 24/06/13.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;

/**
 * This class is for objects that will be shown on screen, e.g., ball, brick, paddle, etc.
 */

public abstract class Entity {

    private Bitmap bitmap;
    private int x,y; //Where the entity is on the screen
    private Paint paint;

    public Entity(Bitmap bitmap, int x, int y){
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        paint = new Paint(Color.WHITE);
    }

    public Entity(Bitmap bitmap, int x, int y, int col){
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.paint = new Paint(col);
        ColorFilter filter = new LightingColorFilter(col, 1);
        paint.setColorFilter(filter);
    }

    public Bitmap getBitmap(){
        return this.bitmap;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
    
    public void setBitmap(Bitmap bit){
        this.bitmap = bit;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }

    public void setColour(int col){
        paint = new Paint(col);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap,x - (bitmap.getWidth() / 2), y - (bitmap.getHeight()/2),paint);
    }

    public Paint getPaint()
    {
        return paint;
    }

    public abstract void update();
    
    
}
