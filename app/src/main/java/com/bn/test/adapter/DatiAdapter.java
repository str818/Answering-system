package com.bn.test.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bn.test.MainActivity;
import com.bn.test.R;
import com.bn.test.bean.Tiku;

import java.util.List;
import java.util.Map;

/**
 * Created by JD on 2017/12/1.
 */

public class DatiAdapter extends BaseAdapter {
    List<Tiku> Tl;
    Map<String,Bitmap[]>DaanT;              //答案图
    Context context;                        //上下文
    int flage=0;                            //当前题号
    public int clickStatus = -1;//初始化都没选中
    String ss[];
    int postitions;

    @Override
    public boolean areAllItemsEnabled() {
        return super.areAllItemsEnabled();
    }

    public DatiAdapter(Context context, List<Tiku> Tl, int flag,String[]ss)
    {
        this.Tl=Tl;
        this.flage=flag;
        this.context=context;
        this.ss=ss;
    }

    //  定义一个公有方法，在activity中调用
    public void setSeclection(int position) {
        clickStatus = position;
    }
    @Override
    public int getCount() {
        if(Tl.get(flage)!=null) {
            return ss.length ;
        }return 0;
    }

    @Override
    public Object getItem(int position) {
        return ss[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DatiAdapter.Hodler hodler;
        View view;
        //设置Item的高度和宽度
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.daan_items,parent,false);
            view=convertView;
            hodler=new DatiAdapter.Hodler();
            view.setTag(hodler);
        }
        else {
            view=convertView;
            hodler=(DatiAdapter.Hodler) view.getTag();
        }
            hodler.textView=(TextView) view.findViewById(R.id.neirong);
            hodler.linearLayout=(LinearLayout)view.findViewById(R.id.daan);
            hodler.textView.setText(ss[position]);
        if (MainActivity.mes[flage][0] != null) {
            if(MainActivity.mes[flage][2]!=null) {
                if (MainActivity.mes[flage][2].equals("单选") || MainActivity.mes[flage][2].equals("判断")) {
                    if (MainActivity.mes[flage][1].equals(position + "")) {
                        if (MainActivity.mes[flage][0].equals("0")) {
                            hodler.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.item_down_cuwu));// 错
                        } else if (MainActivity.mes[flage][0].equals("1")) {
                            hodler.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.item_down_right));//对
                        }
                    }
                } else if (MainActivity.mes[flage][2].equals("多选")) {
                    if (MainActivity.duoxuan[flage][position] != null) {
                        if (MainActivity.duoxuan[flage][position].equals("0")) {//选项正确
                            hodler.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.item_down_right));//对
                        } else if (MainActivity.duoxuan[flage][position].equals("1")) {//部分正确
                            hodler.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.item_down_right));//对
                        } else if (MainActivity.duoxuan[flage][position].equals("2")) {//错误
                            hodler.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.item_down_cuwu));// 错
                        }
                    }
                }
            }
        }

        if (clickStatus == position) {
            String daan="";
            boolean  ringht=false;
            if(ss.length==2)
            {
                switch (position) {
                    case 0:
                        daan = "对";
                        break;
                    case 1:
                        daan = "错";
                        break;
                }
                if (Tl.get(flage).getTiku_daan().equals(daan)) {
                    hodler.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.item_down_right));//对
                    ringht=true;
                } else {
                    hodler.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.item_down_cuwu));// 错
                }
            }else if(ss.length==4) {
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
                if (Tl.get(flage).getTiku_daan().equals(daan)) {
                    hodler.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.item_down_right));//对
                    ringht=true;
                } else {
                    hodler.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.item_down_cuwu));// 错
                    ringht=false;
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
                int choose= isRightAns(str,Tl.get(flage).getTiku_daan());
                if(choose==0){//正确
                    hodler.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.item_down_right));//对

                }else if(choose==1){//错误
                    hodler.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.item_down_right));//对

                }else if(choose==2){//
                    hodler.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.item_down_cuwu));// 错
                }
            }
     }
        return view;
    }
    /**
     *  多选题判断是否为正确答案
     *
     *  isSelected-所选选项  ans-正确答案
     *
     *  return 0-答案正确  1-选项正确但没有选择全部答案 2-答案错误
     */
    public static int isRightAns(String isSelectedStr,String ans){

        //判断正确答案中是否包含已选选项
        for(int i=0;i<isSelectedStr.length();i++){
            if(ans.indexOf(isSelectedStr.charAt(i))==-1) return 2;
            else continue;
        }

        if(isSelectedStr.length()==ans.length()) return 0;

        return 1;
    }
    class  Hodler{
        TextView  textView;
        LinearLayout linearLayout;
    }

}
