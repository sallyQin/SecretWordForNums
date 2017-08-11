package com.example.a1.digitsecretword;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by 1 on 2017/8/5.
 */

public class Dolphin {
    Bitmap bitmap;
    int left,right,left_increased,right_increased;
    public Dolphin(Bitmap bitmap, int left,int right,int left_increased,int right_increased){
        this.bitmap = bitmap;
        this.left = left;
        this.right = right;
        this.left_increased = left_increased;
        this.right_increased = right_increased;
    }

    public void onDraw(Canvas canvas, Paint mPaint){
        canvas.drawBitmap(bitmap,left+left_increased,right+right_increased,mPaint);
    }
}
