package com.bn.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import com.bn.test.adapter.CheshiJieguoAdapter;
import com.bn.test.bean.Tiku;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by JD on 2017/12/2.
 */

public class CheShiJieGuoActivity extends Activity  implements View.OnClickListener{
    CheshiJieguoAdapter cheshiJieguoAdapter;
    GridView gridView;
    ArrayList<String>ls;
    ImageView kaishi;
    TextView textView;
    ImageView back;
    Button jixu;
    String duoxuan[][];
    ArrayList<Tiku>Tl;
    TextView quanbu_jiexi,cuoti_jiexi;
    String scStr[];
    public static Activity activity;
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.cheshi_jieguo_activity);
        activity=this;
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        ls=bundle.getStringArrayList("jieguo");
        Tl=(ArrayList<Tiku>) getIntent().getSerializableExtra("listobj");
        duoxuan=new String[Tl.size()][];
        for (int i = 0; i < Tl.size(); i++){
           duoxuan[i]= bundle.getStringArray("xuanxian" +i);
        }
        scStr=bundle.getStringArray("shoucang");
        initView();
    }
    public void initView(){
        gridView=(GridView)this.findViewById(R.id.gview) ;
        kaishi=(ImageView)this.findViewById(R.id.back);
        textView=(TextView)this.findViewById(R.id.btname);
        back=(ImageView)this.findViewById(R.id.back);
        back.setVisibility(View.GONE);
        jixu=(Button)this.findViewById(R.id.jixu);
        cuoti_jiexi=(TextView)this.findViewById(R.id.cuoti);
       quanbu_jiexi = (TextView) this.findViewById(R.id.quanbu_jiexi);
        cuoti_jiexi.setOnClickListener(this);
        quanbu_jiexi.setOnClickListener(this);
        textView.setText("在线测试结果报告");
        kaishi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(CheShiJieGuoActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            }
        });
        cheshiJieguoAdapter=new CheshiJieguoAdapter(getApplicationContext(),ls);
        gridView.setAdapter(cheshiJieguoAdapter);
        jixu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();
            Intent intent=new Intent(CheShiJieGuoActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            }
        });
    }
    @Override
    public void onClick(View v) {
        String type="cuoti";
        if(v.getId()==R.id.cuoti){
            type="cuoti";
        }
        if(v.getId()==R.id.quanbu_jiexi){
            type="shoucang";
        }
        Intent intent=new Intent(CheShiJieGuoActivity.this, CuotiActivity.class);
        Bundle bundle=new Bundle();
        bundle.putStringArrayList("jieguo",ls);
        for (int i = 0; i < duoxuan.length; i++){
            bundle.putStringArray("xuanxian" +i, duoxuan[i]);
        }
        intent.putExtra("listobj", (Serializable) Tl);
        bundle.putString("type",type);
        bundle.putStringArray("shoucang",scStr);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
