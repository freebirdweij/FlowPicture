package com.weij.pic.folwpicture;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.RadioGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.origamilabs.library.views.StaggeredGridView;
import com.weij.pic.flowpicture.R;
import com.weij.pic.flowpicture.views.CircleViewFlow;
import com.weij.pic.flowpicture.views.ImageViewFlipper;

public class MainActivity extends Activity implements OnTouchListener,OnGestureListener{

	int time = 5;
	private StaggeredGridView gview;
	public String[] urls;
	public Integer[] imags;
	public ArrayList<Integer> imaglist = new ArrayList<Integer>();
	private GestureDetector mGestureDetector;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gview = (StaggeredGridView) findViewById(R.id.gridView1);
		int margin = getResources().getDimensionPixelSize(R.dimen.margin);

		gview.setItemMargin(margin); // set the GridView margin

		gview.setPadding(margin, 0, margin, 0); // have the margin on the sides
												// as well
		//HorizontalScrollView hview = (HorizontalScrollView) gview.getParent().getParent();
		//hview.setOnTouchListener(this);
		gview.setOnTouchListener(this);
		//gview.setFocusable(true);  
		  
		gview.setClickable(true);  
  
		//gview.setLongClickable(true);  
  
        //mGestureDetector.setIsLongpressEnabled(true);
		

		Field[] fields = R.drawable.class.getDeclaredFields();
		List<String> list = new ArrayList<String>();
		for (Field field : fields) {
			if (!"ic_launcher".equals(field.getName())&&!"icon".equals(field.getName())&&!"wait".equals(field.getName())) {
				try {
					list.add(String.valueOf(field.getInt(field.getName())));
					imaglist.add(field.getInt(field.getName()));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		urls = new String[list.size()];
		urls = list.toArray(urls);
		
		String[] urlst = new String[8];
		urlst[0] = urls[0];
		urlst[1] = urls[1];
		urlst[2] = urls[2];
		urlst[3] = urls[3];
		urlst[4] = urls[4];
		urlst[5] = urls[5];
		urlst[6] = urls[6];
		urlst[7] = urls[7];
		DisplayMetrics dm = getResources().getDisplayMetrics();
		gview.setLayoutParams(new RelativeLayout.LayoutParams(dm.widthPixels,LayoutParams.MATCH_PARENT));
		gview.setColumnCount(2);

		StaggeredAdapter adapter = new StaggeredAdapter(MainActivity.this,
				R.id.imageView1, urlst);
		gview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		gview.setOnItemClickListener(new StaggeredGridView.OnItemClickListener() {

			@Override
			public void onItemClick(StaggeredGridView parent, View view,
					int position, long id) {
				Intent intent = new Intent(MainActivity.this,
						ImageViewFlipper.class);
				Bundle bundle = new Bundle();
				SharedPreferences indexPrefs = getSharedPreferences("currentIndex",
						MODE_PRIVATE);
				
				bundle.putIntegerArrayList("imaglist", imaglist);
				intent.putExtras(bundle);
				SharedPreferences.Editor indexEditor = indexPrefs.edit();
				indexEditor.putInt("currentIndex", position);
				indexEditor.commit();
				startActivity(intent);
			}
		});

	}

	public MainActivity() {
		mGestureDetector = new GestureDetector(this);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	/**
     * 以最省内存的方式读取本地资源的图片
	 * @param context
	 * @param resId
	 * @return
	 */  
	 public static Bitmap readBitMap(Context context, int resId){  
	   BitmapFactory.Options opt = new BitmapFactory.Options();  
	   opt.inPreferredConfig = Bitmap.Config.RGB_565;   
	  opt.inPurgeable = true;  
	  opt.inInputShareable = true;  
	  //获取资源图片  
	  InputStream is = context.getResources().openRawResource(resId);  
	   return BitmapFactory.decodeStream(is,null,opt);  
	  }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return mGestureDetector.onTouchEvent(event);
	}

	// 用户轻触触摸屏，由1个MotionEvent ACTION_DOWN触发 
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		Log.i("MyGesture", "onDown");  
		  
        //Toast.makeText(this, "onDown", Toast.LENGTH_SHORT).show();  
  
        return true;  
	}

	// 用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE, 1个ACTION_UP触发 
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		// 参数解释：  
		  
        // e1：第1个ACTION_DOWN MotionEvent  
  
        // e2：最后一个ACTION_MOVE MotionEvent  
  
        // velocityX：X轴上的移动速度，像素/秒  
  
        // velocityY：Y轴上的移动速度，像素/秒  
  
        // 触发条件 ：  
  
        // X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒  
		Toast.makeText(this, "Fling", Toast.LENGTH_LONG).show();
  
        final int FLING_MIN_DISTANCE = 10, FLING_MIN_VELOCITY = 200;  
  
        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE  
                /*&& Math.abs(velocityX) > FLING_MIN_VELOCITY*/)  
  
        {  
  
            // Fling left  
  
            Log.i("MyGesture", "Fling left");  
  
            Toast.makeText(this, "Fling Left", Toast.LENGTH_SHORT).show();  
  
        }  
  
        else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE  
                /*&& Math.abs(velocityX) > FLING_MIN_VELOCITY*/)  
  
        {  
  
            // Fling right  
  
            Log.i("MyGesture", "Fling right");  
  
            Toast.makeText(this, "Fling Right", Toast.LENGTH_SHORT).show();  
  
        }  
  
        return true;  
	}

	 // 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发 
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		Log.i("MyGesture", "onLongPress");  
		  
        Toast.makeText(this, "onLongPress", Toast.LENGTH_SHORT).show();
        
	}

	// 用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发 
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		 Log.i("MyGesture", "onScroll");  
		  
	        Toast.makeText(this, "onScroll", Toast.LENGTH_SHORT).show();  
	  
	        return false; 
	}

	/*        
     * * 用户轻触触摸屏，尚未松开或拖动，由一个1个MotionEvent ACTION_DOWN触发 * 
     * 注意和onDown()的区别，强调的是没有松开或者拖动的状态 
     */ 
	@Override
	public void onShowPress(MotionEvent e) {
		 Log.i("MyGesture", "onShowPress");  
		  
	        Toast.makeText(this, "onShowPress", Toast.LENGTH_SHORT).show();  
	}

	// 用户（轻触触摸屏后）松开，由一个1个MotionEvent ACTION_UP触发 
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		Log.i("MyGesture", "onSingleTapUp");  
		  
        Toast.makeText(this, "onSingleTapUp", Toast.LENGTH_SHORT).show();  
  
        return false;
	}


}
