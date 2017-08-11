package com.example.a1.digitsecretword;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;


public class MyDatabaseOpenHelper extends SQLiteOpenHelper {
    public static final  String TABLE_NAME = "NUMS_MEANINGS";
    private String nums[] ={"01","02","03","04","05","06","07","08","09","10","11","12","13","17","18","20","21",//第一行，17
            "22","24","33","36","44","50","55","56","57","66","77","88",//第二行，12
            "99","100","101","108","144","365","520","530","800","920","999","1000","1001","1314",//第三行，14
            "165","195","198","147","217","231","234","235","241","246","282","296","311","356","361","39","456","461","515","514",//第四行，20
            "521","526","531","1241","1437","1456","1457","1487","1748","1837","1372","1414","1573","2137","2627","3113","3344","3399","3731",//第五行，19
            "4457","5261","5366","5361","5416","5417","5421","546","5461","5491","555","5621","5631","5671","5776","58","586",//第六行，17
            "587","5871","592","596","5976","619","6121","6868","687","716","7187","7319","741","756","765","7678","7731","7752","786","8116","8113","8174","825",//第七行，23
            "837","8384","85","885","861","865","881","886","898","9189","911","918","921","9213","931","995","98","987","995","9958",//第八行，20
            "0487","0437","065","0748","095","098","137","1711","1920","1930","200","2406","211","230","135","300","3710","4456","460","505",//第九行，20
            "5230","5240","5260","5360","5376","5406","5407","5420","5460","5490","5620","5630","5670","570","578","580","609","6120","6699","706",//第十行，20
            "7086","729","740","7998","7186","780","8006","8013","8023","8074","8084","820","860","9089","910","912","915","917","9240","930","95",//第十一行，21
            "3456","809","168","70","245","360","0837","721","1240","310","3013" };//第十二行，11

    private String meanings[] ={"唯一的爱","两情相悦","我爱你","誓言","无悔","顺利","喜相逢","弥补","长相守","十全十美","一心一意","心心相印","暗恋","好聚好散","你是我的另一半","此情不渝","最爱",//第一行，17
            "双双对对","圆圆满满","我爱你三生三世","我心属於你","至死不渝","无悔的爱","无怨无悔的爱","吾爱","吾爱吾妻","事事顺利","相逢自是有缘","弥补歉意",//第二行，12
            "长相厮守","白首偕老","你是我的唯一","嫁给我吧","爱你生生世世","天天爱你","我愿意！","我想你！","无悔的爱","就爱你","天长地久","心想事成","直到永远","爱你一生一世",//第三行，14
            "原谅我","你找我？","你走吧！","一世情","爱你哦","爱上你","爱相随","要想你","爱死你","饿死了","饿不饿","爱走了","想你哦","上网啦！","想念你","Thank you","是我啦","思念你","SOS","无意思",//第四行，20
            "我爱你","我饿了","我想你","最爱是你","你是神经","你是我的","你是我妻","你是白痴","你去死吧","你别生气","一厢情愿","意思意思","一往情深","为你伤心","爱来爱去","想你一生","生生世世","长长久久","真心真意",//第五行，19
            "速速回机","我暗恋你","我想聊聊","我想念你","我是你的","我是你妻","我只爱你","我输了","我思念你","我去找你","呜呜呜","我很爱你","我很想你","我要娶你","我出去了","晚安","我不来",//第六行，17
            "我抱歉","我不介意","我好饿","我走了","我到家了","到永久","懒得理你","溜吧！溜吧！","对不起","起来吧","请你别走","天长地久","气死你！","辛苦了！","去跳舞？","吃饱了吗？","心心相印","亲亲吾爱","吃饱了","不理你了","伴你一生","把你气死！","别爱我！",//第七行，23
            "别生气","不三不四","帮我","帮帮我","不留你","别惹我","拜拜唉","拜拜罗","分手吧","求你别走","就依你","加油吧","好爱你","钟爱一生","好想你","救救我","早安","对不起","救救我！","救救我吧！",//第八行 20
            "你是白痴！","你是神经","原谅我！","你去死吧！","你找我？","你走吧！","一生情","一心一意","依旧爱你","依旧想你","爱你哦！","爱死你啦","爱你哦!","爱死你","你想我","想你哦！","想起了你","速速回来","想念你","SOS",//第九行，20
            "我爱上你","我爱是你","我暗恋你","我想念你","我生气了！","我是你的","我是你妻","我只爱你","我思念你","我去找你","我很爱你","我很想你","我要娶你!","我气你","补习班","我明白","到永久","懒得理你","顺顺利利","起来吧！",//第十行，20
            "七零八落","去喝酒","气死你","去走走吧","七零八落","牵挂你","不理你了！","伴你一生","我爱你（手势LOVE）","把你气死","BABY（宝贝）","不分开","不留你","求你别走！","就依你","就要爱","就要吻","就要亲","最爱是你","好想你","救我！",//第十一行，21
            "相思无用","保龄球","一路发","亲你！","饿死我咯！","想念你","你別生气","亲爱的","最爱是你","先依你","想你一生" };//第十二行，11
    private String status[] = { //red= 爱或好消息  black = 坏消息  white = 一般情况  yellow = 告知特殊情况
            "red","white","red","white","white","white","white","white","white","white","red","red","yellow","black","red","white","red",
            "white","white","red","red","white","white","white","red","red","white","white","yellow",
            "white","white","red","red","red","red","red","white","white","red","white","white","white","red",
            "yellow","yellow","black","white","red","red","red","white","red","white","white","black","red","white","white","yellow","white","white","yellow","white",
            "red","white","white","red","black","white","red","black","black","yellow","black","white","white","black","white","red","white","white","white",
            "yellow","yellow","yellow","red","red","red","red","black","red","yellow","black","red","red","red","yellow","white","yellow",
            "black","white","white","white","white","white","yellow","white","black","white","black","white","black","yellow","white","yellow","white","red","white","black","white","black","black",
            "yellow","black","yellow","yellow","black","black","yellow","yellow","black","white","white","yellow","red","white","white","yellow","white","yellow","yellow","yellow",//第八行，23（多3个）"你是白痴！","原谅我！","你去死吧！"
            "black","black","yellow","black","yellow","yellow","white","white","red","red","red","red","red","red","red","red","white","yellow","red","yellow",//第九行，20"我爱上你","我爱是你","我暗恋你",
            "red","red","red","red","black","red","red","red","red","yellow","red","red","red","black","white","white","white","black","white","yellow",//第十行，20"请你别走","去喝酒！","气死你！","去走走吧！",
            "white","yellow","black","white","white","red","black","white","red","black","red","white","black","black","white","white","white","white","red","red","yellow",//第十一行，21
            "black","white","red","white","yellow","white","white","red","red","yellow","red"};//第十二行，11

    public MyDatabaseOpenHelper(Context context) {
        super(context,String.valueOf(new File(context.getFilesDir(),"context_info")), null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_NAME + " ( alphabet text,num_means text,status text )");
        ContentValues contentValues = new ContentValues( );
        for(int i = 0; i< nums.length;i++){
            contentValues.put("alphabet",nums[i]);
            contentValues.put("num_means",meanings[i]);
            contentValues.put("status",status[i]);
            db.insert(TABLE_NAME,null,contentValues);
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
