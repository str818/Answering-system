package com.bn.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bn.test.adapter.GuideViewPagerAdapter;
import com.bn.test.bean.Tiku;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CuotiActivity extends Activity  implements View.OnClickListener {
    public static Activity activity;
    private ViewPager vp;
    private GuideViewPagerAdapter adapter;
    private List<View> views;
    TextView textView;
    ArrayList<Tiku>Tl;
    ArrayList<Tiku>Tll;
    ArrayList<String>ls;                    //正确错误答案
    ArrayList<String>cls;                   //正确错误答案
    Map<String,String[]>xx;
    ImageView back;
    String type;
    String duoxuan[][];
    // 界面
    private static final int[] pics = { R.layout.jiexi_view};
    int count=0;                            //界面数量
    // 题号数组
    private String [] dots;
    // 记录当前选中位置
    private int currentIndex;
    TextView danan;
    String  scStr[];
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.jiexi_activity);
        textView=(TextView)this.findViewById(R.id.text) ;
        back=(ImageView)this.findViewById(R.id.back);
        danan=(TextView)this.findViewById(R.id.danan) ;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        activity=this;
        Bundle bundle=getIntent().getExtras();
        views = new ArrayList<View>();
        ls=bundle.getStringArrayList("jieguo");
        Tl=(ArrayList<Tiku>) getIntent().getSerializableExtra("listobj");
        duoxuan=new String[Tl.size()][];
        for (int i = 0; i < Tl.size(); i++){
            duoxuan[i]= bundle.getStringArray("xuanxian" +i);
        }
        type=bundle.getString("type");
        scStr=bundle.getStringArray("shoucang");
        initDots();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private void initDots() {
        cls=new ArrayList<>();
        xx=new HashMap<>();
        Tll=new ArrayList<>();
        if(type.equals("cuoti")){                               //错题
            for(int i=0;i<ls.size();i++){
                if(!ls.get(i).equals("1")){
                    count++;
                    cls.add(ls.get(i));
                    xx.put(Tl.get(i).getTiku_id(),duoxuan[i]);//当前题库id  与选项
                    Tll.add(Tl.get(i));
                }
            }
        }else{
            if(scStr!=null) {                                //收藏
                for (int i = 0; i < scStr.length; i++) {
                    if(scStr[i]!=null) {
                        count++;
                        cls.add(ls.get(i));
                        xx.put(Tl.get(i).getTiku_id(), duoxuan[i]);//当前题库id  与选项
                        Tll.add(Tl.get(i));
                        System.out.println(i+"收藏位置");
                    }
                }
            }
        }
        // 初始化视图列表
        for (int i = 0; i < count; i++) {
            View view = LayoutInflater.from(this).inflate(pics[0], null);
            views.add(view);
        }
        vp = (ViewPager) findViewById(R.id.vp_guide);
        // 初始化adapter
        if(type.equals("shoucang")) {
            adapter = new GuideViewPagerAdapter(getApplicationContext(), views, Tll, ls,xx , type);
            danan.setText("收藏本");
        }else {
            adapter = new GuideViewPagerAdapter(getApplicationContext(), views, Tll, cls, xx,  type);
        }
        vp.setAdapter(adapter);
        vp.setOnPageChangeListener(new PageChangeListener());
        currentIndex = 0;
        textView.setText("第"+1+"题"+"/共"+count+"题");
    }

    /**
     * 设置当前view
     *
     * @param position
     */
    private void setCurView(int position) {
        if (position < 0 || position >= count) {
            return;
        }
        vp.setCurrentItem(position);
        vp.findViewWithTag(position);
    }
    /**
     * 设置当前题号
     *
     * @param position
     */
    private void setCurDot(int position) {
        if (position < 0 || position > count ) {
            return;
        }
        currentIndex = position+1;
        textView.setText("第"+currentIndex+"题"+"/共"+count+"题");
    }
    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }
    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        // 当滑动状态改变时调用
        @Override
        public void onPageScrollStateChanged(int position) {
            // arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。
        }
        // 当前页面被滑动时调用
        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {
            // arg0 :当前页面，及你点击滑动的页面
            // arg1:当前页面偏移的百分比
            // arg2:当前页面偏移的像素位置
        }
        @Override
        public void onPageSelected(int position) {
            setCurDot(position);//改变题号
        }
    }

}
