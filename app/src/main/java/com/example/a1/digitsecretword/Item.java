package com.example.a1.digitsecretword;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;


public class Item {

    public int x,y;

    private Bitmap bitmap;
    private int rotate_angle;
    private int rotate_speed;
    private int drop_speed;

    public Item(Bitmap bitmap) {
        this.bitmap = bitmap;
        Random random = new Random();
        rotate_speed = random.nextInt(16) - 8;
        drop_speed = random.nextInt(8) + 16;
    }

    public void onDraw(Canvas canvas,Paint paint) {
        rotate_angle += rotate_speed;
        y += drop_speed;
        canvas.save();
        canvas.rotate(rotate_angle, x, y);
        canvas.drawBitmap(bitmap, x - bitmap.getWidth() / 2, y - bitmap.getHeight() / 2, paint);
        canvas.restore();
    }
}
