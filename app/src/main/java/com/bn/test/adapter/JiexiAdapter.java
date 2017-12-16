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


import com.bn.test.R;
import com.bn.test.bean.Tiku;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by JD on 2017/12/12.
 */

public class JiexiAdapter extends BaseAdapter {
    private List<View> views;
    ArrayList<Tiku> Tl;
    ArrayList<String>ls;                    //正确错误答案
    Map<String,String[] >xx;                   //具体选型
    String type;
    Context context;
    String ss[];
    int flage;
    public JiexiAdapter(Context context, ArrayList<Tiku>Tl, ArrayList<String>ls,Map<String,String[] >xx, String type, int flage) {
        super();
        this.Tl=Tl;
        this.ls=ls;
        this.xx=xx;
        this.type=type;
        this.context=context;
        this.flage=flage;
        if(Tl.get(flage).getTiku_leixing().equals("多选题")){
            ss=new String[6];
            ss[0]= Tl.get(flage).getA();
            ss[1]= Tl.get(flage).getB();
            ss[2]= Tl.get(flage).getC();
            ss[3]= Tl.get(flage).getD();
            ss[4]= Tl.get(flage).getE();
            ss[5]= Tl.get(flage).getF();
        }else if(Tl.get(flage).getTiku_leixing().equals("单选题")){
            ss=new String[4];
            ss[0]= Tl.get(flage).getA();
            ss[1]= Tl.get(flage).getB();
            ss[2]= Tl.get(flage).getC();
            ss[3]= Tl.get(flage).getD();
        }else {
            ss=new String[2];
            ss[0]= Tl.get(flage).getA();
            ss[1]= Tl.get(flage).getB();
        }
    }
    @Override
    public int getCount() {
        return ss.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hodler hodler;
        View view;
        //设置Item的高度和宽度
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.daan_items,parent,false);
            view=convertView;
            hodler=new Hodler();
            view.setTag(hodler);
        }
        else {
            view=convertView;
            hodler=(Hodler) view.getTag();
        }
        hodler.textView=(TextView) view.findViewById(R.id.neirong);
        hodler.linearLayout=(LinearLayout)view.findViewById(R.id.daan);
            hodler.textView.setText(ss[position]);
        if(xx!=null &&xx.get(Tl.get(flage).getTiku_id())!=null) {
            if(ss.length==2||ss.length==4) {
                if(xx.get(Tl.get(flage).getTiku_id())[position]!=null)
                {
                    if (xx.get(Tl.get(flage).getTiku_id())[position].equals("0")) {
                        hodler.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.item_down_cuwu));// 错
                    } else if (xx.get(Tl.get(flage).getTiku_id())[position].equals("1")) {
                        hodler.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.item_down_right));//对
                    }
                }
            }else {
                if(xx.get(Tl.get(flage).getTiku_id())[position]!=null)
                {
                    if (xx.get(Tl.get(flage).getTiku_id())[position].equals("0")) {
                        hodler.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.item_down_cuwu));// 错
                    } else if (xx.get(Tl.get(flage).getTiku_id())[position].equals("1")) {
                        hodler.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.item_down_right));//对
                    }else if(xx.get(Tl.get(flage).getTiku_id())[position]!=null)
                    {
                        if (xx.get(Tl.get(flage).getTiku_id())[position].equals("0")) {
                            hodler.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.item_down_right));//对
                        } else if (xx.get(Tl.get(flage).getTiku_id())[position].equals("1")) {
                            hodler.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.item_down_right));//对
                        } else if (xx.get(Tl.get(flage).getTiku_id())[position].equals("2")) {
                            hodler.linearLayout.setBackground(context.getResources().getDrawable(R.drawable.item_down_cuwu));// 错
                        }
                    }
                }
            }
        }
        return view;
    }
    class  Hodler{
        TextView  textView;
        LinearLayout linearLayout;

    }
}
