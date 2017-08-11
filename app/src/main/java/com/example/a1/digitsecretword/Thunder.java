package com.example.a1.digitsecretword;

import android.graphics.Bitmap;


public class Thunder {
    Bitmap bitmap;
    int x,y;
    public Thunder(Bitmap bitmap){
        this.bitmap = bitmap;
        x = bitmap.getWidth();
        y = bitmap.getHeight();
    }
}
