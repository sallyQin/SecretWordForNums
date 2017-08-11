package com.example.a1.digitsecretword;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by 1 on 2017/7/31.
 */

public class Dandelion  {
    float left, top;
    Bitmap bitmap;

    public Dandelion(Bitmap bitmap,float left,float top){
        this.left = left;
        this.top = top;
        this.bitmap = bitmap;

    }
   public void onDraw(Canvas canvas,Paint mPaint,float leftSpeed){
       left =  left + leftSpeed;
       top =  top - leftSpeed/2;
       canvas.drawBitmap(bitmap,left,top,mPaint);
   }

}
