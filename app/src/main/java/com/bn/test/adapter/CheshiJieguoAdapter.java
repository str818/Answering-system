package com.bn.test.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bn.test.CheShiJieGuoActivity;
import com.bn.test.R;
import com.bn.test.util.BaseTools;

import java.util.ArrayList;

/**
 * Created by JD on 2017/12/2.
 */

public class CheshiJieguoAdapter extends BaseAdapter {
    Context context;
    String[] mes={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16" +
            ""};
    ArrayList<String> ls;
    public CheshiJieguoAdapter(Context context, ArrayList<String>ls){
        this.context=context;
        this.ls=ls;
    }
    @Override
    public int getCount() {
        return ls.size();
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
        View view;
        Hodler hodler;
        if(convertView==null){
            hodler=new Hodler();
            convertView= LayoutInflater.from(context).inflate(R.layout.cheshijieguo_item,null,false);
            view=convertView;
            view.setTag(hodler);
        }else {
            view=convertView;
            hodler=(Hodler)  view.getTag();
        }
        hodler.textView=(TextView)view.findViewById(R.id.text);
        hodler.textView.setText(position+1+"");
        int width=  BaseTools.getWindowWidth(CheShiJieGuoActivity.activity)/4-80;
        hodler.textView.setWidth(width);
        hodler.textView.setHeight(width);
        hodler.textView.setGravity(Gravity.CENTER);
        if(position>=120&&position<160) {
            System.out.println(ls.get(position)+"测试结果");
            if (ls.get(position).equals("3")) {//未做
                hodler.textView.setBackground(context.getResources().getDrawable(R.drawable.da_up));
                hodler.textView.setTextColor(context.getResources().getColor(R.color.colorPrimaryGreen));
            } else if (ls.get(position).equals("0")) {
                hodler.textView.setBackground(context.getResources().getDrawable(R.drawable.zhengque_sharp));
                hodler.textView.setTextColor(context.getResources().getColor(R.color.black));
            } else if (ls.get(position).equals("1")) {
                hodler.textView.setBackground(context.getResources().getDrawable(R.drawable.cuowu_sharpe));
                hodler.textView.setTextColor(context.getResources().getColor(R.color.black));
            } else if (ls.get(position).equals("2")) {
                hodler.textView.setBackground(context.getResources().getDrawable(R.drawable.cuowu_sharpe));
                hodler.textView.setTextColor(context.getResources().getColor(R.color.black));
            }
        }else {
             if (ls.get(position).equals("0")) {
                 hodler.textView.setBackground(context.getResources().getDrawable(R.drawable.cuowu_sharpe));
                 hodler.textView.setTextColor(context.getResources().getColor(R.color.black));
            } else if (ls.get(position).equals("1")) {
                 hodler.textView.setBackground(context.getResources().getDrawable(R.drawable.zhengque_sharp));
                 hodler.textView.setTextColor(context.getResources().getColor(R.color.black));
            }else if (ls.get(position).equals("3")) {//未做
                hodler.textView.setBackground(context.getResources().getDrawable(R.drawable.da_up));
                hodler.textView.setTextColor(context.getResources().getColor(R.color.colorPrimaryGreen));
            }
        }
        return view;
    }
    class  Hodler{
        TextView textView;
    }
}
