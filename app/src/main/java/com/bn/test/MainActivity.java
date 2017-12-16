package com.bn.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bn.test.adapter.DatiAdapter;
import com.bn.test.bean.Tiku;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import jxl.Sheet;
import jxl.Workbook;


public class MainActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{
    public static String mes[][];                                   //存放答题结果数组 0 错误,1正确,2未答题
    ListView listView;                                              //答案部分
    TextView timu;
    ImageView back;
    LinearLayout linear_shangyiti,linear_xiayiti,linear_shoucang;   //上一题  下一题  收藏
    ScrollView scrollView;
    TextView zhengque,cuowu;                                        //答题情况
    ImageView timut;                                                //题目部分
    LinearLayout linear_daanxuanxiang;                              //答案
    TextView textview_daanxuanxiang,jia,jiang;                      //答案显示
    TextView textview_jiaojuan;                                     //显示解析
    ImageView pre_bg,next_bg,shoucang;                              //收藏图标,上一题,下一题
    DatiAdapter datiAdapter;
    ArrayList<Tiku> countryList;
    ArrayList<Tiku> List;
    int flage=0;
    String ss[];
    int zqs,cws;
    int count=0;
    public static String duoxuan[][];
    boolean sc;                                                     //收藏标志位
    String scStr[];                                                 //收藏题目情况
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datijiemian);
        initView();
        try
        {
            new ExcelDataLoader().execute("test.xls");              //获取数据部分
        }catch (Exception e){

        }
        mes=new String[200][3];//结果数组
        duoxuan=new String[200][7];
        scStr=new String[200];
    }
    /**  动态计算grideview的高度  根据item数量*/
    public void setHeight(){
        int height = 0;
        int count = datiAdapter.getCount();
        for(int i=0;i<count;i++){
            View temp = datiAdapter.getView(i,null,listView);
            temp.measure(0,0);
            height += temp.getMeasuredHeight()+count*10;            //每条item高度加上分解线高度
        }
        ViewGroup.LayoutParams params = this.listView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.FILL_PARENT;
        params.height = height;
        listView.setLayoutParams(params);
    }
    /** 数据初始化部分*/
    public  void initData( ){
        count=0;
        if(mes[flage][0]!=null){
            linear_daanxuanxiang.setVisibility(View.VISIBLE);
            textview_daanxuanxiang.setVisibility(View.VISIBLE);
            textview_daanxuanxiang.setText(countryList.get(flage).getTiku_daan());
        }else {
            linear_daanxuanxiang.setVisibility(View.GONE);
            textview_daanxuanxiang.setVisibility(View.GONE);
        }
        if(!countryList.isEmpty()) {
            if (countryList.get(flage).getTiku_leixing().equals("判断题")) {
                ss = new String[2];
                ss[0] = countryList.get(flage).getA();
                ss[1] = countryList.get(flage).getB();
            } else if (countryList.get(flage).getTiku_leixing().equals("多选题")) {
                ss = new String[6];
                ss[0] = countryList.get(flage).getA();
                ss[1] = countryList.get(flage).getB();
                ss[2] = countryList.get(flage).getC();
                ss[3] = countryList.get(flage).getD();
                ss[4] = countryList.get(flage).getE();
                ss[5] = countryList.get(flage).getF();
            } else if (countryList.get(flage).getTiku_leixing().equals("单选题")) {
                ss = new String[4];
                ss[0] = countryList.get(flage).getA();
                ss[1] = countryList.get(flage).getB();
                ss[2] = countryList.get(flage).getC();
                ss[3] = countryList.get(flage).getD();
            }
            timu.setText(countryList.get(flage).getTiku_name());
            datiAdapter = new DatiAdapter(getApplicationContext(), countryList, flage, ss);
            listView.setAdapter(datiAdapter);
            jia.setText(flage + "");
            jiang.setText(countryList.size() - flage + "");
            setHeight();
            listView.setOnItemClickListener(this);
        }
    }
    /** 初始化界面*/
    public  void initView( ) {
        sc=false;
        back=(ImageView)this.findViewById(R.id.back);
        back.setOnClickListener(this);
        listView=(ListView) this.findViewById(R.id.gview);
        timu=(TextView)this.findViewById(R.id.TextView_timu);
        timut=(ImageView)this.findViewById(R.id.imageview_timuleixing);
        scrollView=(ScrollView)this.findViewById(R.id.sc);
        linear_daanxuanxiang=(LinearLayout)this.findViewById(R.id.linear_daanxuanxiang);
        textview_daanxuanxiang=(TextView)this.findViewById(R.id.textview_daanxuanxiang);
        zhengque=(TextView)this.findViewById(R.id.zhenque);
        cuowu=(TextView)this.findViewById(R.id.cuowu);
        pre_bg=(ImageView)this.findViewById(R.id.pre) ;
        next_bg=(ImageView)this.findViewById(R.id.next);
        linear_shangyiti=(LinearLayout)this.findViewById(R.id.linear_shangyiti);
        linear_xiayiti=(LinearLayout)this.findViewById(R.id.linear_xiayiti);
        linear_shangyiti.setOnClickListener(this);
        linear_xiayiti.setOnClickListener(this);
        jia=(TextView)this.findViewById(R.id.jia);
        jiang=(TextView)this.findViewById(R.id.jiang);
        textview_jiaojuan=(TextView)this.findViewById(R.id.textview_jiaojuan);
        textview_jiaojuan.setOnClickListener(this);
        linear_shoucang=(LinearLayout)this.findViewById(R.id.linear_shoucang);
        shoucang=(ImageView)this.findViewById(R.id.shoucang);
        linear_shoucang.setOnClickListener(this);
        getScStr();
    }
    protected void dialog( String str,String type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(str);
        builder.setTitle("提示");
        if(type.equals("tijiao")) {
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    jieGuoShuLian();
                    Intent intent = new Intent(MainActivity.this, CheShiJieGuoActivity.class);
                    Bundle bundle = new Bundle();
                    ArrayList<String> ls = new ArrayList<String>();
                    for (String sss[] : mes) {
                        if (sss[0] == null) {
                            sss[0] = "3";
                        }
                        ls.add(sss[0]);
                    }
                    bundle.putStringArrayList("jieguo", ls);
                    for (int i = 0; i < duoxuan.length; i++){
                        bundle.putStringArray("xuanxian" +i, duoxuan[i]);
                        }
                    bundle.putStringArray("shoucang",scStr);
                    intent.putExtra("listobj", (Serializable) countryList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    }
            });
        }else {
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
        }
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private ArrayList<Tiku> getXlsData(String xlsName, int index) {
        countryList = new ArrayList<Tiku>();
        List=new ArrayList<>();
        AssetManager assetManager = getAssets();
        try {
            Workbook workbook = Workbook.getWorkbook(assetManager.open(xlsName));
            Sheet sheet = workbook.getSheet(index);
            int sheetNum = workbook.getNumberOfSheets();
            int sheetRows = sheet.getRows();
            int sheetColumns = sheet.getColumns();
            Log.d("资源", "the num of sheets is " + sheetNum);
            Log.d("", "the name of sheet is  " + sheet.getName());
            Log.d("", "total rows is 行=" + sheetRows);
            Log.d("", "total cols is 列=" + sheetColumns);
            for(int j=52; j<852; j++) {
                Tiku countryModel = new Tiku(j-51+"",sheet.getCell(0, j).getContents(),sheet.getCell(1, j).getContents(),sheet.getCell(2, j).getContents(),sheet.getCell(3, j).getContents(),sheet.getCell(4, j).getContents(),sheet.getCell(5, j).getContents(),sheet.getCell(6, j).getContents(),sheet.getCell(7, j).getContents(),sheet.getCell(8, j).getContents());
                List.add(countryModel);
            }
            workbook.close();

        } catch (Exception e) {
        }
        return List;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.linear_shangyiti:
                if(flage>0) {
                    int l = isChoose(flage);
                    duoxuan[flage][6] = l + "";
                    flage--;
                    jieGuoShuLian();
                    xiuGaiTiMu();
                }
                break;
            case R.id.linear_xiayiti:
                if(0<=flage&&flage<=countryList.size()-1){
                    int l = isChoose(flage);
                    duoxuan[flage][6] = l + "";
                    flage++;
                    jieGuoShuLian();
                    xiuGaiTiMu();
                }else if(flage==countryList.size()){
                    jia.setText(flage+"");
                    jiang.setText(countryList.size()-flage+"");
                }
                break;
            case R.id.textview_jiaojuan:
                jieGuoShuLian();
                int c=200-zqs-cws;
                if(c>0) {
                    dialog("你还有"+c+"道题未答！你确定提交吗？", "tijiao");
                }
            case R.id.linear_shoucang:
                setScStr();
                break;
            default:
                break;
        }
    }
    public void setScStr(){
        if(scStr[flage]==null){
            scStr[flage]=1+"";
            shoucang.setImageDrawable(getResources().getDrawable(R.drawable.sc_down));
        }else if(scStr[flage].equals("1")){
            scStr[flage]="0";
            shoucang.setImageDrawable(getResources().getDrawable(R.drawable.sc));
        }else if(scStr[flage].equals("0")){
            scStr[flage]="1";
            shoucang.setImageDrawable(getResources().getDrawable(R.drawable.sc_down));
        }
    }
    public void getScStr(){
        if( scStr!=null) {
            if (scStr[flage] == null) {
                shoucang.setImageDrawable(getResources().getDrawable(R.drawable.sc));
            } else if (scStr[flage].equals("1")) {
                shoucang.setImageDrawable(getResources().getDrawable(R.drawable.sc_down));
            } else if (scStr[flage].equals("0")) {
                shoucang.setImageDrawable(getResources().getDrawable(R.drawable.sc));
            }
        }
    }
    public int  isChoose(int flage){//判断当前已经被选择
        int choose=0;               //没有选择
        for(int i=0;i<6;i++){
            if(duoxuan[flage][i]!=null){
                choose=1;//已经有过选择情况
            }
        }
        return choose;
    }
    public void  xiuGaiTiMu( ){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 0;
                mHandler.sendMessage(message);
            }
        }).start();
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                if(0<=flage) {
                    if(flage<countryList.size()) {
                        initView();
                        initData();
                    }
                }
            }
        }
    };
    public void jieGuoShuLian(){
        cws=0;
        zqs=0;
        for(int i=0;i<countryList.size();i++) {
            if (mes[i][0] != null) {
                if (mes[i][2] != null) {
                    if (mes[i][2].equals("单选") || mes[i][2].equals("判断")) {
                        if (mes[i][0].equals("0")) {
                            cws++;
                        } else if (mes[i][0].equals("1")) {
                            zqs++;
                        }
                    } else if (mes[i][2].equals("多选")) {
                        if (mes[i][0].equals("0")) {
                            zqs++;
                        } else if (mes[i][0].equals("1")) {
                            cws++;
                        } else if (mes[i][0].equals("2")) {
                            cws++;
                        }
                    }
                }
            }
        }
        zhengque.setText(zqs+" ");
        cuowu.setText(cws+" ");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(ss.length==2||ss.length==4) {
            count++;
            if (count == 1) {
                if(flage<countryList.size()) {
                    String daan="";
                    if (mes[flage][0] == null) {
                        datiAdapter.setSeclection(position);
                        datiAdapter.notifyDataSetChanged();
                        if (ss.length == 2) {
                            switch (position) {
                                case 0:
                                    daan = "对";
                                    break;
                                case 1:
                                    daan = "错";
                                    break;
                            }
                            if (countryList.get(flage).getTiku_daan().equals(daan)) {
                                mes[flage][0] = 1 + ""; //对
                                mes[flage][1] = position + "";
                                mes[flage][2]="判断";
                                duoxuan[flage][position]=1+"";
                            } else {
                                linear_daanxuanxiang.setVisibility(View.VISIBLE);
                                textview_daanxuanxiang.setVisibility(View.VISIBLE);
                                textview_daanxuanxiang.setText(countryList.get(flage).getTiku_daan());
                                mes[flage][0] = 0 + ""; // 错
                                mes[flage][1] = position + "";
                                mes[flage][2]="判断";
                                duoxuan[flage][position]=0+"";
                            }
                        } else if (ss.length == 4) {
                            switch (position) {
                                case 0:
                                    daan = "A";
                                    break;
                                case 1:
                                    daan = "B";
                                    break;
                                case 2:
                                    daan = "C";
                                    break;
                                case 3:
                                    daan = "D";
                                    break;
                            }
                            if (countryList.get(flage).getTiku_daan().equals(daan)) {
                                mes[flage][0] = 1 + ""; //对
                                mes[flage][1] = position + "";
                                mes[flage][2]="单选";
                                duoxuan[flage][position]=1+"";
                            } else {
                                linear_daanxuanxiang.setVisibility(View.VISIBLE);
                                textview_daanxuanxiang.setVisibility(View.VISIBLE);
                                textview_daanxuanxiang.setText(countryList.get(flage).getTiku_daan());
                                mes[flage][0] = 0 + ""; // 错
                                mes[flage][1] = position + "";
                                mes[flage][2]="单选";
                                duoxuan[flage][position]=0+"";
                            }
                        }
                    }else {
                        linear_daanxuanxiang.setVisibility(View.VISIBLE);
                        textview_daanxuanxiang.setVisibility(View.VISIBLE);
                        textview_daanxuanxiang.setText(countryList.get(flage).getTiku_daan());
                    }

                }
            }
        }else {
            String str="";
            switch (position){
                case 0:str="A";
                    break;
                case 1:str="B";
                    break;
                case 2:str="C";
                    break;
                case 3:str="D";
                    break;
                case 4:str="E";
                    break;
                case 5:str="F";
                    break;
            }
            int choose= datiAdapter.isRightAns(str,countryList.get(flage).getTiku_daan());
            if(count==0) {
                if(duoxuan[flage][position]==null) {//当前位置还未选择
                    System.out.println(flage+"位置"+duoxuan[flage][6]+"情况");
                    if (duoxuan[flage][6]==null||duoxuan[flage][6].equals("0")) {
                        datiAdapter.setSeclection(position);
                        datiAdapter.notifyDataSetChanged();
                        if (choose == 1) {//部分正确
                            mes[flage][0] = "1";
                            mes[flage][1] = position + "";
                            mes[flage][2] = "多选";
                            duoxuan[flage][position] = "1";
                        } else if (choose == 2) {//错误
                            count++;
                            mes[flage][0] = "2";
                            mes[flage][1] = position + "";
                            mes[flage][2] = "多选";
                            duoxuan[flage][position] = "2";
                            linear_daanxuanxiang.setVisibility(View.VISIBLE);
                            textview_daanxuanxiang.setVisibility(View.VISIBLE);
                            textview_daanxuanxiang.setText(countryList.get(flage).getTiku_daan());
                        }
                        String s="";
                        for(int i=0;i<6;i++){
                            if(duoxuan[flage][i]!=null) {//组合之前的选项部分
                                switch (i){
                                    case 0:s=s+"A";
                                        break;
                                    case 1:s=s+"B";
                                        break;
                                    case 2:s=s+"C";
                                        break;
                                    case 3:s=s+"D";
                                        break;
                                    case 4:s=s+"E";
                                        break;
                                    case 5:s=s+"F";
                                        break;
                                }
                            }
                        }
                        int choose2= datiAdapter.isRightAns(s,countryList.get(flage).getTiku_daan());
                        if (choose2 == 0) {//全对
                            mes[flage][0] = "0";
                            mes[flage][1] = position + "";
                            mes[flage][2] = "多选";
                            duoxuan[flage][position] = "0";
                        }
                    }
                }
            }
        }

    }
    //在异步方法中 调用
    private class ExcelDataLoader extends AsyncTask<String, Void, ArrayList<Tiku>> {
        @Override
        protected void onPreExecute() {
            System.out.println("开始处理数据");
        }

        @Override
        protected ArrayList<Tiku> doInBackground(String... params) {
            System.out.println("处理数据中");
            return getXlsData(params[0], 0);
        }

        @Override
        protected void onPostExecute(ArrayList<Tiku> countryModels) {
            if(countryModels != null && countryModels.size()>0){
                MyThread myThread=new MyThread();
                myThread.start();
                try {
                    myThread.join();
                }catch (Exception e){
                    e.printStackTrace();
                }
                initData();
            }else {
                //加载失败
            }
        }
        //生成不重复的随机数 start起始数 end终止数 count随机数数量
        public Hashtable nonRepeatRandom(int start, int end, int count) {
            Hashtable hashtable = new Hashtable();
            for (int i = 0; hashtable.size() < count; i++) {
                int nValue = start + (int)(Math.random() * ((end - start) + 1));
                if (!hashtable.containsKey(nValue)) {
                    hashtable.put(nValue, nValue);
                }
            }
            return hashtable;
        }
        class  MyThread extends Thread{
            public void run(){
                Hashtable hashtable1 = nonRepeatRandom(100,599,120);
                for(Iterator<Integer> iterator = hashtable1.keySet().iterator(); iterator.hasNext();){
                    Integer key=iterator.next();
                    countryList.add(List.get(key));
                }
                Hashtable hashtable = nonRepeatRandom(0,99,40);
                for(Iterator<Integer> iterator = hashtable.keySet().iterator(); iterator.hasNext();){
                    Integer key=iterator.next();
                    countryList.add(List.get(key));
                }
                Hashtable hashtable2 = nonRepeatRandom(600,799,40);
                for(Iterator<Integer> iterator = hashtable2.keySet().iterator(); iterator.hasNext();){
                    Integer key=iterator.next();
                    countryList.add(List.get(key));
                }
            }
        }
    }

}
