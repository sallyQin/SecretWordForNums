package com.example.a1.digitsecretword;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2017/7/13.
 */

public class CoverView extends View implements Runnable{
    Paint mPaint;
    private static  final SparseArray<SoftReference<Bitmap>> CACHE = new SparseArray<>();
    private Bitmap bitmap_blue,bitmap_purple,bitmap_pink,bitmap_yellow,bitmap_star;
    int inSampleSize = 1;
    List<CoverBaloon> list = new ArrayList<>();
    public CoverView(Context context) {
        super(context);
        init();
    }

    public CoverView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CoverView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(list!=null&& list.size()>0 ){
            for(int i = 0;i< list.size();i++){
                list.get(i).drawBallon(canvas,mPaint);
            }
        }

    }


    /**decode图片**/
    public  Bitmap decode(View view, @DrawableRes int drawableRes) {
        SoftReference<Bitmap> reference = CACHE.get(drawableRes);
        if (reference != null) {
            Bitmap bitmap = reference.get();
            if (bitmap != null) {
                return bitmap;
            }
        }

        int reqWidth = view.getWidth();
        int reqHeight = view.getHeight();

        // First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;// *注：只有图片在res\下的drawable需要设置false。（因为此drawable下有hdpi,xhdpi等，会自动进行缩放，那么不同手机可能就不匹配了。）
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(view.getResources(),drawableRes,options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), drawableRes,options);

        CACHE.put(drawableRes,new SoftReference<>(bitmap));
        return bitmap;
    }

    /**decode图片**/
    public  Bitmap decodeOthers(View view, @DrawableRes int drawableRes) {
        SoftReference<Bitmap> reference = CACHE.get(drawableRes);
        if (reference != null) {
            Bitmap bitmap = reference.get();
            if (bitmap != null) {
                return bitmap;
            }
        }

        int reqWidth = view.getWidth();
        int reqHeight = view.getHeight();

        // First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;// *注：只有图片在res\下的drawable需要设置false。（因为此drawable下有hdpi,xhdpi等，会自动进行缩放，那么不同手机可能就不匹配了。）
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(view.getResources(),drawableRes,options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), drawableRes,options);

        CACHE.put(drawableRes,new SoftReference<>(bitmap));
        return bitmap;
    }
    /**计算缩放比例方法（android文档上提供的）**/
    public int calculateInSampleSize(BitmapFactory.Options op, int reqWidth,
                                     int reqHeight) {
        int originalWidth = op.outWidth;
        int originalHeight = op.outHeight;

        inSampleSize = 1;
        if (originalWidth > reqWidth/4|| originalHeight > reqHeight ){
            int halfWidth = originalWidth;
            int halfHeight = originalHeight;
            while ((halfWidth / inSampleSize > reqWidth/4)
                    ||(halfHeight / inSampleSize > reqHeight)) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**初始化画笔**/
    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);  // 反锯齿
        mPaint.setTypeface(Typeface.SERIF);         // 衬线字体
    }

    @Override
    public void run() {
        if(bitmap_yellow== null) {

            bitmap_pink = decode(this, R.drawable.baloon_pink_bg);

            post(new Runnable() {   //left_margin distance
                @Override
                public void run() {

                    int left_coverView_modified = (getWidth() -(bitmap_pink.getWidth()*4-120))/2;
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
                    layoutParams.setMargins(left_coverView_modified,0,0,0);//setting left_margin distance,4个参数按顺序分别是左上右下
                    setLayoutParams(layoutParams);
                }
            });

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            options.inSampleSize = inSampleSize;
            bitmap_blue = BitmapFactory.decodeResource(getResources(), R.drawable.baloon_blue_bg, options);
            bitmap_purple = BitmapFactory.decodeResource(getResources(), R.drawable.baloon_purple_bg, options);
            bitmap_yellow =BitmapFactory.decodeResource(getResources(), R.drawable.baloon_yellow_bg, options);
            list.add(new CoverBaloon(bitmap_blue.getHeight()/40,0,bitmap_blue));
            list.add(new CoverBaloon(bitmap_purple.getHeight()/40,bitmap_blue.getWidth()-40,bitmap_purple));
            list.add(new CoverBaloon(bitmap_pink.getHeight()/40,bitmap_purple.getWidth()+bitmap_blue.getWidth()-80,bitmap_pink));
            list.add(new CoverBaloon(bitmap_yellow.getHeight()/40,bitmap_pink.getWidth()+bitmap_purple.getWidth()+bitmap_blue.getWidth()-120,bitmap_yellow));

            int drop0_Y = list.get(0).droped_y;
            int drop1_Y = list.get(1).droped_y;
            int drop2_Y = list.get(2).droped_y;
            int drop3_Y = list.get(3).droped_y;

            for(int i =0; i<41;i++){
                list.get(0).droped_y =  drop0_Y * i;

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postInvalidate();
            }


            for(int i =0; i<41;i++){
                list.get(1).droped_y = drop1_Y * i;

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postInvalidate();
            }

            for(int i =0; i<41;i++){
                list.get(2).droped_y =  drop2_Y * i;

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postInvalidate();
            }

            for(int i =0; i<41;i++){
                list.get(3).droped_y =  drop3_Y * i;

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postInvalidate();
            }

            ((MainActivity)getContext()).enabledButton();

        }
    }
    public void startCoverBaloonThread(){
        Thread thread_s = new Thread(this);
        thread_s.start();

    }

}
