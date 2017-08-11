package com.example.a1.digitsecretword;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by 1 on 2017/7/15.
 */

public class SecondActivity  extends AppCompatActivity{
    RecyclerView recyclerView;
    MyView myView;
    Button button;
    TextView delete_icon;//删除清空按钮
    String num_means_str;//对应的密语String
    String status_color;//对应的密语颜色
    String num_str;//对应的密号码
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final MyAdapter myAdapter = new MyAdapter();
        myAdapter.secondActivity = this;
        recyclerView.setAdapter(myAdapter);
        myView = (MyView) findViewById(R.id.myView);
        delete_icon = (TextView) findViewById(R.id.delete_icon);
        button = (Button) findViewById(R.id.button);
        button.setEnabled(false);
        button.setOnClickListener(new View.OnClickListener() { //
            @Override
            public void onClick(View v) {
                //button.setSelected(true);
                myView.resultShowed = true; //设定显示密语结果为true
                String wholeNumStr = "";
                for(int i= 0;i< myView.getOrderLists().size();i++){
                    String perNumStr = myView.getOrderLists().get(i).toString();
                    wholeNumStr = wholeNumStr + perNumStr;
                }
                // int wholeNumInt = Integer.getInteger(wholeNumStr); //获取int值
                MyDatabaseOpenHelper myDatabaseOpenHelper = new MyDatabaseOpenHelper(SecondActivity.this);
                SQLiteDatabase sqlDatabase = myDatabaseOpenHelper.getWritableDatabase();
                Cursor cursor = sqlDatabase.query(MyDatabaseOpenHelper.TABLE_NAME, null, "alphabet = ?",
                        new String[]{wholeNumStr}, null,null,null);//数字对应的数据源密语数


                if(cursor!= null && cursor.getCount() > 0){ //查到有对应的密语时
                    cursor.moveToPosition(0);
                    num_str = cursor.getString(cursor.getColumnIndex("alphabet"));//获取对应的密语String
                    num_means_str = cursor.getString(cursor.getColumnIndex("num_means"));//获取对应的密语String
                    status_color = cursor.getString(cursor.getColumnIndex("status"));
                    if(status_color.equals("red")){
                        if(num_str.equals("520")){
                            if(myView.last_520Thread!=null){
                                myView.last_520Thread.interrupt();
                            }
                            /**设置绘制气球&上升**/
                            myView.setBackgroundResource(R.drawable.light_pink_bg);
                            myView.setCount(0);
                            myView.ascendDegreeForBaloon = -10;  //重置上升幅度
                            /**设置小鸟飞行和琢气球**/
                            myView.bitmap_bird = null;//清空小鸟bitmap
                            myView.bird_flyAngle = -0.15f;//重置小鸟飞的角度
                            myView.bird_x_descend = 0;//重置小鸟下降的x值
                            myView.bird_y_descend = 0;//重置小鸟下降的y值
                            /**设置撒花**/
                            myView.descendDegree1= -10;myView.descendDegree2= -10;myView.descendDegree3= -10;myView.descendDegree4= -10;myView.descendDegree5= -10;myView.descendDegree6= -10;myView.descendDegree7= -10;
                            myView.descendDegree8= -10;myView.descendDegree9= -10;myView.descendDegree10= -10;myView.descendDegree11= -10;myView.descendDegree12= -10;myView.descendDegree13= -10
                            ;myView.descendDegree14= -10;myView.descendDegree15= -10;myView.descendDegree16 = -10; //重置rose下降幅度
                            myView.updateBaloonFor520();

                        }else{
                            myView.setBackgroundResource(R.drawable.pink_bg);
                            myView.updateRedHeart(); //撒红心
                        } //重置下降幅度
                    }else if(status_color.equals("white") ){
                      //  myView.setBackgroundResource(R.drawable.green_bg);
                        if(myView.thread_seaDolphin != null){
                            myView.thread_seaDolphin.interrupt();
                        }
                        try {
          //                  myView.bitmap_dolphin=null;
                            myView.getWhiteBgBitmap();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else if(status_color.equals("yellow")){
                        myView.setBackgroundResource(R.drawable.yellow_bg_c);
                        if(myView.thread_dandelion != null){
                            myView.thread_dandelion.interrupt();
                        }
                        try {
                            myView.getYellowBgBitmap();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else if(status_color.equals("black")){
                        myView.setBackgroundResource(R.drawable.start_black_bg);
                        if(myView.cloudsThread !=null){
                            myView.cloudsThread.interrupt();
                        }
                        if(myView.thread_wind !=null){
                            myView.thread_wind.interrupt();
                        }
                        if(myView.thunder_thread !=null){
                            myView.thunder_thread.interrupt();
                        }
                        myView.getClouds();
                        myView.getWind();
                        myView.getThunder();


                    }
                }else { //未查到有对应的密语时
                    myView.setBackgroundResource(R.drawable.normal_bg);
                    num_means_str = null;//没有查到
                }

                myView.invalidate();//刷新界面


            }
        });


        delete_icon.setEnabled(false);
        delete_icon.setOnClickListener(new View.OnClickListener() {  //清空重选
            @Override
            public void onClick(View v) {
                myView.resultShowed = false; //设定显示密语结果为false
                if(myView.last_520Thread!=null){
                    myView.last_520Thread.interrupt();
                }
                if(myView.cloudsThread !=null){
                    myView.cloudsThread.interrupt();
                }
                myAdapter.count_selected = 0;//选中数清零
                myAdapter.reset();//重置数据
                myAdapter.notifyDataSetChanged();//刷新recyclerview
                myView.invalidate(); //刷新myView界面
                delete_icon.setEnabled(false);
                button.setEnabled(false);
                myView.setBackgroundResource(R.drawable.bg);


            }
        });

    }

    public String getNum_means_str(){
        return num_means_str;
    }

    public String status_color_str(){
        return status_color;
    }

    public String getNum_str(){
        return num_str;
    }

    }

