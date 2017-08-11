package com.example.a1.digitsecretword;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.imagepipeline.request.BasePostprocessor;

/**
 * Created by 1 on 2017/6/13.
 */

public class GreyProcessor extends BasePostprocessor {

    private CacheKey mKey;
    public GreyProcessor(@Nullable Uri uri) {
        if (uri != null) {
            mKey = new SimpleCacheKey(uri.toString());
        }
    }

    @Override
    public String getName() {
        return "grayProcessor";
    }

    //然后，@override process()方法。  【getName()和getPostprocessorCacheKey()等方法不是必须的。】
// 其中的 @Override getName()、process()和getPostprocessorCacheKey()等方法都会在执行 GrayProcessor时，由Fresco自动调用。
    @Override
    public void process(Bitmap destBitmap, Bitmap sourceBitmap) { //这里是对原图修改成粉色度图。
        ColorMatrix matrix = new ColorMatrix();//亮度矩阵
        matrix.setSaturation(0);
//        matrix.setRotate(0, 40);//红
//        matrix.setRotate(1, 20);//绿
//        matrix.setRotate(2, 30);//蓝
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        Paint paint = new Paint();//画刷 可改变颜色 对比度等属性
        paint.setColorFilter(filter);
        Canvas canvas = new Canvas(destBitmap); //给出需要被画的画布区域
        canvas.drawBitmap(sourceBitmap, 0, 0, paint);//把图画上去
//设置饱和度 0表示灰度图像 大于1饱和度增加 0-1饱和度减小
    }

    @Override
    public CacheKey getPostprocessorCacheKey() { //把处理后的图片进行缓存【不是必须步骤】
        return mKey;
    }
}
