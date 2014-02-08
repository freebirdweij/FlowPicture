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
import android.view.Menu;
import android.view.View;

import com.origamilabs.library.views.StaggeredGridView;
import com.weij.pic.flowpicture.R;
import com.weij.pic.flowpicture.views.CircleViewFlow;
import com.weij.pic.flowpicture.views.ImageViewFlipper;

public class MainActivity extends Activity {

	int time = 5;
	private StaggeredGridView gview;
	public String[] urls;
	public Integer[] imags;
	public ArrayList<Integer> imaglist = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gview = (StaggeredGridView) findViewById(R.id.gridView1);
		int margin = getResources().getDimensionPixelSize(R.dimen.margin);

		gview.setItemMargin(margin); // set the GridView margin

		gview.setPadding(margin, 0, margin, 0); // have the margin on the sides
												// as well

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
		
		String[] urlst = new String[6];
		urlst[0] = urls[0];
		urlst[1] = urls[1];
		urlst[2] = urls[2];
		urlst[3] = urls[3];
		urlst[4] = urls[4];
		urlst[5] = urls[5];

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

}
