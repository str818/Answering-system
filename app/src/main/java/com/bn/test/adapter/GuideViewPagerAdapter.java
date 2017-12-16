package com.bn.test.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.bn.test.R;
import com.bn.test.bean.Tiku;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GuideViewPagerAdapter extends PagerAdapter {
	private List<View> views;
	ArrayList<Tiku>Tl;
	ArrayList<String>ls;   					//正确错误答案
	Map<String,String[] >xx	;				//具体项
	String type;
	Context context;
	public JiexiAdapter datiAdapter;
	private LinkedList<View> mViewCache = null;  //缓存view
	public GuideViewPagerAdapter(Context context, List<View> views, ArrayList<Tiku>Tl, ArrayList<String>ls, Map<String,String[] >xx, String type) {
		super();
		this.views = views;
		this.Tl=Tl;
		this.ls=ls;
		this.xx=xx;
		this.type=type;
		this.context=context;
		this.mViewCache = new LinkedList<>();
	}

	@Override
	public int getCount() {
		if (views != null) {
			return views.size();
		}
		return 0;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		View contentView = (View) object;
		container.removeView(contentView);
		this.mViewCache.add(contentView);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ViewHodler viewHodler=null;
		View convertView = null;
		if(mViewCache.size() == 0){
			convertView = LayoutInflater.from(context).inflate(R.layout.jiexi_view ,
					null ,false);
			viewHodler=new ViewHodler();
			viewHodler.gview=(ListView) convertView.findViewById(R.id.gview);
			viewHodler.TextView_timu=(TextView)convertView.findViewById(R.id.TextView_timu);
			viewHodler.imageview_timuleixing=(ImageView)convertView.findViewById(R.id.imageview_timuleixing);
			viewHodler.textview_daanxuanxiang=(TextView)convertView.findViewById(R.id.textview_daanxuanxiang);
			convertView.setTag(viewHodler);
		}else {
			convertView = mViewCache.removeFirst();
			viewHodler = (ViewHodler)convertView.getTag();
		}
		datiAdapter = new JiexiAdapter(context, Tl ,ls,xx,type,position);
		viewHodler.gview.setAdapter(datiAdapter);
		setHeight(datiAdapter,viewHodler);
		viewHodler.TextView_timu.setText(Tl.get(position).getTiku_name());
		viewHodler.textview_daanxuanxiang.setText(Tl.get(position).getTiku_daan());
		container.addView(convertView ,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT );
		return convertView;
	}
  class  ViewHodler {
	  ListView gview;
	  TextView TextView_timu;
	  ImageView imageview_timuleixing;
	  TextView textview_daanxuanxiang;
  }
	public void setHeight( JiexiAdapter datiAdapter,ViewHodler viewHodler){
		int height = 0;
		int count = datiAdapter  .getCount();
		for(int i=0;i<count;i++){
			View temp = datiAdapter.getView(i,null,viewHodler.gview);
			temp.measure(0,0);
			height += temp.getMeasuredHeight()+count*20;    //每条item高度加上分解线高度
		}
		ViewGroup.LayoutParams params = viewHodler.gview.getLayoutParams();
		params.width = ViewGroup.LayoutParams.FILL_PARENT;
		params.height = height;
		viewHodler.gview.setLayoutParams(params);
	}
}
