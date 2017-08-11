package com.example.a1.digitsecretword;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by 1 on 2017/7/13.
 */

public class CoverBaloon {
    int droped_y;
    int left;
    int y;
    Bitmap bitmap;

    CoverBaloon(int droped_y,int left, Bitmap bitmap){
        this.left = left;
        this.droped_y = droped_y;
        this.bitmap = bitmap;
         y = - bitmap.getHeight();

    }

    public void drawBallon(Canvas canvas, Paint mPaint){
        if(y <= 0){
            canvas.drawBitmap(bitmap,left,y + droped_y,mPaint);
        }
    }
}
