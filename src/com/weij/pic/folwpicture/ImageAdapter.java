/**
 * 
 */
package com.weij.pic.folwpicture;

import com.weij.pic.flowpicture.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * @author weij
 *
 */
public class ImageAdapter extends BaseAdapter {

	 private Context mContext;
	 
	 public ImageAdapter(Context c)
	 {
	  mContext=c;
	 }
	 @Override
	 public int getCount() {
	  // TODO Auto-generated method stub
	  return mThumbIds.length;
	 }

	 @Override
	 public Object getItem(int position) {
	  // TODO Auto-generated method stub
	  return null;
	 }

	 @Override
	 public long getItemId(int position) {
	  // TODO Auto-generated method stub
	  return 0;
	 }

	 

	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {
	  // TODO Auto-generated method stub
	  
	  ImageView imageview;
	  if(convertView==null)
	  {
	   imageview=new ImageView(mContext);
	   imageview.setLayoutParams(new GridView.LayoutParams(100, 120));
	   imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
	   imageview.setPadding(1,1,1,1);
	 }
	  else
	  {
	   imageview=(ImageView) convertView;
	  }
	  imageview.setImageResource(mThumbIds[position]);
	  return imageview;
	  }

	 private Integer[] mThumbIds={//��ʾ��ͼƬ����
	  
	  R.drawable.f3214421_0,
	  R.drawable.a_0508260010,
	  R.drawable.a_0508260100,
	  R.drawable.a_0508260120,
	  R.drawable.a_0508260141,
	  R.drawable.a_0508260202,
	  R.drawable.a_0508260238,
	  R.drawable.a_0508260320,
        R.drawable.a_0508260339,
        R.drawable.a_0508260401,
        R.drawable.a_0508260422,
        R.drawable.a_0508260444,
        R.drawable.a_0508260506,
        R.drawable.a_0508260527,
        R.drawable.a_0508260551,
        R.drawable.a_0508260612,
        R.drawable.a_0508260729,
        R.drawable.a_0508260743,
        R.drawable.a_0508260755,
        R.drawable.a_0508260800,
        R.drawable.a_0508260821,
        R.drawable.a_0508260825,
        R.drawable.a_0508260846,
        R.drawable.a_0508260905,
        R.drawable.a_0508260934,
        R.drawable.a_0508261003,
        R.drawable.a_0508261644,
        R.drawable.a_0508262020,
        R.drawable.a_0508262045,
        R.drawable.a_0508262111,
        R.drawable.a_0508262140,
        R.drawable.a_0508262205,
        R.drawable.a_0508262226,
        R.drawable.a_0508262245,
        R.drawable.a_0508262301,
        R.drawable.a_0508262343,
        R.drawable.a_0508262437,
        R.drawable.a_0508263735,
        R.drawable.a_0508264502,
        R.drawable.a_0508264522,
        R.drawable.a_0508264602,
        R.drawable.a_0508264711,
        R.drawable.a_0508264833,
        R.drawable.a_0508264907,
        R.drawable.a_0508265147,
        R.drawable.a_0508265700,
        R.drawable.a_0508265819,
        R.drawable.a_0508265841,
        R.drawable.a_0508265901,
        R.drawable.a_0508265930,
        R.drawable.a_0508272455,
        R.drawable.a_0508272550,
        R.drawable.a_0508272633,
        R.drawable.a_0508272718,
        R.drawable.a_0508272756,
        R.drawable.a_0508272856,
        R.drawable.a_0508272941,
        R.drawable.a_0508273019,
        R.drawable.a_0508273311,
        R.drawable.a_0508273338,
        R.drawable.a_0508273433,
        R.drawable.a_0508273508,
        R.drawable.a_0508273557,
        R.drawable.a_0508273658,
        R.drawable.a_0508274007,
        R.drawable.a_0508274109,
        R.drawable.a_0508274139,
        R.drawable.a_0508274211,
        R.drawable.a_0508274350,
        R.drawable.a_0508274438,
        R.drawable.a_0508274625,
        R.drawable.a_0508274707,
        R.drawable.a_0508274741,
        R.drawable.a_0508274819,
        R.drawable.a_0508274907,
        R.drawable.a_0508275045,
        R.drawable.a_0508275127,
        R.drawable.a_0508275639,
        R.drawable.a_0508275732,
        R.drawable.a_0508275904,
	 };
	 }
