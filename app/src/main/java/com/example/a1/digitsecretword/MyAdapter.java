package com.example.a1.digitsecretword;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import java.util.List;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    int count_selected = 0;
    static SecondActivity secondActivity;
    List<Baloon> baloon_list  = Baloon.getBaloon();

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(secondActivity).inflate(R.layout.recyclerview_my,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        int res_image = holder.itemView.getContext().getResources().getIdentifier(baloon_list.get(position).res_pic,"drawable",holder.itemView.getContext().getPackageName());
        final Uri uri = Uri.parse("res:///" + res_image);
        holder.imageView.setImageURI(uri);

        if (baloon_list.get(position).selected) { //可选中时，加灰度
                holder.imageView.setController(Fresco.newDraweeControllerBuilder()
                        .setImageRequest(ImageRequestBuilder.newBuilderWithSource(uri).setPostprocessor(new GreyProcessor(uri)).build())
                        .build());

            }
        holder.selected_count_id.setBackgroundResource(0);
        if(baloon_list.get(position).selected_counts>0){
            if(baloon_list.get(position).selected_counts ==1){
                holder.selected_count_id.setBackgroundResource(R.drawable.round_1);
            }else if(baloon_list.get(position).selected_counts == 2){
                holder.selected_count_id.setBackgroundResource(R.drawable.round_2);
            }else if(baloon_list.get(position).selected_counts == 3){
                holder.selected_count_id.setBackgroundResource(R.drawable.round_3);
            }else if(baloon_list.get(position).selected_counts == 4){
                holder.selected_count_id.setBackgroundResource(R.drawable.round_4);
            }
        }


        holder.imageView.setOnClickListener(new View.OnClickListener() {//设置点击事件
            @Override
            public void onClick(View v) {//多选

                if(count_selected >= 4){//check是否已选了4个字母气球
                    Toast.makeText(secondActivity,"最多只能选4个字母气球！",Toast.LENGTH_SHORT).show();}
                else { //未超过4个就刷新视图
                    secondActivity.myView.resultShowed = false; //设定显示密语结果为false
//                    new Thread(new Runnable() { //?????????????????????????????????????????????
//                        @Override
//                        public void run() {
//                            mainActivity.myView.setBackgroundResource(R.drawable.bg);
//                        }
//                    }).start();
                    secondActivity.myView.setBackgroundResource(R.drawable.bg);



                    baloon_list.get(position).selected = true;
                    baloon_list.get(position).selected_counts = baloon_list.get(position).selected_counts +1;
                    count_selected = count_selected +1;
                    secondActivity.myView.setOrderList(position);//记录点中的位置
                    if(count_selected == 0){
                        secondActivity. delete_icon.setEnabled(false);
                    }else{
                        secondActivity.delete_icon.setEnabled(true);}
                    if(count_selected < 2){
                        secondActivity.button.setEnabled(false);
                    }else{
                        secondActivity.button.setEnabled(true);}
                    notifyItemChanged(position); //未超过4个就刷新视图
                    secondActivity.myView.setCount(count_selected);
                    secondActivity.myView.invalidate();//刷新自定义myView的界面
                    }
                }
//                   if(baloon_list.get(position).selected){
//                      baloon_list.get(position).selected = false;
//                       count_selected = count_selected -1;
//                        notifyItemChanged(position); //未超过4个就刷新视图
//                       mainActivity.myView.setOrderList(position); //记录点中的位置
//                        mainActivity.myView.setCount(count_selected);
//                       mainActivity.myView.invalidate();//刷新自定义myView的界面
//                   }else{
//                    Toast.makeText(mainActivity,"最多只能选4个字母气球！",Toast.LENGTH_SHORT).show();}
//
//                else { //未超过4个就刷新视图
//                    if(!baloon_list.get(position).selected){
//                       baloon_list.get(position).selected = true;
//                       count_selected = count_selected +1;
//                       mainActivity.myView.setOrderList(position);//记录点中的位置
//                   }else{
//                        baloon_list.get(position).selected = false;
//                        if(count_selected>=1){
//                           count_selected = count_selected -1;
//                          mainActivity.myView.setOrderList(position);//记录点中的位置
//                      }
//                   }
//
//                    if(count_selected < 2){
//                        mainActivity.button.setEnabled(false);
//                    }else{
//                        mainActivity.button.setEnabled(true);
//                    }
//                    notifyItemChanged(position); //未超过4个就刷新视图
//              //      mainActivity.myView.setDataRes(baloon_list.get(position).res_drawableAlpha);
//                    mainActivity.myView.setCount(count_selected);
//                    mainActivity.myView.invalidate();//刷新自定义myView的界面
//                }
//            }
        });

    }


    @Override
    public int getItemCount() {
        if(baloon_list!=null){
            return baloon_list.size();
        }else{
            return 0;}
    }

    public void reset(){  //重置数据源
        baloon_list  = Baloon.getBaloon();
        MyView.orderLists.clear();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView imageView;
        TextView selected_count_id;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (SimpleDraweeView) itemView.findViewById(R.id.image);
            selected_count_id = (TextView) itemView.findViewById(R.id.selected_count_id);
        }
    }



}
