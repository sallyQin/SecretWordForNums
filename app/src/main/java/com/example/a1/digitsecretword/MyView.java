package com.example.a1.digitsecretword;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class MyView extends View {
    List<Wind> wind_list = new ArrayList<>();
    List<Thunder> thunder_list = new ArrayList<>();
    List <Dandelion> dandelion_list = new ArrayList<>();//蒲公英1
    List <Dandelion1> dandelion_list1 = new ArrayList<>();//蒲公英2
    List <Dolphin> dolphin_list = new ArrayList<>();//海豚
    float increasedSpeed_dandelion;//蒲公英飞的速度
    private static final Map<String, SoftReference<Bitmap>> CACHE = new ArrayMap<>();
    List<Item> items = new ArrayList<>();
    List<Item> items_hearts = new ArrayList<>();
    Thread last_520Thread,cloudsThread,thread_wind,thunder_thread = null;//520线程,//云线程,//秋风线程,//雷雨线程
    Thread thread_dandelion,thread_seaDolphin = null;
    int res_drawableAlphabet;
    int count_selected;
    Paint mPaint;
    static List<Integer> orderLists = new ArrayList<>();
    boolean resultShowed;//check结果是否在显示状态
    Bitmap bitmap1,bitmap2,bitmap3,bitmap4,bitmap5,bitmap6,bitmap7, bitmap8, bitmap9, bitmap10, bitmap11, bitmap12, bitmap13, bitmap14, bitmap15, bitmap16;//心形
    Bitmap bitmap_rose1,bitmap_rose2,bitmap_rose3,bitmap_rose4,bitmap_rose5,bitmap_rose6,bitmap_rose7, bitmap_rose8, bitmap_rose9, bitmap_rose10, bitmap_rose11, bitmap_rose12, bitmap_rose13, bitmap_rose14, bitmap_rose15, bitmap_rose16;//rose花
    Bitmap bitmap_baloon;//气球for520
    Bitmap bitmap_bird;//小鸟for520
    Bitmap bitmap_exploredBaloon;//爆炸for520
    Bitmap bitmap_love;//字幕for520
    Bitmap bitmap_clouds,bitmap_blueClouds,bitmap_yellowClouds,bitmap_yellow40Clouds,bitmap_yellow60Clouds,bitmap_gray20Clouds,bitmap_gray30Clouds,bitmap_gray40Clouds,bitmap_blackClouds;//云
    Bitmap bitmap_thunder1,bitmap_thunder2;//雷
    Bitmap bitmap_leaf1,bitmap_leaf2,bitmap_leaf3,bitmap_leaf4,bitmap_leaf5,bitmap_leaf6,bitmap_leaf7;//秋叶
    int descendDegree1,descendDegree2,descendDegree3,descendDegree4,descendDegree5,descendDegree6,descendDegree7,
            descendDegree8,descendDegree9,descendDegree10,descendDegree11,descendDegree12,descendDegree13,descendDegree14,descendDegree15,descendDegree16 = -10; //rose下降幅度
    float rotate_px = 0;
    float rotate_py  = 0;
    int ascendDegreeForBaloon = 0;//520气球上升幅度
    float bird_flyAngle = -0.15f;//小鸟飞的角度
    int bird_x_descend = 0;//小鸟下降的x值
    float bird_y_descend = 0;//小鸟下降的y值
    float scaleX_explored;//X方向缩放比例（爆烟花）
    float scaleY_explored;//Y方向缩放比例（爆烟花）
    boolean isFirstShow_baloon;//第一次显示爆炸烟花
    int cloudsIndex = -1;
    private List<Clouds> clouds;
    Bitmap bitmap_dandelionBg,bitmap_dandelion,bitmap_dandelion1,bitmap_dandelion2;//蒲公英图Dandelion
    Bitmap bitmap_sea,bitmap_dolphin,bitmap_dolphin_scaled;//海,海豚
    boolean isOverMiddle = false;//海豚是否跳到最中间
    int mIncreasedLeft,mIncreasedRight = 0;
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();

        int center_Height;//居中高度
        int left_margin_width;//每个宽度
        if(!resultShowed){ //显示气球对应的数字的情况
            if(orderLists.size() == 1){
                Bitmap bitmap = decode(this, Baloon.getBaloon().get(orderLists.get(0)).res_drawableAlpha);
                left_margin_width =(getWidth()-bitmap.getWidth())/2;
                center_Height = (getHeight()-bitmap.getHeight())/2;
                canvas.drawBitmap(bitmap,left_margin_width,center_Height,mPaint);
            }else if(orderLists.size() == 2){
                Bitmap bitmap = decode(this, Baloon.getBaloon().get(orderLists.get(0)).res_drawableAlpha);
                left_margin_width = (getWidth()/2-bitmap.getWidth())/2;
                center_Height = (getHeight()-bitmap.getHeight())/2;
                canvas.drawBitmap(bitmap,left_margin_width,center_Height,mPaint);
                bitmap = decode(this, Baloon.getBaloon().get(orderLists.get(1)).res_drawableAlpha);
                canvas.drawBitmap(bitmap,getWidth()/2 + left_margin_width,center_Height,mPaint);
            }else if(orderLists.size() == 3){
                Bitmap bitmap = decode(this, Baloon.getBaloon().get(orderLists.get(0)).res_drawableAlpha);
                left_margin_width = (getWidth()/3-bitmap.getWidth())/2;
                center_Height = (getHeight()-bitmap.getHeight())/2;
                canvas.drawBitmap(bitmap,left_margin_width,center_Height,mPaint);
                bitmap = decode(this, Baloon.getBaloon().get(orderLists.get(1)).res_drawableAlpha);
                canvas.drawBitmap(bitmap,getWidth()/3 +left_margin_width,center_Height,mPaint);
                bitmap = decode(this, Baloon.getBaloon().get(orderLists.get(2)).res_drawableAlpha);
                canvas.drawBitmap(bitmap,getWidth()/3*2 +left_margin_width,center_Height,mPaint);

            }else if(orderLists.size() == 4){
                Bitmap bitmap = decode(this, Baloon.getBaloon().get(orderLists.get(0)).res_drawableAlpha);
                left_margin_width = (getWidth()/4-bitmap.getWidth())/2;
                center_Height = (getHeight()-bitmap.getHeight())/2;
                canvas.drawBitmap(bitmap,left_margin_width,center_Height,mPaint);
                bitmap = decode(this, Baloon.getBaloon().get(orderLists.get(1)).res_drawableAlpha);
                canvas.drawBitmap(bitmap,getWidth()/4 +left_margin_width,center_Height,mPaint);
                bitmap = decode(this, Baloon.getBaloon().get(orderLists.get(2)).res_drawableAlpha);
                canvas.drawBitmap(bitmap,getWidth()/4*2 + left_margin_width,center_Height,mPaint);
                bitmap = decode(this, Baloon.getBaloon().get(orderLists.get(3)).res_drawableAlpha);
                canvas.drawBitmap(bitmap,getWidth()/4*3 + left_margin_width,center_Height,mPaint);
            }
        }else {//显示结果（密语）的情况
            mPaint.setTextSize(70);
            mPaint.setFakeBoldText(true);//粗体
            if (((SecondActivity) getContext()).getNum_means_str() != null) { //设置密语--字颜色
                if(((SecondActivity)getContext()).status_color_str().equals("red")){ //密语颜色为"红"时，
                    Paint mPaintPen = new Paint(Paint.ANTI_ALIAS_FLAG);
                    mPaintPen.setTypeface(Typeface.SERIF);

                    /**字为“520”时，**/
                    if(((SecondActivity)getContext()).getNum_str().equals("520")) {
                        if (bitmap_baloon != null) {//“520” 画上升气球
                            canvas.drawBitmap(bitmap_baloon, (getWidth() - bitmap_baloon.getWidth()) / 2, getHeight() - ascendDegreeForBaloon, mPaintPen);//气球初始位置

                        }
                        if (bitmap_exploredBaloon != null) {//画“爆炸烟花”图
                            if(isFirstShow_baloon ){
                                canvas.drawBitmap(bitmap_exploredBaloon, (getWidth() - bitmap_exploredBaloon.getWidth()) / 2, (getHeight() - bitmap_exploredBaloon.getHeight()), mPaintPen);//爆炸烟花图初始位置
                            }else{
                                canvas.save();
                                canvas.scale(scaleX_explored,scaleY_explored,bitmap_exploredBaloon.getWidth()/2+(getWidth()-bitmap_exploredBaloon.getWidth())/2,bitmap_exploredBaloon.getHeight()/2+(getHeight()-bitmap_exploredBaloon.getHeight())/2);
                                canvas.drawBitmap(bitmap_exploredBaloon, (getWidth() - bitmap_exploredBaloon.getWidth()) / 2, (getHeight() - bitmap_exploredBaloon.getHeight())/2, mPaintPen);//爆炸烟花图初始位置
                                canvas.restore();}

                        } else if (bitmap_love != null) { //画“520字”图
                            canvas.drawBitmap(bitmap_love, (getWidth() - bitmap_love.getWidth()) / 2, (getHeight() - bitmap_love.getHeight()) / 2, mPaintPen);//“520字”图初始位置
                        }
                        if (bitmap_bird != null) { //“520” 画小鸟//
                            canvas.save();
                            canvas.rotate(bird_flyAngle, rotate_px, rotate_py);
                            canvas.drawBitmap(bitmap_bird, bird_x_descend - bitmap_bird.getWidth() / 2, bird_y_descend - bitmap_bird.getHeight() / 2, mPaintPen);//小鸟初始位置
                            canvas.restore();
                        }
                    }else{
                        /**“red时” 画爱心雨（16种样式）**/
                        mPaint.setColor(0xffCD0000);
                        float secretWordWidth = mPaint.measureText(((SecondActivity) getContext()).getNum_means_str());//测量字符串的长度
                        canvas.drawText(((SecondActivity) getContext()).getNum_means_str(),(getWidth()-secretWordWidth)/2,getHeight()/2, mPaint);//居中的位置

                        if (bitmap16 != null) {
                            for(int i= 0;i<items_hearts.size();i++){
                                items_hearts.get(i).onDraw(canvas,mPaintPen);
                            }
                        }
                    }

                }else if(((SecondActivity)getContext()).status_color_str().equals("yellow")){ //密语颜色为"黄色"时，
                    mPaint.setColor(0xffCDAD00);
                    float secretWordWidth = mPaint.measureText(((SecondActivity) getContext()).getNum_means_str());//测量字符串的长度
                    canvas.drawText(((SecondActivity) getContext()).getNum_means_str(),(getWidth()-secretWordWidth)/2,getHeight()/2, mPaint);//居中的位置
                    if(bitmap_dandelionBg != null){
                        canvas.drawBitmap(bitmap_dandelionBg,0,getHeight()-bitmap_dandelionBg.getHeight(),mPaint);//画黄色蒲公英底图
                        dandelion_list.get(0).onDraw(canvas,mPaint,increasedSpeed_dandelion);
                        dandelion_list.get(1).onDraw(canvas,mPaint,increasedSpeed_dandelion);
                        dandelion_list1.get(0).onDraw(canvas,mPaint,increasedSpeed_dandelion);
                        dandelion_list1.get(1).onDraw(canvas,mPaint,increasedSpeed_dandelion);
                    }

                }else if(((SecondActivity)getContext()).status_color_str().equals("black")){ //密语颜色为"黑"时，
                    mPaint.setColor(0xff000000);
                    if (clouds != null && clouds.size()!=0  && cloudsIndex >= 0) {

                        if(clouds.get(cloudsIndex).colorDegree == cloudsIndex){  //画“云”
                            int bitmap_desWidth  = getWidth()/5*3;
                            int bitmap_desHeight = clouds.get(cloudsIndex).bitmap.getHeight() * bitmap_desWidth / clouds.get(cloudsIndex).bitmap.getWidth();
                            Rect rectDes = new Rect();
                            rectDes.left = (getWidth() - bitmap_desWidth)/2;
                            rectDes.right = rectDes.left + bitmap_desWidth;
                            rectDes.top = 0;
                            rectDes.bottom = bitmap_desHeight;
                            canvas.drawBitmap(clouds.get(cloudsIndex).bitmap,null,rectDes,mPaint);
                            if(cloudsIndex>=2){
                                if(wind_list.size()>0) {
                                    for (int i = 0; i < 7; i++) {//画“落叶”
                                        wind_list.get(i).onDraw(canvas, mPaint);
                                    }
                                }
                                   if(cloudsIndex ==2){
                                       setBackgroundResource(R.drawable.yellow_bg);
                                   }else if(cloudsIndex == 5){
                                    setBackgroundResource(R.drawable.startb_blackbg);
                                }
                                if(cloudsIndex == 5){//画“打雷”
                                    canvas.drawBitmap(thunder_list.get(0).bitmap,rectDes.left-clouds.get(cloudsIndex).bitmap.getWidth()/3,rectDes.bottom,mPaint);
                                    canvas.drawBitmap(thunder_list.get(1).bitmap,rectDes.left+clouds.get(cloudsIndex).bitmap.getWidth()/6,rectDes.bottom,mPaint);
                                }

                            }
                            if(cloudsIndex == 8){
                                setBackgroundResource(R.drawable.black_bg);
                                float secretWordWidth = mPaint.measureText(((SecondActivity) getContext()).getNum_means_str());//测量字符串的长度
                                canvas.drawText(((SecondActivity) getContext()).getNum_means_str(),(getWidth()-secretWordWidth)/2,getHeight()/2, mPaint);//居中的位置
                            }

                        }
                    }

                }else if(((SecondActivity)getContext()).status_color_str().equals("white")){ //密语颜色为"白"时，>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                    mPaint.setColor(0xff3A5FCD);
                    float secretWordWidth = mPaint.measureText(((SecondActivity) getContext()).getNum_means_str());//测量字符串的长度
                    canvas.drawText(((SecondActivity) getContext()).getNum_means_str(),(getWidth()-secretWordWidth)/2,getHeight()/2, mPaint);//居中的位置
                    if(dolphin_list.size()> 0){
                        canvas.drawBitmap(bitmap_sea,0,getHeight()-bitmap_sea.getHeight(),mPaint);
                        dolphin_list.get(0).onDraw(canvas,mPaint);
                        //   canvas.drawBitmap(bitmap_dolphin,dolphin_list.get(0).left,dolphin_list.get(0).right,mPaint);
                    }
                }


            }else{//要换行显示，所有使用TextPaint
                TextPaint textPaint = new TextPaint();
                textPaint.setAntiAlias(true);
                textPaint.setTextSize(45.0F);
                textPaint.setColor(0xffCAE1FF);
                String defaultSecretWord = "哦！尴尬了。显然是一句“无内涵”密语.";
                StaticLayout layout = new StaticLayout(defaultSecretWord,textPaint,getWidth()/2, Layout.Alignment.ALIGN_NORMAL,1.0f,0.0f,true);
                //,textPaint(TextPaint 类型)设置了字符串格式及属性 的画笔,240为设置 画多宽后 换行，后面的参数是对齐方式...
                canvas.translate(getWidth()/4,getHeight() /5*2);//居中的位置开始绘制
                layout.draw(canvas);
            }
        }

        for (Item item : items) {
            item.onDraw(canvas, mPaint);
        }
    }

    public  Bitmap decode(View view, @DrawableRes int drawableRes) {
        String key = drawableRes + "_" + count_selected;
        SoftReference<Bitmap> reference = CACHE.get(key);
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
        if(count_selected!=0){
            options.inSampleSize = calculateInSampleSize(options, reqWidth/count_selected, reqHeight/count_selected);
        }else{
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        }


        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), drawableRes,options);

        CACHE.put(key, new SoftReference<>(bitmap));
        return bitmap;
    }

    /**计算缩放比例方法（android文档上提供的）**/
    public int calculateInSampleSize(BitmapFactory.Options op, int reqWidth,
                                     int reqHeight) {
        int originalWidth = op.outWidth;
        int originalHeight = op.outHeight;
        int inSampleSize = 1;

        if (originalWidth > reqWidth || originalHeight > reqHeight) {
            int halfWidth = originalWidth;
            int halfHeight = originalHeight;
            while ((halfWidth / inSampleSize > reqWidth)
                    &&(halfHeight / inSampleSize > reqHeight)) {
                inSampleSize *= 2;

            }

        }

        return inSampleSize;
    }

    /**设置字母图资源**/
    public void setDataRes(int res_drawableAlphabet){
        this.res_drawableAlphabet = res_drawableAlphabet;
    }

    /**已选个数**/
    public void setCount(int count_selected){
        this.count_selected = count_selected;
    }

    /**初始画笔**/
    public void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);  // 反锯齿
        mPaint.setTypeface(Typeface.SERIF);         // 衬线字体
    }


    /**记录点中的所有数字和顺序**/
    List<Integer> setOrderList(Integer orderPosition){

        orderLists.add(orderPosition);
        return  orderLists;
    }

    public  List<Integer> getOrderLists() {
        return orderLists;
    }

    /** 画redHeart **/
    public void updateRedHeart(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.purple_light_c,null);
                bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.purple_light_pink_c,null);
                bitmap3 = BitmapFactory.decodeResource(getResources(),R.drawable.red_light_c,null);
                bitmap4 = BitmapFactory.decodeResource(getResources(),R.drawable.purple_b,null);
                bitmap5 = BitmapFactory.decodeResource(getResources(),R.drawable.orange_m,null);

                bitmap6 = BitmapFactory.decodeResource(getResources(),R.drawable.purple_light_pink_c,null);
                bitmap7 = BitmapFactory.decodeResource(getResources(),R.drawable.transparent_ball_c,null);
                bitmap8 = BitmapFactory.decodeResource(getResources(),R.drawable.red_light_c,null);
                bitmap9 = BitmapFactory.decodeResource(getResources(),R.drawable.purple_light_c,null);
                bitmap10 = BitmapFactory.decodeResource(getResources(),R.drawable.blue_b,null);

                bitmap11 = BitmapFactory.decodeResource(getResources(),R.drawable.orange_m,null);
                bitmap12 = BitmapFactory.decodeResource(getResources(),R.drawable.purple_light_c,null);
                bitmap13 = BitmapFactory.decodeResource(getResources(),R.drawable.transparent_ball_c,null);
                bitmap14 = BitmapFactory.decodeResource(getResources(),R.drawable.purple_c,null);
                bitmap15 = BitmapFactory.decodeResource(getResources(),R.drawable.blue_c,null);
                bitmap16 = BitmapFactory.decodeResource(getResources(),R.drawable.red_light_c,null);

                Item[] hearts = new Item[16];
                hearts[0]= new Item(bitmap1);
                hearts[1]= new Item(bitmap2);
                hearts[2]= new Item(bitmap3);
                hearts[3]= new Item(bitmap4);
                hearts[4]= new Item(bitmap5);
                hearts[5]= new Item(bitmap6);
                hearts[6]= new Item(bitmap7);
                hearts[7]= new Item(bitmap8);
                hearts[8]= new Item(bitmap9);
                hearts[9]= new Item(bitmap10);
                hearts[10]= new Item(bitmap11);
                hearts[11]= new Item(bitmap12);
                hearts[12]= new Item(bitmap13);
                hearts[13]= new Item(bitmap14);
                hearts[14]= new Item(bitmap15);
                hearts[15]= new Item(bitmap16);

                items_hearts.clear();

                int n = hearts.length;
                int view_width = getWidth();
                int view_height = getHeight();
                int average_width = view_width / (n + 1);
                int start_x = (view_width - average_width * (n - 1)) / 2; //第一颗heart的x位置
                Random random = new Random();
                for (int i = 0; i < n; ++i) {
                    hearts[i].x = start_x + average_width * i;
                    hearts[i].y = -random.nextInt(view_height / 4);
                    items.add(hearts[i]);
                }

                for(int i =0;i< 100;i++ ) {
                    try {
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }


            }
        }).start();
    }

    /** 画上升气球（520数字时） **/
    void updateBaloonFor520(){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                bitmap_love = null;
                bitmap_bird = null;
                bitmap_exploredBaloon = null;
                /** 画上升气球**/
                bitmap_baloon = decode(MyView.this,R.drawable.baloon520);
                for(int i=0;i<MyView.this.getHeight();i++ ){
                    ascendDegreeForBaloon = ascendDegreeForBaloon + 10;//每次上升10
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(ascendDegreeForBaloon>= MyView.this.getHeight()){
                        break;
                    }
                    postInvalidate();
                }

                /** 0.7秒后画上小鸟开飞并琢气球**/
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //小鸟飞行后停下来
                bitmap_bird = BitmapFactory.decodeResource(getResources(),R.drawable.yellow_bird,null);
                rotate_px = bird_x_descend + bitmap_bird.getWidth()/2;
                rotate_py = bird_y_descend + bitmap_bird.getHeight()/2;
                postInvalidate();
                for(int i= 0;i< 150;i++){
                    bird_flyAngle = 0.1f + bird_flyAngle;
                    bird_x_descend = (bitmap_baloon.getWidth()/2 + (getWidth()-bitmap_baloon.getWidth())/2)/150 + bird_x_descend;
                    bird_y_descend =  bitmap_baloon.getHeight()/6f/150 + bird_y_descend ;
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }


                //停止0.7秒后小鸟原地像由倾10°
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for(int i= 0;i< 50;i++){
                    bird_flyAngle = 0.2f + bird_flyAngle;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }

                bitmap_baloon = null;//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>气球消失
                //爆炸烟花显示
                bitmap_exploredBaloon = decode(MyView.this,R.drawable.explore_baloon);
                for(int i=0;i<200;i++){
                    if(i==0){
                        isFirstShow_baloon = true;
                    }else{
                        isFirstShow_baloon = false;
                        scaleX_explored = 1.05f*i;
                        scaleY_explored = 1.05f*i;//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>每次放大1.05倍
                        if(bitmap_exploredBaloon.getWidth()*scaleX_explored>getWidth()|| (bitmap_exploredBaloon.getHeight()*scaleY_explored)>getHeight() ){
                            break;
                        }
                    }

                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    postInvalidate();//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>烟花显示
                }



                /** 气球爆炸后（鸟向右飞走和气球消失，爆炸烟花显示）**/
                for(int i= 0;i< 200;i++){

                    if(bird_flyAngle>=0){
                        bird_flyAngle = bird_flyAngle - 0.05f;
                    }else{
                        bird_flyAngle =0;
                    }
                    bird_x_descend = bird_x_descend + 3;
                    bird_y_descend = bird_y_descend - 3 ;

                    if(bird_x_descend >=getWidth() || bird_y_descend >= getHeight() ) {
                        break;
                    }
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    postInvalidate();

                }
                bitmap_bird = null;
                bitmap_exploredBaloon = null;

                /** 显示“我爱你”图 和 更新背景**/
                bitmap_love = decode(MyView.this,R.drawable.love_logo);
                postInvalidate();
                ((SecondActivity) getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setBackgroundResource(R.drawable.rose_520_bg);
                    }
                });
                /** 显示“散花”图**/
                throwRose();
            }
        };
        last_520Thread = new Thread(runnable);
        last_520Thread.start();
    }


    public void throwRose(){
        if (null == bitmap_rose16) {
            bitmap_rose1 = BitmapFactory.decodeResource(getResources(), R.drawable.rose_red, null);
            bitmap_rose2 = BitmapFactory.decodeResource(getResources(), R.drawable.rose_yellow, null);
            bitmap_rose3 = BitmapFactory.decodeResource(getResources(), R.drawable.rose_white, null);
            bitmap_rose4 = BitmapFactory.decodeResource(getResources(), R.drawable.rose_white, null);
            bitmap_rose5 = BitmapFactory.decodeResource(getResources(), R.drawable.rose_red, null);

            bitmap_rose6 = BitmapFactory.decodeResource(getResources(), R.drawable.rose_red, null);
            bitmap_rose7 = BitmapFactory.decodeResource(getResources(), R.drawable.rose_yellow, null);
            bitmap_rose8 = BitmapFactory.decodeResource(getResources(), R.drawable.rose_red, null);
            bitmap_rose9 = BitmapFactory.decodeResource(getResources(), R.drawable.rose_white, null);
            bitmap_rose10 = BitmapFactory.decodeResource(getResources(), R.drawable.rose_white, null);

            bitmap_rose11 = BitmapFactory.decodeResource(getResources(), R.drawable.rose_white, null);
            bitmap_rose12 = BitmapFactory.decodeResource(getResources(), R.drawable.rose_yellow, null);
            bitmap_rose13 = BitmapFactory.decodeResource(getResources(), R.drawable.rose_red, null);
            bitmap_rose14 = BitmapFactory.decodeResource(getResources(), R.drawable.rose_red, null);
            bitmap_rose15 = BitmapFactory.decodeResource(getResources(), R.drawable.rose_white, null);
            bitmap_rose16 = BitmapFactory.decodeResource(getResources(), R.drawable.rose_yellow, null);
        }
        Item[] flowers = new Item[16];
        flowers[0] = new Item(bitmap_rose1);
        flowers[1] = new Item(bitmap_rose2);
        flowers[2] = new Item(bitmap_rose3);
        flowers[3] = new Item(bitmap_rose4);
        flowers[4] = new Item(bitmap_rose5);
        flowers[5] = new Item(bitmap_rose6);
        flowers[6] = new Item(bitmap_rose7);
        flowers[7] = new Item(bitmap_rose8);
        flowers[8] = new Item(bitmap_rose9);
        flowers[9] = new Item(bitmap_rose10);
        flowers[10] = new Item(bitmap_rose11);
        flowers[11] = new Item(bitmap_rose12);
        flowers[12] = new Item(bitmap_rose13);
        flowers[13] = new Item(bitmap_rose14);
        flowers[14] = new Item(bitmap_rose15);
        flowers[15] = new Item(bitmap_rose16);

        items.clear();
        int n = flowers.length;
        int view_width = getWidth();
        int view_height = getHeight();
        int average_width = view_width / (n + 1);
        int start_x = (view_width - average_width * (n - 1)) / 2; //第一朵rose的x位置
        Random random = new Random();
        for (int i = 0; i < n; ++i) {
            flowers[i].x = start_x + average_width * i;
            flowers[i].y = -random.nextInt(view_height / 4);
            items.add(flowers[i]);
        }

        for(int i =0;i< 200;i++ ) {
            try {
                Thread.sleep(65);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            postInvalidate();
        }
    }

    /**云的变化过程**/
    public void getClouds(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (bitmap_blackClouds == null) {
                    bitmap_clouds = decode(MyView.this, R.drawable.start_clouds);
                    bitmap_blueClouds = decode(MyView.this, R.drawable.blue_clouds);
                    bitmap_yellowClouds = decode(MyView.this, R.drawable.yellow_clouds);
                    bitmap_yellow40Clouds = decode(MyView.this, R.drawable.yellow40_clouds);
                    bitmap_yellow60Clouds = decode(MyView.this, R.drawable.yellow60_clouds);
                    bitmap_gray20Clouds = decode(MyView.this, R.drawable.grey20_clouds);
                    bitmap_gray30Clouds = decode(MyView.this, R.drawable.grey30_clouds);
                    bitmap_gray40Clouds = decode(MyView.this, R.drawable.grey40_clouds);
                    bitmap_blackClouds = decode(MyView.this, R.drawable.black_clouds);
                }
                clouds = createDefaultClouds();
                for(int i = 0;i<9;i++){
                    cloudsIndex = i;
                    postInvalidate();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        return;
                    }
                }


                try {
                    Thread.sleep(2700);
                } catch (InterruptedException e) {
                    return;
                }
                //cloudsIndex = -1;
                postInvalidate();
            }
        };

        cloudsThread = new Thread(runnable);
        cloudsThread.start();
    }


    public List<Clouds> createDefaultClouds(){ //加载“云”资源
        List<Clouds> list_clouds = new ArrayList<>();
        list_clouds.add(new Clouds(0,bitmap_clouds));
        list_clouds.add(new Clouds(1,bitmap_blueClouds));
        list_clouds.add(new Clouds(2,bitmap_yellowClouds));
        list_clouds.add(new Clouds(3,bitmap_yellow40Clouds));
        list_clouds.add(new Clouds(4,bitmap_yellow60Clouds));
        list_clouds.add(new Clouds(5,bitmap_gray20Clouds));
        list_clouds.add(new Clouds(6,bitmap_gray30Clouds));
        list_clouds.add(new Clouds(7,bitmap_gray40Clouds));
        list_clouds.add(new Clouds(8,bitmap_blackClouds));
        return list_clouds;
    }


    /**刮风**/
    public void getWind() {
        Runnable runnable_wind = new Runnable() {
            @Override
            public void run() {
                if (bitmap_leaf7 == null) {
                    bitmap_leaf1 = BitmapFactory.decodeResource(getResources(), R.drawable.leaf_1, null);
                    bitmap_leaf2 = BitmapFactory.decodeResource(getResources(), R.drawable.leaf_2, null);
                    bitmap_leaf3 = BitmapFactory.decodeResource(getResources(), R.drawable.leaf_3, null);
                    bitmap_leaf4 = BitmapFactory.decodeResource(getResources(), R.drawable.leaf_4, null);
                    bitmap_leaf5 = BitmapFactory.decodeResource(getResources(), R.drawable.leaf_5, null);
                    bitmap_leaf6 = BitmapFactory.decodeResource(getResources(), R.drawable.leaf_6, null);
                    bitmap_leaf7 = BitmapFactory.decodeResource(getResources(), R.drawable.leaf_7, null);

                    wind_list.add(new Wind(15, 10,100,bitmap_leaf1));
                    wind_list.add(new Wind(15, bitmap_leaf1.getWidth()+55,5,bitmap_leaf2));
                    wind_list.add(new Wind(15, bitmap_leaf1.getWidth()+bitmap_leaf2.getWidth()+155,0,bitmap_leaf3));
                    wind_list.add(new Wind(15, bitmap_leaf1.getWidth()+bitmap_leaf2.getWidth()+ bitmap_leaf3.getWidth()+50,40, bitmap_leaf4));
                    wind_list.add(new Wind(15, bitmap_leaf1.getWidth()+bitmap_leaf2.getWidth()+ bitmap_leaf3.getWidth()+bitmap_leaf4.getWidth()+185,200,bitmap_leaf5));
                    wind_list.add(new Wind(15, bitmap_leaf1.getWidth()+bitmap_leaf2.getWidth()+ bitmap_leaf3.getWidth()+bitmap_leaf4.getWidth()+bitmap_leaf5.getWidth()+205,0,bitmap_leaf6));
                    wind_list.add(new Wind(15, bitmap_leaf1.getWidth()+bitmap_leaf2.getWidth()+ bitmap_leaf3.getWidth()+bitmap_leaf4.getWidth()+bitmap_leaf5.getWidth()+bitmap_leaf6.getWidth()+230,10, bitmap_leaf7));
                }
                for (int i = 0; i < 200; i++) {
                    wind_list.get(0).y = i * 8;
                    wind_list.get(1).y = i * 8;
                    wind_list.get(2).y = i * 8;
                    wind_list.get(3).y = i * 8;
                    wind_list.get(4).y = i * 8;
                    wind_list.get(5).y = i * 8;
                    wind_list.get(6).y = i * 8;
                    if (wind_list.get(0).y > getHeight() + 50) {
                        break;
                    }
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        return;
                    }
                    postInvalidate();

                }
            }
        };

        thread_wind = new Thread(runnable_wind);
        thread_wind.start();

    }


    /**打雷**/
    public void getThunder() {
    Runnable runnable_thunder = new Runnable() {
        @Override
        public void run() {
            if(bitmap_thunder1 == null){
                bitmap_thunder1 = BitmapFactory.decodeResource(getResources(), R.drawable.thunder, null);
                thunder_list.clear();
                thunder_list.add(new Thunder(bitmap_thunder1));
                thunder_list.add(new Thunder(bitmap_thunder1));
            }
            for(int i = 0 ;i<1;i++){ //打雷
                try {
                    Thread.sleep(45);
                } catch (InterruptedException e) {
                    return;
                }
                postInvalidate();
            }

        }
    };
        thunder_thread = new Thread(runnable_thunder);
        thunder_thread.start();
    }

    public void getYellowBgBitmap() throws InterruptedException { //获得当数字密码为“黄色”时候，屏幕显示蒲公英

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                increasedSpeed_dandelion = 0;
                Bitmap bitmap_yellow_dandelion = decodeDandelion(MyView.this,R.drawable.yellow_dandelion_bg_c);//背景图
                if(bitmap_dandelionBg== null){
                    bitmap_dandelion = BitmapFactory.decodeResource(getResources(),R.drawable.dandelion,null);//蒲公英1
                    bitmap_dandelion1 = BitmapFactory.decodeResource(getResources(),R.drawable.dandelion_stand,null);//蒲公英2
                    bitmap_dandelion2 = BitmapFactory.decodeResource(getResources(),R.drawable.dandelion_right,null);//蒲公英3
                    bitmap_dandelionBg = Bitmap.createScaledBitmap(bitmap_yellow_dandelion,getWidth(),bitmap_yellow_dandelion.getHeight(),true);//蒲公英背景图

                }
                if(bitmap_dandelionBg!=null){
                    dandelion_list.clear();
                    dandelion_list1.clear();
                    dandelion_list.add(new Dandelion(bitmap_dandelion,getWidth()/2.7f,getHeight()-bitmap_dandelionBg.getHeight()/1.5f));
                    dandelion_list.add(new Dandelion(bitmap_dandelion,bitmap_dandelion.getWidth()+getWidth()/2f,getHeight()-bitmap_dandelionBg.getHeight()/1.5f-bitmap_dandelion.getHeight()));
                    dandelion_list1.add(new Dandelion1(bitmap_dandelion1, (int) (bitmap_dandelion.getWidth()+getWidth()/2.3f),getHeight()-bitmap_dandelionBg.getHeight()/2-bitmap_dandelion.getHeight()*2-bitmap_dandelion1.getHeight()));
                    dandelion_list1.add(new Dandelion1(bitmap_dandelion2, (int) (bitmap_dandelion.getWidth()+getWidth()/1.6f),getHeight()-bitmap_dandelionBg.getHeight()/2-bitmap_dandelion.getHeight()*2-bitmap_dandelion1.getHeight()-bitmap_dandelion2.getHeight()));

                }
                for(int i = 0;i<50;i++){
                    increasedSpeed_dandelion = increasedSpeed_dandelion + 1;
                    try {
                        Thread.sleep(55);
                    } catch (InterruptedException e) {
                        return;
                    }
                    postInvalidate();
                }
            }
        };
        thread_dandelion = new Thread(runnable);
        thread_dandelion.start();

    }


    public  Bitmap decodeDandelion(View view, @DrawableRes int drawableRes) {
        String key = drawableRes + "_dandelionBG";
        SoftReference<Bitmap> reference_dandelion = CACHE.get(key);
        if (reference_dandelion != null) {
            Bitmap bitmap = reference_dandelion.get();
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

        CACHE.put(key, new SoftReference<>(bitmap));
        return bitmap;
    }


    public void getWhiteBgBitmap() throws InterruptedException {  //获得当数字密码为“白色”时候，屏幕显示sea


        Runnable runnable = new Runnable() {
              @Override
              public void run() {


                  if(bitmap_dolphin == null){
                      bitmap_sea = BitmapFactory.decodeResource(getResources(),R.drawable.sea2_bg);
                      // bitmap_dolphin = BitmapFactory.decodeResource(getResources(),R.drawable.left_dolphin);
                      bitmap_dolphin = BitmapFactory.decodeResource(getResources(),R.drawable.bitmap_dolphin);
                  }
                  if(bitmap_dolphin != null){
                      mIncreasedLeft = 0;
                      mIncreasedRight = 0;
                      dolphin_list.clear();
                      isOverMiddle = false;
                      dolphin_list.add(new Dolphin(bitmap_dolphin,0,getHeight()-bitmap_sea.getHeight()-bitmap_dolphin.getHeight()/2,mIncreasedLeft,mIncreasedRight));
                      for(int i =0;i< 2000;i++ ){
                          if(dolphin_list.get(0).left_increased > getWidth()/2 - bitmap_dolphin.getWidth()/2 || dolphin_list.get(0).right_increased + dolphin_list.get(0).right < getHeight()/4){ //left到达中间
                              isOverMiddle = true;
                              mIncreasedLeft += 4;
                              mIncreasedRight += 2;
                              dolphin_list.get(0).left_increased = mIncreasedLeft;
                              dolphin_list.get(0).right_increased = mIncreasedRight;
                              if(dolphin_list.get(0).right_increased+dolphin_list.get(0).right> getHeight()-bitmap_sea.getHeight()-bitmap_dolphin.getHeight()/3){//???????????????????????????????????????????
                                  dolphin_list.get(0).left_increased = 0;
                                  dolphin_list.get(0).right_increased =  -getHeight();
                                  dolphin_list.get(0).left = 0;
                                  dolphin_list.get(0).right = 0;
                                  break;
                              }
                          }else{
                              if(!isOverMiddle){
                                  mIncreasedLeft += 4;
                                  mIncreasedRight -= 2;
                                  dolphin_list.get(0).left_increased = mIncreasedLeft;
                                  dolphin_list.get(0).right_increased = mIncreasedRight;
                              }else{
                                  mIncreasedLeft += 4;
                                  mIncreasedRight += 2;
                                  dolphin_list.get(0).left_increased = mIncreasedLeft;
                                  dolphin_list.get(0).right_increased = mIncreasedRight;
                                  if(dolphin_list.get(0).right_increased+dolphin_list.get(0).right> getHeight()-bitmap_sea.getHeight()-bitmap_dolphin.getHeight()/3){//???????????????????????????????????????????
                                      dolphin_list.get(0).left_increased = 0;
                                      dolphin_list.get(0).right_increased = -getHeight();
                                      dolphin_list.get(0).left = 0;
                                      dolphin_list.get(0).right = 0;
                                      break;
                                  }
                              }

                          }

                          try {
                              Thread.sleep(11);
                          } catch (InterruptedException e) {
                              return;
                          }
                          postInvalidate();
                      }
                  }

              }
          };
        thread_seaDolphin = new Thread(runnable);
        thread_seaDolphin.start();
    }
}
