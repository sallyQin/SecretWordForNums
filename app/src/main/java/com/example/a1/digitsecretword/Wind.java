package com.example.a1.digitsecretword;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;


public class Wind {

    int angle;
    Bitmap bitmap;
    private int rotate_speed;
    Random random = new Random();
    int x,y;

    public Wind(int angle,int x,int y,Bitmap bitmap){
        this.angle = angle;
        this.x = x;
        this.bitmap = bitmap;
        rotate_speed = random.nextInt(14) - 8;
        this.y = y;
      //  drop_speed = random.nextInt(8) + 16;

    }

     public void onDraw(Canvas canvas, Paint paint) {
        angle += rotate_speed;
         y = y + rotate_speed;
        canvas.save();
        canvas.rotate(angle, x+ bitmap.getWidth() / 2, y +bitmap.getHeight()/ 2);
        canvas.drawBitmap(bitmap, x - bitmap.getWidth() / 2, y, paint);
        canvas.restore();
    }

}
