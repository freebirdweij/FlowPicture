package com.weij.pic.flowpicture;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.origamilabs.library.views.StaggeredGridView;
import com.weij.pic.flowpicture.R;
import com.weij.pic.flowpicture.views.ImageViewFlipper;

public class MainActivity extends Activity {

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.gc();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		imaglist = null;
		System.gc();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		imaglist = null;
		System.gc();
	}

	public ArrayList<String> imaglist = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		StaggeredGridView gview = (StaggeredGridView) findViewById(R.id.gridView1);
		int margin = getResources().getDimensionPixelSize(R.dimen.margin);

		gview.setItemMargin(margin); // set the GridView margin

		gview.setPadding(margin, 0, margin, 0); // have the margin on the sides
												// as well

		Field[] fields = R.raw.class.getDeclaredFields();
	    imaglist = new ArrayList<String>();
		for (Field field : fields) {
			
				try {
					imaglist.add(String.valueOf(field.getInt(field.getName())));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		String[] urls = new String[imaglist.size()];
		urls = imaglist.toArray(urls);

		// DisplayMetrics dm = getResources().getDisplayMetrics();
		// gview.setLayoutParams(new
		// RelativeLayout.LayoutParams(dm.widthPixels,LayoutParams.MATCH_PARENT));
		// gview.setColumnCount(2);

		StaggeredAdapter adapter = new StaggeredAdapter(MainActivity.this, R.id.imageView1, urls);

		gview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		System.gc();

		gview.setOnItemClickListener(new StaggeredGridView.OnItemClickListener() {

			@Override
			public void onItemClick(StaggeredGridView parent, View view,
					int position, long id) {
				Intent intent = new Intent(MainActivity.this,
						ImageViewFlipper.class);
				Bundle bundle = new Bundle();
				SharedPreferences indexPrefs = getSharedPreferences(
						"currentIndex", MODE_PRIVATE);

				bundle.putStringArrayList("imaglist", imaglist);
				intent.putExtras(bundle);
				SharedPreferences.Editor indexEditor = indexPrefs.edit();
				indexEditor.putInt("currentIndex", position);
				indexEditor.commit();
				startActivity(intent);
				System.gc();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
