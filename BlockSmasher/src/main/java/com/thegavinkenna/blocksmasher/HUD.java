package com.thegavinkenna.blocksmasher;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Gavin on 29/06/13.
 */
public class HUD extends Entity {

    private Paddle paddle;

    public HUD(Bitmap life, int x, int y, Paddle paddle){
        super(life,  x,  y);
        this.paddle = paddle;
        this.setColour(Color.RED);

    }

    public void update(){

    }


    public void draw(Canvas canvas){
        //super.draw(canvas);

        //Draw how many hearts for lives
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(2);
        paint.setTextSize(30);
        canvas.drawText(String.valueOf("LIVES LEFT::"),0,getY()-30,paint );

        for(int i = 0; i < this.paddle.GetLivesLeft() ;i++){
            canvas.drawBitmap(getBitmap(),10+(getX()+(getBitmap().getWidth())*i+5) - (getBitmap().getWidth() / 2), getY() - (getBitmap().getHeight()/2),getPaint());
        }

        //Draw score
      ///  paint = new Paint();
      //  paint.setColor(Color.YELLOW);
     //   paint.setStyle(Paint.Style.FILL_AND_STROKE);
      //  paint.setStrokeWidth(2);
      //  paint.setStrokeMiter(10);
      //  paint.setTextSize(30);
        canvas.drawText(String.valueOf("SCORE::"),getX()+400,getY()-30,paint );
        canvas.drawText(String.valueOf(paddle.GetScore()),getX()+400,getY()-5,paint);

    }

}
