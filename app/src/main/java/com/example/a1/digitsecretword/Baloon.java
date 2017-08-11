package com.example.a1.digitsecretword;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 1 on 2017/6/16.
 */

public class Baloon implements Parcelable {
    int num; //代表的数字
    String res_pic;    // 图片资源
    Boolean selected;  //是否被选中
    int res_drawableAlpha;//数字drawableID
    int selected_counts;//被选中的次数
    static String[] res_drawable  = {"num0","num1","num2","num3","num4","num5","num6","num7","num8","num9"};//所有图片资源
    static int[] res_drawableAlphabet = {R.drawable.pic0,R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,
            R.drawable.pic5,R.drawable.pic6,R.drawable.pic7,R.drawable.pic8,R.drawable.pic9};//所有数字drawable资源

    Baloon(int num, String res_pic, int res_drawableAlpha, Boolean selected, int selected_counts){
        this.num = num;
        this.res_pic = res_pic;
        this.selected = selected;
        this.res_drawableAlpha = res_drawableAlpha;
        this.selected_counts = selected_counts;

    }


    protected Baloon(Parcel in) {
        num = in.readInt();
        res_pic = in.readString();
        res_drawableAlpha = in.readInt();
        selected_counts = in.readInt();
    }

    public static final Creator<Baloon> CREATOR = new Creator<Baloon>() {
        @Override
        public Baloon createFromParcel(Parcel in) {
            return new Baloon(in);
        }

        @Override
        public Baloon[] newArray(int size) {
            return new Baloon[size];
        }
    };

    public static List<Baloon> getBaloon(){
        List<Baloon> list = new ArrayList<>();
        for(int i= 0; i< res_drawable.length;i++){
            list.add(new Baloon(i,res_drawable[i],res_drawableAlphabet[i],false,0));
        }
      return list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(num);
        dest.writeString(res_pic);
        dest.writeInt(res_drawableAlpha);
        dest.writeInt(selected_counts);
    }


}
